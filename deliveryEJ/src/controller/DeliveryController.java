package controller;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.InboundVO;
import model.MemberVO;
import model.OutboundVO;

public class DeliveryController implements Initializable {
	@FXML
	private Button btnSearchPkgNo;

	@FXML
	private TextField txtPkgNo;
	@FXML
	private Button btnPickup;

	@FXML
	private TableView<InboundVO> tableViewInbound;

	@FXML
	private Button btnApproval;
	@FXML
	private ImageView btnHome;

	@FXML
	private TableView<OutboundVO> tableViewOutbound;
	// �����Ҷ��� ��ư �������� ����
	private boolean editDelete = false;
	private DeliveryDAO deliveryDAO = null;
	// ���̺�並 ���������� ��ġ���� ��ü���� ������ �� �ִ� ���� ����
	private int selectedInboundIndex;
	private int selectedOutboundIndex;
	private ObservableList<InboundVO> selectInboundVO;
	private ObservableList<OutboundVO> selectOutboundVO;
	ObservableList<InboundVO> dataInbound = null; // ���̺� �信 �����ϱ� ���� ������
	ObservableList<OutboundVO> dataOutbound = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ���̺� �� ����
		tableViewInboundSetting();
		tableViewOutboundSetting();
		// �����ȣ �˻�- ��ư Ŭ��
		btnSearchPkgNo.setOnAction(e -> {
			handlerBtnSearchPkgNoAction();
		});
		// �����ȣ �˻�- ����Ű
		btnSearchPkgNo.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				handlerBtnSearchPkgNoAction();
			}
		});
		// �ιٿ�� ���̺� �� Ŭ��
		tableViewInbound.setOnMousePressed((e) -> {
			handlerInboundTableViewPressedAction(e);
		});
		// �ƿ��ٿ�� ���̺� �� Ŭ��
		tableViewOutbound.setOnMousePressed((e) -> {
			handlerOutboundTableViewPressedAction(e);
		});
		// �Ⱦ������ ��ư Ŭ��
		btnPickup.setOnAction(e -> {
			handlerBtnPickupAction();
		});
		// ���� ��ư Ŭ��
		btnApproval.setOnAction(e -> {
			handlerBtnApprovalAction();
		});
		// Ȩ��ư Ŭ��
		btnHome.setOnMouseClicked((e) -> {
			handlerBtnExitAction(e);
		});
		// ��ü����Ʈ
		inboundTotalList();
		outboundTotalList();

	}// end of initialize

	// �Ⱦ������ ��ü�� �ҷ����� ����Ʈ
	public void pickupTotalList() {
		ArrayList<OutboundVO> list = null;
		DeliveryDAO deliveryDAO = new DeliveryDAO();
		OutboundVO OutboundVO = null;
		try {
			list = deliveryDAO.getPickupPkgCheck();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list == null) {
			Utility.alertDisplay(1, "���", "DB �������� ����", "���� ���");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			OutboundVO = list.get(i);
			dataOutbound.add(OutboundVO);
		}

	}

	// �Ⱦ� ����� ��ư Ŭ�� �̺�Ʈ
	private void handlerBtnPickupAction() {
		try {
			dataOutbound.removeAll(dataOutbound);
			pickupTotalList();
		} catch (Exception e1) {
			Utility.alertDisplay(1, "��ü����Ʈ ����", " ��ü����Ʈ ���� �߻�", e1.toString());
		}
	}

	// ���� ��ư Ŭ�� �̺�Ʈ
	private void handlerBtnApprovalAction() {
		try {
			selectedOutboundIndex = tableViewOutbound.getSelectionModel().getSelectedIndex();
			selectOutboundVO = tableViewOutbound.getSelectionModel().getSelectedItems();
			OutboundVO ovo = new OutboundVO(selectOutboundVO.get(0).getTrkNum(), selectOutboundVO.get(0).getPkgType(),
					selectOutboundVO.get(0).getWeight(), selectOutboundVO.get(0).getMemID(),
					selectOutboundVO.get(0).getReceiverName(), selectOutboundVO.get(0).getReceiverAddress(),
					selectOutboundVO.get(0).getPostCode(), selectOutboundVO.get(0).getReceiverPhone(),
					selectOutboundVO.get(0).getPayMethod(), selectOutboundVO.get(0).getBookDate(),
					selectOutboundVO.get(0).getBookTime(), selectOutboundVO.get(0).getBookSpot(),
					selectOutboundVO.get(0).getCost(), selectOutboundVO.get(0).getApproval());
			DeliveryDAO deliveryDAO = new DeliveryDAO();
			OutboundVO outboundVO = deliveryDAO.getApprovalUpdate(ovo, selectOutboundVO.get(0).getTrkNum());

			if (editDelete == true && ovo != null) {
				dataOutbound.remove(selectedOutboundIndex);
				// dataOutbound.add(selectedOutboundIndex, ovo);
				editDelete = false;
			} else {
				throw new Exception("���� ��� ����");
			}

		} catch (Exception e2) {
			Utility.alertDisplay(1, "���� ����", "���ο� �����߽��ϴ�", "�ٽ� �õ����ּ���" + e2.getMessage());
		}
	} // end of handlerBtnApprovalAction

	// Ȩ��ư Ŭ�� �̺�Ʈ
	private void handlerBtnExitAction(MouseEvent e) {
		((Stage) btnHome.getScene().getWindow()).close();
	}
	
	// �ƿ��ٿ�� ���̺� �� ����
	private void tableViewOutboundSetting() {
		dataOutbound = FXCollections.observableArrayList();
		tableViewOutbound.setEditable(false);
		// ��۰��� ���̺� �÷� ����
		TableColumn colTrkNum = new TableColumn("�����ȣ");
		colTrkNum.setCellValueFactory(new PropertyValueFactory("trkNum"));
		TableColumn colPkgType = new TableColumn("ǰ��");
		colPkgType.setCellValueFactory(new PropertyValueFactory("pkgType"));
		TableColumn colWeight = new TableColumn("����");
		colWeight.setCellValueFactory(new PropertyValueFactory("weight"));
		TableColumn colMemID = new TableColumn("ȸ����ȣ");
		colMemID.setCellValueFactory(new PropertyValueFactory("memID"));
		TableColumn colReceiverName = new TableColumn("�޴� ��");
		colReceiverName.setCellValueFactory(new PropertyValueFactory("receiverName"));
		TableColumn colReceiverAddress = new TableColumn("�޴º� �ּ�");
		colReceiverAddress.setCellValueFactory(new PropertyValueFactory("receiverAddress"));
		TableColumn colPostCode = new TableColumn("�����ȣ");
		colPostCode.setCellValueFactory(new PropertyValueFactory("postCode"));
		TableColumn colReceiverPhone = new TableColumn("�޴º� ����ó");
		colReceiverPhone.setCellValueFactory(new PropertyValueFactory("receiverPhone"));
		TableColumn colPayMethod = new TableColumn("��/��");
		colPayMethod.setCellValueFactory(new PropertyValueFactory("payMethod"));
		TableColumn colBookDate = new TableColumn("�Ⱦ�������");
		colBookDate.setCellValueFactory(new PropertyValueFactory("bookDate"));
		TableColumn colBooktime = new TableColumn("�Ⱦ��ð�");
		colBooktime.setCellValueFactory(new PropertyValueFactory("bookTime"));
		TableColumn colSpot = new TableColumn("�Ⱦ����");
		colSpot.setCellValueFactory(new PropertyValueFactory("bookSpot"));
		TableColumn colCost = new TableColumn("����");
		colCost.setCellValueFactory(new PropertyValueFactory("cost"));
		TableColumn colApproval = new TableColumn("����");
		colApproval.setCellValueFactory(new PropertyValueFactory("approval"));

		tableViewOutbound.setItems(dataOutbound);
		tableViewOutbound.getColumns().addAll(colTrkNum, colPkgType, colWeight, colMemID, colReceiverName,
				colReceiverAddress, colPostCode, colReceiverPhone, colBookDate, colBooktime, colSpot, colPayMethod,
				colCost, colApproval);

	}
	
	// �ιٿ�� ���̺�� ���� 
	private void tableViewInboundSetting() {
		dataInbound = FXCollections.observableArrayList();
		tableViewInbound.setEditable(false);
		// ��۰��� ���̺� �÷� ����
		TableColumn colTrkNum = new TableColumn("�����ȣ");
		colTrkNum.setCellValueFactory(new PropertyValueFactory("trkNum"));
		TableColumn colPkgType = new TableColumn("ǰ��");
		colPkgType.setCellValueFactory(new PropertyValueFactory("pkgType"));
		TableColumn colWeight = new TableColumn("����");
		colWeight.setCellValueFactory(new PropertyValueFactory("weight"));
		TableColumn colMemID = new TableColumn("ȸ����ȣ");
		colMemID.setCellValueFactory(new PropertyValueFactory("memID"));
		TableColumn colSendName = new TableColumn("�����º�");
		colSendName.setCellValueFactory(new PropertyValueFactory("sendName"));
		TableColumn colSendAddress = new TableColumn("�����º� �ּ�");
		colSendAddress.setCellValueFactory(new PropertyValueFactory("sendAddress"));
		TableColumn colSendPhone = new TableColumn("������ �� ����ó");
		colSendPhone.setCellValueFactory(new PropertyValueFactory("sendPhone"));
		TableColumn colPostCode = new TableColumn("�����ȣ");
		colPostCode.setCellValueFactory(new PropertyValueFactory("postCode"));
		TableColumn colStartDate = new TableColumn("�����");
		colStartDate.setCellValueFactory(new PropertyValueFactory("startDate"));
		TableColumn colStartTime = new TableColumn("��۽ð�");
		colStartTime.setCellValueFactory(new PropertyValueFactory("startTime"));
		tableViewInbound.setItems(dataInbound);
		tableViewInbound.getColumns().addAll(colTrkNum, colPkgType, colWeight, colMemID, colSendName, colSendAddress,
				colSendPhone, colPostCode, colStartDate, colStartTime);
	}
	
	// �ιٿ�� ���̺��� ����
	public void handlerInboundTableViewPressedAction(MouseEvent e) {
		// ���̺� �並 Ŭ�� ���� �� ��ġ�� �׿� �ش�� ��ü�� �����´�
		dataInbound.removeAll(dataInbound);
		inboundTotalList();
		try {
			editDelete = true;
			selectedInboundIndex = tableViewInbound.getSelectionModel().getSelectedIndex();
			selectInboundVO = tableViewInbound.getSelectionModel().getSelectedItems();
		} catch (Exception e2) { // ���̺�� ���� ���������� Ŭ������ ��� nullpointerException
			editDelete = false;
		}
	}
	// �ƿ��ٿ�� ���̺��� ����
	public void handlerOutboundTableViewPressedAction(MouseEvent e) {
		// ���̺� �並 Ŭ�� ���� �� ��ġ�� �׿� �ش�� ��ü�� �����´�
		// dataOutbound.removeAll(dataOutbound);
		// outboundTotalList();
		try {
			editDelete = true;
			selectedOutboundIndex = tableViewOutbound.getSelectionModel().getSelectedIndex();
			selectOutboundVO = tableViewOutbound.getSelectionModel().getSelectedItems();
		} catch (Exception e2) { // ���̺�� ���� ���������� Ŭ������ ��� nullpointerException
			editDelete = false;
		}
	}

	// �ιٿ�� ��Ż����Ʈ
	public void inboundTotalList() {
		ArrayList<InboundVO> list = null;
		DeliveryDAO deliveryDAO = new DeliveryDAO();
		InboundVO inboundVO = null;
		list = deliveryDAO.getInboundTotal();
		if (list == null) {
			Utility.alertDisplay(1, "���", "DB �������� ����", "���� ���");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			inboundVO = list.get(i);
			dataInbound.add(inboundVO);
		}
	}

	// �ƿ��ٿ�� ��Ż����Ʈ
	public void outboundTotalList() {
		ArrayList<OutboundVO> list = null;
		DeliveryDAO deliveryDAO = new DeliveryDAO();
		OutboundVO outboundVO = null;
		list = deliveryDAO.getOutboundTotal();
		if (list == null) {
			Utility.alertDisplay(1, "���", "DB �������� ����", "���� ���");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			outboundVO = list.get(i);
			dataOutbound.add(outboundVO);
		}
	}
	
	// �����ȣ �˻� �̺�Ʈ
	private void handlerBtnSearchPkgNoAction() {
		try {
			InboundVO inlist = new InboundVO();
			OutboundVO outlist = new OutboundVO();
			DeliveryDAO deliveryDAO = new DeliveryDAO();
			inlist = deliveryDAO.getInboundPkgCheck(Integer.parseInt(txtPkgNo.getText()));
			outlist = deliveryDAO.getOutboundPkgCheck(Integer.parseInt(txtPkgNo.getText()));

			if (inlist == null && outlist != null) {
				dataOutbound.removeAll(dataOutbound);
				dataOutbound.add(outlist);
			} else if (outlist == null && inlist != null) {
				dataInbound.removeAll(dataInbound);
				dataInbound.add(inlist);
			} else {
				throw new Exception("�˻�����");
			}
		} catch (Exception e1) {
			Utility.alertDisplay(1, "�˻� ���", "*******�����ȣ �˻� ����", e1.toString());
			e1.printStackTrace();
		}

	}// end of handlerBtnSearchPkgNoAction

}// end of DeliveryController
