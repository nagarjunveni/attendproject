/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syncall.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author home
 */
public class LoginDao extends BaseDao{
    
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
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "failure";
    }
    
    
    public void insertRecordToAttendance(String recordValue){
    	recordValue = "$99999&99&0008478100&27122013113700*";
    	recordValue = recordValue.replace("*", "");
    	String[] values = recordValue.split("&");
    	String schoolId = values[0];
    	String machineId = values[1];
    	String rfid = values[2];
    	String stringDate = values[3];
    	
    	System.out.println("LoginDao.insertRecordToAttendance() schoolId "+schoolId);
    	System.out.println("LoginDao.insertRecordToAttendance() machineId "+machineId);
    	System.out.println("LoginDao.insertRecordToAttendance() rfid "+rfid);
    	System.out.println("LoginDao.insertRecordToAttendance() stringDate "+stringDate.trim());
    	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
    	java.util.Date dateObj;
		try {
			dateObj = sdf.parse(stringDate);
			System.out.println("LoginDao.insertRecordToAttendance() "+dateObj);
	    	java.sql.Timestamp ts = new Timestamp(dateObj.getTime());
	    	System.out.println("LoginDao.insertRecordToAttendance() "+ts);
	    	String query = "insert into att_attendance values ("+rfid+",?,"+machineId+",sysdate)";
	    	System.out.println("LoginDao.insertRecordToAttendance() teh query is ---> "+query);
	    	Connection conn = getDBConnection();
	    	PreparedStatement ps = conn.prepareCall(query);
	    	ps.setTimestamp(1, ts);
	        ps.executeUpdate();
	        conn.commit();
	        
		} catch (ParseException e) {
			e.printStackTrace();
		} catch(SQLException sqe){
			sqe.printStackTrace();
		}
    }
    
    public static void main(String[] args){
    	LoginDao dao = new LoginDao();
    	//dao.testDB();
    	dao.insertRecordToAttendance(null);
    }
    
}
