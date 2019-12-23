package model;

import java.sql.Date;

public class userMemberVO {
	private int memID;
	private String memName;
	private String memPhone;
	private String postCode;
	private String memAddress;
	private String memPW;
	private Date memJoinDate;

	public userMemberVO(int memID, String memName, String memPhone, String postCode, String memAddress, String memPW) {
		super();
		this.memID = memID;
		this.memName = memName;
		this.memPhone = memPhone;
		this.postCode = postCode;
		this.memAddress = memAddress;
		this.memPW = memPW;
	}

	public userMemberVO(String memPW) {
		super();
		this.memPW = memPW;
	}

	public userMemberVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public userMemberVO(int memID) {
		super();
		this.memID = memID;
	}

	public userMemberVO(int memID, String memName, String memPhone, String postCode, String memAddress, String memPW,
			Date memJoinDate) {
		super();
		this.memID = memID;
		this.memName = memName;
		this.memPhone = memPhone;
		this.postCode = postCode;
		this.memAddress = memAddress;
		this.memPW = memPW;
		this.memJoinDate = memJoinDate;
	}

	public userMemberVO(String memPhone, String memPW) {
		super();
		this.memPhone = memPhone;
		this.memPW = memPW;
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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getMemAddress() {
		return memAddress;
	}

	public void setMemAddress(String memAddress) {
		this.memAddress = memAddress;
	}

	public String getMemPW() {
		return memPW;
	}

	public void setMemPW(String memPW) {
		this.memPW = memPW;
	}

	public Date getMemJoinDate() {
		return memJoinDate;
	}

	public void setMemJoinDate(Date memJoinDate) {
		this.memJoinDate = memJoinDate;
	}

}
