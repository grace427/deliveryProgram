package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Java로 택배 잡아");
		Parent root = FXMLLoader.load(getClass().getResource("/view/userMain.fxml"));
		Scene scene = new Scene(root);
//		scene.getStylesheets().add(getClass().getResource("app.css").toString());
		primaryStage.setScene(scene);
	
		primaryStage.setResizable(false); // 창고정
		primaryStage.show();
		
	}


}
