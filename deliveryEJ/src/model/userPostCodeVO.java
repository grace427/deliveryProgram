package model;

public class userPostCodeVO {
	private String postCode;
	private String memAddress;

	public userPostCodeVO(String postCode, String memAddress) {
		super();
		this.postCode = postCode;
		this.memAddress = memAddress;
	}

	public userPostCodeVO(String memAddress) {
		super();
		this.memAddress = memAddress;
	}

	public userPostCodeVO() {
		super();
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

}
