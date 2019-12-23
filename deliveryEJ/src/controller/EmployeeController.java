package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import model.EmployeeVO;
import model.MemberVO;

public class EmployeeController implements Initializable {

	@FXML
	private TextField txtEmpName;

	@FXML
	private ImageView btnHome;
	@FXML
	private Button btnSearchID;
	@FXML
	private Button btnSearchName;

	@FXML
	private Button btnDelete;

	@FXML
	private TextField txtEmpID;
	@FXML
	private TextField txtInputEmpPhone;
	@FXML
	private TextField txtInputEmpCity;
	@FXML
	private TextField txtInputEmpName;

	@FXML
	private DatePicker dateEmpHireDate;

	@FXML
	private ImageView imageView;

	@FXML
	private Button btnImageEdit;

	@FXML
	private Button btnRegister;

	@FXML
	private Button btnModify;

	private boolean editDelete = false;
	private EmployeeDAO employeeDAO = null;
	@FXML
	private TableView<EmployeeVO> tableView;
	// ���̺�並 ���������� ��ġ���� ��ü���� ������ �� �ִ� ���� ����
	private int selectedIndex;
	private ObservableList<EmployeeVO> selectEmployeeVO;
	ObservableList<EmployeeVO> dataEmployee = null; // ���̺� �信 �����ϱ� ���� ������
	String selectFileName = "";
	String localUrl = "";
	Image localImage;
	int no;
	File selectedFile = null;
	private File dirSave = new File("C:/images");
	private File file = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnHome.setOnMouseClicked((e) -> {
			handlerBtnExitAction(e);
		});
		imageViewInit();
		// 1. ��ư �ʱ�ȭ
		buttonInitSetting(false, false, false, false, false);
		// 2. ���̺�� ����
		tableViewSetting();
		// 3. ��ư(ȸ����ȣ �˻�, �̸� �˻�, ����, ����) ��Ʈ��
		// 3.1 �˻� - ȸ����ȣ
		btnSearchID.setOnAction(e -> handlerBtnSearchIDAction());
		btnSearchID.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				handlerBtnSearchIDAction();
			}
		});
		// 3.1 �˻� - �̸�
		btnSearchName.setOnMouseClicked(e -> {
			handlerBtnSearchNameAction();
		});
		btnSearchName.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				handlerBtnSearchNameAction();
			}
		});
		// 3.2 ����
		btnModify.setOnAction((e) -> {
			handlerBtnModifyAction(e);
		});
		// 3.3 ����
		btnDelete.setOnAction((e) -> {
			handlerBtnDeleteAction(e);
		});
		// 4. ���
		btnRegister.setOnAction((e) -> {
			handlerbtnRegisterAction(e);
		});
		// ���̺� �� Ŭ��
		tableView.setOnMousePressed((e) -> {
			handlerTableViewPressedAction(e);
		});
		// �Ի��� ����
		dateEmpHireDate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LocalDate date = dateEmpHireDate.getValue();
				selectEmployeeVO.get(0).setEmpHireDate(("" + date)); // ""+date�� date.toString()���� �ص� �ȴ�.
			}
		});
		// ������ ���� ����
		btnImageEdit.setOnAction(e -> handlerBtnImageFileAction(e));
		// ��ü ����Ʈ
		totalList();
	} // end of initialize

	// ��Ϲ�ư
	private void handlerbtnRegisterAction(ActionEvent e) {
		try {
			File dirMake = new File(dirSave.getAbsolutePath());
			// �̹��� ���� ���� ����
			if (!dirMake.exists()) {
				dirMake.mkdir();
			}
			// �̹��� ���� ����
			String fileName = imageSave(selectedFile);
			if (txtInputEmpName.getText().contentEquals("") || txtInputEmpPhone.getText().equals("")
					|| txtInputEmpCity.getText().equals("")) {
				throw new Exception();
			} else {
				LocalDate date = dateEmpHireDate.getValue();
				EmployeeVO evo = new EmployeeVO(txtInputEmpName.getText(), txtInputEmpPhone.getText(),
						txtInputEmpCity.getText(), fileName, date.toString());

				if (editDelete == true) {
					dataEmployee.remove(selectedIndex);
					dataEmployee.add(selectedIndex, evo);
					editDelete = false;
				} else {
					employeeDAO = new EmployeeDAO();
					// �����ͺ��̽� ���̺� �Է°��� �Է��ϴ� �Լ�
					int count = employeeDAO.getEmployeeregister(evo);
					if (count != 0) {
						dataEmployee.removeAll(dataEmployee);
						totalList();
						// �̹����並 �ʱ�ȭ ����
						imageViewInit();
					} else {
						throw new Exception("�����ͺ��̽� ��Ͻ���");
					}
				}

				Utility.alertDisplay(5, "��� ����", "������", "��Ͽ� �����߽��ϴ�");
			}
		} catch (Exception e2) {
			Utility.alertDisplay(1, "��� ����", "��Ȯ�� ���� �Է����ּ���", e2.toString());
			return;
		}

		// ��ư �ʱ�ȭ
		buttonInitSetting(false, false, false, false, false);
	}

	// Ȩ��ư
	private void handlerBtnExitAction(MouseEvent e) {
		((Stage) btnHome.getScene().getWindow()).close();
	}

	// ���� ��ư
	private void handlerBtnDeleteAction(ActionEvent e) {
		try {
			EmployeeDAO employeeDAO = new EmployeeDAO();
			employeeDAO.getEmployeeDelete(selectEmployeeVO.get(0).getEmpID());
			dataEmployee.removeAll(dataEmployee);
			totalList();
		} catch (Exception e1) {
			Utility.alertDisplay(1, "���� ����", "8�� ���� ����", e1.toString());
		}

		editDelete = false;
	}

	// ��ü����Ʈ
	public void totalList() {
		ArrayList<EmployeeVO> list = null;
		EmployeeDAO employeeDAO = new EmployeeDAO();
		EmployeeVO employeeVO = null;
		list = employeeDAO.getEmployeeTotal();
		if (list == null) {
			Utility.alertDisplay(1, "���", "DB �������� ����", "���� ���");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			employeeVO = list.get(i);
			dataEmployee.add(employeeVO);
		}
	}

	// 1. ������ ����â ��ư �ʱ�ȭ(��������)
	public void buttonInitSetting(boolean c, boolean d, boolean e, boolean f, boolean g) {
		btnModify.setDisable(c);
		btnRegister.setDisable(d);
		btnDelete.setDisable(e);
		txtEmpID.setDisable(g);
		txtEmpName.setDisable(f);
	}

	// 2. ���̺�� ����
	public void tableViewSetting() {
		dataEmployee = FXCollections.observableArrayList();
		tableView.setEditable(false);
		// 2-1. ȸ������ ���̺� �÷� ����
		TableColumn colEmpID = new TableColumn("������ȣ");
		colEmpID.setCellValueFactory(new PropertyValueFactory("empID"));

		TableColumn colEmpName = new TableColumn("�̸�");
		colEmpName.setCellValueFactory(new PropertyValueFactory("empName"));

		TableColumn colEmpPhone = new TableColumn("����ó");
		colEmpPhone.setCellValueFactory(new PropertyValueFactory("empPhone"));

		TableColumn colEmpCity = new TableColumn("��� ����");
		colEmpCity.setCellValueFactory(new PropertyValueFactory("empCity"));

		TableColumn colEmpHireDate = new TableColumn("�Ի���");
		colEmpHireDate.setCellValueFactory(new PropertyValueFactory("empHireDate"));

		// 2-2. ���̺� ���� : �÷��� ��ü�� ���̺� �信 ����Ʈ �߰� �� �׸� �߰�
		tableView.setItems(dataEmployee);
		tableView.getColumns().addAll(colEmpID, colEmpName, colEmpPhone, colEmpCity, colEmpHireDate);
	} // tableViewSetting

	// 3.1 �˻� - ������ȣ
	public void handlerBtnSearchIDAction() {
		try {
			EmployeeVO list = new EmployeeVO();
			EmployeeDAO employeeDAO = new EmployeeDAO();
			list = employeeDAO.getEmployeeIDCheck(Integer.parseInt(txtEmpID.getText()));
			if (list.getEmpID() == 0) {
				Utility.alertDisplay(1, "�˻� ���", "������ȣ �˻� ����", "ddddd");
			}
			if (list == null) {
				throw new Exception("�˻�����");
			}
			dataEmployee.removeAll(dataEmployee);
			dataEmployee.add(list);
		} catch (Exception e1) {
			Utility.alertDisplay(1, "�˻� ���", "������ȣ �˻� ����", e1.toString());
			e1.printStackTrace();
		}
	}

	// 3.1 �˻� - �����̸�
	public void handlerBtnSearchNameAction() {
		try {
			ArrayList<EmployeeVO> list = new ArrayList<EmployeeVO>();
			EmployeeDAO employeeDAO = new EmployeeDAO();
			list = employeeDAO.getEmployeeNameCheck(txtEmpName.getText());
			if (list == null) {
				throw new Exception("�˻�����");
			}
			dataEmployee.removeAll(dataEmployee);
			for (EmployeeVO evo : list) {
				dataEmployee.add(evo);
			}
		} catch (Exception e1) {
			Utility.alertDisplay(1, "�˻� ���", "�̸� �˻� ����", e1.toString());
			e1.printStackTrace();
		}
	}

	// ���̺�� ����
	public void handlerTableViewPressedAction(MouseEvent e) {
		try {
			editDelete = true;
			selectedIndex = tableView.getSelectionModel().getSelectedIndex();
			selectEmployeeVO = tableView.getSelectionModel().getSelectedItems();
			// ���̺� �並 Ŭ�� ���� �� ���̺� �ִ� ���� �����ͼ� �ؽ�Ʈ�ʵ忡 ���� �ִ´�.
			txtInputEmpName.setText(selectEmployeeVO.get(0).getEmpName());
			txtInputEmpPhone.setText(selectEmployeeVO.get(0).getEmpPhone());
			txtInputEmpCity.setText(selectEmployeeVO.get(0).getEmpCity());
			LocalDate data = null;
			String[] str = selectEmployeeVO.get(0).getEmpHireDate().split("-");
			String date = selectEmployeeVO.get(0).getEmpHireDate();
			dateEmpHireDate.setValue(data.of(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2])));

			String fileName = selectEmployeeVO.get(0).getEmpImage();
			selectedFile = new File("C:/images/" + fileName);
			if (selectedFile != null) {
				// �̹��� ���� ���
				localUrl = selectedFile.toURI().toURL().toString();
				localImage = new Image(localUrl, false);
				imageView.setImage(localImage);
				imageView.setFitHeight(250);
				imageView.setFitWidth(230);
			} else {
				imageViewInit();
			}
			// ��ư �ʱ�ȭ
			 buttonInitSetting(false, false, false, false, false);
		} catch (Exception e2) { 
			editDelete = false;
		}
	}

	// ������ư
	private void handlerBtnModifyAction(ActionEvent e) {
		try {
			File dirMake = new File(dirSave.getAbsolutePath());
			// �̹��� ���� ���� ����
			if (!dirMake.exists()) {
				dirMake.mkdir();
			}
			// �̹��� ���� ����
			String fileName = imageSave(selectedFile);
			selectedFile = new File("C:/images/" + fileName);
			if (selectedFile != null) {
				// �̹��� ���� ���
				localUrl = selectedFile.toURI().toURL().toString();
				localImage = new Image(localUrl, false);
				imageView.setImage(localImage);
				imageView.setFitHeight(250);
				imageView.setFitWidth(230);
			} else {
				imageViewInit();
			}

			if (txtInputEmpName.getText().contentEquals("") || txtInputEmpPhone.getText().equals("")
					|| txtInputEmpCity.getText().equals("")) {
				throw new Exception();
			} else {
				LocalDate date = dateEmpHireDate.getValue();
				EmployeeVO evo = new EmployeeVO(txtInputEmpName.getText(), txtInputEmpPhone.getText(),
						txtInputEmpCity.getText(), fileName, date.toString());

				if (editDelete == true) {
					dataEmployee.remove(selectedIndex);
					dataEmployee.add(selectedIndex, evo);
					editDelete = false;
				} else {
					employeeDAO = new EmployeeDAO();
					// �����ͺ��̽� ���̺� �Է°��� �Է��ϴ� �Լ�
					int count = employeeDAO.getEmployeeregister(evo);
					if (count != 0) {
						dataEmployee.removeAll(dataEmployee);
						totalList();
						// �̹����並 �ʱ�ȭ ����
						imageViewInit();
					} else {
						throw new Exception("�����ͺ��̽� ��Ͻ���");
					}
				}

				Utility.alertDisplay(5, "���� ����", "������", "������ �����߽��ϴ�");
			}
		} catch (Exception e2) {
			Utility.alertDisplay(1, "���� ����", "��Ȯ�� ���� �Է����ּ���", e2.toString());
		}
	}

	// �⺻�̹��� ����
	public void imageViewInit() {
		localUrl = "/images/�⺻�̹���.png";
		localImage = new Image(localUrl, false);
		imageView.setImage(localImage);
	}

	// �̹��� ����
	public String imageSave(File file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		try {
			// �̹��� ���ϸ� ����
			fileName = "Employee" + System.currentTimeMillis() + "_" + file.getName();
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "\\" + fileName));
			// ������ �̹��� ���� InputStream�� �������� �̸����� ���� -1
			while ((data = bis.read()) != -1) {
				bos.write(data);
				bos.flush();
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				e.getMessage();
			}
		}
		return fileName;
	}

	// �̹��� ����
	public boolean imageDelete(String fileName) {
		boolean result = false;
		try {
			File fileDelete = new File(dirSave.getAbsolutePath() + "\\" + fileName); // �����̹��� ����
			if (fileDelete.exists() && fileDelete.isFile()) {
				result = fileDelete.delete();
				// �⺻ �̹���
				localUrl = "/image/�⺻�̹���.png";
				localImage = new Image(localUrl, false);
				imageView.setImage(localImage);
			}
		} catch (Exception ie) {
			System.out.println("ie = [ " + ie.getMessage() + "]");
			result = false;
		}
		return result;
	}

	// �������̹��� ���� ��ư
	public void handlerBtnImageFileAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(btnImageEdit.getScene().getWindow());
			if (selectedFile != null) {
				// �̹��� ���� ���
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		localImage = new Image(localUrl, false);
		imageView.setImage(localImage);
		imageView.setFitHeight(250);
		imageView.setFitWidth(230);
		btnImageEdit.setDisable(false);

		if (selectedFile != null) {
			selectFileName = selectedFile.getName();
		}

	}

} // end of EmployeeController
