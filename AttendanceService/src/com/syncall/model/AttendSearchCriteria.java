package com.syncall.model;

import java.util.Date;

public class AttendSearchCriteria {

	private Date fromDate;
	private Date toDate;
	private String strRollNo;
	private String strClass;
	private String strSection;
	private String attSearchType;
	
	public String getStrRollNo() {
		return strRollNo;
	}

	public void setStrRollNo(String strRollNo) {
		this.strRollNo = strRollNo;
	}

	public String getStrClass() {
		return strClass;
	}

	public void setStrClass(String strClass) {
		this.strClass = strClass;
	}

	public String getStrSection() {
		return strSection;
	}

	public void setStrSection(String strSection) {
		this.strSection = strSection;
	}

	public String getAttSearchType() {
		return attSearchType;
	}

	public void setAttSearchType(String attSearchType) {
		this.attSearchType = attSearchType;
	}
	

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
