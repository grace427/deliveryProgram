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
	// 수정할때의 버튼 설정상태 결정
	private boolean editDelete = false;
	private MemberDAO memberDAO = null;
	@FXML
	private TableView<MemberVO> tableView = new TableView<>();

	// 테이블뷰를 선택했을때 위치값과 객체값을 저장할 수 있는 변수 선언
	private int selectedIndex;
	private ObservableList<MemberVO> selectMemberVO;
	ObservableList<MemberVO> dataMember = null; // 테이블 뷰에 저장하기 위한 데이터

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 1. 버튼 초기화
		buttonInitSetting(false, false, false, false);
		// 2. 테이블뷰 셋팅
		tableViewSetting();

		// 3. 버튼(회원번호 검색, 이름 검색, 수정, 삭제) 컨트롤
		// 3.1 검색 - 회원번호
		btnSearchID.setOnAction(e -> handlerBtnSearchIDAction(e));
		// 3.1 검색 - 이름
		btnSearchName.setOnAction(e -> {
			handlerBtnSearchNameAction(e);
		});
		// 3.2 수정
		btnModify.setOnAction((e) -> {
			handlerBtnModifyAction(e);
		});
		// 3.3 삭제
		btnDelete.setOnAction((e) -> {
			handlerBtnDeleteAction(e);
		});
		// 테이블뷰 클릭
		tableView.setOnMousePressed((e) -> {
			handlerTableViewPressedAction(e);
		});
		// 홈버튼
		btnHome.setOnMouseClicked((e) -> {
			handlerBtnExitAction(e);
		});
		// 전체리스트
		totalList();
	} // end of initialize
	
	
	// 홈버튼
	private void handlerBtnExitAction(MouseEvent e) {
		((Stage) btnHome.getScene().getWindow()).close();		
	}

	// 회원 전체리스트
	public void totalList() {
		ArrayList<MemberVO> list = null;
		MemberDAO memberDAO = new MemberDAO();
		MemberVO memberVO = null;
		list = memberDAO.getMemberTotal();
		if (list == null) {
			Utility.alertDisplay(1, "경고", "DB 가져오기 오류", "점검 요망");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			memberVO = list.get(i);
			System.out.println(memberVO.getMemName());
			dataMember.add(memberVO);
		}
	}

	// 1. 관리자 메인창 버튼 초기화(회원관리)
	public void buttonInitSetting(boolean c, boolean d, boolean e, boolean f) {
		btnModify.setDisable(c);
		btnDelete.setDisable(d);
		txtMemName.setDisable(e);
		txtMemID.setDisable(f);
	}

	// 2. 테이블뷰 셋팅
	public void tableViewSetting() {
		dataMember = FXCollections.observableArrayList();
		tableView.setEditable(false);

		TableColumn colMemID = new TableColumn("회원정보");
		colMemID.setPrefWidth(130);
		colMemID.setStyle("-fx-alignment:CENTER");
		colMemID.setCellValueFactory(new PropertyValueFactory("memID"));

		TableColumn colMemName = new TableColumn("이름");
		colMemName.setPrefWidth(130);
		colMemName.setStyle("-fx-alignment:CENTER");
		colMemName.setCellValueFactory(new PropertyValueFactory("memName"));

		TableColumn colMemPhone = new TableColumn("연락처");
		colMemPhone.setPrefWidth(150);
		colMemPhone.setStyle("-fx-alignment:CENTER");
		colMemPhone.setCellValueFactory(new PropertyValueFactory("memPhone"));

		TableColumn colMemAddress = new TableColumn("주소");
		colMemAddress.setPrefWidth(200);
		colMemAddress.setStyle("-fx-alignment:CENTER");
		colMemAddress.setCellValueFactory(new PropertyValueFactory("memAddress"));

		TableColumn colMemPostCode = new TableColumn("우편번호");
		colMemPostCode.setPrefWidth(130);
		colMemPostCode.setStyle("-fx-alignment:CENTER");
		colMemPostCode.setCellValueFactory(new PropertyValueFactory("postCode"));

		TableColumn colMemPW = new TableColumn("비밀번호");
		colMemPW.setPrefWidth(130);
		colMemPW.setStyle("-fx-alignment:CENTER");
		colMemPW.setCellValueFactory(new PropertyValueFactory("memPW"));

		TableColumn colMemJoinDate = new TableColumn("가입일");
		colMemJoinDate.setPrefWidth(130);
		colMemJoinDate.setStyle("-fx-alignment:CENTER");
		colMemJoinDate.setCellValueFactory(new PropertyValueFactory("memJoinDate"));
		// 2-2. 테이블 설정 : 컬럼들 객체를 테이블 뷰에 리스트 추가 및 항목 추가
		tableView.setItems(dataMember);
		tableView.getColumns().addAll(colMemID, colMemName, colMemPhone, colMemAddress, colMemPostCode, colMemPW,
				colMemJoinDate);
	} // tableViewSetting

	// 3.1 검색 - 회원번호
	public void handlerBtnSearchIDAction(ActionEvent e) {
		try {
			MemberVO list = new MemberVO();
			MemberDAO memberDAO = new MemberDAO();
			list = memberDAO.getMemberIDCheck(Integer.parseInt(txtMemID.getText()));
			if (list == null) {
				Utility.alertDisplay(1, "검색 결과", "회원번호 검색 오류", "잘못 입력했습니다.");
				throw new Exception("검색오류");
			}
			dataMember.removeAll(dataMember);
			dataMember.add(list);
			
		} catch (Exception e1) {
			Utility.alertDisplay(1, "검색 결과", "회원번호 검색 오류", e1.toString());// 회원번호 입력 안하거나 잘못된 값 입력시
			e1.printStackTrace();
		}
	}

	// 3.1 검색 - 이름
	public void handlerBtnSearchNameAction(ActionEvent e) {
		try {
			ArrayList<MemberVO> list = new ArrayList<MemberVO>();
			MemberDAO memberDAO = new MemberDAO();
			list = memberDAO.getMemberNameCheck(txtMemName.getText());
			if (list == null) {
				Utility.alertDisplay(1, "검색 결과", "이름 검색 오류", "잘못된 값을 입력하셨습니다"); 
			}
			dataMember.removeAll(dataMember);
			for (MemberVO mvo : list) {
				dataMember.add(mvo);
			}
		} catch (Exception e1) {
			Utility.alertDisplay(1, "검색 결과", "이름 검색 오류", e1.toString());
			e1.printStackTrace();
		}
	}

	// 3.2 수정
	public void handlerBtnModifyAction(ActionEvent e) {
		
		try {
			Parent editRoot = FXMLLoader.load(getClass().getResource("/view/adminEditMember.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnModify.getScene().getWindow());
			stageDialog.setTitle("회원정보 수정창");

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
			// 확인 버튼 이벤트 처리
			btnOK.setOnAction(e3 -> {
			
				try {
					if (editMemName.getText().equals("") || editMemPhone.getText().equals("")
							|| editMemAddress.getText().equals("")) {
						Utility.alertDisplay(1, "등록 실패", "수정에 실패했습니다", "다시 입력해주세요" );
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
							throw new Exception("수정 등록 오류");
						}
					}
				} catch (Exception e2) {
					Utility.alertDisplay(1, "등록 실패", "수정에 실패했습니다", "다시 시도해주세요" + e2.getMessage());
				}
				buttonInitSetting(false, false, false, false);
				stageDialog.close();
			});

			// 취소 버튼 이벤트 처리
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

	// 3.3 삭제
	public void handlerBtnDeleteAction(ActionEvent e) {
		Alert alert = null;
		try {
			alert = new Alert(Alert.AlertType.WARNING, "Delete " + "정말 삭제하시겠습니까 ?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				MemberDAO memberDAO = new MemberDAO();
				memberDAO.getMemberDelete(selectMemberVO.get(0).getMemID());
				
				dataMember.removeAll(dataMember);
				totalList();
			}
		} catch (Exception e1) {
			Utility.alertDisplay(1, "삭제 오류", "삭제 오류", e1.toString());
		}
		editDelete = false;
	} // end of handlerBtnDeleteAction

	// 테이블 뷰를 클릭 했을 때 테이블에 있는 값을 가져와서 텍스트필드에 집어 넣는다.
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
