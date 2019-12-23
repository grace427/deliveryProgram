package model;

public class userBookVO {
	private int trkNum;
	private String pkgType;
	private double weight;
	private int memID;
	private String receiverName;
	private String receiverAddress;
	private String postCode;
	private String receiverPhone;
	private String payMethod;
	private String bookDate;
	private String bookTime;
	private String bookSpot;
	private double cost;
	private int approval;

	
	public userBookVO(String pkgType, double weight, String receiverName, String receiverAddress, String postCode,
			String receiverPhone, String payMethod, String bookDate, String bookTime, String bookSpot) {
		super();
		this.pkgType = pkgType;
		this.weight = weight;
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.postCode = postCode;
		this.receiverPhone = receiverPhone;
		this.payMethod = payMethod;
		this.bookDate = bookDate;
		this.bookTime = bookTime;
		this.bookSpot = bookSpot;
	}

	public userBookVO(String pkgType, double weight, String receiverName, String receiverAddress, String postCode,
			String receiverPhone, String payMethod, String bookDate, String bookTime, String bookSpot, double cost,
			int approval) {
		super();
		this.pkgType = pkgType;
		this.weight = weight;
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

	public userBookVO() {
		super();
	}

	public userBookVO(String pkgType, double weight, int memID, String receiverName, String receiverAddress,
			String postCode, String receiverPhone, String payMethod, String bookDate, String bookTime,
			String bookSpot) {
		super();
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
	}

	public userBookVO(String pkgType, double weight, int memID, String receiverName, String receiverAddress,
			String postCode, String receiverPhone, String payMethod, String bookDate, String bookTime, String bookSpot,
			double cost, int approval) {
		super();
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

	public userBookVO(int trkNum, String pkgType, double weight, int memID, String receiverName, String receiverAddress,
			String postCode, String receiverPhone, String payMethod, String bookDate, String bookTime, String bookSpot,
			double cost, int approval) {
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

	public userBookVO(int trkNum, String pkgType, double weight, int memID, String receiverName, String receiverAddress,
			String postCode, String receiverPhone, String payMethod, String bookDate, String bookTime,
			String bookSpot) {
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
	}

	public userBookVO(int trkNum, String pkgType, double weight, String receiverName, String receiverAddress,
			String postCode, String receiverPhone, String payMethod, String bookDate, String bookTime,
			String bookSpot) {
		super();
		this.trkNum = trkNum;
		this.pkgType = pkgType;
		this.weight = weight;
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.postCode = postCode;
		this.receiverPhone = receiverPhone;
		this.payMethod = payMethod;
		this.bookDate = bookDate;
		this.bookTime = bookTime;
		this.bookSpot = bookSpot;
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

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public String getBookTime() {
		return bookTime;
	}

	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}

	public String getBookSpot() {
		return bookSpot;
	}

	public void setBookSpot(String bookSpot) {
		this.bookSpot = bookSpot;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getApproval() {
		return approval;
	}

	public void setApproval(int approval) {
		this.approval = approval;
	}

}
