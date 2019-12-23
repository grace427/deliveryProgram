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

		// 월 선택 기능 & 통계
		cbMonth.setItems(
				FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
		cbMonth.setValue("10");
		cbMonth.getSelectionModel().selectedItemProperty();

	} // end of initialize

	// 콤보박스 선택시 차트 보이기
	private void handlerCbMonthAction() {
		deliveryDAO = new DeliveryDAO();
		// 파이차트-----------------------------------------------------------------------------------------------------
		try {
			PieChart.setData(FXCollections.observableArrayList(
					new PieChart.Data("의류/잡화", (double) (deliveryDAO.getPieChart("의류/잡화"))),
					new PieChart.Data("서적", (double) (deliveryDAO.getPieChart("서적"))),
					new PieChart.Data("서류", (double) (deliveryDAO.getPieChart("서류"))),
					new PieChart.Data("가전제품", (double) (deliveryDAO.getPieChart("가전제품"))),
					new PieChart.Data("식품류", (double) (deliveryDAO.getPieChart("식품류")))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 바차트-----------------------------------------------------------------------------------------------------
		try {
			XYChart.Series seriesCity1 = new XYChart.Series();
			seriesCity1.setName("선불");
			seriesCity1.setData(
					FXCollections.observableArrayList(new XYChart.Data("서울", deliveryDAO.getBarChart("서울", "선불")),
							new XYChart.Data("대전", deliveryDAO.getBarChart("대전", "선불")),
							new XYChart.Data("대구", deliveryDAO.getBarChart("대구", "선불")),
							new XYChart.Data("부산", deliveryDAO.getBarChart("부산", "선불")),
							new XYChart.Data("광주", deliveryDAO.getBarChart("광주", "선불")),
							new XYChart.Data("제주", deliveryDAO.getBarChart("제주", "선불")),
							new XYChart.Data("강릉", deliveryDAO.getBarChart("강릉", "선불")),
							new XYChart.Data("전주", deliveryDAO.getBarChart("전주", "선불"))));
			BarChart.getData().add(seriesCity1);

			XYChart.Series seriesCity2 = new XYChart.Series();
			seriesCity2.setName("착불");
			seriesCity2.setData(
					FXCollections.observableArrayList(new XYChart.Data("서울", deliveryDAO.getBarChart("서울", "착불")),
							new XYChart.Data("대전", deliveryDAO.getBarChart("대전", "착불")),
							new XYChart.Data("대구", deliveryDAO.getBarChart("대구", "착불")),
							new XYChart.Data("부산", deliveryDAO.getBarChart("부산", "착불")),
							new XYChart.Data("광주", deliveryDAO.getBarChart("광주", "착불")),
							new XYChart.Data("제주", deliveryDAO.getBarChart("제주", "착불")),
							new XYChart.Data("강릉", deliveryDAO.getBarChart("강릉", "착불")),
							new XYChart.Data("전주", deliveryDAO.getBarChart("전주", "착불"))));
			BarChart.getData().add(seriesCity2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 라인 차트-----------------------------------------------------------------------------------------------------
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

	// 회원관리 버튼
	private void handlerBtnMemAdminAction() {
		Parent editRoot;
		try {
			editRoot = FXMLLoader.load(getClass().getResource("/view/adminMember.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnMemAdmin.getScene().getWindow());
			stageDialog.setTitle("회원관리창");
			Scene scene = new Scene(editRoot);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	// 배송관리 버튼
	private void handlerBtnDelAdminAction() {
		Parent editRoot;
		try {
			editRoot = FXMLLoader.load(getClass().getResource("/view/adminDelivery.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnMemAdmin.getScene().getWindow());
			stageDialog.setTitle("배송관리창");
			Scene scene = new Scene(editRoot);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 직원관리 버튼
	private void handlerBtnEmpAdminAction() {
		Parent editRoot;
		try {
			editRoot = FXMLLoader.load(getClass().getResource("/view/adminEmployee.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnMemAdmin.getScene().getWindow());
			stageDialog.setTitle("직원관리창");
			Scene scene = new Scene(editRoot);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 전체 리스트
		public void totalList() {
			ArrayList<MemberVO> list = null;
			MemberDAO memberDAO = new MemberDAO();
			MemberVO memberVO = null;
			list = memberDAO.getMemberTotal();
			if (list == null) {
				Utility.alertDisplay(1, "경고", "DB 가져오기 오류", "점검 요망");
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				memberVO = list.get(i);
				dataMember.add(memberVO);
			}
		}


} // end of RootController
