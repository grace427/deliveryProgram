package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MemberVO;
import model.OutboundVO;

public class RootController implements Initializable {

	@FXML
	private ComboBox<String> cbMonth;
	@FXML
	private Button btnEmpAdmin;
	@FXML
	private Button btnMemAdmin;
	@FXML
	private Button btnDelAdmin;
	@FXML
	private LineChart LineChart;
	@FXML
	private BarChart BarChart;
	@FXML
	private PieChart PieChart;
	ObservableList<MemberVO> dataMember = null;
	@FXML
	private TableView<MemberVO> tableView;
	DeliveryDAO deliveryDAO = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnMemAdmin.setOnAction(e -> {
			handlerBtnMemAdminAction();
		});
		btnDelAdmin.setOnAction(e -> {
			handlerBtnDelAdminAction();
		});
		btnEmpAdmin.setOnAction(e -> {
			handlerBtnEmpAdminAction();
		});
		cbMonth.setOnMouseClicked(e -> {
			handlerCbMonthAction();
		});

		// �� ���� ��� & ���
		cbMonth.setItems(
				FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
		cbMonth.setValue("10");
		cbMonth.getSelectionModel().selectedItemProperty();

	} // end of initialize

	// �޺��ڽ� ���ý� ��Ʈ ���̱�
	private void handlerCbMonthAction() {
		deliveryDAO = new DeliveryDAO();
		// ������Ʈ-----------------------------------------------------------------------------------------------------
		try {
			PieChart.setData(FXCollections.observableArrayList(
					new PieChart.Data("�Ƿ�/��ȭ", (double) (deliveryDAO.getPieChart("�Ƿ�/��ȭ"))),
					new PieChart.Data("����", (double) (deliveryDAO.getPieChart("����"))),
					new PieChart.Data("����", (double) (deliveryDAO.getPieChart("����"))),
					new PieChart.Data("������ǰ", (double) (deliveryDAO.getPieChart("������ǰ"))),
					new PieChart.Data("��ǰ��", (double) (deliveryDAO.getPieChart("��ǰ��")))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// ����Ʈ-----------------------------------------------------------------------------------------------------
		try {
			XYChart.Series seriesCity1 = new XYChart.Series();
			seriesCity1.setName("����");
			seriesCity1.setData(
					FXCollections.observableArrayList(new XYChart.Data("����", deliveryDAO.getBarChart("����", "����")),
							new XYChart.Data("����", deliveryDAO.getBarChart("����", "����")),
							new XYChart.Data("�뱸", deliveryDAO.getBarChart("�뱸", "����")),
							new XYChart.Data("�λ�", deliveryDAO.getBarChart("�λ�", "����")),
							new XYChart.Data("����", deliveryDAO.getBarChart("����", "����")),
							new XYChart.Data("����", deliveryDAO.getBarChart("����", "����")),
							new XYChart.Data("����", deliveryDAO.getBarChart("����", "����")),
							new XYChart.Data("����", deliveryDAO.getBarChart("����", "����"))));
			BarChart.getData().add(seriesCity1);

			XYChart.Series seriesCity2 = new XYChart.Series();
			seriesCity2.setName("����");
			seriesCity2.setData(
					FXCollections.observableArrayList(new XYChart.Data("����", deliveryDAO.getBarChart("����", "����")),
							new XYChart.Data("����", deliveryDAO.getBarChart("����", "����")),
							new XYChart.Data("�뱸", deliveryDAO.getBarChart("�뱸", "����")),
							new XYChart.Data("�λ�", deliveryDAO.getBarChart("�λ�", "����")),
							new XYChart.Data("����", deliveryDAO.getBarChart("����", "����")),
							new XYChart.Data("����", deliveryDAO.getBarChart("����", "����")),
							new XYChart.Data("����", deliveryDAO.getBarChart("����", "����")),
							new XYChart.Data("����", deliveryDAO.getBarChart("����", "����"))));
			BarChart.getData().add(seriesCity2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ���� ��Ʈ-----------------------------------------------------------------------------------------------------
		ArrayList<OutboundVO> newlist = new ArrayList<OutboundVO>();
		OutboundVO ovo = new OutboundVO();

		try {
			XYChart.Series seriesCost = new XYChart.Series();
			seriesCost.setName("cost");
			newlist = deliveryDAO.getLineChart();
			// LineData lineData = new LineData(xValues)
			ObservableList oolist = FXCollections.observableArrayList();
			for (int i = 0; i < newlist.size(); i++) {
				oolist.add(new XYChart.Data(newlist.get(i).getBookDate().toString(), newlist.get(i).getCost()));
			}
			seriesCost.setData(oolist);
			LineChart.getData().add(seriesCost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // comboBox

	// ȸ������ ��ư
	private void handlerBtnMemAdminAction() {
		Parent editRoot;
		try {
			editRoot = FXMLLoader.load(getClass().getResource("/view/adminMember.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnMemAdmin.getScene().getWindow());
			stageDialog.setTitle("ȸ������â");
			Scene scene = new Scene(editRoot);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	// ��۰��� ��ư
	private void handlerBtnDelAdminAction() {
		Parent editRoot;
		try {
			editRoot = FXMLLoader.load(getClass().getResource("/view/adminDelivery.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnMemAdmin.getScene().getWindow());
			stageDialog.setTitle("��۰���â");
			Scene scene = new Scene(editRoot);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// �������� ��ư
	private void handlerBtnEmpAdminAction() {
		Parent editRoot;
		try {
			editRoot = FXMLLoader.load(getClass().getResource("/view/adminEmployee.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnMemAdmin.getScene().getWindow());
			stageDialog.setTitle("��������â");
			Scene scene = new Scene(editRoot);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ��ü ����Ʈ
		public void totalList() {
			ArrayList<MemberVO> list = null;
			MemberDAO memberDAO = new MemberDAO();
			MemberVO memberVO = null;
			list = memberDAO.getMemberTotal();
			if (list == null) {
				Utility.alertDisplay(1, "���", "DB �������� ����", "���� ���");
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				memberVO = list.get(i);
				dataMember.add(memberVO);
			}
		}


} // end of RootController
