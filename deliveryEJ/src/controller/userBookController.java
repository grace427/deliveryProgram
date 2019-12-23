package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.userBookVO;
import model.userMemberVO;

public class userBookController implements Initializable {

	@FXML
	private TextField txtBkRcvName;
	@FXML
	private TextField txtBkRcvPostCode;
	@FXML
	private TextField txtBkRcvAddress;
	@FXML
	private Button btnFdPostCode1;
	@FXML
	private TextField txtBkRcvPhone1;
	@FXML
	private TextField txtBkRcvPhone2;
	@FXML
	private TextField txtBkRcvPhone3;
	@FXML
	private TextField txtBkSndName;
	@FXML
	private TextField txtBkSndPostCode;
	@FXML
	private TextField txtBkSndAddress;
	@FXML
	private Button btnFdPostCode2;
	@FXML
	private TextField txtBkSndPhone1;
	@FXML
	private TextField txtBkSndPhone2;
	@FXML
	private TextField txtBkSndPhone3;
	@FXML
	private RadioButton rdSecurity;
	@FXML
	private RadioButton rdFrontDoor;
	@FXML
	private RadioButton rdUnBox;
	@FXML
	private RadioButton rdEtc1;
	@FXML
	private TextField txtBkPlaceEtc;
	@FXML
	private RadioButton rdClothes;
	@FXML
	private RadioButton rdBooks;
	@FXML
	private RadioButton rdElec;
	@FXML
	private RadioButton rdFoods;
	@FXML
	private RadioButton rdEtc2;
	@FXML
	private RadioButton rdPapers;
	@FXML
	private TextField txtBkTypeEtc;
	@FXML
	private TextField txtBkWeight;
	@FXML
	private RadioButton rdBeforePay;
	@FXML
	private RadioButton rdAfterPay;
	@FXML
	private DatePicker datePicBook;
	@FXML
	private ComboBox<String> comboTime;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnNext;
	@FXML
	private ToggleGroup hopePlaceGroup;
	@FXML
	private ToggleGroup typeGroup;
	@FXML
	private ToggleGroup payGroup;

	private boolean editDelete = false;
	private ObservableList<userBookVO> selectMember;
	private int selectedIndex;
	ObservableList<userBookVO> data;
	private userBookDAO bookDAO;

	private String hopePlaceGroupSelectedString = null;
	private String typeGroupSelectedString = null;
	private String payGroupSelectedString = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		System.out.println(userLoginController.userPhone);

		// 받는 사람 테스트
//		txtBkRcvName.setText("김관주");
//		txtBkRcvPostCode.setText("10765");
//		txtBkRcvAddress.setText("경기도 고양시 일산서구 후곡로55 206동 801호");
//		txtBkRcvPhone1.setText("010");
//		txtBkRcvPhone2.setText("9208");
//		txtBkRcvPhone3.setText("7649");
		
		btnFdPostCode1.setOnAction(e->{
			Parent loginView = null;
			Stage loginStage = null;
			try {
				loginView = FXMLLoader.load(getClass().getResource("/view/userFindPostCode.fxml"));
				loginStage = new Stage();
				Scene scene = new Scene(loginView);
				loginStage.setScene(scene);
				loginStage.setTitle("우편번호 찾기");
				loginStage.setResizable(false);
//				((Stage) btnJoMem.getScene().getWindow()).close();
				loginStage.show();
			} catch (IOException e1) {
				System.out.println("우편번호 창 열기 실패");
				e1.printStackTrace();
			}
		});
		
		btnFdPostCode2.setOnAction(e->{
			Parent loginView = null;
			Stage loginStage = null;
			try {
				loginView = FXMLLoader.load(getClass().getResource("/view/userFindPostCode.fxml"));
				loginStage = new Stage();
				Scene scene = new Scene(loginView);
				loginStage.setScene(scene);
				loginStage.setTitle("우편번호 찾기");
				loginStage.setResizable(false);
//				((Stage) btnJoMem.getScene().getWindow()).close();
				loginStage.show();
			} catch (IOException e1) {
				System.out.println("우편번호 창 열기 실패");
				e1.printStackTrace();
			}
		});
		
		
		// 보낸 사람 테스트 (데이터베이스로 회원정보 불러오기)?
		txtBkSndName.setText("김지수");
		txtBkSndPostCode.setText("10372");
		txtBkSndAddress.setText("경기도 고양시 일산서구 후곡로 55 205동 401호");
		txtBkSndPhone1.setText("010");
		txtBkSndPhone2.setText("2828");
		txtBkSndPhone3.setText("8705");

		// 무게 테스트
		txtBkWeight.setText("1");

		// 수령희망장소 경비실
		rdSecurity.setOnAction(e -> {
			handlerRdSecurityAction(e);
		});

		// 수령희망장소 현관 앞
		rdFrontDoor.setOnAction(e -> {
			handlerRdFrontDoorAction(e);
		});

		// 수령희망장소 무인택배함
		rdUnBox.setOnAction(e -> {
			handlerRdUnBoxAction(e);
		});

		// 수령희망장소 기타
		rdEtc1.setOnAction(e -> {
			handlerRdEtc1Action(e);
		});

		// 물품종류 의류/잡화
		rdClothes.setOnAction(e -> {
			handlerRdClothesAction(e);
		});

		// 물품종류 서적
		rdBooks.setOnAction(e -> {
			handlerRdBooksAction(e);
		});

		// 물품종류 가전제품
		rdElec.setOnAction(e -> {
			handlerRdElecAction(e);
		});

		// 물품종류 식품류
		rdFoods.setOnAction(e -> {
			handlerRdFoodsAction(e);
		});

		// 물품종류 서류
		rdPapers.setOnAction(e -> {
			handlerRdPapersAction(e);
		});

		// 물품종류 기타
		rdEtc2.setOnAction(e -> {
			handlerRdEtc2Action(e);
		});

		// 선불
		rdBeforePay.setOnAction(e -> {
			handlerRdBeforeAction(e);
		});

		// 착불
		rdAfterPay.setOnAction(e -> {
			handlerRdAfterAction(e);
		});

		// 테스트 택배예약 날짜
		datePicBook.setValue(LocalDate.now());

		// 택배예약 날짜는 오늘날짜 이후로 가능
		comboTime.setItems(FXCollections.observableArrayList("00:00:00", "01:00:00", "02:00:00", "03:00:00", "04:00:00",
				"05:00:00", "06:00:00", "07:00:00", "08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00",
				"13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00",
				"21:00:00", "22:00:00", "23:00:00", "24:00:00"));
//		ObservableList<String> comboData = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06",
//				"07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
//				"24");

//		comboData.setItems(FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07",
//				"08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"));

		// 테스트 택배예약시간
//		comboTime.setValue("15");

		// 받는 사람 우편번호 찾기
		btnFdPostCode1.setOnAction(e -> {
			handlerBtnFdPostCode1Action(e);
		});

		// 보내는 사람 우편번호 찾기
		btnFdPostCode2.setOnAction(e -> {
			handlerBtnFdPostCode2Action(e);
		});

		// 다음 버튼 클릭 시 입력한 값 다시보여준다.
		btnNext.setOnAction(e -> {
			handlerBtnNextAction(e);
		});

		// 취소 버튼 클릭 시 메시지창 출력
		btnCancel.setOnAction(e -> {
			handlerBtnCancelAction(e);
		});

//		// 보내는 사람 입력 란 클릭 시 멤버테이블의 이름 불러오기
//		txtBkSndName.setOnMouseClicked(e -> {
//			handlerTxtBkSndNameAction(e);
//		});

		/*
		 * 받는 사람 전화번호 길이 제한 (txtBkRcvPhone1)
		 */
		DecimalFormat format = new DecimalFormat("###");
		txtBkRcvPhone1.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 4) {
				return null;
			} else {
				return event;
			}
		}));

		/*
		 * 받는 사람 전화번호 길이 제한 (txtBkRcvPhone2)
		 */
		txtBkRcvPhone2.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 5) {
				return null;
			} else {
				return event;
			}
		}));

		/*
		 * 받는 사람 전화번호 길이 제한 (txtBkRcvPhone3)
		 */
		txtBkRcvPhone3.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 5) {
				return null;
			} else {
				return event;
			}
		}));

		/*
		 * 받는 사람 전화번호 길이 제한 (txtBkSndPhone1)
		 */
		txtBkSndPhone1.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 4) {
				return null;
			} else {
				return event;
			}
		}));

		/*
		 * 받는 사람 전화번호 길이 제한 (txtBkSndPhone2)
		 */
		txtBkSndPhone2.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 5) {
				return null;
			} else {
				return event;
			}
		}));

		/*
		 * 받는 사람 전화번호 길이 제한 (txtBkSndPhone3)
		 */
		txtBkSndPhone3.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 5) {
				return null;
			} else {
				return event;
			}
		}));

	}// initialize

//	private void handlerTxtBkSndNameAction(MouseEvent e) {
//		//보내는 분 값을 데이터베이스에 저장되어있는 membertbl에서 불러온다.
//		
//		userMemberVO list1=new userMemberVO();
//		userMemberDAO umDAO=new userMemberDAO();
//		try {
//			list1=umDAO.getMemberCheck(userLoginController.userPhone);
//		} catch (Exception e4) {
//			// TODO Auto-generated catch block
//			e4.printStackTrace();	
//	}
//		System.out.println(userLoginController.userPhone);
//	}

	/*
	 * 수령희망장소 : 경비실 선택 시 나머지 비활성화
	 */
	public void handlerRdSecurityAction(ActionEvent e) {
		rdSecurity.setSelected(true);
		rdFrontDoor.setSelected(false);
		rdUnBox.setSelected(false);
		rdEtc1.setSelected(false);
		txtBkPlaceEtc.setDisable(true);
	}

	/*
	 * 수령희망장소 : 현관 앞 선택 시 나머지 비활성화
	 */
	public void handlerRdFrontDoorAction(ActionEvent e) {
		rdSecurity.setSelected(false);
		rdFrontDoor.setSelected(true);
		rdUnBox.setSelected(false);
		rdEtc1.setSelected(false);
		txtBkPlaceEtc.setDisable(true);

	}

	/*
	 * 수령희망장소 : 무인택배함 선택 시 나머지 비활성화
	 */
	public void handlerRdUnBoxAction(ActionEvent e) {
		rdSecurity.setSelected(false);
		rdFrontDoor.setSelected(false);
		rdUnBox.setSelected(true);
		rdEtc1.setSelected(false);
		txtBkPlaceEtc.setDisable(true);

	}

	/*
	 * 수령희망장소 : 기타 선택 시 나머지 비활성화
	 */
	public void handlerRdEtc1Action(ActionEvent e) {
		rdSecurity.setSelected(false);
		rdFrontDoor.setSelected(false);
		rdUnBox.setSelected(false);
		rdEtc1.setSelected(true);
		txtBkPlaceEtc.setDisable(false);

	}

	/*
	 * 물품종류 : 의류/잡화 선택 시 나머지 비활성화
	 */
	public void handlerRdClothesAction(ActionEvent e) {
		rdClothes.setSelected(true);
		rdBooks.setSelected(false);
		rdElec.setSelected(false);
		rdFoods.setSelected(false);
		rdPapers.setSelected(false);
		rdEtc2.setSelected(false);
		txtBkTypeEtc.setDisable(true);
	}

	/*
	 * 물품종류 : 서적 선택 시 나머지 비활성화
	 */
	public void handlerRdBooksAction(ActionEvent e) {
		rdClothes.setSelected(false);
		rdBooks.setSelected(true);
		rdElec.setSelected(false);
		rdFoods.setSelected(false);
		rdPapers.setSelected(false);
		rdEtc2.setSelected(false);
		txtBkTypeEtc.setDisable(true);
	}

	/*
	 * 물품종류 : 가전제품 선택 시 나머지 비활성화
	 */
	public void handlerRdElecAction(ActionEvent e) {
		rdClothes.setSelected(false);
		rdBooks.setSelected(false);
		rdElec.setSelected(true);
		rdFoods.setSelected(false);
		rdPapers.setSelected(false);
		rdEtc2.setSelected(false);
		txtBkTypeEtc.setDisable(true);
	}

	/*
	 * 물품종류 : 식품류 선택 시 나머지 비활성화
	 */
	public void handlerRdFoodsAction(ActionEvent e) {
		rdClothes.setSelected(false);
		rdBooks.setSelected(false);
		rdElec.setSelected(false);
		rdFoods.setSelected(true);
		rdPapers.setSelected(false);
		rdEtc2.setSelected(false);
		txtBkTypeEtc.setDisable(true);

	}

	/*
	 * 물품종류 : 서류 선택 시 나머지 비활성화
	 */
	public void handlerRdPapersAction(ActionEvent e) {
		rdClothes.setSelected(false);
		rdBooks.setSelected(false);
		rdElec.setSelected(false);
		rdFoods.setSelected(false);
		rdPapers.setSelected(true);
		rdEtc2.setSelected(false);
		txtBkTypeEtc.setDisable(true);
	}

	/*
	 * 물품종류 : 기타 선택 시 나머지 비활성화
	 */
	public void handlerRdEtc2Action(ActionEvent e) {
		rdClothes.setSelected(false);
		rdBooks.setSelected(false);
		rdElec.setSelected(false);
		rdFoods.setSelected(false);
		rdPapers.setSelected(false);
		rdEtc2.setSelected(true);
		txtBkTypeEtc.setDisable(false);

	}

	/*
	 * 선불 선택 시 착불 비활성화
	 */
	public void handlerRdBeforeAction(ActionEvent e) {
		rdBeforePay.setSelected(true);
		rdAfterPay.setSelected(false);
	}

	/*
	 * 착불 선택 시 선불 비활성화
	 */
	public void handlerRdAfterAction(ActionEvent e) {
		rdBeforePay.setSelected(false);
		rdAfterPay.setSelected(true);
	}

	/*
	 * 보내는 사람 우편번호 찾기 클릭 시 우편번호 창 열림
	 */
	public void handlerBtnFdPostCode1Action(ActionEvent e) {
		Parent bookView = null;
		Stage bookStage = null;
		try {
			bookView = FXMLLoader.load(getClass().getResource("/view/userFindPostCode.fxml"));
			bookStage = new Stage();
			Scene scene = new Scene(bookView);
			bookStage.setScene(scene);
			bookStage.setTitle("우편번호 찾기");
			bookStage.setResizable(false);
			bookStage.show();

			// 테이블 뷰 세팅 데이터베이스

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/*
	 * 받는 사람 우편번호 찾기 클릭 시 우편번호 창 열림
	 */
	public void handlerBtnFdPostCode2Action(ActionEvent e) {
		Parent bookView = null;
		Stage bookStage = null;
		try {
			bookView = FXMLLoader.load(getClass().getResource("/view/userFindPostCode.fxml"));
			bookStage = new Stage();
			Scene scene = new Scene(bookView);
			bookStage.setScene(scene);
			bookStage.setTitle("우편번호 찾기");
			bookStage.setResizable(false);
			bookStage.show();

			// 테이블 뷰 세팅 데이터베이스

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/*
	 * 취소버튼 클릭 시 취소하시겠습니까? 창 뜸
	 */
	public void handlerBtnCancelAction(ActionEvent e) {
		try {
			Parent bookView2 = null;
			Stage bookStage2 = null;
			bookView2 = FXMLLoader.load(getClass().getResource("/view/userCancel.fxml"));
			bookStage2 = new Stage();
			Scene scene2 = new Scene(bookView2);
			bookStage2.setScene(scene2);
			bookStage2.setResizable(false);
			bookStage2.show();

			Button btnClose = (Button) bookView2.lookup("#btnClose");
			Button btnOK = (Button) bookView2.lookup("#btnOK");

			// 취소하시겠습니까? 취소 버튼 클릭 시 취소하시겠습니까? 창 꺼짐
			btnClose.setOnAction(e1 -> {
				Stage stage = (Stage) btnClose.getScene().getWindow();
				stage.close();
			});
			
			btnOK.setOnAction(e1-> {
				Platform.exit();
			});

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/*
	 * 다음 버튼 클릭 시 사용자가 입력한 값을 다시 한 번 보여준다. show
	 */
	public void handlerBtnNextAction(ActionEvent e) {

		try {

			double txtweight = Double.parseDouble(txtBkWeight.getText());

			Parent bookView3 = null;
			Stage bookStage3 = null;
			bookView3 = FXMLLoader.load(getClass().getResource("/view/userDeliveryCheckShow.fxml"));
			bookStage3 = new Stage();
			Scene scene3 = new Scene(bookView3);
			bookStage3.setScene(scene3);
			bookStage3.setTitle("입력정보 확인");
			bookStage3.setResizable(false);
			bookStage3.show();

			TextField txtRcvName = (TextField) bookView3.lookup("#txtRcvName");
			TextField txtRcvPostCode = (TextField) bookView3.lookup("#txtRcvPostCode");
			TextField txtRcvAddress = (TextField) bookView3.lookup("#txtRcvAddress");
			TextField txtRcvPhone1 = (TextField) bookView3.lookup("#txtRcvPhone1");
			TextField txtRcvPhone2 = (TextField) bookView3.lookup("#txtRcvPhone2");
			TextField txtRcvPhone3 = (TextField) bookView3.lookup("#txtRcvPhone3");
			TextField txtSndName = (TextField) bookView3.lookup("#txtSndName");
			TextField txtSndPostCode = (TextField) bookView3.lookup("#txtSndPostCode");
			TextField txtSndAddress = (TextField) bookView3.lookup("#txtSndAddress");
			TextField txtSndPhone1 = (TextField) bookView3.lookup("#txtSndPhone1");
			TextField txtSndPhone2 = (TextField) bookView3.lookup("#txtSndPhone2");
			TextField txtSndPhone3 = (TextField) bookView3.lookup("#txtSndPhone3");
			TextField txtType = (TextField) bookView3.lookup("#txtType");
			TextField txtPlace = (TextField) bookView3.lookup("#txtPlace");
			TextField txtWeight = (TextField) bookView3.lookup("#txtWeight");
			TextField txtPay = (TextField) bookView3.lookup("#txtPay");
			TextField txtDate = (TextField) bookView3.lookup("#txtDate");
			TextField txtTime = (TextField) bookView3.lookup("#txtTime");
			Button btnReCancel = (Button) bookView3.lookup("#btnReCancel");
			Button btnReOK = (Button) bookView3.lookup("#btnReOK");

			txtRcvName.setText(txtBkRcvName.getText());
			txtRcvName.setDisable(true);
			txtRcvPostCode.setText(txtBkRcvPostCode.getText());
			txtRcvPostCode.setDisable(true);
			txtRcvAddress.setText(txtBkRcvAddress.getText());
			txtRcvAddress.setDisable(true);
			txtRcvPhone1.setText(txtBkRcvPhone1.getText());
			txtRcvPhone1.setDisable(true);
			txtRcvPhone2.setText(txtBkRcvPhone2.getText());
			txtRcvPhone2.setDisable(true);
			txtRcvPhone3.setText(txtBkRcvPhone3.getText());
			txtRcvPhone3.setDisable(true);

			txtSndName.setText(txtBkSndName.getText());
			txtSndName.setDisable(true);
			txtSndPostCode.setText(txtBkSndPostCode.getText());
			txtSndPostCode.setDisable(true);
			txtSndAddress.setText(txtBkSndAddress.getText());
			txtSndAddress.setDisable(true);
			txtSndPhone1.setText(txtBkSndPhone1.getText());
			txtSndPhone1.setDisable(true);
			txtSndPhone2.setText(txtBkSndPhone2.getText());
			txtSndPhone2.setDisable(true);
			txtSndPhone3.setText(txtBkSndPhone3.getText());
			txtSndPhone3.setDisable(true);

//			userBookDAO ubDAO = new userBookDAO();
//			userBookVO ubvo = new userBookVO(txtType.getText(), Double.parseDouble(txtWeight.getText()),
//					txtRcvName.getText(), txtRcvAddress.getText(), txtRcvPostCode.getText(),
//					(txtRcvPhone1.getText() + txtRcvPhone2.getText() + txtRcvPhone3.getText()), txtPay.getText(),
//					txtDate.getText(), txtTime.getText(), txtPlace.getText(), 2500.0, 0);
//			ubDAO.getBook(ubvo);

			// 라디오 버튼 수령희망장소 값 가져오기
			if (rdSecurity.isSelected()) {
				txtPlace.setText(rdSecurity.getText());
				hopePlaceGroupSelectedString = rdSecurity.getText();

			} else if (rdFrontDoor.isSelected()) {
				txtPlace.setText(rdFrontDoor.getText());
				hopePlaceGroupSelectedString = rdFrontDoor.getText();
			} else if (rdUnBox.isSelected()) {
				txtPlace.setText(rdUnBox.getText());
				hopePlaceGroupSelectedString = rdUnBox.getText();
			} else if (rdEtc1.isSelected()) {
				txtPlace.setText(txtBkPlaceEtc.getText());
				hopePlaceGroupSelectedString = rdEtc1.getText();
			}
			txtPlace.setDisable(true);

			// 라디오 버튼 물품종류 값 가져오기
			if (rdClothes.isSelected()) {
				txtType.setText(rdClothes.getText());
				typeGroupSelectedString = rdClothes.getText();
			} else if (rdBooks.isSelected()) {
				txtType.setText(rdBooks.getText());
				typeGroupSelectedString = rdBooks.getText();
			} else if (rdElec.isSelected()) {
				txtType.setText(rdElec.getText());
				typeGroupSelectedString = rdElec.getText();
			} else if (rdFoods.isSelected()) {
				txtType.setText(rdFoods.getText());
				typeGroupSelectedString = rdFoods.getText();
			} else if (rdPapers.isSelected()) {
				txtType.setText(rdPapers.getText());
				typeGroupSelectedString = rdPapers.getText();
			} else if (rdEtc2.isSelected()) {
				txtType.setText(txtBkTypeEtc.getText());
				typeGroupSelectedString = txtBkTypeEtc.getText();
			}
			txtType.setDisable(true);

			// 무게 값 가져오기
			txtWeight.setText(txtBkWeight.getText());
			txtWeight.setDisable(true);

			// 라디오 버튼 선불 착불 값 가져오기
			if (rdBeforePay.isSelected()) {
				txtPay.setText(rdBeforePay.getText());
				payGroupSelectedString = rdBeforePay.getText();
			} else {
				txtPay.setText(rdAfterPay.getText());
				payGroupSelectedString = rdAfterPay.getText();
			}
			txtPay.setDisable(true);

			// 날짜 값 가져오기
			String date = datePicBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			txtDate.setText(date);
			txtDate.setDisable(true);

			// 시간 값 가져오기
			txtTime.setText(comboTime.getSelectionModel().getSelectedItem());
			txtTime.setDisable(true);

			/*
			 * 확인 버튼 클릭 시 운임정보를 알려준다.
			 */
			btnReOK.setOnAction(e1 -> {

				try {
					if (txtRcvName.getText().equals("") || txtRcvPostCode.getText().equals("")
							|| txtRcvAddress.getText().equals("") || txtRcvPhone1.getText().equals("")
							|| txtRcvPhone2.getText().equals("") || txtRcvPhone3.getText().equals("")
							|| txtPlace.getText().equals("") || txtType.getText().equals("")
							|| txtWeight.getText().equals("") || txtDate.getText().equals("")
							|| txtTime.getText().equals("")) {

						userMainController.alertDisplay(1, "예약 실패", "예약정보를 다시 확인해주세요.", "공란 없이 입력해주세요.");

					} else if (txtweight > 6) {
						userMainController.alertDisplay(1, "kg 초과", "5kg 이하로 적어주세요", "5kg 초과 물품은 고객센터로 문의바랍니다.");
					} else {
						Parent bookView4 = null;
						Stage bookStage4 = null;
						bookView4 = FXMLLoader.load(getClass().getResource("/view/userWeightShow.fxml"));
						bookStage4 = new Stage();
						Scene scene4 = new Scene(bookView4);
						bookStage4.setScene(scene4);
						bookStage4.setTitle("운임정보");
//						((Stage) btnJoMem.getScene().getWindow()).close();
						bookStage4.setResizable(false);
						bookStage4.show();

						Label lblMemWName = (Label) bookView4.lookup("#lblMemWName");
						Label lblWeight = (Label) bookView4.lookup("#lblWeight");
						Button btnWCancel = (Button) bookView4.lookup("#btnWCancel");
						Button btnWOK = (Button) bookView4.lookup("#btnWOK");

						lblMemWName.setText(txtBkSndName.getText());

						// 운임요금 보여주기
						int cost = 0;
						double weight = Double.parseDouble(txtBkWeight.getText());
						if (weight > 0.5 && weight <= 1) {
							cost = 2500;
							lblWeight.setText(String.valueOf(cost));
						} else if (weight > 1 && weight <= 2) {
							cost = 2800;
							lblWeight.setText(String.valueOf(cost));
						} else if (weight > 2 && weight <= 3) {
							cost = 3000;
							lblWeight.setText(String.valueOf(cost));
						} else if (weight > 3 && weight <= 4) {
							cost = 3500;
							lblWeight.setText(String.valueOf(cost));
						} else if (weight > 4 && weight <= 5) {
							cost = 4000;
							lblWeight.setText(String.valueOf(cost));
						}

						// 취소버튼 클릭 시 운임창과 예약확인창을 닫아서 발송픽업예약창을 보이게 한다.
						btnWCancel.setOnAction(e2 -> {
							Stage stage = (Stage) btnWCancel.getScene().getWindow();
							stage.close();
							Stage stage2 = (Stage) btnReCancel.getScene().getWindow();
							stage2.close();
						});

						// 확인버튼 클릭 시 발송픽업예약완료창 뜬다.
						btnWOK.setOnAction(e2 -> {

//							String date = datePicBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//							txtDate.setText(date);

//							userBookVO bookVO = new userBookVO(0, txtRcvName.getText().trim(),
//									Double.parseDouble(txtWeight.getText().trim()), 0, txtRcvName.getText().trim(),
//									txtRcvAddress.getText().trim(), txtRcvPostCode.getText().trim(),
//									txtRcvPhone1.getText().trim() + txtRcvPhone2.getText().trim()
//											+ txtRcvPhone3.getText().trim(),
//									txtPay.getText().trim(), txtDate.getText().trim(), txtTime.getText().trim(),
//									txtPlace.getText().trim());
//							
							System.out.println("****라디오버튼그룹*********" + hopePlaceGroupSelectedString);
							userBookVO bookVO = new userBookVO(0, typeGroupSelectedString,
									Double.parseDouble(txtWeight.getText().trim()), txtBkSndName.getText().trim(),
									txtBkSndAddress.getText().trim(), txtBkSndPostCode.getText().trim(),
									txtBkSndPhone1.getText().trim() + txtBkSndPhone2.getText().trim()
											+ txtBkSndPhone3.getText().trim(),
									payGroupSelectedString, datePicBook.getValue().toString(),
									comboTime.getSelectionModel().getSelectedItem(), hopePlaceGroupSelectedString);

							bookDAO = new userBookDAO();

							int count = bookDAO.getBook(bookVO);
							if (count != 0) {
//									data.removeAll(data);
								totalList();

							}
							userMainController.alertDisplay(1, "등록성공", "아웃바운드테이블 등록 성공", "ㅊㅋㅊㅋ");

							try {
								Parent bookView5 = null;
								Stage bookStage5 = null;
								bookView5 = FXMLLoader.load(getClass().getResource("/view/userBookComplete.fxml"));
								bookStage5 = new Stage();
								Scene scene5 = new Scene(bookView5);
								bookStage5.setScene(scene5);
								bookStage5.setTitle("예약완료");
								((Stage) btnWCancel.getScene().getWindow()).close();
								((Stage) btnReCancel.getScene().getWindow()).close();
								((Stage) btnNext.getScene().getWindow()).close();
								bookStage5.setResizable(false);
								bookStage5.show();

								Label lblMemCName = (Label) bookView5.lookup("#lblMemCName");
								Label lblTrkNum = (Label) bookView5.lookup("#lblTrkNum");
								Label lblBkDate = (Label) bookView5.lookup("#lblBkDate");
								Label lblBkTime = (Label) bookView5.lookup("#lblBkTime");
								Button btnCOK = (Button) bookView5.lookup("#btnCOK");

								lblMemCName.setText(txtBkSndName.getText());
								lblTrkNum.setText("624005102");
								lblBkDate.setText(date);
								lblBkTime.setText(comboTime.getSelectionModel().getSelectedItem());
								btnCOK.setOnAction(e3 -> {
									userMainController.alertDisplay(2, "감사합니다.", "이용해주셔서 감사합니다.", "다음에도 이용해주세요~><");
									Platform.exit();
								});

							} catch (IOException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
						});

					}

				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			});

			// 취소 버튼 누르면 정보팝업창 닫기
			btnReCancel.setOnAction(e1 -> {
				Stage stage = (Stage) btnReCancel.getScene().getWindow();
				stage.close();
			});

		} catch (IOException e1) {
			System.out.println(1);
			e1.printStackTrace();
		}

	}

	public void totalList() {
		ArrayList<userBookVO> list = null;
		userBookDAO bookDAO = new userBookDAO();
		userBookVO bookVO = null;
		list = bookDAO.getBookTotal();
		if (list == null) {
			userMainController.alertDisplay(1, "경고", "DB 가져오기 오류", "점검요망");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			bookVO = list.get(i);
//		data.add(bookVO);
		}

	}

}
