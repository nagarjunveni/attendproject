package com.syncall.backingbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.syncall.dao.AttendanceDao;
import com.syncall.model.AttendSearchCriteria;
import com.syncall.model.AttendanceRecord;


@ManagedBean
@SessionScoped
public class AttendanceBackingBean {
	
	private AttendSearchCriteria attSearchCriteria = new AttendSearchCriteria();
	
	public AttendSearchCriteria getAttSearchCriteria() {
		return attSearchCriteria;
	}

	public void setAttSearchCriteria(AttendSearchCriteria attSearchCriteria) {
		this.attSearchCriteria = attSearchCriteria;
	}


	private List<AttendanceRecord> attendanceSwipes = new ArrayList<AttendanceRecord>();
	
	public List<AttendanceRecord> getAttendanceSwipes() {
		return attendanceSwipes;
	}

	public void setAttendanceSwipes(List<AttendanceRecord> attendanceSwipes) {
		this.attendanceSwipes = attendanceSwipes;
	}

	public void getCurrentReport(){
		
	}
	
	public String refreshResults(){
		AttendanceDao dao = new AttendanceDao();
		attendanceSwipes = dao.getAttendanceSearchResults(attSearchCriteria);
		return null;
	}
	
	public String viewAttendanceSwipes(){
		AttendanceDao dao = new AttendanceDao();
		attendanceSwipes = dao.getTodaysResults();
		return "viewAttendanceSwipesPage";
	}
	
	
	public static void main(String[] args) {
		
	}

}
