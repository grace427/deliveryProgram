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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.userBookVO;
import model.userMemberVO;

public class userMyPageToController implements Initializable {

	@FXML
	private Button btnSndBox;
	@FXML
	private Button btnModifyInfo;
	public ArrayList<userMemberVO> mpList;

	ObservableList<userBookVO> dataOutbound;
	private TableView<userBookVO> tableViewOutBound;
	ObservableList<userMemberVO> showMember;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 내가 보낸 택배 클릭
		btnSndBox.setOnAction(e -> {
			handlerBtnSndBoxAction(e);
		});

		// 내 정보 수정 클릭
		btnModifyInfo.setOnAction(e -> {
			handlerBtnModifyInfoAction(e);
		});

	}

	/*
	 * 내가 보낸 택배 클릭 시 회원이 택백예약한 정보가 띄어진다.
	 */
	public void handlerBtnSndBoxAction(ActionEvent e) {
		Parent mpView = null;
		Stage mpStage = null;
		try {
			mpView = FXMLLoader.load(getClass().getResource("/view/userMpSnd.fxml"));
			mpStage = new Stage();
			Scene scene = new Scene(mpView);
			mpStage.setScene(scene);
			mpStage.setTitle("내가 보낸 택배");
			mpStage.setResizable(false);
//			((Stage) btnSndBox.getScene().getWindow()).close();
			mpStage.show();

			DatePicker datePicStart = (DatePicker) mpView.lookup("#datePicStart");
			DatePicker datePicEnd = (DatePicker) mpView.lookup("#datePicEnd");
			Button btnCheck = (Button) mpView.lookup("#btnCheck");
			tableViewOutBound = (TableView<userBookVO>) mpView.lookup("#tableViewOutBound");
			tableViewOutBoundSetting();

			// outboudTBL를 가져온다.
			outTotalList();

		} catch (IOException e1) {
			System.out.println("내가 보낸 택배 창 열기 실패");
			e1.printStackTrace();
		}

	}

	public void tableViewOutBoundSetting() {
		dataOutbound = FXCollections.observableArrayList();
		tableViewOutBound.setEditable(false);
		// 배송관리 테이블 컬럼 셋팅
		TableColumn colTrkNum = new TableColumn("송장번호");
		colTrkNum.setStyle("-fx-alignment: CENTER;"); 
		colTrkNum.setCellValueFactory(new PropertyValueFactory("trkNum"));

		TableColumn colPkgType = new TableColumn("품명");
		colPkgType.setStyle("-fx-alignment: CENTER;"); 
		colPkgType.setCellValueFactory(new PropertyValueFactory("pkgType"));

		TableColumn colWeight = new TableColumn("무게");
		colWeight.setStyle("-fx-alignment: CENTER;"); 
		colWeight.setCellValueFactory(new PropertyValueFactory("weight"));

		TableColumn colMemID = new TableColumn("회원번호");
		colMemID.setStyle("-fx-alignment: CENTER;"); 
		colMemID.setCellValueFactory(new PropertyValueFactory("memID"));

		TableColumn colSendName = new TableColumn("받는사람");
		colSendName.setStyle("-fx-alignment: CENTER;"); 
		colSendName.setCellValueFactory(new PropertyValueFactory("receiverName"));

		TableColumn colSendAddress = new TableColumn("받는사람 주소");
		colSendAddress.setStyle("-fx-alignment: CENTER;"); 
		colSendAddress.setCellValueFactory(new PropertyValueFactory("receiverAddress"));

		TableColumn colSendPhone = new TableColumn("받는 사람 연락처");
		colSendPhone.setStyle("-fx-alignment: CENTER;"); 
		colSendPhone.setCellValueFactory(new PropertyValueFactory("receiverPhone"));

		TableColumn colPostCode = new TableColumn("우편번호");
		colPostCode.setStyle("-fx-alignment: CENTER;"); 
		colPostCode.setCellValueFactory(new PropertyValueFactory("postCode"));

		TableColumn colStartDate = new TableColumn("픽업예약일");
		colStartDate.setStyle("-fx-alignment: CENTER;"); 
		colStartDate.setCellValueFactory(new PropertyValueFactory("bookDate"));

		TableColumn colStartTime = new TableColumn("픽업예약시간");
		colStartTime.setStyle("-fx-alignment: CENTER;"); 
		colStartTime.setCellValueFactory(new PropertyValueFactory("bookTime"));
		tableViewOutBound.setItems(dataOutbound);
		tableViewOutBound.getColumns().addAll(colTrkNum, colPkgType, colWeight, colMemID, colSendName, colSendAddress,
				colSendPhone, colPostCode, colStartDate, colStartTime);

	}

	/*
	 * 내 정보 수정 클릭 시 택배프로그램 가입 시 입력했던 정보를 수정해서 변경된 값으로 저장되게 한다.
	 */
	public void handlerBtnModifyInfoAction(ActionEvent e) {

		showMember = FXCollections.observableArrayList();
		Parent mpView = null;
		Stage mpStage = null;
		try {
			mpView = FXMLLoader.load(getClass().getResource("/view/userMpModify.fxml"));
			mpStage = new Stage();
			Scene scene = new Scene(mpView);
			mpStage.setScene(scene);
			mpStage.setTitle("내 정보 수정");
			mpStage.setResizable(false);
//			((Stage) btnSndBox.getScene().getWindow()).close();
			mpStage.show();

			TextField txtMemID = (TextField) mpView.lookup("#txtMemID");
			TextField txtMpName = (TextField) mpView.lookup("#txtMpName");
			TextField txtMpPhone1 = (TextField) mpView.lookup("#txtMpPhone1");
			TextField txtMpPostCode = (TextField) mpView.lookup("#txtMpPostCode");
			TextField txtMpAddress = (TextField) mpView.lookup("#txtMpAddress");
			Button btnFdPostCode = (Button) mpView.lookup("#btnFdPostCode");
			TextField txtMpPw = (TextField) mpView.lookup("#txtMpPw");
			Button btnRegist = (Button) mpView.lookup("#btnRegist");
			Button btnModify = (Button) mpView.lookup("#btnModify");

			txtMemID.setText(String.valueOf(mpList.get(0).getMemID()));
			txtMpName.setText(mpList.get(0).getMemName());
			txtMpPhone1.setText(mpList.get(0).getMemPhone());
			txtMpPostCode.setText(mpList.get(0).getPostCode());
			txtMpAddress.setText(mpList.get(0).getMemAddress());
			txtMpPw.setText(mpList.get(0).getMemPW());

			txtMemID.setDisable(true);
			txtMpName.setDisable(true);
			txtMpPhone1.setDisable(true);
			txtMpPostCode.setDisable(true);
			txtMpAddress.setDisable(true);
			btnFdPostCode.setDisable(true);
			txtMpPw.setDisable(true);
//			System.out.println(showMember.get(0).getMemID()+"skdhkfkt");

//			userMemberDAO umDAO=new userMemberDAO();
//			userMemberVO list=new userMemberVO();
//			try {
//				list=umDAO.getMemberCheck(userLoginController.userPhone);
//			} catch (Exception e3) {
//				// TODO Auto-generated catch block
//				e3.printStackTrace();
//			}
//			txtMemID.setText(String.valueOf(list.getMemID()));
//			
//			txtMpName.setText(list.getMemName());
//			txtMpPhone1.setText(list.getMemPhone());
//			txtMpPostCode.setText(list.getPostCode());
//			txtMpAddress.setText(list.getMemAddress());
//			txtMpPw.setText(list.getMemPW());

			btnModify.setOnAction(e1 -> {
				txtMemID.setDisable(true);
				txtMpName.setDisable(false);
				txtMpPhone1.setDisable(false);
//				txtMpPhone2.setDisable(false);
//				txtMpPhone3.setDisable(false);
				txtMpPostCode.setDisable(false);
				txtMpAddress.setDisable(false);
				btnFdPostCode.setDisable(false);
				txtMpPw.setDisable(false);

				btnFdPostCode.setOnAction(e2 -> {
					Parent modifyView = null;
					Stage modifyStage = null;
					try {
						modifyView = FXMLLoader.load(getClass().getResource("/view/userFindPostCode.fxml"));
						modifyStage = new Stage();
						Scene scene1 = new Scene(modifyView);
						modifyStage.setScene(scene1);
						modifyStage.setTitle("우편번호 찾기");
						modifyStage.setResizable(false);
//						((Stage)btnBook.getScene().getWindow()).close();
						modifyStage.show();
						
						
					} catch (IOException e21) {
						System.out.println("내 정보 수정->우편번호 검색 화면 이동 실패");
						e21.printStackTrace();
					}
				});
			});

//			btnRegist.setOnAction(e1-> {
//				//수정한 값을 테이블에 입력시킨다. 
//			});

		} catch (IOException e1) {
			System.out.println("내 정보 수정 창 열기 실패");
			e1.printStackTrace();
		}

	}// end of handlerBtnModifyInfoAction

	/*
	 * 테이블뷰에 outboundtbl를 불러서 테이블뷰에 놓는다.
	 */
	public void outTotalList() {
		ArrayList<userBookVO> list = null;
		userBookDAO ubDAO = new userBookDAO();
		userBookVO ubVO = null;
		list = ubDAO.getBookTotal();
		if (list == null) {
			userMainController.alertDisplay(1, "경고", "outboundDB 가져오기 오류", "점검요망");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			ubVO = list.get(i);
			dataOutbound.add(ubVO);
		}

	}

	/*
	 * 내 정보 수정 창 텍스티 필드에 memberTBL값 불러오기
	 */
//	public void memberTotalList() {
//		userMemberVO list= new userMemberVO();
//		userMemberDAO umDAO = new userMemberDAO();
////		userMemberVO umVO = null;
//		list = umDAO.getMemberCheck();
//		if (list == null) {
//			userMainController.alertDisplay(1, "경고", "mebmerDB 가져오기 오류", "점검요망");
//			return;
//		}
//
////		for (int i = 0; i < list.size(); i++) {
////			umVO = list.get(i);
////			showMember.add(umVO);
////		}
//
//	}

}
