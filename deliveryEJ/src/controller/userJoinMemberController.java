package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import model.userMemberVO;

public class userJoinMemberController implements Initializable {

	@FXML
	private TextField txtJoName;
	@FXML
	private TextField txtJoPhone1;
	@FXML
	private TextField txtJoPhone2;
	@FXML
	private TextField txtJoPhone3;
	@FXML
	private TextField txtJoPw;
	@FXML
	private TextField txtJoRePw;
	@FXML
	private TextField txtJoPostCode;
	@FXML
	private TextField txtJoAddress;
	@FXML
	private Button btnFdPostCode;
	@FXML
	private Button btnJoOK;
	@FXML
	private Button btnJoCancel;

	private boolean editDelete = false;
	private int selectedIndex;
	ObservableList<userMemberVO> data;
	private userMemberDAO memberDAO;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// �׽�Ʈ
//		txtJoName.setText("������");
//		txtJoPhone1.setText("010");
//		txtJoPhone2.setText("2828");
//		txtJoPhone3.setText("8705");
//		txtJoPw.setText("123456");
//		txtJoRePw.setText("123456");
//		txtJoPostCode.setText("10372");
//		txtJoAddress.setText("��⵵ ���� �ϻ꼭�� �İ�� 55 205�� 401ȣ");

		// �����ȣ ã�� ��ư
		btnFdPostCode.setOnAction(e -> {
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

		// ȸ������ â�� 'Ȯ��'��ư
		btnJoOK.setOnAction(e -> {
			handlerBtnJoOkAction(e);
		});

		// ȸ������ â�� '���'��ư
		btnJoCancel.setOnAction(e -> {
			Platform.exit();
		});

		// �׽�Ʈ (�����ȣ ã�� ���̺�� ����)

		/*
		 * ȸ������ ��ȭ��ȣ ���� ���� (txtJoPhone1)
		 */
		DecimalFormat format = new DecimalFormat("###");
		txtJoPhone1.setTextFormatter(new TextFormatter<>(event -> {
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
		 * ȸ������ ��ȭ��ȣ ���� ���� (txtJoPhone2)
		 */
		txtJoPhone2.setTextFormatter(new TextFormatter<>(event -> {
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
		 * ȸ������ ��ȭ��ȣ ���� ���� (txtJoPhone3)
		 */
		txtJoPhone3.setTextFormatter(new TextFormatter<>(event -> {
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
		 * ��й�ȣ �Է¶� ���� ���ѵα� (txtJoPw)
		 */
		DecimalFormat format1 = new DecimalFormat("###");
		txtJoPw.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format1.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 7) {
				return null;
			} else {
				return event;
			}
		}));

		/*
		 * ��й�ȣ �Է¶� ���� ���ѵα� (txtJoRePw)
		 */
		txtJoRePw.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format1.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 7) {
				return null;
			} else {
				return event;
			}
		}));

//		/*
//		 * �̸� �Է¶� ���� ���ѵα�(�����ͺ��̽� ����)
//		 */
//		txtJoName.setTextFormatter(new TextFormatter<>(event -> {
//			if (event.getControlNewText().isEmpty()) {
//				return event;
//			}
//			ParsePosition parsePosition = new ParsePosition(0);
//			Object object = format.parse(event.getControlNewText(), parsePosition);
//
//			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
//					|| event.getControlNewText().length() == 10) {
//				return null;
//			} else {
//				return event;
//			}
//		}));

	}

	/*
	 * �����ȣ ã��******************************************************* �����ȣ ã�� ��ư Ŭ��
	 * �� �����ȣ ã�� â�� ������. �ؽ�Ʈ �ʵ忡 ���θ� �ּ� �Է��ϰ� �˻� Ŭ�� �� ���̺� �信 �ش� �ּҸ���� ���´�. ���̺�� ����Ŭ��
	 * �� txtJoPostCode�� txtJoAddress�ּҰ� ����.
	 */
	private void handlerBtnPostCodeAction(ActionEvent e) {

		Parent JoView = null;
		Stage JoStage = null;
		try {
			JoView = FXMLLoader.load(getClass().getResource("/view/userFindPostCode.fxml"));
			JoStage = new Stage();
			Scene scene = new Scene(JoView);
			JoStage.setScene(scene);
			JoStage.setTitle("�����ȣ ã��");
			JoStage.setResizable(false);
			JoStage.show();

			// ���̺� �� ���� �����ͺ��̽�

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/***************************************************************/

	/*
	 * ȸ������ â�� 'Ȯ��'��ư********************************************** 'Ȯ��'��ư Ŭ�� �� �α���
	 * â���� �̵��Ѵ�.
	 */
	public void handlerBtnJoOkAction(ActionEvent e) {
		if (txtJoName.getText().equals("")) {
			userMainController.alertDisplay(1, "ȸ������ ����", "�̸��� �Է����ּ���.", "�̸��� �ٽ� �Է����ּ���.");
		} else if (txtJoPhone1.getText().equals("") || txtJoPhone2.getText().equals("")
				|| txtJoPhone3.getText().equals("")) {
			userMainController.alertDisplay(1, "ȸ������ ����", "����ó�� �Է����ּ���.", "����ó�� �ٽ� �Է����ּ���.");
		} else if (txtJoPw.getText().equals("") || txtJoRePw.getText().equals("")) {
			userMainController.alertDisplay(1, "ȸ������ ����", "��й�ȣ�� �Է����ּ���.", "��й�ȣ�� �ٽ� �Է����ּ���.");
		} else if (txtJoPostCode.getText().equals("") || txtJoAddress.getText().equals("")) {
			userMainController.alertDisplay(1, "ȸ������ ����", "�ּҸ� �Է����ּ���", "�ּҸ� �ٽ� �Է����ּ���.");
		} else if (!txtJoPw.getText().equals(txtJoRePw.getText())) {
			userMainController.alertDisplay(1, "ȸ������ ����", "��й�ȣ�� ��ġ���� �ʽ��ϴ�.", "��й�ȣ�� �ٽ� �Է����ּ���.");
		} else {

			userMainController.alertDisplay(5, "ȸ������ ����", "ȸ�����Կ� �����ϼ̽��ϴ�.", "�α��� â���� �̵��մϴ�.");
			Stage stage = (Stage) btnJoOK.getScene().getWindow();
			stage.close();

			try {
				Parent loginView = null;
				Stage loginStage = null;
				loginView = FXMLLoader.load(getClass().getResource("/view/userLogin.fxml"));
				loginStage = new Stage();
				Scene scene = new Scene(loginView);
				loginStage.setScene(scene);
				loginStage.setTitle("�α���");
//					((Stage) btnJoMem.getScene().getWindow()).close();
				loginStage.setResizable(false);
				loginStage.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			userMemberVO umvo = new userMemberVO(0, txtJoName.getText().trim(),
					txtJoPhone1.getText().trim() + txtJoPhone2.getText().trim() + txtJoPhone3.getText().trim(),
					txtJoPostCode.getText().trim(), txtJoAddress.getText().trim(), txtJoPw.getText().trim());

			if (editDelete == true) {
				data.remove(selectedIndex);
				data.add(selectedIndex, umvo);
				editDelete = false;
			} else {
				memberDAO = new userMemberDAO();

				int count = memberDAO.getMemberRegister(umvo);
				if (count != 0) {
//					data.removeAll(data);
					totalList();
				}
			}
			userMainController.alertDisplay(1, "��ϼ���", "���̺� ��� ����", "��������");
		}

		// ȸ������ ���� �Է¶��� ������� �ʰ� ��й�ȣ �Է¶��� ��й�ȣ ���Է��� ���� ������
		// ȸ������ ��ư�� Ŭ�� ���� �� ���̹����̽��� ȸ�������� ����ǰ� �α���â���� �Ѿ��.
//			if(txtJoName.getText()!=null && txtJoPhone1.getText()!=null && txtJoPhone2.getText()!=null && txtJoPhone3.getText()!=null
//					&& txtJoPw.getText()!=null && txtJoRePw.getText()!=null && txtJoPostCode.getText()!=null && txtJoAddress.getText()!=null
//					) {
//	
//			}

	}

	/***************************************************************/

	/*
	 * ���̺�信 �����ͺ��̽� ���� �о ���̺�信 �����´�.
	 * 
	 */
	public void totalList() {
		ArrayList<userMemberVO> list = null;
		userMemberDAO memberDAO = new userMemberDAO();
		userMemberVO memberVO = null;
		list = memberDAO.getMemberTotal();

		if (list == null) {
			userMainController.alertDisplay(1, "���", "DB �������� ����", "���˿��");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			memberVO = list.get(i);
//		data.add(memberVO);
		}

	}
}
