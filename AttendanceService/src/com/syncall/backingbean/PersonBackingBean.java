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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.syncall.dao.BaseDao;
import com.syncall.dao.PersonDao;
import com.syncall.model.Person;
import com.syncall.model.Student;

/**
 *
 * @author Nagarjun_v
 */
@ManagedBean(name="personBackingBean")
@SessionScoped
public class PersonBackingBean {
	
	private List<Student> searchStudentsList = new ArrayList<Student>();
	private List<Person> searchPersonList = new ArrayList<Person>();
	private Student selectedStudent;
	private Student newStudent = new Student();
	private Person newPerson = new Person();
	private Person editPerson = new Person();
	private Person selectedPerson = new Person();
	private Student searchStudent=new Student();
	private Person searchPerson = new Person();
	
	
	public Person getEditPerson() {
		return editPerson;
	}


	public void setEditPerson(Person editPerson) {
		this.editPerson = editPerson;
	}


	public Person getSearchPerson() {
		return searchPerson;
	}


	public void setSearchPerson(Person searchPerson) {
		this.searchPerson = searchPerson;
	}

	public Student getSearchStudent() {
		return searchStudent;
	}


	public void setSearchStudent(Student searchStudent) {
		this.searchStudent = searchStudent;
	}


	public Person getSelectedPerson() {
		return selectedPerson;
	}


	public void setSelectedPerson(Person selectedPerson) {
		this.selectedPerson = selectedPerson;
	}


	public Person getNewPerson() {
		return newPerson;
	}


	public void setNewPerson(Person newPerson) {
		this.newPerson = newPerson;
	}


	public List<Person> getSearchPersonList() {
		return searchPersonList;
	}


	public void setSearchPersonList(List<Person> searchPersonList) {
		this.searchPersonList = searchPersonList;
	}


	public String goToPeopleSearchPage(){
		return "peopleSearchPage";
	}
	
	public String goToStudentSearchPage(){
		return "studentSearchPage";
	}
	
	public String goToPeopleResultsPage(){
        Connection conn = BaseDao.getDBConnection();
        searchPersonList = new ArrayList<Person>();
        try {
            String strUserQuery = "select * from ATT_PERSON where 1=1";
            if(null != searchPerson.getStrFirstName() && !"".equals(searchPerson.getStrFirstName())){
            	strUserQuery = strUserQuery + " AND FIRSTNAME='"+searchPerson.getStrFirstName()+"'";
            }
            
            if(null != searchPerson.getStrUniqueNo() && !"".equals(searchPerson.getStrUniqueNo())){
            	strUserQuery = strUserQuery + " AND UNIQUENO="+searchPerson.getStrUniqueNo();
            }
            
            if(null != searchPerson.getStrLastName() && !"".equals(searchPerson.getStrLastName())){
            	strUserQuery = strUserQuery + " AND LASTNAME='"+searchPerson.getStrLastName()+"'";
            }
            
            if(null != searchPerson.getStrContactNo() && !"".equals(searchPerson.getStrContactNo())){
            	strUserQuery = strUserQuery + " AND CONTACTNO='"+searchPerson.getStrContactNo()+"'";
            }
            
            if(null != searchPerson.getStrRfidNo() && !"".equals(searchPerson.getStrRfidNo())){
            	strUserQuery = strUserQuery + " AND RFIDNUM="+searchPerson.getStrRfidNo();
            }
            
            strUserQuery = strUserQuery + " order by 1";
            System.out.println("--- search people  query ---> "+strUserQuery);
            
            PreparedStatement ps = conn.prepareCall(strUserQuery);
            ResultSet resultset = ps.executeQuery();
            Person person;
            while(resultset.next()){
            	person = new Person();
            	person.setStrUniqueNo(resultset.getString("UNIQUENO"));
            	person.setStrFirstName(resultset.getString("FIRSTNAME"));
            	person.setStrLastName(resultset.getString("LASTNAME"));
            	person.setStrContactNo(resultset.getString("CONTACTNO"));
            	person.setStrRfidNo(resultset.getString("RFIDNUM"));
            	person.setStrConsumerId(resultset.getString("CONSUMERID"));
            	searchPersonList.add(person);
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

       return null;
	}
	
	
	public String createNewPerson(){
        Connection conn = BaseDao.getDBConnection();
        try {
            String strUserQuery = "insert into ATT_PERSON values(?,?,?,?,?,?)";
            System.out.println("--- select students query ---> "+strUserQuery);
            PreparedStatement ps = conn.prepareCall(strUserQuery);
            ps.setLong(1, Long.valueOf(newPerson.getStrUniqueNo()));
            ps.setString(2, newPerson.getStrFirstName());
            ps.setString(3, newPerson.getStrLastName());
            ps.setLong(4, Long.valueOf(newPerson.getStrContactNo()));
            ps.setLong(5, Long.valueOf(newPerson.getStrRfidNo()));
            ps.setLong(6, Long.valueOf(newPerson.getStrConsumerId()));
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

       return goToPeopleResultsPage();
	}
	
	
	public String searchStudents(){
		System.out.println("PersonBackingBean.searchStudents()");
		System.out.println("PersonBackingBean.searchStudents()"+searchStudent.getStrFirstName());
		PersonDao personDao = new PersonDao();
		searchStudentsList = personDao.searchStudents(searchStudent);
		return null;
	}
	
	
	public String gotToCreatePersonPage(){
		return "createPersonPage";
	}
	
	
	public String goToEditPersonPage(){
		editPerson = new Person();
		editPerson.setStrFirstName(selectedPerson.getStrFirstName());
		editPerson.setStrLastName(selectedPerson.getStrLastName());
		editPerson.setStrRfidNo(selectedPerson.getStrRfidNo());
		editPerson.setStrRollno(selectedPerson.getStrRollno());
		editPerson.setStrUniqueNo(selectedPerson.getStrUniqueNo());
		editPerson.setStrContactNo(selectedPerson.getStrContactNo());		
		return null;
	}
	
	public String updatePerson(){
		
		return null;
	}
	
	public String deletePerson(){
		Connection conn = BaseDao.getDBConnection();
        try {
            String strUserQuery = "delete from ATT_PERSON where UNIQUENO in (?)";
            System.out.println("--- select students query ---> "+strUserQuery);
            PreparedStatement ps = conn.prepareCall(strUserQuery);
            ps.setLong(1, Long.valueOf(selectedPerson.getStrUniqueNo()));
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
		return goToPeopleResultsPage();
	}
	
	
	/*Getters and Setters*/

	public List<Student> getSearchStudentsList() {
		return searchStudentsList;
	}

	public void setSearchStudentsList(List<Student> searchStudentsList) {
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
