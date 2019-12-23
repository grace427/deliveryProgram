package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MemberVO;

public class MemberController implements Initializable {
	@FXML
	private ImageView btnHome;
	@FXML
	private TextField txtMemID;
	@FXML
	private TextField txtMemName;
	@FXML
	private Button btnSearchID;
	@FXML
	private Button btnSearchName;
	@FXML
	private Button btnModify;
	@FXML
	private Button btnDelete;
	// �����Ҷ��� ��ư �������� ����
	private boolean editDelete = false;
	private MemberDAO memberDAO = null;
	@FXML
	private TableView<MemberVO> tableView = new TableView<>();

	// ���̺�並 ���������� ��ġ���� ��ü���� ������ �� �ִ� ���� ����
	private int selectedIndex;
	private ObservableList<MemberVO> selectMemberVO;
	ObservableList<MemberVO> dataMember = null; // ���̺� �信 �����ϱ� ���� ������

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 1. ��ư �ʱ�ȭ
		buttonInitSetting(false, false, false, false);
		// 2. ���̺�� ����
		tableViewSetting();

		// 3. ��ư(ȸ����ȣ �˻�, �̸� �˻�, ����, ����) ��Ʈ��
		// 3.1 �˻� - ȸ����ȣ
		btnSearchID.setOnAction(e -> handlerBtnSearchIDAction(e));
		// 3.1 �˻� - �̸�
		btnSearchName.setOnAction(e -> {
			handlerBtnSearchNameAction(e);
		});
		// 3.2 ����
		btnModify.setOnAction((e) -> {
			handlerBtnModifyAction(e);
		});
		// 3.3 ����
		btnDelete.setOnAction((e) -> {
			handlerBtnDeleteAction(e);
		});
		// ���̺�� Ŭ��
		tableView.setOnMousePressed((e) -> {
			handlerTableViewPressedAction(e);
		});
		// Ȩ��ư
		btnHome.setOnMouseClicked((e) -> {
			handlerBtnExitAction(e);
		});
		// ��ü����Ʈ
		totalList();
	} // end of initialize
	
	
	// Ȩ��ư
	private void handlerBtnExitAction(MouseEvent e) {
		((Stage) btnHome.getScene().getWindow()).close();		
	}

	// ȸ�� ��ü����Ʈ
	public void totalList() {
		ArrayList<MemberVO> list = null;
		MemberDAO memberDAO = new MemberDAO();
		MemberVO memberVO = null;
		list = memberDAO.getMemberTotal();
		if (list == null) {
			Utility.alertDisplay(1, "���", "DB �������� ����", "���� ���");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			memberVO = list.get(i);
			System.out.println(memberVO.getMemName());
			dataMember.add(memberVO);
		}
	}

	// 1. ������ ����â ��ư �ʱ�ȭ(ȸ������)
	public void buttonInitSetting(boolean c, boolean d, boolean e, boolean f) {
		btnModify.setDisable(c);
		btnDelete.setDisable(d);
		txtMemName.setDisable(e);
		txtMemID.setDisable(f);
	}

	// 2. ���̺�� ����
	public void tableViewSetting() {
		dataMember = FXCollections.observableArrayList();
		tableView.setEditable(false);

		TableColumn colMemID = new TableColumn("ȸ������");
		colMemID.setPrefWidth(130);
		colMemID.setStyle("-fx-alignment:CENTER");
		colMemID.setCellValueFactory(new PropertyValueFactory("memID"));

		TableColumn colMemName = new TableColumn("�̸�");
		colMemName.setPrefWidth(130);
		colMemName.setStyle("-fx-alignment:CENTER");
		colMemName.setCellValueFactory(new PropertyValueFactory("memName"));

		TableColumn colMemPhone = new TableColumn("����ó");
		colMemPhone.setPrefWidth(150);
		colMemPhone.setStyle("-fx-alignment:CENTER");
		colMemPhone.setCellValueFactory(new PropertyValueFactory("memPhone"));

		TableColumn colMemAddress = new TableColumn("�ּ�");
		colMemAddress.setPrefWidth(200);
		colMemAddress.setStyle("-fx-alignment:CENTER");
		colMemAddress.setCellValueFactory(new PropertyValueFactory("memAddress"));

		TableColumn colMemPostCode = new TableColumn("�����ȣ");
		colMemPostCode.setPrefWidth(130);
		colMemPostCode.setStyle("-fx-alignment:CENTER");
		colMemPostCode.setCellValueFactory(new PropertyValueFactory("postCode"));

		TableColumn colMemPW = new TableColumn("��й�ȣ");
		colMemPW.setPrefWidth(130);
		colMemPW.setStyle("-fx-alignment:CENTER");
		colMemPW.setCellValueFactory(new PropertyValueFactory("memPW"));

		TableColumn colMemJoinDate = new TableColumn("������");
		colMemJoinDate.setPrefWidth(130);
		colMemJoinDate.setStyle("-fx-alignment:CENTER");
		colMemJoinDate.setCellValueFactory(new PropertyValueFactory("memJoinDate"));
		// 2-2. ���̺� ���� : �÷��� ��ü�� ���̺� �信 ����Ʈ �߰� �� �׸� �߰�
		tableView.setItems(dataMember);
		tableView.getColumns().addAll(colMemID, colMemName, colMemPhone, colMemAddress, colMemPostCode, colMemPW,
				colMemJoinDate);
	} // tableViewSetting

	// 3.1 �˻� - ȸ����ȣ
	public void handlerBtnSearchIDAction(ActionEvent e) {
		try {
			MemberVO list = new MemberVO();
			MemberDAO memberDAO = new MemberDAO();
			list = memberDAO.getMemberIDCheck(Integer.parseInt(txtMemID.getText()));
			if (list == null) {
				Utility.alertDisplay(1, "�˻� ���", "ȸ����ȣ �˻� ����", "�߸� �Է��߽��ϴ�.");
				throw new Exception("�˻�����");
			}
			dataMember.removeAll(dataMember);
			dataMember.add(list);
			
		} catch (Exception e1) {
			Utility.alertDisplay(1, "�˻� ���", "ȸ����ȣ �˻� ����", e1.toString());// ȸ����ȣ �Է� ���ϰų� �߸��� �� �Է½�
			e1.printStackTrace();
		}
	}

	// 3.1 �˻� - �̸�
	public void handlerBtnSearchNameAction(ActionEvent e) {
		try {
			ArrayList<MemberVO> list = new ArrayList<MemberVO>();
			MemberDAO memberDAO = new MemberDAO();
			list = memberDAO.getMemberNameCheck(txtMemName.getText());
			if (list == null) {
				Utility.alertDisplay(1, "�˻� ���", "�̸� �˻� ����", "�߸��� ���� �Է��ϼ̽��ϴ�"); 
			}
			dataMember.removeAll(dataMember);
			for (MemberVO mvo : list) {
				dataMember.add(mvo);
			}
		} catch (Exception e1) {
			Utility.alertDisplay(1, "�˻� ���", "�̸� �˻� ����", e1.toString());
			e1.printStackTrace();
		}
	}

	// 3.2 ����
	public void handlerBtnModifyAction(ActionEvent e) {
		
		try {
			Parent editRoot = FXMLLoader.load(getClass().getResource("/view/adminEditMember.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnModify.getScene().getWindow());
			stageDialog.setTitle("ȸ������ ����â");

			TextField editMemID = (TextField) editRoot.lookup("#TxtMemID");
			TextField editMemName = (TextField) editRoot.lookup("#TxtMemName");
			TextField editMemPhone = (TextField) editRoot.lookup("#TxtMemPhone");
			TextField editMemAddress = (TextField) editRoot.lookup("#TxtMemAddress");
			TextField editMemPostcode = (TextField) editRoot.lookup("#TxtMemPostcode");
			TextField editMemPW = (TextField) editRoot.lookup("#TxtMemPW");
			TextField editMemJoinDate = (TextField) editRoot.lookup("#TxtMemJoinDate");
			
			Button btnOK = (Button) editRoot.lookup("#btnOK");
			Button btnClose = (Button) editRoot.lookup("#btnClose");
			
						
			editMemID.setDisable(true);
			editMemPW.setDisable(true);
			editMemJoinDate.setDisable(true);
			editMemPostcode.setDisable(true);
			
			editMemID.setText(String.valueOf(selectMemberVO.get(0).getMemID()));
			editMemName.setText(selectMemberVO.get(0).getMemName());
			editMemPhone.setText(selectMemberVO.get(0).getMemPhone());
			editMemAddress.setText(selectMemberVO.get(0).getMemAddress());
			editMemPostcode.setText(selectMemberVO.get(0).getPostCode());
			editMemPW.setText(selectMemberVO.get(0).getMemPW());
			editMemJoinDate.setText(selectMemberVO.get(0).getMemJoinDate());
			// Ȯ�� ��ư �̺�Ʈ ó��
			btnOK.setOnAction(e3 -> {
			
				try {
					if (editMemName.getText().equals("") || editMemPhone.getText().equals("")
							|| editMemAddress.getText().equals("")) {
						Utility.alertDisplay(1, "��� ����", "������ �����߽��ϴ�", "�ٽ� �Է����ּ���" );
						throw new Exception();
					} else {
						MemberDAO memberDAO = new MemberDAO();
						MemberVO mvo = new MemberVO(selectMemberVO.get(0).getMemID(), editMemName.getText(),
								editMemPhone.getText(), editMemAddress.getText(), editMemPostcode.getText(),
								editMemPW.getText(), editMemJoinDate.getText());
						MemberVO memberVO = memberDAO.getMemberUpdate(mvo, selectMemberVO.get(0).getMemID());
						System.out.println(selectMemberVO.get(0).getMemID());
						if (editDelete == true && memberVO != null) {
							dataMember.remove(selectedIndex);
							dataMember.add(selectedIndex, mvo);
							editDelete = false;
						} else {
							throw new Exception("���� ��� ����");
						}
					}
				} catch (Exception e2) {
					Utility.alertDisplay(1, "��� ����", "������ �����߽��ϴ�", "�ٽ� �õ����ּ���" + e2.getMessage());
				}
				buttonInitSetting(false, false, false, false);
				stageDialog.close();
			});

			// ��� ��ư �̺�Ʈ ó��
			btnClose.setOnAction(e4 -> {
				stageDialog.close();
			});
			
			Scene scene = new Scene(editRoot);
			stageDialog.setScene(scene);
			stageDialog.setResizable(true);
			stageDialog.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	} // end of handlerBtnModifyAction

	// 3.3 ����
	public void handlerBtnDeleteAction(ActionEvent e) {
		Alert alert = null;
		try {
			alert = new Alert(Alert.AlertType.WARNING, "Delete " + "���� �����Ͻðڽ��ϱ� ?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				MemberDAO memberDAO = new MemberDAO();
				memberDAO.getMemberDelete(selectMemberVO.get(0).getMemID());
				
				dataMember.removeAll(dataMember);
				totalList();
			}
		} catch (Exception e1) {
			Utility.alertDisplay(1, "���� ����", "���� ����", e1.toString());
		}
		editDelete = false;
	} // end of handlerBtnDeleteAction

	// ���̺� �並 Ŭ�� ���� �� ���̺� �ִ� ���� �����ͼ� �ؽ�Ʈ�ʵ忡 ���� �ִ´�.
	public void handlerTableViewPressedAction(MouseEvent e) {
		try {
			editDelete = true;
			selectedIndex = tableView.getSelectionModel().getSelectedIndex();
			selectMemberVO = tableView.getSelectionModel().getSelectedItems();
			
		} catch (Exception e2) { 
			editDelete = false;
		}
	}

} // end of MemberController
