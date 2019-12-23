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
		// 관리자 로그인
		btnAdminLogin.setOnAction((e) -> {
			handlerButtonLoginAction(e);
		});
		// 취소
		btnAdminCancel.setOnAction((e) -> {
			handlerButtonCancelAction(e);
		});
		// 디버깅
		txtAdminID.setText("admin");
		txtAdminPW.setText("1111");
	} // end initialize

	// 로그인 버튼
	public void handlerButtonLoginAction(ActionEvent e) {
		// 1. 관리자 아이디, 패스워드 미입력시
		if (txtAdminID.getText().equals("") || txtAdminPW.getText().equals("")) {
			Utility.alertDisplay(1, "로그인 실패", "아이디 또는 패스워드 미입력", "정신차려 다시 입력하세요");
			// 2. 아이디와 패스워드를 제대로 입력했을 경우
		} else if (txtAdminID.getText().equals("admin") && txtAdminPW.getText().equals("1111")) {
			// 로그인 잘 되었으면 로그인 창 닫고 공지사항 창으로 이동, 공지사항 창 닫으면 메인창 나온다
			Parent loginInfo = null;
			try {
				// stageDialog = 관리자 로그인 공지사항 창  ******************************************************************//
				loginInfo = FXMLLoader.load(getClass().getResource("/view/adminLoginInfo.fxml"));
				stageDialog = new Stage();
				Scene scene1 = new Scene(loginInfo);
				stageDialog.setTitle("관리자 로그인 성공");
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
				txtAreaNotice.setText("** 공지사항 Notice **\n" + "공지사항 입력해주세요.이번 달 목표 진행상황 ");

				stageDialog.show();
				((Stage) btnAdminLogin.getScene().getWindow()).close();
				// **********************************************************************************************//
				btnOK.setOnAction(event -> {
					handlerButtonNoticeOKAction(event);
				});				

			} catch (IOException e1) {
				System.out.println("관리자 공지사항 창 부르기 오류" + e1.toString());
			}
		} else {
			Utility.alertDisplay(2, "로그인 실패", "아이디와 패스워드 불일치", "정신차려 다시 입력하세요");
		}
	}

	// 공지사항 창에서 확인 누르면 창 꺼지고 메인 창 나오기
	public void handlerButtonNoticeOKAction(ActionEvent event) {
		Parent mainView = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/dashBoard.fxml"));
			mainStage = new Stage();
			Scene scene = new Scene(mainView);
			mainStage.setTitle("메인 윈도우");
			mainStage.setScene(scene);
			mainStage.setResizable(true);
			mainStage.show();
		} catch (IOException e2) {
			System.out.println("메인 창 부르기 오류" + e2.toString());
		}
		stageDialog.close();
	}

	// 취소
	private void handlerButtonCancelAction(ActionEvent e) {
		((Stage) btnAdminLogin.getScene().getWindow()).close();
	}

} // end of LoginController
