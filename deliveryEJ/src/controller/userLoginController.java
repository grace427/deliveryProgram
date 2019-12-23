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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.userMemberVO;

public class userLoginController implements Initializable {

	/*
	 * �α��� ȭ�� ���� ����ó, ��й�ȣ, ȸ�����Թ�ư, �α��� ��ư, ��й�ȣ ã�� ��ư
	 */
	@FXML
	private TextField txtLoginPhone;
	@FXML
	private TextField txtPw;
	@FXML
	private Button btnJoMem;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnFdPw;

	private ObservableList<userMemberVO> selectMember;

	public static String userPhone;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// ��й�ȣ ã�� ��ư
		btnFdPw.setOnAction(e -> {
			handlerBtnFdPwAction(e);
		});

		// ȸ������ ��ư
		btnJoMem.setOnAction(e -> {
			handlerBtnJoMemAction(e);
		});

		// �α��� ��ư
		btnLogin.setOnAction(e -> {
			handlerBtnLoginAction(e);
		});

		// �׽�Ʈ��
//		txtLoginPhone.setText("01028288705");
//		txtPw.setText("123456");

	}// end of initialize

	/*
	 * ��й�ȣ ã��******************************************************************* ��ư
	 * ��й�ȣ ã�� ��ư Ŭ�� �� ��й�ȣ ã�� �������� �̵��Ѵ�. ȸ�� �̸�, ����ó, Ȯ�ι�ư, ��ҹ�ư
	 */
	public void handlerBtnFdPwAction(ActionEvent e) {
		Parent pwView = null;
		Stage pwStage = null;

		try {
			// ��й�ȣ ã�� ��ư Ŭ�� �� ->��й�ȣ �˷��ֱ� ���� ����� ���� �Է�â ����
			pwView = FXMLLoader.load(getClass().getResource("/view/userPassWord.fxml"));
			pwStage = new Stage();
			Scene scene = new Scene(pwView);
			pwStage.setScene(scene);
			pwStage.setTitle("��й�ȣ ã��");
			pwStage.setResizable(false);
			pwStage.initModality(Modality.WINDOW_MODAL);
			((Stage) btnFdPw.getScene().getWindow()).close();
			pwStage.show();

			// userPassWord.fxml�� ID�� �� �� lookup���� �ҷ�����
			TextField txtFdPwName = (TextField) pwView.lookup("#txtFdPwName");
			TextField txtFdPwPhone1 = (TextField) pwView.lookup("#txtFdPwPhone1");
			TextField txtFdPwPhone2 = (TextField) pwView.lookup("#txtFdPwPhone2");
			TextField txtFdPwPhone3 = (TextField) pwView.lookup("#txtFdPwPhone3");
			Button btnFind = (Button) pwView.lookup("#btnFind");
			Button btnCancel = (Button) pwView.lookup("#btnCancel");

			// �׽�Ʈ
//			txtFdPwName.setText("������");
//			txtFdPwPhone1.setText("010");
//			txtFdPwPhone2.setText("2828");
//			txtFdPwPhone3.setText("8705");

			// ��й�ȣ ã�� â���� ��� ��ư Ŭ�� �� ���α׷� ����
			btnCancel.setOnAction(e1 -> {
				Platform.exit();
			});

			/*
			 * ����ó �Է¶� ���� ���ѵα� (txtFdPwPhone1)
			 */
			DecimalFormat format = new DecimalFormat("###");
			txtFdPwPhone1.setTextFormatter(new TextFormatter<>(event -> {
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
			 * ����ó �Է¶� ���� ���ѵα� (txtFdPwPhone2)
			 */
			txtFdPwPhone2.setTextFormatter(new TextFormatter<>(event -> {
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
			 * ����ó �Է¶� ���� ���ѵα� (txtFdPwPhone3)
			 */
			txtFdPwPhone3.setTextFormatter(new TextFormatter<>(event -> {
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
			 * �α��� ������ ����ó �Է¶� ���� ���ѵα� (txtLoginPhone)
			 */
			txtLoginPhone.setTextFormatter(new TextFormatter<>(event -> {
				if (event.getControlNewText().isEmpty()) {
					return event;
				}
				ParsePosition parsePosition = new ParsePosition(0);
				Object object = format.parse(event.getControlNewText(), parsePosition);

				if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
						|| event.getControlNewText().length() == 12) {
					return null;
				} else {
					return event;
				}
			}));

			/*
			 * ��й�ȣ ã�� ��ư Ŭ�� �� �����ͺ��̽��� ��ϵǾ��ִ� ȸ���̸���, ����ó�� ��ġ�ϸ� ȸ���� ��й�ȣ�� �˷��ش�. ȸ���̸��� ����ó��
			 * �����̸� ����â ���� ȸ���̸����� ���ڰ� ���� �� �� ����ó �Է¶��� 3,4,4 ������ ���ڸ� �� �� �ִ�.
			 */
			btnFind.setOnAction(e1 -> {

				try {
					ArrayList<userMemberVO> list = new ArrayList<userMemberVO>();
					userFdPwDAO ufpwDAO = new userFdPwDAO();
					list = ufpwDAO.getPassWord(txtFdPwName.getText(), txtFdPwPhone1.getText(), txtFdPwPhone2.getText(),
							txtFdPwPhone3.getText());

					if (list.size() != 0) {
						Parent pwView2 = null;
						Stage pwStage2 = null;
						pwView2 = FXMLLoader.load(getClass().getResource("/view/userPassWordInfo.fxml"));
						pwStage2 = new Stage();
						Scene scene2 = new Scene(pwView2);
						pwStage2.setScene(scene2);
						pwStage2.setTitle("��й�ȣ ã��");
						pwStage2.setResizable(false);
						pwStage2.initModality(Modality.WINDOW_MODAL);
						((Stage) btnFind.getScene().getWindow()).close();
						pwStage2.show();

						// userPassWordInfo.fxml�� ID�� �� �� lookup���� �ҷ�����
						Label lblMemName = (Label) pwView2.lookup("#lblMemName");
						Label lblPw = (Label) pwView2.lookup("#lblPw");
						Button btnGoLogin = (Button) pwView2.lookup("#btnGoLogin");
						Button btnClose = (Button) pwView2.lookup("#btnClose");

						userFdPwDAO fdDAO=new userFdPwDAO();
						userMemberVO list1=new userMemberVO();
						list1=fdDAO.getPassWordInfo(txtFdPwPhone1.getText()+txtFdPwPhone2.getText()+txtFdPwPhone3.getText());
						System.out.println(txtFdPwPhone1.getText()+txtFdPwPhone2.getText()+txtFdPwPhone3.getText()+"1111111");
						lblMemName.setText(list1.getMemName());
						// ȸ������ �� �����ͺ��̽��� ������ ������ Ȯ���ؼ� ��й�ȣ�� ����ش�.
						lblPw.setText(list1.getMemPW());

						// ��й�ȣ ���� â���� �α����Ϸ����� ��ư Ŭ�� �� ����ȭ������ �̵�.
						btnGoLogin.setOnAction(e2 -> {
							try {
								Parent mainView = null;
								Stage mainStage = null;
								mainView = FXMLLoader.load(getClass().getResource("/view/userMain.fxml"));
								mainStage = new Stage();
								Scene scene3 = new Scene(mainView);
								mainStage.setScene(scene3);
								((Stage) btnGoLogin.getScene().getWindow()).close();
								mainStage.setResizable(false);
								mainStage.initModality(Modality.WINDOW_MODAL);
								mainStage.setTitle("Java �ù� ���");
								((Stage) btnFind.getScene().getWindow()).close();
								mainStage.show();
							} catch (IOException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
						});

						btnClose.setOnAction(e2 -> {
							Platform.exit();
						});

					}

					else if (txtFdPwName.getText().equals("") || txtFdPwPhone1.getText().equals("")
							|| txtFdPwPhone2.getText().equals("") || txtFdPwPhone3.getText().equals("")) {
						userMainController.alertDisplay(1, "��й�ȣ ã�� ����", "ȸ�������� ã�� ��  �����ϴ�.", "�̸��� ����ó�� ���� ���� �Է����ּ���.");
					}

					else {
						userMainController.alertDisplay(1, "��й�ȣ ã�� ����", "�Է��� ������ ��ġ�ϴ� ȸ�������� �����ϴ�.",
								"�̸��� ����ó�� �ٽ� �Է����ּ���.");
					}
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			});// end of btnFind

		} catch (IOException e1) {
			System.out.println("��й�ȣ ã�� â ���� ����" + "***************" + e1);
			e1.printStackTrace();
		}

	}// end of handlerBtnPwAction

	/****************************************************************************************/

	/*
	 * �α��� ���� �˾� â �α��� ȭ�� ����ó �ʵ�� ��й�ȣ�� ��ġ�ϸ� �α��� ���ش�. ����ó�� �����ͺ��̽��� ���� ���� ���� 'ȸ��������
	 * ��ġ���� �ʽ��ϴ�' ���â ����. ����ó�� �Է��ϰ� ��й�ȣ�� �Է����� �ʰ� �α��� ��ư ������ '��й�ȣ�� �Է����ּ���' ���â
	 * ����. ��й�ȣ�� �Է��ϰ� ����ó�� �Է����� �ʾ��� ���� '��й�ȣ�� �Է����ּ���' ���â ����.
	 */
	public void handlerBtnLoginAction(ActionEvent e) {
		userPhone = txtLoginPhone.getText();

		try {

			ArrayList<userMemberVO> list = new ArrayList<userMemberVO>();
			userLoginDAO ulDAO = new userLoginDAO();
			list = ulDAO.getLogin(txtLoginPhone.getText(), txtPw.getText());
			System.out.println(list.size());
			if (list.size() != 0) {
				Parent bookView = null;
				Stage bookStage = null;

				bookView = FXMLLoader.load(getClass().getResource("/view/userBook.fxml"));
				bookStage = new Stage();
				Scene scene = new Scene(bookView);
				bookStage.setScene(scene);
				bookStage.setTitle("�ù��Ⱦ�����");
				bookStage.setResizable(false);
				((Stage) btnLogin.getScene().getWindow()).close();
				bookStage.show();

			} else if (txtLoginPhone.getText().equals("") || txtPw.getText().equals("")) {
				userMainController.alertDisplay(1, "�α��� ����", "�α��ο� �����߽��ϴ�.", "����ó�� ��й�ȣ�� �Է����ּ���.");
			} else {
				userMainController.alertDisplay(1, "�α��� ����", "ȸ�������� �����ϴ�.", "ȸ�������� �������ּ���.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

//		userPhone=txtLoginPhone.getText();

	/*
	 * ȸ������ ��ư ȸ������ ��ư Ŭ�� �� ȸ������ ȭ������ �̵��Ѵ�.
	 */
	public void handlerBtnJoMemAction(ActionEvent e) {
		Parent loginView = null;
		Stage loginStage = null;
		try {
			loginView = FXMLLoader.load(getClass().getResource("/view/userJoinMember.fxml"));
			loginStage = new Stage();
			Scene scene = new Scene(loginView);
			loginStage.setScene(scene);
			loginStage.setTitle("ȸ������");
			loginStage.setResizable(false);
			((Stage) btnJoMem.getScene().getWindow()).close();
			loginStage.show();
		} catch (IOException e1) {
			System.out.println("ȸ������ â ���� ����");
			e1.printStackTrace();
		}

	}// end of handlerBtnJoMemAction

}
