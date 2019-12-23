package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class userMainController implements Initializable {

	@FXML private TextField txtTrkNum;
	@FXML public Button btnBook;
	@FXML public Button  btnMyPage;
	@FXML private Button  btnAdmin;
	@FXML private Button btnTrkNumChk;

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//조회하기 버튼
		btnTrkNumChk.setOnAction(e -> {
			handlerBtnTrkNumChkAction(e);
		});
		
		//택배픽업예약 버튼
		btnBook.setOnAction(e -> {
			handlerBtnBookAction(e);
		});
		
		//My Page 버튼
		btnMyPage.setOnAction(e -> {
			handlerBtnMyPageAction(e);
		});
		
		//관리자 모드 버튼
		btnAdmin.setOnAction(e -> {
			handlerBtnAdminAction(e);
		});
		
		//테스트용
//		txtTrkNum.setText("6547862");
		
		
	}
	
	/*조회하기 버튼 
	 * 조회버튼 클릭 시 배송조회 창이 열린다. 
	 * 송장번호 입력 칸에 값을 입력하지 않으면 '송장번호 미입력' 경고 창 뜨게 하기
	 * 송장번호 입력 칸에 숫자가 아닌 한글,영문이 들어가면 숫자만 입력할 수 있다는 경고창 뜨게 하기
	 * 송장번호 입력 칸에 송장번호 길이와 다른 값이나 부여받지 않은 송장번호를  입력하면 '송장번호 불일치'경고창 뜨게 하기
	 **/
	public void handlerBtnTrkNumChkAction(ActionEvent e) {
		if(txtTrkNum.getText().equals("")) {
			alertDisplay(1, "송장번호 조회 실패", "송장번호 미입력", "송장번호를 입력해주세요");
		} else if (txtTrkNum.getText().equals(txtTrkNum.getText())) {
			Parent firstView=null;
			Stage firstStage=null;
			try {
				firstView=FXMLLoader.load(getClass().getResource("/view/userDeliveryCheck.fxml"));
				firstStage=new Stage();
				Scene scene = new Scene(firstView);
				firstStage.setScene(scene);
				firstStage.setTitle("배송조회");
				firstStage.setResizable(false);
				((Stage)btnTrkNumChk.getScene().getWindow()).close();
				firstStage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (txtTrkNum.getText().length()!=9) {
			alertDisplay(1, "송장번호 조회 실패", "9자리 숫자로 송장번호를 조회해주세요.", "송장번호를 다시 입력해주세요");				
			} else {
			alertDisplay(1, "송장번호 조회 실패", "송장번호 불일치", "부여받은 송장번호를 바르게 입력해주세요");
		}
	}

	
	/*택배 픽업 예약 버튼
	 * 택배 픽업 예약 버튼클릭 시 로그인 화면 출력.  
	 * 로그인 성공 시 택배 픽업 예약 화면으로 이동
	 */
	public void handlerBtnBookAction(ActionEvent e) {
		Parent firstView=null;
		Stage firstStage=null;
		try {
			firstView=FXMLLoader.load(getClass().getResource("/view/userLogin.fxml"));
			firstStage=new Stage();
			Scene scene = new Scene(firstView);
			firstStage.setScene(scene);
			firstStage.setTitle("로그인 화면");
			firstStage.setResizable(false);
			((Stage)btnBook.getScene().getWindow()).close();
			firstStage.show();
		} catch (IOException e1) {
			System.out.println("택배 픽업 예약 -> 로그인 화면 이동 실패");
			e1.printStackTrace();
		}
		
	}
	

	/*My Page 버튼
	 * My Page 버튼클릭 시 로그인 화면 출력.  
	 * 로그인 성공 시 My Page 화면으로 이동
	 */
	public void handlerBtnMyPageAction(ActionEvent e) {
		Parent firstView=null;
		Stage firstStage=null;
		try {
			firstView=FXMLLoader.load(getClass().getResource("/view/userMyPageLogin.fxml"));
			firstStage=new Stage();
			Scene scene = new Scene(firstView);
			firstStage.setScene(scene);
			firstStage.setTitle("로그인 화면");
			firstStage.setResizable(false);
			((Stage)btnMyPage.getScene().getWindow()).close();
			firstStage.show();
			
			
			
			
			
		} catch (IOException e1) {
			System.out.println("My Page -> 로그인 화면 이동 실패");
			e1.printStackTrace();
		}
	}


	/*관리자 모드 버튼
	 * 관리자 모드 버튼 클릭 시 관리자모드 로그인창이 열린다. 
	 */
	public void handlerBtnAdminAction(ActionEvent e) {
		Parent firstView=null;
		Stage firstStage=null;
		try {
			firstView = FXMLLoader.load(getClass().getResource("/view/adminLogin.fxml"));
			firstStage=new Stage();
			Scene scene=new Scene(firstView);
			firstStage.setScene(scene);
			firstStage.setTitle("관리자 모드");
			firstStage.setResizable(false);
			((Stage)btnBook.getScene().getWindow()).close();
			firstStage.show();
		} catch (IOException e1) {
			System.out.println("관리자 모드 창 열기 실패");
			e1.printStackTrace();
		}
	}
	
	//경고창 처리함수
		public static void alertDisplay(int type, String title, String headerText, String contentText) {
			//Alert alert = new Alert(AlertType.WARNING); // 경고창을 열어줌.
			// 이걸 하지 않으면 팝업창이나 사용자 다이얼로그를 만들어야 한다.
			Alert alert=null;
			switch(type) {
			case 1 : alert = new Alert(AlertType.WARNING); break;
			case 2 : alert = new Alert(AlertType.CONFIRMATION); break;
			case 3 : alert = new Alert(AlertType.ERROR); break;
			case 4 : alert = new Alert(AlertType.NONE); break;
			case 5 : alert = new Alert(AlertType.INFORMATION); break;
			default : ;
			}
			alert.setTitle(title);
			alert.setHeaderText(headerText);
			alert.setContentText(headerText+"\n"+contentText);
			alert.setResizable(false);
			alert.showAndWait();
			return; // 함수를 끝내려고 쓴 것! 쓰지 않아도 무방(함수가 끝나기 때문)
				
			}
}


