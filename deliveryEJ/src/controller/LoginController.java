package controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	@FXML
	private TextField txtAdminID;
	@FXML
	private PasswordField txtAdminPW;
	@FXML
	private Button btnAdminLogin;
	@FXML
	private Button btnAdminCancel;

	Stage stageDialog = null;
	Stage mainStage = null;	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ������ �α���
		btnAdminLogin.setOnAction((e) -> {
			handlerButtonLoginAction(e);
		});
		// ���
		btnAdminCancel.setOnAction((e) -> {
			handlerButtonCancelAction(e);
		});
		// �����
		txtAdminID.setText("admin");
		txtAdminPW.setText("1111");
	} // end initialize

	// �α��� ��ư
	public void handlerButtonLoginAction(ActionEvent e) {
		// 1. ������ ���̵�, �н����� ���Է½�
		if (txtAdminID.getText().equals("") || txtAdminPW.getText().equals("")) {
			Utility.alertDisplay(1, "�α��� ����", "���̵� �Ǵ� �н����� ���Է�", "�������� �ٽ� �Է��ϼ���");
			// 2. ���̵�� �н����带 ����� �Է����� ���
		} else if (txtAdminID.getText().equals("admin") && txtAdminPW.getText().equals("1111")) {
			// �α��� �� �Ǿ����� �α��� â �ݰ� �������� â���� �̵�, �������� â ������ ����â ���´�
			Parent loginInfo = null;
			try {
				// stageDialog = ������ �α��� �������� â  ******************************************************************//
				loginInfo = FXMLLoader.load(getClass().getResource("/view/adminLoginInfo.fxml"));
				stageDialog = new Stage();
				Scene scene1 = new Scene(loginInfo);
				stageDialog.setTitle("������ �α��� ����");
				stageDialog.setScene(scene1);
				stageDialog.setResizable(true);

				Label lblAdmin = (Label) loginInfo.lookup("#lblAdmin");
				Label lblLoginDate = (Label) loginInfo.lookup("#lblLoginDate");
				TextArea txtAreaNotice = (TextArea) loginInfo.lookup("#txtAreaNotice");
				Button btnOK = (Button) loginInfo.lookup("#btnOK");

				lblAdmin.setText(txtAdminID.getText());
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date today = new Date();
				lblLoginDate.setText(format1.format(today));				
				txtAreaNotice.setText("** �������� Notice **\n" + "�������� �Է����ּ���.�̹� �� ��ǥ �����Ȳ ");

				stageDialog.show();
				((Stage) btnAdminLogin.getScene().getWindow()).close();
				// **********************************************************************************************//
				btnOK.setOnAction(event -> {
					handlerButtonNoticeOKAction(event);
				});				

			} catch (IOException e1) {
				System.out.println("������ �������� â �θ��� ����" + e1.toString());
			}
		} else {
			Utility.alertDisplay(2, "�α��� ����", "���̵�� �н����� ����ġ", "�������� �ٽ� �Է��ϼ���");
		}
	}

	// �������� â���� Ȯ�� ������ â ������ ���� â ������
	public void handlerButtonNoticeOKAction(ActionEvent event) {
		Parent mainView = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/dashBoard.fxml"));
			mainStage = new Stage();
			Scene scene = new Scene(mainView);
			mainStage.setTitle("���� ������");
			mainStage.setScene(scene);
			mainStage.setResizable(true);
			mainStage.show();
		} catch (IOException e2) {
			System.out.println("���� â �θ��� ����" + e2.toString());
		}
		stageDialog.close();
	}

	// ���
	private void handlerButtonCancelAction(ActionEvent e) {
		((Stage) btnAdminLogin.getScene().getWindow()).close();
	}

} // end of LoginController
