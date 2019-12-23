package model;

public class EmployeeVO {
	
	private int empID;
	private String empName;
	private String empPhone;
	private String empCity;
	private String empImage;
	private String empHireDate;
	
	
	public EmployeeVO() {
		
	}
	

	public EmployeeVO(String empName, String empPhone, String empCity, String empImage,
			String empHireDate) {
		super();
		this.empName = empName;
		this.empPhone = empPhone;
		this.empCity = empCity;
		this.empImage = empImage;
		this.empHireDate = empHireDate;
	}

	
	public EmployeeVO(int empID, String empName, String empPhone, String empCity, String empImage,
			String empHireDate) {
		super();
		this.empID = empID;
		this.empName = empName;
		this.empPhone = empPhone;
		this.empCity = empCity;
		this.empImage = empImage;
		this.empHireDate = empHireDate;
	}


	public int getEmpID() {
		return empID;
	}


	public void setEmpID(int empID) {
		this.empID = empID;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getEmpPhone() {
		return empPhone;
	}


	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}


	public String getEmpCity() {
		return empCity;
	}


	public void setEmpCity(String empCity) {
		this.empCity = empCity;
	}




	public String getEmpImage() {
		return empImage;
	}


	public void setEmpImage(String empImage) {
		this.empImage = empImage;
	}


	public String getEmpHireDate() {
		return empHireDate;
	}


	public void setEmpHireDate(String empHireDate) {
		this.empHireDate = empHireDate;
	}
	
	
}

