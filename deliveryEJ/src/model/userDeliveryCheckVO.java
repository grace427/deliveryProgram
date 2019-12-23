package model;

import java.sql.Date;
import java.sql.Time;

public class userDeliveryCheckVO {
	private Date realStartDate;
	private Time realStartTime;
	private String currentLocation;
	private String currentStatus;
	
	public userDeliveryCheckVO() {
		
	}
	
	public userDeliveryCheckVO(Date realStartDate, Time realStartTime, String currentLocation, String currentStatus) {
		super();
		this.realStartDate = realStartDate;
		this.realStartTime = realStartTime;
		this.currentLocation = currentLocation;
		this.currentStatus = currentStatus;
	}

	public Date getRealStartDate() {
		return realStartDate;
	}

	public void setRealStartDate(Date realStartDate) {
		this.realStartDate = realStartDate;
	}

	public Time getRealStartTime() {
		return realStartTime;
	}

	public void setRealStartTime(Time realStartTime) {
		this.realStartTime = realStartTime;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	
	
}