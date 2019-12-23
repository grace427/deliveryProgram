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
	 * 로그인 화면 변수 연락처, 비밀번호, 회원가입버튼, 로그인 버튼, 비밀번호 찾기 버튼
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

		// 비밀번호 찾기 버튼
		btnFdPw.setOnAction(e -> {
			handlerBtnFdPwAction(e);
		});

		// 회원가입 버튼
		btnJoMem.setOnAction(e -> {
			handlerBtnJoMemAction(e);
		});

		// 로그인 버튼
		btnLogin.setOnAction(e -> {
			handlerBtnLoginAction(e);
		});

		// 테스트용
//		txtLoginPhone.setText("01028288705");
//		txtPw.setText("123456");

	}// end of initialize

	/*
	 * 비밀번호 찾기******************************************************************* 버튼
	 * 비밀번호 찾기 버튼 클릭 시 비밀번호 찾기 페이지로 이동한다. 회원 이름, 연락처, 확인버튼, 취소버튼
	 */
	public void handlerBtnFdPwAction(ActionEvent e) {
		Parent pwView = null;
		Stage pwStage = null;

		try {
			// 비밀번호 찾기 버튼 클릭 시 ->비밀번호 알려주기 위한 사용자 정보 입력창 띄우기
			pwView = FXMLLoader.load(getClass().getResource("/view/userPassWord.fxml"));
			pwStage = new Stage();
			Scene scene = new Scene(pwView);
			pwStage.setScene(scene);
			pwStage.setTitle("비밀번호 찾기");
			pwStage.setResizable(false);
			pwStage.initModality(Modality.WINDOW_MODAL);
			((Stage) btnFdPw.getScene().getWindow()).close();
			pwStage.show();

			// userPassWord.fxml에 ID로 준 값 lookup으로 불러오기
			TextField txtFdPwName = (TextField) pwView.lookup("#txtFdPwName");
			TextField txtFdPwPhone1 = (TextField) pwView.lookup("#txtFdPwPhone1");
			TextField txtFdPwPhone2 = (TextField) pwView.lookup("#txtFdPwPhone2");
			TextField txtFdPwPhone3 = (TextField) pwView.lookup("#txtFdPwPhone3");
			Button btnFind = (Button) pwView.lookup("#btnFind");
			Button btnCancel = (Button) pwView.lookup("#btnCancel");

			// 테스트
//			txtFdPwName.setText("김지수");
//			txtFdPwPhone1.setText("010");
//			txtFdPwPhone2.setText("2828");
//			txtFdPwPhone3.setText("8705");

			// 비밀번호 찾기 창에서 취소 버튼 클릭 시 프로그램 종료
			btnCancel.setOnAction(e1 -> {
				Platform.exit();
			});

			/*
			 * 연락처 입력란 길이 제한두기 (txtFdPwPhone1)
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
			 * 연락처 입력란 길이 제한두기 (txtFdPwPhone2)
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
			 * 연락처 입력란 길이 제한두기 (txtFdPwPhone3)
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
			 * 로그인 페이지 연락처 입력란 길이 제한두기 (txtLoginPhone)
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
			 * 비밀번호 찾기 버튼 클릭 시 데이터베이스에 등록되어있는 회원이름과, 연락처가 일치하면 회원의 비밀번호를 알려준다. 회원이름과 연락처가
			 * 공란이면 오류창 띄우기 회원이름에는 숫자가 들어가면 안 됌 연락처 입력란에 3,4,4 길이의 숫자만 들어갈 수 있다.
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
						pwStage2.setTitle("비밀번호 찾기");
						pwStage2.setResizable(false);
						pwStage2.initModality(Modality.WINDOW_MODAL);
						((Stage) btnFind.getScene().getWindow()).close();
						pwStage2.show();

						// userPassWordInfo.fxml에 ID로 준 값 lookup으로 불러오기
						Label lblMemName = (Label) pwView2.lookup("#lblMemName");
						Label lblPw = (Label) pwView2.lookup("#lblPw");
						Button btnGoLogin = (Button) pwView2.lookup("#btnGoLogin");
						Button btnClose = (Button) pwView2.lookup("#btnClose");

						userFdPwDAO fdDAO=new userFdPwDAO();
						userMemberVO list1=new userMemberVO();
						list1=fdDAO.getPassWordInfo(txtFdPwPhone1.getText()+txtFdPwPhone2.getText()+txtFdPwPhone3.getText());
						System.out.println(txtFdPwPhone1.getText()+txtFdPwPhone2.getText()+txtFdPwPhone3.getText()+"1111111");
						lblMemName.setText(list1.getMemName());
						// 회원가입 시 데이터베이스에 저장한 정보를 확인해서 비밀번호를 띄어준다.
						lblPw.setText(list1.getMemPW());

						// 비밀번호 정보 창에서 로그인하러가기 버튼 클릭 시 메인화면으로 이동.
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
								mainStage.setTitle("Java 택배 잡아");
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
						userMainController.alertDisplay(1, "비밀번호 찾기 실패", "회원정보를 찾을 수  없습니다.", "이름과 연락처를 공란 없이 입력해주세요.");
					}

					else {
						userMainController.alertDisplay(1, "비밀번호 찾기 실패", "입력한 정보와 일치하는 회원정보가 없습니다.",
								"이름과 연락처를 다시 입력해주세요.");
					}
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			});// end of btnFind

		} catch (IOException e1) {
			System.out.println("비밀번호 찾기 창 열기 실패" + "***************" + e1);
			e1.printStackTrace();
		}

	}// end of handlerBtnPwAction

	/****************************************************************************************/

	/*
	 * 로그인 실패 팝업 창 로그인 화면 연락처 필드와 비밀번호가 일치하면 로그인 해준다. 연락처가 데이터베이스에 없는 값이 들어가면 '회원정보가
	 * 일치하지 않습니다' 경고창 띄우기. 연락처만 입력하고 비밀번호를 입력하지 않고 로그인 버튼 누르면 '비밀번호를 입력해주세요' 경고창
	 * 띄우기. 비밀번호만 입력하고 연락처는 입력하지 않았을 때도 '비밀번호를 입력해주세요' 경고창 띄우기.
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
				bookStage.setTitle("택배픽업예약");
				bookStage.setResizable(false);
				((Stage) btnLogin.getScene().getWindow()).close();
				bookStage.show();

			} else if (txtLoginPhone.getText().equals("") || txtPw.getText().equals("")) {
				userMainController.alertDisplay(1, "로그인 실패", "로그인에 실패했습니다.", "연락처와 비밀번호를 입력해주세요.");
			} else {
				userMainController.alertDisplay(1, "로그인 실패", "회원정보가 없습니다.", "회원가입을 진행해주세요.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

//		userPhone=txtLoginPhone.getText();

	/*
	 * 회원가입 버튼 회원가입 버튼 클릭 시 회원가입 화면으로 이동한다.
	 */
	public void handlerBtnJoMemAction(ActionEvent e) {
		Parent loginView = null;
		Stage loginStage = null;
		try {
			loginView = FXMLLoader.load(getClass().getResource("/view/userJoinMember.fxml"));
			loginStage = new Stage();
			Scene scene = new Scene(loginView);
			loginStage.setScene(scene);
			loginStage.setTitle("회원가입");
			loginStage.setResizable(false);
			((Stage) btnJoMem.getScene().getWindow()).close();
			loginStage.show();
		} catch (IOException e1) {
			System.out.println("회원가입 창 열기 실패");
			e1.printStackTrace();
		}

	}// end of handlerBtnJoMemAction

}
