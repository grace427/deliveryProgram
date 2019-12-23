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
		
		//��ȸ�ϱ� ��ư
		btnTrkNumChk.setOnAction(e -> {
			handlerBtnTrkNumChkAction(e);
		});
		
		//�ù��Ⱦ����� ��ư
		btnBook.setOnAction(e -> {
			handlerBtnBookAction(e);
		});
		
		//My Page ��ư
		btnMyPage.setOnAction(e -> {
			handlerBtnMyPageAction(e);
		});
		
		//������ ��� ��ư
		btnAdmin.setOnAction(e -> {
			handlerBtnAdminAction(e);
		});
		
		//�׽�Ʈ��
//		txtTrkNum.setText("6547862");
		
		
	}
	
	/*��ȸ�ϱ� ��ư 
	 * ��ȸ��ư Ŭ�� �� �����ȸ â�� ������. 
	 * �����ȣ �Է� ĭ�� ���� �Է����� ������ '�����ȣ ���Է�' ��� â �߰� �ϱ�
	 * �����ȣ �Է� ĭ�� ���ڰ� �ƴ� �ѱ�,������ ���� ���ڸ� �Է��� �� �ִٴ� ���â �߰� �ϱ�
	 * �����ȣ �Է� ĭ�� �����ȣ ���̿� �ٸ� ���̳� �ο����� ���� �����ȣ��  �Է��ϸ� '�����ȣ ����ġ'���â �߰� �ϱ�
	 **/
	public void handlerBtnTrkNumChkAction(ActionEvent e) {
		if(txtTrkNum.getText().equals("")) {
			alertDisplay(1, "�����ȣ ��ȸ ����", "�����ȣ ���Է�", "�����ȣ�� �Է����ּ���");
		} else if (txtTrkNum.getText().equals(txtTrkNum.getText())) {
			Parent firstView=null;
			Stage firstStage=null;
			try {
				firstView=FXMLLoader.load(getClass().getResource("/view/userDeliveryCheck.fxml"));
				firstStage=new Stage();
				Scene scene = new Scene(firstView);
				firstStage.setScene(scene);
				firstStage.setTitle("�����ȸ");
				firstStage.setResizable(false);
				((Stage)btnTrkNumChk.getScene().getWindow()).close();
				firstStage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (txtTrkNum.getText().length()!=9) {
			alertDisplay(1, "�����ȣ ��ȸ ����", "9�ڸ� ���ڷ� �����ȣ�� ��ȸ���ּ���.", "�����ȣ�� �ٽ� �Է����ּ���");				
			} else {
			alertDisplay(1, "�����ȣ ��ȸ ����", "�����ȣ ����ġ", "�ο����� �����ȣ�� �ٸ��� �Է����ּ���");
		}
	}

	
	/*�ù� �Ⱦ� ���� ��ư
	 * �ù� �Ⱦ� ���� ��ưŬ�� �� �α��� ȭ�� ���.  
	 * �α��� ���� �� �ù� �Ⱦ� ���� ȭ������ �̵�
	 */
	public void handlerBtnBookAction(ActionEvent e) {
		Parent firstView=null;
		Stage firstStage=null;
		try {
			firstView=FXMLLoader.load(getClass().getResource("/view/userLogin.fxml"));
			firstStage=new Stage();
			Scene scene = new Scene(firstView);
			firstStage.setScene(scene);
			firstStage.setTitle("�α��� ȭ��");
			firstStage.setResizable(false);
			((Stage)btnBook.getScene().getWindow()).close();
			firstStage.show();
		} catch (IOException e1) {
			System.out.println("�ù� �Ⱦ� ���� -> �α��� ȭ�� �̵� ����");
			e1.printStackTrace();
		}
		
	}
	

	/*My Page ��ư
	 * My Page ��ưŬ�� �� �α��� ȭ�� ���.  
	 * �α��� ���� �� My Page ȭ������ �̵�
	 */
	public void handlerBtnMyPageAction(ActionEvent e) {
		Parent firstView=null;
		Stage firstStage=null;
		try {
			firstView=FXMLLoader.load(getClass().getResource("/view/userMyPageLogin.fxml"));
			firstStage=new Stage();
			Scene scene = new Scene(firstView);
			firstStage.setScene(scene);
			firstStage.setTitle("�α��� ȭ��");
			firstStage.setResizable(false);
			((Stage)btnMyPage.getScene().getWindow()).close();
			firstStage.show();
			
			
			
			
			
		} catch (IOException e1) {
			System.out.println("My Page -> �α��� ȭ�� �̵� ����");
			e1.printStackTrace();
		}
	}


	/*������ ��� ��ư
	 * ������ ��� ��ư Ŭ�� �� �����ڸ�� �α���â�� ������. 
	 */
	public void handlerBtnAdminAction(ActionEvent e) {
		Parent firstView=null;
		Stage firstStage=null;
		try {
			firstView = FXMLLoader.load(getClass().getResource("/view/adminLogin.fxml"));
			firstStage=new Stage();
			Scene scene=new Scene(firstView);
			firstStage.setScene(scene);
			firstStage.setTitle("������ ���");
			firstStage.setResizable(false);
			((Stage)btnBook.getScene().getWindow()).close();
			firstStage.show();
		} catch (IOException e1) {
			System.out.println("������ ��� â ���� ����");
			e1.printStackTrace();
		}
	}
	
	//���â ó���Լ�
		public static void alertDisplay(int type, String title, String headerText, String contentText) {
			//Alert alert = new Alert(AlertType.WARNING); // ���â�� ������.
			// �̰� ���� ������ �˾�â�̳� ����� ���̾�α׸� ������ �Ѵ�.
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
			return; // �Լ��� �������� �� ��! ���� �ʾƵ� ����(�Լ��� ������ ����)
				
			}
}


