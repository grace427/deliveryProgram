package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.userPostCodeVO;

public class userPostCodeController implements Initializable {
	
	@FXML private TextField txtSearch;
	@FXML private Button btnSearch;
	@FXML private TableView<userPostCodeVO> postTableView;
	ObservableList<userPostCodeVO> postCode;
	ObservableList<userPostCodeVO> postCodeData;
//	private TableView<userPostCodeVO> tableViewPostCode;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		tableViewPostCodeSetting();
		postCodeTotalList();
		btnSearch.setOnAction(e-> {
			handlerBtnSearchAction(e);
		});

	}

	public void handlerBtnSearchAction(ActionEvent e) {
		try {
			userPostCodeVO list = new userPostCodeVO();
			userPostCodeDAO upcDAO = new userPostCodeDAO();
			list = upcDAO.getPostCodeSearch(txtSearch.getText());

			if (list == null) {
				throw new Exception("�˻�����");
			}
			postCodeData.removeAll(postCodeData);
//			for (userPostCodeVO svo : list) {
//				postCodeData.add(svo);
//			}

		} catch (Exception e1) {
			userMainController.alertDisplay(1, "�˻����", "�ּҸ� ã�� �� �����ϴ�.", "��Ȯ�� ���θ� �ּҸ� �Է����ּ���.");
		}
		
		
	
}

	public void tableViewPostCodeSetting() {
		postCode = FXCollections.observableArrayList();
		
		postTableView.setEditable(false);
		// ��۰��� ���̺� �÷� ����
		TableColumn colPostCode = new TableColumn("�����ȣ");
		colPostCode.setStyle("-fx-alignment: CENTER;"); 
		colPostCode.setCellValueFactory(new PropertyValueFactory("postCode"));

		TableColumn colAddress = new TableColumn("�ּ�");
		colAddress.setStyle("-fx-alignment: CENTER;"); 
		colAddress.setCellValueFactory(new PropertyValueFactory("memAddress"));
		
		postTableView.setItems(postCode);
		postTableView.getColumns().addAll(colPostCode, colAddress);
		
	}

	public void postCodeTotalList() {
		ArrayList<userPostCodeVO> list = null;
		userPostCodeDAO upcDAO = new userPostCodeDAO();
		userPostCodeVO upcVO = null;
		list = upcDAO.getPostCode();
		if (list == null) {
			userMainController.alertDisplay(1, "���", "postCodedDB �������� ����", "���˿��");
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			upcVO = list.get(i);
			postCode.add(upcVO);
		}

	}
	

}
