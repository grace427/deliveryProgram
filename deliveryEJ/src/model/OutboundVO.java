package model;

import java.sql.Date;
import java.sql.Time;

public class OutboundVO {
	private int trkNum;
	private String pkgType;
	private double weight;
	private int memID;
	private String receiverName;
	private String receiverAddress;
	private String postCode;
	private String receiverPhone;
	private String payMethod;
	private Date bookDate;
	private Time bookTime;
	private String bookSpot;
	private int cost;
	private String approval;
	private int sumCost;
//	private int cityCount;
//	
//	public OutboundVO(int cityCount) {
//		this.cityCount = cityCount;
//	}
	
	
	
	public OutboundVO() {
		
	}
	
	// lineChart
	public OutboundVO(Date bookDate, int sumCost) {
		this.bookDate = bookDate;
		this.cost = sumCost;
	}
	public OutboundVO(int trkNum, Date bookDate) {
		this.trkNum = trkNum;
		this.bookDate = bookDate;
	}
	public OutboundVO(int trkNum, String approval) {
		this.trkNum = trkNum;
		this.approval = approval;
	}
	// // 송장번호 찾아서 아웃바운드 테이블뷰에 넣기 위해 필요한 생성자
	public OutboundVO(int trkNum, String receiverName, String payMethod, int cost, String approval) {
		super();
		this.trkNum = trkNum;
		this.receiverName = receiverName;
		this.payMethod = payMethod;
		this.cost = cost;
		this.approval = approval;
	}
	
	
	public OutboundVO(int trkNum, String pkgType, double weight, int memID, String receiverName, String receiverAddress,
			String postCode, String receiverPhone, String payMethod, Date bookDate, Time bookTime, String bookSpot, 
			int cost, String approval) {
		super(); 
		this.trkNum = trkNum;
		this.pkgType = pkgType;
		this.weight = weight;
		this.memID = memID;
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.postCode = postCode;
		this.receiverPhone = receiverPhone;
		this.payMethod = payMethod;
		this.bookDate = bookDate;
		this.bookTime = bookTime;
		this.bookSpot = bookSpot;
		this.cost = cost;
		this.approval = approval;
		
	}


//	public int getCityCount() {
//		return cityCount;
//	}
//
//	public void setCityCount(int cityCount) {
//		this.cityCount = cityCount;
//	}

	public int getSumCost() {
		return sumCost;
	}

	public void setSumCost(int sumCost) {
		this.sumCost = sumCost;
	}

	public int getTrkNum() {
		return trkNum;
	}


	public void setTrkNum(int trkNum) {
		this.trkNum = trkNum;
	}


	public String getPkgType() {
		return pkgType;
	}


	public void setPkgType(String pkgType) {
		this.pkgType = pkgType;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}


	public int getMemID() {
		return memID;
	}


	public void setMemID(int memID) {
		this.memID = memID;
	}


	public String getReceiverName() {
		return receiverName;
	}


	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}


	public String getReceiverAddress() {
		return receiverAddress;
	}


	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}


	public String getPostCode() {
		return postCode;
	}


	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}


	public String getReceiverPhone() {
		return receiverPhone;
	}


	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}


	public String getPayMethod() {
		return payMethod;
	}


	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}


	public Date getBookDate() {
		return bookDate;
	}


	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}


	public Time getBookTime() {
		return bookTime;
	}


	public void setBookTime(Time bookTime) {
		this.bookTime = bookTime;
	}


	public String getBookSpot() {
		return bookSpot;
	}


	public void setBookSpot(String bookSpot) {
		this.bookSpot = bookSpot;
	}


	public int getCost() {
		return cost;
	}


	public void setCost(int cost) {
		this.cost = cost;
	}


	public String getApproval() {
		return approval;
	}


	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	
	
	
	
}
