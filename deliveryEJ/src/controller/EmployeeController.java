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
	// 테이블뷰를 선택했을때 위치값과 객체값을 저장할 수 있는 변수 선언
	private int selectedIndex;
	private ObservableList<EmployeeVO> selectEmployeeVO;
	ObservableList<EmployeeVO> dataEmployee = null; // 테이블 뷰에 저장하기 위한 데이터
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
		// 1. 버튼 초기화
		buttonInitSetting(false, false, false, false, false);
		// 2. 테이블뷰 셋팅
		tableViewSetting();
		// 3. 버튼(회원번호 검색, 이름 검색, 수정, 삭제) 컨트롤
		// 3.1 검색 - 회원번호
		btnSearchID.setOnAction(e -> handlerBtnSearchIDAction());
		btnSearchID.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				handlerBtnSearchIDAction();
			}
		});
		// 3.1 검색 - 이름
		btnSearchName.setOnMouseClicked(e -> {
			handlerBtnSearchNameAction();
		});
		btnSearchName.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				handlerBtnSearchNameAction();
			}
		});
		// 3.2 수정
		btnModify.setOnAction((e) -> {
			handlerBtnModifyAction(e);
		});
		// 3.3 삭제
		btnDelete.setOnAction((e) -> {
			handlerBtnDeleteAction(e);
		});
		// 4. 등록
		btnRegister.setOnAction((e) -> {
			handlerbtnRegisterAction(e);
		});
		// 테이블 뷰 클릭
		tableView.setOnMousePressed((e) -> {
			handlerTableViewPressedAction(e);
		});
		// 입사일 선택
		dateEmpHireDate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LocalDate date = dateEmpHireDate.getValue();
				selectEmployeeVO.get(0).setEmpHireDate(("" + date)); // ""+date를 date.toString()으로 해도 된다.
			}
		});
		// 프로필 사진 편집
		btnImageEdit.setOnAction(e -> handlerBtnImageFileAction(e));
		// 전체 리스트
		totalList();
	} // end of initialize

	// 등록버튼
	private void handlerbtnRegisterAction(ActionEvent e) {
		try {
			File dirMake = new File(dirSave.getAbsolutePath());
			// 이미지 저장 폴더 생성
			if (!dirMake.exists()) {
				dirMake.mkdir();
			}
			// 이미지 파일 저장
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
					// 데이터베이스 테이블에 입력값을 입력하는 함수
					int count = employeeDAO.getEmployeeregister(evo);
					if (count != 0) {
						dataEmployee.removeAll(dataEmployee);
						totalList();
						// 이미지뷰를 초기화 셋팅
						imageViewInit();
					} else {
						throw new Exception("데이터베이스 등록실패");
					}
				}

				Utility.alertDisplay(5, "등록 성공", "직원의", "등록에 성공했습니다");
			}
		} catch (Exception e2) {
			Utility.alertDisplay(1, "등록 실패", "정확한 값을 입력해주세요", e2.toString());
			return;
		}

		// 버튼 초기화
		buttonInitSetting(false, false, false, false, false);
	}

	// 홈버튼
	private void handlerBtnExitAction(MouseEvent e) {
		((Stage) btnHome.getScene().getWindow()).close();
	}

	// 삭제 버튼
	private void handlerBtnDeleteAction(ActionEvent e) {
		try {
			EmployeeDAO employeeDAO = new EmployeeDAO();
			employeeDAO.getEmployeeDelete(selectEmployeeVO.get(0).getEmpID());
			dataEmployee.removeAll(dataEmployee);
			totalList();
		} catch (Exception e1) {
			Utility.alertDisplay(1, "삭제 오류", "8번 삭제 오류", e1.toString());
		}

		editDelete = false;
	}

	// 전체리스트
	public void totalList() {
		ArrayList<EmployeeVO> list = null;
		EmployeeDAO employeeDAO = new EmployeeDAO();
		EmployeeVO employeeVO = null;
		list = employeeDAO.getEmployeeTotal();
		if (list == null) {
			Utility.alertDisplay(1, "경고", "DB 가져오기 오류", "점검 요망");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			employeeVO = list.get(i);
			dataEmployee.add(employeeVO);
		}
	}

	// 1. 관리자 메인창 버튼 초기화(직원관리)
	public void buttonInitSetting(boolean c, boolean d, boolean e, boolean f, boolean g) {
		btnModify.setDisable(c);
		btnRegister.setDisable(d);
		btnDelete.setDisable(e);
		txtEmpID.setDisable(g);
		txtEmpName.setDisable(f);
	}

	// 2. 테이블뷰 셋팅
	public void tableViewSetting() {
		dataEmployee = FXCollections.observableArrayList();
		tableView.setEditable(false);
		// 2-1. 회원관리 테이블 컬럼 셋팅
		TableColumn colEmpID = new TableColumn("직원번호");
		colEmpID.setCellValueFactory(new PropertyValueFactory("empID"));

		TableColumn colEmpName = new TableColumn("이름");
		colEmpName.setCellValueFactory(new PropertyValueFactory("empName"));

		TableColumn colEmpPhone = new TableColumn("연락처");
		colEmpPhone.setCellValueFactory(new PropertyValueFactory("empPhone"));

		TableColumn colEmpCity = new TableColumn("담당 지역");
		colEmpCity.setCellValueFactory(new PropertyValueFactory("empCity"));

		TableColumn colEmpHireDate = new TableColumn("입사일");
		colEmpHireDate.setCellValueFactory(new PropertyValueFactory("empHireDate"));

		// 2-2. 테이블 설정 : 컬럼들 객체를 테이블 뷰에 리스트 추가 및 항목 추가
		tableView.setItems(dataEmployee);
		tableView.getColumns().addAll(colEmpID, colEmpName, colEmpPhone, colEmpCity, colEmpHireDate);
	} // tableViewSetting

	// 3.1 검색 - 직원번호
	public void handlerBtnSearchIDAction() {
		try {
			EmployeeVO list = new EmployeeVO();
			EmployeeDAO employeeDAO = new EmployeeDAO();
			list = employeeDAO.getEmployeeIDCheck(Integer.parseInt(txtEmpID.getText()));
			if (list.getEmpID() == 0) {
				Utility.alertDisplay(1, "검색 결과", "직원번호 검색 오류", "ddddd");
			}
			if (list == null) {
				throw new Exception("검색오류");
			}
			dataEmployee.removeAll(dataEmployee);
			dataEmployee.add(list);
		} catch (Exception e1) {
			Utility.alertDisplay(1, "검색 결과", "직원번호 검색 오류", e1.toString());
			e1.printStackTrace();
		}
	}

	// 3.1 검색 - 직원이름
	public void handlerBtnSearchNameAction() {
		try {
			ArrayList<EmployeeVO> list = new ArrayList<EmployeeVO>();
			EmployeeDAO employeeDAO = new EmployeeDAO();
			list = employeeDAO.getEmployeeNameCheck(txtEmpName.getText());
			if (list == null) {
				throw new Exception("검색오류");
			}
			dataEmployee.removeAll(dataEmployee);
			for (EmployeeVO evo : list) {
				dataEmployee.add(evo);
			}
		} catch (Exception e1) {
			Utility.alertDisplay(1, "검색 결과", "이름 검색 오류", e1.toString());
			e1.printStackTrace();
		}
	}

	// 테이블뷰 선택
	public void handlerTableViewPressedAction(MouseEvent e) {
		try {
			editDelete = true;
			selectedIndex = tableView.getSelectionModel().getSelectedIndex();
			selectEmployeeVO = tableView.getSelectionModel().getSelectedItems();
			// 테이블 뷰를 클릭 했을 때 테이블에 있는 값을 가져와서 텍스트필드에 집어 넣는다.
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
				// 이미지 파일 경로
				localUrl = selectedFile.toURI().toURL().toString();
				localImage = new Image(localUrl, false);
				imageView.setImage(localImage);
				imageView.setFitHeight(250);
				imageView.setFitWidth(230);
			} else {
				imageViewInit();
			}
			// 버튼 초기화
			 buttonInitSetting(false, false, false, false, false);
		} catch (Exception e2) { 
			editDelete = false;
		}
	}

	// 수정버튼
	private void handlerBtnModifyAction(ActionEvent e) {
		try {
			File dirMake = new File(dirSave.getAbsolutePath());
			// 이미지 저장 폴더 생성
			if (!dirMake.exists()) {
				dirMake.mkdir();
			}
			// 이미지 파일 저장
			String fileName = imageSave(selectedFile);
			selectedFile = new File("C:/images/" + fileName);
			if (selectedFile != null) {
				// 이미지 파일 경로
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
					// 데이터베이스 테이블에 입력값을 입력하는 함수
					int count = employeeDAO.getEmployeeregister(evo);
					if (count != 0) {
						dataEmployee.removeAll(dataEmployee);
						totalList();
						// 이미지뷰를 초기화 셋팅
						imageViewInit();
					} else {
						throw new Exception("데이터베이스 등록실패");
					}
				}

				Utility.alertDisplay(5, "수정 성공", "직원의", "수정에 성공했습니다");
			}
		} catch (Exception e2) {
			Utility.alertDisplay(1, "수정 실패", "정확한 값을 입력해주세요", e2.toString());
		}
	}

	// 기본이미지 셋팅
	public void imageViewInit() {
		localUrl = "/images/기본이미지.png";
		localImage = new Image(localUrl, false);
		imageView.setImage(localImage);
	}

	// 이미지 저장
	public String imageSave(File file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		try {
			// 이미지 파일명 생성
			fileName = "Employee" + System.currentTimeMillis() + "_" + file.getName();
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "\\" + fileName));
			// 선택한 이미지 파일 InputStream의 마지막에 이르렀을 경우는 -1
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

	// 이미지 삭제
	public boolean imageDelete(String fileName) {
		boolean result = false;
		try {
			File fileDelete = new File(dirSave.getAbsolutePath() + "\\" + fileName); // 삭제이미지 파일
			if (fileDelete.exists() && fileDelete.isFile()) {
				result = fileDelete.delete();
				// 기본 이미지
				localUrl = "/image/기본이미지.png";
				localImage = new Image(localUrl, false);
				imageView.setImage(localImage);
			}
		} catch (Exception ie) {
			System.out.println("ie = [ " + ie.getMessage() + "]");
			result = false;
		}
		return result;
	}

	// 프로필이미지 편집 버튼
	public void handlerBtnImageFileAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(btnImageEdit.getScene().getWindow());
			if (selectedFile != null) {
				// 이미지 파일 경로
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
