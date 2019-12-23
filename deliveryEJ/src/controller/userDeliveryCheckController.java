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
		//홈 버튼
		btnHome.setOnAction(e-> {
			handlerBtnHomeAction(e);
		});
		
		//닫기 버튼
		btnClose.setOnAction(e-> {
			Platform.exit();
		});
		
	}

	/*홈 버튼
	 * 홈 버튼 클릭 시 메인창으로 이동 
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
			System.out.println("배송조회 화면 -> 메인화면 이동 실패");
			e1.printStackTrace();
		}
		
		
	}
	

}
