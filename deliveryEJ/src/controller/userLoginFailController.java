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
		
		//�α��� ���� â : ��� ��ư
		btnCancel.setOnAction(e -> {
			handlerBtnCancelAction(e);
		});;
		
		//�α��� ���� â : �ٽ� �õ� ��ư
		btnRetry.setOnAction(e -> {
			handlerBtnRetryAction(e);
		});;
	}

	/*�α��� ���� â : ��� ��ư
	 *��� ��ư ������ ���α׷��� �����Ѵ�.
	 */
	private void handlerBtnCancelAction(ActionEvent e) {
		
	}
	
	/*�α��� ���� â : �ٽ� �õ� ��ư
	 * �ٽ� �õ� ��ư�� ������ �α��� �������� �̵��Ѵ�. 
	 */
	private void handlerBtnRetryAction(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
