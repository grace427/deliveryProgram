package model;

public class userDeliChkVO {
	private int trkNum;
	private String pkgType;
	private double weight;
	private int memID;
	private String sendName;
	private String sendAddress;
	private String sendPhone;
	private String postCode;
	private String startDate;
	private String startTime;
	
	public userDeliChkVO(int trkNum, String pkgType, double weight, int memID, String sendName, String sendAddress,
			String sendPhone, String postCode, String startDate, String startTime) {
		super();
		this.trkNum = trkNum;
		this.pkgType = pkgType;
		this.weight = weight;
		this.memID = memID;
		this.sendName = sendName;
		this.sendAddress = sendAddress;
		this.sendPhone = sendPhone;
		this.postCode = postCode;
		this.startDate = startDate;
		this.startTime = startTime;
	}

	public userDeliChkVO(String pkgType, double weight, int memID, String sendName, String sendAddress,
			String sendPhone, String postCode, String startDate, String startTime) {
		super();
		this.pkgType = pkgType;
		this.weight = weight;
		this.memID = memID;
		this.sendName = sendName;
		this.sendAddress = sendAddress;
		this.sendPhone = sendPhone;
		this.postCode = postCode;
		this.startDate = startDate;
		this.startTime = startTime;
	}

	public userDeliChkVO(int trkNum, String pkgType, double weight, String sendName, String sendAddress,
			String sendPhone, String postCode, String startDate, String startTime) {
		super();
		this.trkNum = trkNum;
		this.pkgType = pkgType;
		this.weight = weight;
		this.sendName = sendName;
		this.sendAddress = sendAddress;
		this.sendPhone = sendPhone;
		this.postCode = postCode;
		this.startDate = startDate;
		this.startTime = startTime;
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

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getSendPhone() {
		return sendPhone;
	}

	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	
}
