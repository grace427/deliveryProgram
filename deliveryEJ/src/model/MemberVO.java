package model;


public class MemberVO {
	private int memID;
	private String memName;
	private String memPhone;
	private String memAddress;
	private String postCode;
	private String memPW;
	private String memJoinDate;	
	
	public MemberVO() {
		
	}
	
	
	public MemberVO(int memID, String memName, String memPhone, String memAddress, String postCode) {
		this.memID = memID;
		this.memName = memName;
		this.memPhone = memPhone;
		this.memAddress = memAddress;	
		this.postCode = postCode;
	}
	
	public MemberVO(int memID, String memName, String memPhone, String memAddress, String postCode, String memPW,
			String memJoinDate) {
		this.memID = memID;
		this.memName = memName;
		this.memPhone = memPhone;
		this.memAddress = memAddress;
		this.postCode = postCode;
		this.memPW = memPW;
		this.memJoinDate = memJoinDate;		
	}
	
	public MemberVO(int memID, String memName, String memPhone, String memAddress, String postCode, String memPW) {
		this.memID = memID;
		this.memName = memName;
		this.memPhone = memPhone;
		this.memAddress = memAddress;
		this.postCode = postCode;
		this.memPW = memPW;
	}
	// auto-increment되는 memID 없는 생성자
	public MemberVO(String memName, String memPhone, String memAddress, String postCode, String memPW,
			String memJoinDate) {
		super();		
		this.memName = memName;
		this.memPhone = memPhone;
		this.memAddress = memAddress;
		this.postCode = postCode;
		this.memPW = memPW;
		this.memJoinDate = memJoinDate;		
	}

	public int getMemID() {
		return memID;
	}

	public void setMemID(int memID) {
		this.memID = memID;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public String getMemPhone() {
		return memPhone;
	}

	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}

	public String getMemAddress() {
		return memAddress;
	}

	public void setMemAddress(String memAddress) {
		this.memAddress = memAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getMemPW() {
		return memPW;
	}

	public void setMemPW(String memPW) {
		this.memPW = memPW;
	}

	public String getMemJoinDate() {
		return memJoinDate;
	}

	public void setMemJoinDate(String memJoinDate) {
		this.memJoinDate = memJoinDate;
	}
	
	
}


