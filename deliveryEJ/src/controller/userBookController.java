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

		// �޴� ��� �׽�Ʈ
//		txtBkRcvName.setText("�����");
//		txtBkRcvPostCode.setText("10765");
//		txtBkRcvAddress.setText("��⵵ ���� �ϻ꼭�� �İ��55 206�� 801ȣ");
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
				loginStage.setTitle("�����ȣ ã��");
				loginStage.setResizable(false);
//				((Stage) btnJoMem.getScene().getWindow()).close();
				loginStage.show();
			} catch (IOException e1) {
				System.out.println("�����ȣ â ���� ����");
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
				loginStage.setTitle("�����ȣ ã��");
				loginStage.setResizable(false);
//				((Stage) btnJoMem.getScene().getWindow()).close();
				loginStage.show();
			} catch (IOException e1) {
				System.out.println("�����ȣ â ���� ����");
				e1.printStackTrace();
			}
		});
		
		
		// ���� ��� �׽�Ʈ (�����ͺ��̽��� ȸ������ �ҷ�����)?
		txtBkSndName.setText("������");
		txtBkSndPostCode.setText("10372");
		txtBkSndAddress.setText("��⵵ ���� �ϻ꼭�� �İ�� 55 205�� 401ȣ");
		txtBkSndPhone1.setText("010");
		txtBkSndPhone2.setText("2828");
		txtBkSndPhone3.setText("8705");

		// ���� �׽�Ʈ
		txtBkWeight.setText("1");

		// ���������� ����
		rdSecurity.setOnAction(e -> {
			handlerRdSecurityAction(e);
		});

		// ���������� ���� ��
		rdFrontDoor.setOnAction(e -> {
			handlerRdFrontDoorAction(e);
		});

		// ���������� �����ù���
		rdUnBox.setOnAction(e -> {
			handlerRdUnBoxAction(e);
		});

		// ���������� ��Ÿ
		rdEtc1.setOnAction(e -> {
			handlerRdEtc1Action(e);
		});

		// ��ǰ���� �Ƿ�/��ȭ
		rdClothes.setOnAction(e -> {
			handlerRdClothesAction(e);
		});

		// ��ǰ���� ����
		rdBooks.setOnAction(e -> {
			handlerRdBooksAction(e);
		});

		// ��ǰ���� ������ǰ
		rdElec.setOnAction(e -> {
			handlerRdElecAction(e);
		});

		// ��ǰ���� ��ǰ��
		rdFoods.setOnAction(e -> {
			handlerRdFoodsAction(e);
		});

		// ��ǰ���� ����
		rdPapers.setOnAction(e -> {
			handlerRdPapersAction(e);
		});

		// ��ǰ���� ��Ÿ
		rdEtc2.setOnAction(e -> {
			handlerRdEtc2Action(e);
		});

		// ����
		rdBeforePay.setOnAction(e -> {
			handlerRdBeforeAction(e);
		});

		// ����
		rdAfterPay.setOnAction(e -> {
			handlerRdAfterAction(e);
		});

		// �׽�Ʈ �ù迹�� ��¥
		datePicBook.setValue(LocalDate.now());

		// �ù迹�� ��¥�� ���ó�¥ ���ķ� ����
		comboTime.setItems(FXCollections.observableArrayList("00:00:00", "01:00:00", "02:00:00", "03:00:00", "04:00:00",
				"05:00:00", "06:00:00", "07:00:00", "08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00",
				"13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00",
				"21:00:00", "22:00:00", "23:00:00", "24:00:00"));
//		ObservableList<String> comboData = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06",
//				"07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
//				"24");

//		comboData.setItems(FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07",
//				"08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"));

		// �׽�Ʈ �ù迹��ð�
//		comboTime.setValue("15");

		// �޴� ��� �����ȣ ã��
		btnFdPostCode1.setOnAction(e -> {
			handlerBtnFdPostCode1Action(e);
		});

		// ������ ��� �����ȣ ã��
		btnFdPostCode2.setOnAction(e -> {
			handlerBtnFdPostCode2Action(e);
		});

		// ���� ��ư Ŭ�� �� �Է��� �� �ٽú����ش�.
		btnNext.setOnAction(e -> {
			handlerBtnNextAction(e);
		});

		// ��� ��ư Ŭ�� �� �޽���â ���
		btnCancel.setOnAction(e -> {
			handlerBtnCancelAction(e);
		});

//		// ������ ��� �Է� �� Ŭ�� �� ������̺��� �̸� �ҷ�����
//		txtBkSndName.setOnMouseClicked(e -> {
//			handlerTxtBkSndNameAction(e);
//		});

		/*
		 * �޴� ��� ��ȭ��ȣ ���� ���� (txtBkRcvPhone1)
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
		 * �޴� ��� ��ȭ��ȣ ���� ���� (txtBkRcvPhone2)
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
		 * �޴� ��� ��ȭ��ȣ ���� ���� (txtBkRcvPhone3)
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
		 * �޴� ��� ��ȭ��ȣ ���� ���� (txtBkSndPhone1)
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
		 * �޴� ��� ��ȭ��ȣ ���� ���� (txtBkSndPhone2)
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
		 * �޴� ��� ��ȭ��ȣ ���� ���� (txtBkSndPhone3)
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
//		//������ �� ���� �����ͺ��̽��� ����Ǿ��ִ� membertbl���� �ҷ��´�.
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
	 * ���������� : ���� ���� �� ������ ��Ȱ��ȭ
	 */
	public void handlerRdSecurityAction(ActionEvent e) {
		rdSecurity.setSelected(true);
		rdFrontDoor.setSelected(false);
		rdUnBox.setSelected(false);
		rdEtc1.setSelected(false);
		txtBkPlaceEtc.setDisable(true);
	}

	/*
	 * ���������� : ���� �� ���� �� ������ ��Ȱ��ȭ
	 */
	public void handlerRdFrontDoorAction(ActionEvent e) {
		rdSecurity.setSelected(false);
		rdFrontDoor.setSelected(true);
		rdUnBox.setSelected(false);
		rdEtc1.setSelected(false);
		txtBkPlaceEtc.setDisable(true);

	}

	/*
	 * ���������� : �����ù��� ���� �� ������ ��Ȱ��ȭ
	 */
	public void handlerRdUnBoxAction(ActionEvent e) {
		rdSecurity.setSelected(false);
		rdFrontDoor.setSelected(false);
		rdUnBox.setSelected(true);
		rdEtc1.setSelected(false);
		txtBkPlaceEtc.setDisable(true);

	}

	/*
	 * ���������� : ��Ÿ ���� �� ������ ��Ȱ��ȭ
	 */
	public void handlerRdEtc1Action(ActionEvent e) {
		rdSecurity.setSelected(false);
		rdFrontDoor.setSelected(false);
		rdUnBox.setSelected(false);
		rdEtc1.setSelected(true);
		txtBkPlaceEtc.setDisable(false);

	}

	/*
	 * ��ǰ���� : �Ƿ�/��ȭ ���� �� ������ ��Ȱ��ȭ
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
	 * ��ǰ���� : ���� ���� �� ������ ��Ȱ��ȭ
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
	 * ��ǰ���� : ������ǰ ���� �� ������ ��Ȱ��ȭ
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
	 * ��ǰ���� : ��ǰ�� ���� �� ������ ��Ȱ��ȭ
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
	 * ��ǰ���� : ���� ���� �� ������ ��Ȱ��ȭ
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
	 * ��ǰ���� : ��Ÿ ���� �� ������ ��Ȱ��ȭ
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
	 * ���� ���� �� ���� ��Ȱ��ȭ
	 */
	public void handlerRdBeforeAction(ActionEvent e) {
		rdBeforePay.setSelected(true);
		rdAfterPay.setSelected(false);
	}

	/*
	 * ���� ���� �� ���� ��Ȱ��ȭ
	 */
	public void handlerRdAfterAction(ActionEvent e) {
		rdBeforePay.setSelected(false);
		rdAfterPay.setSelected(true);
	}

	/*
	 * ������ ��� �����ȣ ã�� Ŭ�� �� �����ȣ â ����
	 */
	public void handlerBtnFdPostCode1Action(ActionEvent e) {
		Parent bookView = null;
		Stage bookStage = null;
		try {
			bookView = FXMLLoader.load(getClass().getResource("/view/userFindPostCode.fxml"));
			bookStage = new Stage();
			Scene scene = new Scene(bookView);
			bookStage.setScene(scene);
			bookStage.setTitle("�����ȣ ã��");
			bookStage.setResizable(false);
			bookStage.show();

			// ���̺� �� ���� �����ͺ��̽�

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/*
	 * �޴� ��� �����ȣ ã�� Ŭ�� �� �����ȣ â ����
	 */
	public void handlerBtnFdPostCode2Action(ActionEvent e) {
		Parent bookView = null;
		Stage bookStage = null;
		try {
			bookView = FXMLLoader.load(getClass().getResource("/view/userFindPostCode.fxml"));
			bookStage = new Stage();
			Scene scene = new Scene(bookView);
			bookStage.setScene(scene);
			bookStage.setTitle("�����ȣ ã��");
			bookStage.setResizable(false);
			bookStage.show();

			// ���̺� �� ���� �����ͺ��̽�

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/*
	 * ��ҹ�ư Ŭ�� �� ����Ͻðڽ��ϱ�? â ��
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

			// ����Ͻðڽ��ϱ�? ��� ��ư Ŭ�� �� ����Ͻðڽ��ϱ�? â ����
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
	 * ���� ��ư Ŭ�� �� ����ڰ� �Է��� ���� �ٽ� �� �� �����ش�. show
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
			bookStage3.setTitle("�Է����� Ȯ��");
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

			// ���� ��ư ���������� �� ��������
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

			// ���� ��ư ��ǰ���� �� ��������
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

			// ���� �� ��������
			txtWeight.setText(txtBkWeight.getText());
			txtWeight.setDisable(true);

			// ���� ��ư ���� ���� �� ��������
			if (rdBeforePay.isSelected()) {
				txtPay.setText(rdBeforePay.getText());
				payGroupSelectedString = rdBeforePay.getText();
			} else {
				txtPay.setText(rdAfterPay.getText());
				payGroupSelectedString = rdAfterPay.getText();
			}
			txtPay.setDisable(true);

			// ��¥ �� ��������
			String date = datePicBook.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			txtDate.setText(date);
			txtDate.setDisable(true);

			// �ð� �� ��������
			txtTime.setText(comboTime.getSelectionModel().getSelectedItem());
			txtTime.setDisable(true);

			/*
			 * Ȯ�� ��ư Ŭ�� �� ���������� �˷��ش�.
			 */
			btnReOK.setOnAction(e1 -> {

				try {
					if (txtRcvName.getText().equals("") || txtRcvPostCode.getText().equals("")
							|| txtRcvAddress.getText().equals("") || txtRcvPhone1.getText().equals("")
							|| txtRcvPhone2.getText().equals("") || txtRcvPhone3.getText().equals("")
							|| txtPlace.getText().equals("") || txtType.getText().equals("")
							|| txtWeight.getText().equals("") || txtDate.getText().equals("")
							|| txtTime.getText().equals("")) {

						userMainController.alertDisplay(1, "���� ����", "���������� �ٽ� Ȯ�����ּ���.", "���� ���� �Է����ּ���.");

					} else if (txtweight > 6) {
						userMainController.alertDisplay(1, "kg �ʰ�", "5kg ���Ϸ� �����ּ���", "5kg �ʰ� ��ǰ�� �����ͷ� ���ǹٶ��ϴ�.");
					} else {
						Parent bookView4 = null;
						Stage bookStage4 = null;
						bookView4 = FXMLLoader.load(getClass().getResource("/view/userWeightShow.fxml"));
						bookStage4 = new Stage();
						Scene scene4 = new Scene(bookView4);
						bookStage4.setScene(scene4);
						bookStage4.setTitle("��������");
//						((Stage) btnJoMem.getScene().getWindow()).close();
						bookStage4.setResizable(false);
						bookStage4.show();

						Label lblMemWName = (Label) bookView4.lookup("#lblMemWName");
						Label lblWeight = (Label) bookView4.lookup("#lblWeight");
						Button btnWCancel = (Button) bookView4.lookup("#btnWCancel");
						Button btnWOK = (Button) bookView4.lookup("#btnWOK");

						lblMemWName.setText(txtBkSndName.getText());

						// ���ӿ�� �����ֱ�
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

						// ��ҹ�ư Ŭ�� �� ����â�� ����Ȯ��â�� �ݾƼ� �߼��Ⱦ�����â�� ���̰� �Ѵ�.
						btnWCancel.setOnAction(e2 -> {
							Stage stage = (Stage) btnWCancel.getScene().getWindow();
							stage.close();
							Stage stage2 = (Stage) btnReCancel.getScene().getWindow();
							stage2.close();
						});

						// Ȯ�ι�ư Ŭ�� �� �߼��Ⱦ�����Ϸ�â ���.
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
							System.out.println("****������ư�׷�*********" + hopePlaceGroupSelectedString);
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
							userMainController.alertDisplay(1, "��ϼ���", "�ƿ��ٿ�����̺� ��� ����", "��������");

							try {
								Parent bookView5 = null;
								Stage bookStage5 = null;
								bookView5 = FXMLLoader.load(getClass().getResource("/view/userBookComplete.fxml"));
								bookStage5 = new Stage();
								Scene scene5 = new Scene(bookView5);
								bookStage5.setScene(scene5);
								bookStage5.setTitle("����Ϸ�");
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
									userMainController.alertDisplay(2, "�����մϴ�.", "�̿����ּż� �����մϴ�.", "�������� �̿����ּ���~><");
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

			// ��� ��ư ������ �����˾�â �ݱ�
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
			userMainController.alertDisplay(1, "���", "DB �������� ����", "���˿��");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			bookVO = list.get(i);
//		data.add(bookVO);
		}

	}

}
