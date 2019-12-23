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
	// 수정할때의 버튼 설정상태 결정
	private boolean editDelete = false;
	private DeliveryDAO deliveryDAO = null;
	// 테이블뷰를 선택했을때 위치값과 객체값을 저장할 수 있는 변수 선언
	private int selectedInboundIndex;
	private int selectedOutboundIndex;
	private ObservableList<InboundVO> selectInboundVO;
	private ObservableList<OutboundVO> selectOutboundVO;
	ObservableList<InboundVO> dataInbound = null; // 테이블 뷰에 저장하기 위한 데이터
	ObservableList<OutboundVO> dataOutbound = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 테이블 뷰 셋팅
		tableViewInboundSetting();
		tableViewOutboundSetting();
		// 송장번호 검색- 버튼 클릭
		btnSearchPkgNo.setOnAction(e -> {
			handlerBtnSearchPkgNoAction();
		});
		// 송장번호 검색- 엔터키
		btnSearchPkgNo.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				handlerBtnSearchPkgNoAction();
			}
		});
		// 인바운드 테이블 뷰 클릭
		tableViewInbound.setOnMousePressed((e) -> {
			handlerInboundTableViewPressedAction(e);
		});
		// 아웃바운드 테이블 뷰 클릭
		tableViewOutbound.setOnMousePressed((e) -> {
			handlerOutboundTableViewPressedAction(e);
		});
		// 픽업예상건 버튼 클릭
		btnPickup.setOnAction(e -> {
			handlerBtnPickupAction();
		});
		// 승인 버튼 클릭
		btnApproval.setOnAction(e -> {
			handlerBtnApprovalAction();
		});
		// 홈버튼 클릭
		btnHome.setOnMouseClicked((e) -> {
			handlerBtnExitAction(e);
		});
		// 전체리스트
		inboundTotalList();
		outboundTotalList();

	}// end of initialize

	// 픽업예약건 전체만 불러오는 리스트
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
			Utility.alertDisplay(1, "경고", "DB 가져오기 오류", "점검 요망");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			OutboundVO = list.get(i);
			dataOutbound.add(OutboundVO);
		}

	}

	// 픽업 예약건 버튼 클릭 이벤트
	private void handlerBtnPickupAction() {
		try {
			dataOutbound.removeAll(dataOutbound);
			pickupTotalList();
		} catch (Exception e1) {
			Utility.alertDisplay(1, "전체리스트 오류", " 전체리스트 오류 발생", e1.toString());
		}
	}

	// 승인 버튼 클릭 이벤트
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
				throw new Exception("수정 등록 오류");
			}

		} catch (Exception e2) {
			Utility.alertDisplay(1, "승인 실패", "승인에 실패했습니다", "다시 시도해주세요" + e2.getMessage());
		}
	} // end of handlerBtnApprovalAction

	// 홈버튼 클릭 이벤트
	private void handlerBtnExitAction(MouseEvent e) {
		((Stage) btnHome.getScene().getWindow()).close();
	}
	
	// 아웃바운드 테이블 뷰 셋팅
	private void tableViewOutboundSetting() {
		dataOutbound = FXCollections.observableArrayList();
		tableViewOutbound.setEditable(false);
		// 배송관리 테이블 컬럼 셋팅
		TableColumn colTrkNum = new TableColumn("송장번호");
		colTrkNum.setCellValueFactory(new PropertyValueFactory("trkNum"));
		TableColumn colPkgType = new TableColumn("품명");
		colPkgType.setCellValueFactory(new PropertyValueFactory("pkgType"));
		TableColumn colWeight = new TableColumn("무게");
		colWeight.setCellValueFactory(new PropertyValueFactory("weight"));
		TableColumn colMemID = new TableColumn("회원번호");
		colMemID.setCellValueFactory(new PropertyValueFactory("memID"));
		TableColumn colReceiverName = new TableColumn("받는 분");
		colReceiverName.setCellValueFactory(new PropertyValueFactory("receiverName"));
		TableColumn colReceiverAddress = new TableColumn("받는분 주소");
		colReceiverAddress.setCellValueFactory(new PropertyValueFactory("receiverAddress"));
		TableColumn colPostCode = new TableColumn("우편번호");
		colPostCode.setCellValueFactory(new PropertyValueFactory("postCode"));
		TableColumn colReceiverPhone = new TableColumn("받는분 연락처");
		colReceiverPhone.setCellValueFactory(new PropertyValueFactory("receiverPhone"));
		TableColumn colPayMethod = new TableColumn("선/착");
		colPayMethod.setCellValueFactory(new PropertyValueFactory("payMethod"));
		TableColumn colBookDate = new TableColumn("픽업예약일");
		colBookDate.setCellValueFactory(new PropertyValueFactory("bookDate"));
		TableColumn colBooktime = new TableColumn("픽업시간");
		colBooktime.setCellValueFactory(new PropertyValueFactory("bookTime"));
		TableColumn colSpot = new TableColumn("픽업장소");
		colSpot.setCellValueFactory(new PropertyValueFactory("bookSpot"));
		TableColumn colCost = new TableColumn("운임");
		colCost.setCellValueFactory(new PropertyValueFactory("cost"));
		TableColumn colApproval = new TableColumn("승인");
		colApproval.setCellValueFactory(new PropertyValueFactory("approval"));

		tableViewOutbound.setItems(dataOutbound);
		tableViewOutbound.getColumns().addAll(colTrkNum, colPkgType, colWeight, colMemID, colReceiverName,
				colReceiverAddress, colPostCode, colReceiverPhone, colBookDate, colBooktime, colSpot, colPayMethod,
				colCost, colApproval);

	}
	
	// 인바운드 테이블뷰 셋팅 
	private void tableViewInboundSetting() {
		dataInbound = FXCollections.observableArrayList();
		tableViewInbound.setEditable(false);
		// 배송관리 테이블 컬럼 셋팅
		TableColumn colTrkNum = new TableColumn("송장번호");
		colTrkNum.setCellValueFactory(new PropertyValueFactory("trkNum"));
		TableColumn colPkgType = new TableColumn("품명");
		colPkgType.setCellValueFactory(new PropertyValueFactory("pkgType"));
		TableColumn colWeight = new TableColumn("무게");
		colWeight.setCellValueFactory(new PropertyValueFactory("weight"));
		TableColumn colMemID = new TableColumn("회원번호");
		colMemID.setCellValueFactory(new PropertyValueFactory("memID"));
		TableColumn colSendName = new TableColumn("보내는분");
		colSendName.setCellValueFactory(new PropertyValueFactory("sendName"));
		TableColumn colSendAddress = new TableColumn("보내는분 주소");
		colSendAddress.setCellValueFactory(new PropertyValueFactory("sendAddress"));
		TableColumn colSendPhone = new TableColumn("보내는 분 연락처");
		colSendPhone.setCellValueFactory(new PropertyValueFactory("sendPhone"));
		TableColumn colPostCode = new TableColumn("우편번호");
		colPostCode.setCellValueFactory(new PropertyValueFactory("postCode"));
		TableColumn colStartDate = new TableColumn("배송일");
		colStartDate.setCellValueFactory(new PropertyValueFactory("startDate"));
		TableColumn colStartTime = new TableColumn("배송시간");
		colStartTime.setCellValueFactory(new PropertyValueFactory("startTime"));
		tableViewInbound.setItems(dataInbound);
		tableViewInbound.getColumns().addAll(colTrkNum, colPkgType, colWeight, colMemID, colSendName, colSendAddress,
				colSendPhone, colPostCode, colStartDate, colStartTime);
	}
	
	// 인바운드 테이블에서 선택
	public void handlerInboundTableViewPressedAction(MouseEvent e) {
		// 테이블 뷰를 클릭 했을 때 위치와 그에 해당된 개체를 가져온다
		dataInbound.removeAll(dataInbound);
		inboundTotalList();
		try {
			editDelete = true;
			selectedInboundIndex = tableViewInbound.getSelectionModel().getSelectedIndex();
			selectInboundVO = tableViewInbound.getSelectionModel().getSelectedItems();
		} catch (Exception e2) { // 테이블뷰 내의 여백지역을 클릭했을 경우 nullpointerException
			editDelete = false;
		}
	}
	// 아웃바운드 테이블에서 선택
	public void handlerOutboundTableViewPressedAction(MouseEvent e) {
		// 테이블 뷰를 클릭 했을 때 위치와 그에 해당된 개체를 가져온다
		// dataOutbound.removeAll(dataOutbound);
		// outboundTotalList();
		try {
			editDelete = true;
			selectedOutboundIndex = tableViewOutbound.getSelectionModel().getSelectedIndex();
			selectOutboundVO = tableViewOutbound.getSelectionModel().getSelectedItems();
		} catch (Exception e2) { // 테이블뷰 내의 여백지역을 클릭했을 경우 nullpointerException
			editDelete = false;
		}
	}

	// 인바운드 토탈리스트
	public void inboundTotalList() {
		ArrayList<InboundVO> list = null;
		DeliveryDAO deliveryDAO = new DeliveryDAO();
		InboundVO inboundVO = null;
		list = deliveryDAO.getInboundTotal();
		if (list == null) {
			Utility.alertDisplay(1, "경고", "DB 가져오기 오류", "점검 요망");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			inboundVO = list.get(i);
			dataInbound.add(inboundVO);
		}
	}

	// 아웃바운드 토탈리스트
	public void outboundTotalList() {
		ArrayList<OutboundVO> list = null;
		DeliveryDAO deliveryDAO = new DeliveryDAO();
		OutboundVO outboundVO = null;
		list = deliveryDAO.getOutboundTotal();
		if (list == null) {
			Utility.alertDisplay(1, "경고", "DB 가져오기 오류", "점검 요망");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			outboundVO = list.get(i);
			dataOutbound.add(outboundVO);
		}
	}
	
	// 송장번호 검색 이벤트
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
				throw new Exception("검색오류");
			}
		} catch (Exception e1) {
			Utility.alertDisplay(1, "검색 결과", "*******송장번호 검색 오류", e1.toString());
			e1.printStackTrace();
		}

	}// end of handlerBtnSearchPkgNoAction

}// end of DeliveryController
