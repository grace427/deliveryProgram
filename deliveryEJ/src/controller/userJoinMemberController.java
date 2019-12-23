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

		// 테스트
//		txtJoName.setText("김지수");
//		txtJoPhone1.setText("010");
//		txtJoPhone2.setText("2828");
//		txtJoPhone3.setText("8705");
//		txtJoPw.setText("123456");
//		txtJoRePw.setText("123456");
//		txtJoPostCode.setText("10372");
//		txtJoAddress.setText("경기도 고양시 일산서구 후곡로 55 205동 401호");

		// 우편번호 찾기 버튼
		btnFdPostCode.setOnAction(e -> {
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

		// 회원가입 창의 '확인'버튼
		btnJoOK.setOnAction(e -> {
			handlerBtnJoOkAction(e);
		});

		// 회원가입 창의 '취소'버튼
		btnJoCancel.setOnAction(e -> {
			Platform.exit();
		});

		// 테스트 (우편번호 찾기 테이블뷰 셋팅)

		/*
		 * 회원가입 전화번호 길이 제한 (txtJoPhone1)
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
		 * 회원가입 전화번호 길이 제한 (txtJoPhone2)
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
		 * 회원가입 전화번호 길이 제한 (txtJoPhone3)
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
		 * 비밀번호 입력란 길이 제한두기 (txtJoPw)
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
		 * 비밀번호 입력란 길이 제한두기 (txtJoRePw)
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
//		 * 이름 입력란 길이 제한두기(데이터베이스 참고)
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
	 * 우편번호 찾기******************************************************* 우편번호 찾기 버튼 클릭
	 * 시 우편번호 찾기 창이 열린다. 텍스트 필드에 도로명 주소 입력하고 검색 클릭 시 테이블 뷰에 해당 주소목록이 나온다. 테이블뷰 더블클릭
	 * 시 txtJoPostCode와 txtJoAddress주소가 들어간다.
	 */
	private void handlerBtnPostCodeAction(ActionEvent e) {

		Parent JoView = null;
		Stage JoStage = null;
		try {
			JoView = FXMLLoader.load(getClass().getResource("/view/userFindPostCode.fxml"));
			JoStage = new Stage();
			Scene scene = new Scene(JoView);
			JoStage.setScene(scene);
			JoStage.setTitle("우편번호 찾기");
			JoStage.setResizable(false);
			JoStage.show();

			// 테이블 뷰 세팅 데이터베이스

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/***************************************************************/

	/*
	 * 회원가입 창의 '확인'버튼********************************************** '확인'버튼 클릭 시 로그인
	 * 창으로 이동한다.
	 */
	public void handlerBtnJoOkAction(ActionEvent e) {
		if (txtJoName.getText().equals("")) {
			userMainController.alertDisplay(1, "회원가입 실패", "이름을 입력해주세요.", "이름을 다시 입력해주세요.");
		} else if (txtJoPhone1.getText().equals("") || txtJoPhone2.getText().equals("")
				|| txtJoPhone3.getText().equals("")) {
			userMainController.alertDisplay(1, "회원가입 실패", "연락처를 입력해주세요.", "연락처를 다시 입력해주세요.");
		} else if (txtJoPw.getText().equals("") || txtJoRePw.getText().equals("")) {
			userMainController.alertDisplay(1, "회원가입 실패", "비밀번호를 입력해주세요.", "비밀번호를 다시 입력해주세요.");
		} else if (txtJoPostCode.getText().equals("") || txtJoAddress.getText().equals("")) {
			userMainController.alertDisplay(1, "회원가입 실패", "주소를 입력해주세요", "주소를 다시 입력해주세요.");
		} else if (!txtJoPw.getText().equals(txtJoRePw.getText())) {
			userMainController.alertDisplay(1, "회원가입 실패", "비밀번호가 일치하지 않습니다.", "비밀번호를 다시 입력해주세요.");
		} else {

			userMainController.alertDisplay(5, "회원가입 성공", "회원가입에 성공하셨습니다.", "로그인 창으로 이동합니다.");
			Stage stage = (Stage) btnJoOK.getScene().getWindow();
			stage.close();

			try {
				Parent loginView = null;
				Stage loginStage = null;
				loginView = FXMLLoader.load(getClass().getResource("/view/userLogin.fxml"));
				loginStage = new Stage();
				Scene scene = new Scene(loginView);
				loginStage.setScene(scene);
				loginStage.setTitle("로그인");
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
			userMainController.alertDisplay(1, "등록성공", "테이블 등록 성공", "ㅊㅋㅊㅋ");
		}

		// 회원가입 정보 입력란이 비어있지 않고 비밀번호 입력란과 비밀번호 재입력의 값이 같으면
		// 회원가입 버튼을 클릭 했을 때 데이버테이스에 회원정보가 저장되고 로그인창으로 넘어간다.
//			if(txtJoName.getText()!=null && txtJoPhone1.getText()!=null && txtJoPhone2.getText()!=null && txtJoPhone3.getText()!=null
//					&& txtJoPw.getText()!=null && txtJoRePw.getText()!=null && txtJoPostCode.getText()!=null && txtJoAddress.getText()!=null
//					) {
//	
//			}

	}

	/***************************************************************/

	/*
	 * 테이블뷰에 데이터베이스 값을 읽어서 테이블뷰에 가져온다.
	 * 
	 */
	public void totalList() {
		ArrayList<userMemberVO> list = null;
		userMemberDAO memberDAO = new userMemberDAO();
		userMemberVO memberVO = null;
		list = memberDAO.getMemberTotal();

		if (list == null) {
			userMainController.alertDisplay(1, "경고", "DB 가져오기 오류", "점검요망");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			memberVO = list.get(i);
//		data.add(memberVO);
		}

	}
}
