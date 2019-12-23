package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class userLoginFailController implements Initializable {

	@FXML
	private Button btnCancel;
	@FXML
	private Button btnRetry;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//로그인 실패 창 : 취소 버튼
		btnCancel.setOnAction(e -> {
			handlerBtnCancelAction(e);
		});;
		
		//로그인 실패 창 : 다시 시도 버튼
		btnRetry.setOnAction(e -> {
			handlerBtnRetryAction(e);
		});;
	}

	/*로그인 실패 창 : 취소 버튼
	 *취소 버튼 누르면 프로그램을 종료한다.
	 */
	private void handlerBtnCancelAction(ActionEvent e) {
		
	}
	
	/*로그인 실패 창 : 다시 시도 버튼
	 * 다시 시도 버튼을 누르면 로그인 페이지로 이동한다. 
	 */
	private void handlerBtnRetryAction(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
