package com.syncall.model;

import java.sql.Time;
import java.util.Date;

public class AttendanceRecord {
	
	private String rollNo;
	private String rfidNumber;
	private String firstName;
	private String strTimeStamp;
	private Date dateOfSwipe;
	private Time timeOfSwipe;
	private Time timeOfSwipeOut;
	private Time totalTimeSpent;
	
	private String strTimeOfSwipeOut;
	private String strTotalTimeSpent;
	
	
	
	public Time getTimeOfSwipeOut() {
		return timeOfSwipeOut;
	}
	public void setTimeOfSwipeOut(Time timeOfSwipeOut) {
		this.timeOfSwipeOut = timeOfSwipeOut;
	}
	public Time getTotalTimeSpent() {
		return totalTimeSpent;
	}
	public void setTotalTimeSpent(Time totalTimeSpent) {
		this.totalTimeSpent = totalTimeSpent;
	}
	public String getStrTimeOfSwipeOut() {
		return strTimeOfSwipeOut;
	}
	public void setStrTimeOfSwipeOut(String strTimeOfSwipeOut) {
		this.strTimeOfSwipeOut = strTimeOfSwipeOut;
	}
	public String getStrTotalTimeSpent() {
		return strTotalTimeSpent;
	}
	public void setStrTotalTimeSpent(String strTotalTimeSpent) {
		this.strTotalTimeSpent = strTotalTimeSpent;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getRfidNumber() {
		return rfidNumber;
	}
	public void setRfidNumber(String rfidNumber) {
		this.rfidNumber = rfidNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getStrTimeStamp() {
		return strTimeStamp;
	}
	public void setStrTimeStamp(String strTimeStamp) {
		this.strTimeStamp = strTimeStamp;
	}
	public Date getDateOfSwipe() {
		return dateOfSwipe;
	}
	public void setDateOfSwipe(Date dateOfSwipe) {
		this.dateOfSwipe = dateOfSwipe;
	}
	public Time getTimeOfSwipe() {
		return timeOfSwipe;
	}
	public void setTimeOfSwipe(Time timeOfSwipe) {
		this.timeOfSwipe = timeOfSwipe;
	}
	
	
	

}
