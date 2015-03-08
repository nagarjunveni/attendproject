/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syncall.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.syncall.model.Student;



/**
 *
 * @author home
 */
public class PersonDao extends BaseDao{
    
    public String testDB(){
        String query = "select count(*) from ATT_STUDENTS";
        Connection conn = getDBConnection();
        try {
            PreparedStatement ps = conn.prepareCall(query);
            ResultSet rs =ps.executeQuery();
            if(rs.next()){
            	 int count = rs.getInt(1); 
                 System.out.println("LoginDao.testDB()----> "+count);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(PersonDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "failure";
    }
    
    
   public List searchStudents(Student studentCriteria){
	   List<Student> studentList = new ArrayList<Student>();
	   String query = "select * from ATT_STUDENTS where 1=1  ";
	   if(null != studentCriteria.getStrFirstName() && ! "".equals(studentCriteria.getStrFirstName())){
		   query+=" and FIRSTNAME ='"+studentCriteria.getStrFirstName()+"'";
	   }
	   
	   if(null != studentCriteria.getStrLastName() && !"".equals(studentCriteria.getStrLastName())){
		   query+=" and LASTNAME ='"+studentCriteria.getStrLastName()+"'";
	   }
	   
	   if(null != studentCriteria.getStrRollno() && !"".equals(studentCriteria.getStrRollno())){
		   query+=" and ROLLNO ='"+studentCriteria.getStrRollno()+"'";
	   }
	   
	   if(null != studentCriteria.getStrClass() && !"".equals(studentCriteria.getStrClass())){
		   query+=" and CLASS ='"+studentCriteria.getStrClass()+"'";
	   }
	   System.out.println("PersonDao.searchStudents() query to get the student search results --> "+query);
	   Connection conn = getDBConnection();
       try {
           PreparedStatement ps = conn.prepareCall(query);
           ResultSet rs =ps.executeQuery();
           Student student = null;
           while(rs.next()){
        	   student = new Student();
        	   student.setStrFirstName(rs.getString("FIRSTNAME"));
        	   student.setStrLastName(rs.getString("LASTNAME"));
        	   student.setStrRollno(rs.getString("ROLLNO"));
        	   student.setStrRfidNo(rs.getString("RFIDNUM"));
        	   student.setStrClass(rs.getString("CLASS"));
        	   studentList.add(student);
           }
       } catch (SQLException ex) {
           Logger.getLogger(PersonDao.class.getName()).log(Level.SEVERE, null, ex);
       }
	   return studentList;	   
   }
    
}
