package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class userDeliveryCheckController implements Initializable {

	@FXML private TextField txtDeChkTrkNum;
	@FXML private Button btnHome;
	@FXML private Button btnClose;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Ȩ ��ư
		btnHome.setOnAction(e-> {
			handlerBtnHomeAction(e);
		});
		
		//�ݱ� ��ư
		btnClose.setOnAction(e-> {
			Platform.exit();
		});
		
	}

	/*Ȩ ��ư
	 * Ȩ ��ư Ŭ�� �� ����â���� �̵� 
	 */
	public void handlerBtnHomeAction(ActionEvent e) {
		Parent firstView=null;
		Stage firstStage=null;
		try {
			firstView=FXMLLoader.load(getClass().getResource("/view/userMain.fxml"));
			firstStage=new Stage();
			Scene scene = new Scene(firstView);
			firstStage.setScene(scene);
			firstStage.setResizable(false);
			((Stage)btnHome.getScene().getWindow()).close();
			firstStage.show();
		} catch (IOException e1) {
			System.out.println("�����ȸ ȭ�� -> ����ȭ�� �̵� ����");
			e1.printStackTrace();
		}
		
		
	}
	

}
