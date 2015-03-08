/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syncall.backingbean;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.syncall.dao.BaseDao;
import com.syncall.model.Student;

/**
 *
 * @author Nagarjun_v
 */
@ManagedBean(name="studentBackingBean")
@SessionScoped
public class StudentBackingBean {
	
	private ArrayList<Student> searchStudentsList = new ArrayList<Student>();
	private Student selectedStudent;
	private Student newStudent = new Student();
	
	public String goToStudentSearchPage(){
		return "studentSearchPage";
	}
	
	public String gotToCreateStudentPage(){
		
		return "createStudentPage";
	}
	
	
	public String goToEditStudentPage(){
		
		return "editStudentPage";
	}
	

	
	public String deleteStudent(){
		
		return "editStudentPage";
	}
	
	public String goToPeopleSearchPage(){
		return "peopleSearchPage";
	}
	
	
	public String goToStudentResultsPage(){
        Connection conn = BaseDao.getDBConnection();
        searchStudentsList = new ArrayList<Student>();
        try {
            String strUserQuery = "select * from ATT_STUDENTS";
            System.out.println("--- select students query ---> "+strUserQuery);
            PreparedStatement ps = conn.prepareCall(strUserQuery);
            ResultSet resultset = ps.executeQuery();
            Student student;
            while(resultset.next()){
            	student = new Student();
            	student.setStrRollno(resultset.getString("ROLLNO"));
            	student.setStrFirstName(resultset.getString("FIRSTNAME"));
            	student.setStrLastName(resultset.getString("LASTNAME"));
            	student.setStrContactNo(resultset.getString("CONTACTNO"));
            	student.setStrClass(resultset.getString("CLASS"));
            	student.setStrRfidNo(resultset.getString("RFIDNUM"));
            	searchStudentsList.add(student);
            }
           
            if (null != resultset) {
                resultset.close();
            }
        } catch (SQLException sQLException) {
        }
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

       return "studentSearchResultsPage";
	}
	
	
	public String createNewStudent(){
        Connection conn = BaseDao.getDBConnection();
        try {
            String strUserQuery = "insert into ATT_STUDENTS values(?,?,?,?,?,?)";
            System.out.println("--- select students query ---> "+strUserQuery);
            PreparedStatement ps = conn.prepareCall(strUserQuery);
            ps.setLong(1, Long.valueOf(newStudent.getStrRollno()));
            ps.setString(2, newStudent.getStrFirstName());
            ps.setString(3, newStudent.getStrLastName());
            ps.setLong(4, Long.valueOf(newStudent.getStrContactNo()));
            ps.setString(5, newStudent.getStrClass());
            ps.setLong(6, Long.valueOf(newStudent.getStrRfidNo()));
            int result = ps.executeUpdate();
        } catch (SQLException sQLException) {
        }
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

       return goToStudentResultsPage();
	}
	
	
	
	/*Getters and Setters*/

	public ArrayList<Student> getSearchStudentsList() {
		return searchStudentsList;
	}

	public void setSearchStudentsList(ArrayList<Student> searchStudentsList) {
		this.searchStudentsList = searchStudentsList;
	}

	public Student getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(Student selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public Student getNewStudent() {
		return newStudent;
	}

	public void setNewStudent(Student newStudent) {
		this.newStudent = newStudent;
	}
	
	
	
	
	
}
