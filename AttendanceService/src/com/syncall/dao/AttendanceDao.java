/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syncall.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.directory.SearchControls;

import com.syncall.backingbean.LoginBackingBean;
import com.syncall.model.AttendSearchCriteria;
import com.syncall.model.AttendanceRecord;



/**
 *
 * @author home
 */
public class AttendanceDao extends BaseDao{
    
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
            Logger.getLogger(AttendanceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "failure";
    }
    
    
    public void insertRecordToAttendance(String recordValue){
    	//recordValue = "$99999&99&0008478100&27122013113700*";
    	System.out.println("AttendanceDao.insertRecordToAttendance() "+recordValue);
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
    	String strRollNo = getRollno(rfid);
    	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
    	java.util.Date dateObj;
		try {
			dateObj = sdf.parse(stringDate);
			System.out.println("LoginDao.insertRecordToAttendance() "+dateObj);
	    	java.sql.Timestamp ts = new Timestamp(dateObj.getTime());
	    	System.out.println("LoginDao.insertRecordToAttendance() "+ts);
	    	String query = "insert into ATT_PERSON_SWIPES values ("+strRollNo+",?,"+machineId+",trunc(sysdate),0)";
	    	System.out.println("LoginDao.insertRecordToAttendance() teh query is ---> "+query);
	    	Connection conn = getDBConnection();
	    	PreparedStatement ps = conn.prepareCall(query);
	    	ps.setTimestamp(1, ts);
	        ps.executeUpdate();
	        conn.commit();
	        updateSummary(machineId,strRollNo,ts);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch(SQLException sqe){
			sqe.printStackTrace();
		}
    }
    
    public void updateSummary(String machineId, String strRollNo,java.sql.Timestamp ts){
    
    	String isExistQuery = "select count(1) from ATT_PERSON_SWIPES_SUMMARY where SWIPEDATE = trunc(sysdate) and UNIQUENO="+strRollNo;
    	String insertQuery =  "insert into ATT_PERSON_SWIPES_SUMMARY values ("+strRollNo+",?,null,"+machineId+",'',trunc(sysdate),0)";
    	String updateQuery =  "update ATT_PERSON_SWIPES_SUMMARY set LOGOUTTIME = ? where SWIPEDATE = trunc(sysdate) and UNIQUENO="+strRollNo;
    	Connection conn = getDBConnection();
    	
    	try {
    		ResultSet rs   = null;
    		System.out.println("AttendanceDao.updateSummary() isExistQuery --> "+isExistQuery);
    		PreparedStatement ps = conn.prepareCall(isExistQuery);
			rs = ps.executeQuery();
			int count = 0;
			if(rs.next()){
				count = rs.getInt(1);
			}
			if(count == 0){
				System.out.println("AttendanceDao.updateSummary() insertQuery --> "+insertQuery);
				ps = conn.prepareCall(insertQuery);
				ps.setTimestamp(1, ts);
		        ps.executeUpdate();
		        conn.commit();
			}else{
				System.out.println("AttendanceDao.updateSummary() updateQuery --> "+updateQuery);
				ps = conn.prepareCall(updateQuery);
				ps.setTimestamp(1, ts);
		        ps.executeUpdate();
		        conn.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public String getRollno(String strRfid){
    	String strRollNo="";
        Connection conn = BaseDao.getDBConnection();
        try {
            String strUserQuery = "select * from ATT_PERSON where RFIDNUM=" + strRfid;
            System.out.println("--- Login Query ---> "+strUserQuery);
            PreparedStatement ps = conn.prepareCall(strUserQuery);
            ResultSet resultset = ps.executeQuery();
            if (resultset.next()) {
            	strRollNo = resultset.getString("UNIQUENO");
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
    	return strRollNo;
    }
    
    
    public List<AttendanceRecord> getTodaysResults(){
    	List<AttendanceRecord> todaysSwipeList = null;
    	String strQuery = "select * from ATT_PERSON person, ATT_PERSON_SWIPES swipe WHERE person.UNIQUENO = swipe.UNIQUENO";
    	Connection conn = getDBConnection();
    	todaysSwipeList = new ArrayList<AttendanceRecord>();
    	
    	try {
			PreparedStatement ps = conn.prepareCall(strQuery);
			ResultSet rs = ps.executeQuery();
			AttendanceRecord record = null;
			while(rs.next()){
				record = new AttendanceRecord();
				record.setRollNo(rs.getString(1));
				record.setFirstName(rs.getString(2));
				record.setStrTimeStamp(rs.getString("SWIPETIME"));
				todaysSwipeList.add(record);
			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return todaysSwipeList;
    	
    }
    
    
    public List<AttendanceRecord> getAttendanceSearchResults(AttendSearchCriteria searchCriteria){
    	if("0".equals(searchCriteria.getAttSearchType())){
    		return getAbscentList(searchCriteria);
    	}else{
    		return getPresentList(searchCriteria);
    	}
    	
    }
    
    
    public  List<AttendanceRecord> getPresentList(AttendSearchCriteria searchCriteria){
    	List<AttendanceRecord> swipePresentSearchList = new ArrayList<AttendanceRecord>();
    	
    	String presentQuery = "select * from ATT_STUDENTS stu, ATT_PERSON_SWIPES swipe where stu.ROLLNO=swipe.UNIQUENO ";
    	if(null != searchCriteria.getStrClass() && !"".equals(searchCriteria.getStrClass())){
    		presentQuery =presentQuery+" AND stu.class="+searchCriteria.getStrClass();
    	}

    	if(null != searchCriteria.getStrRollNo() && !"".equals(searchCriteria.getStrRollNo())){
    		presentQuery =presentQuery+" AND stu.ROLLNO="+searchCriteria.getStrRollNo();
    	}
    	
    	if(null != searchCriteria.getFromDate() && null != searchCriteria.getToDate() ){
    		presentQuery =presentQuery+" AND swipe.SWIPEDATE between (?) and (?)";
    	}
    	
    	System.out.println("AttendanceDao.getPresentList()"+presentQuery);
    	Connection conn = getDBConnection();
    	
    	try {
			PreparedStatement ps = conn.prepareCall(presentQuery);
			if(null != searchCriteria.getFromDate() && null != searchCriteria.getToDate() ){
				ps.setDate(1,  new java.sql.Date(searchCriteria.getFromDate().getTime()));
				ps.setDate(2,  new java.sql.Date(searchCriteria.getToDate().getTime()));
			}
			ResultSet rs = ps.executeQuery();
			AttendanceRecord record = null;
			while(rs.next()){
				record = new AttendanceRecord();
				record.setRollNo(rs.getString(1));
				record.setFirstName(rs.getString(2));
				record.setStrTimeStamp(rs.getString("SWIPETIME"));
				record.setDateOfSwipe(rs.getDate("SWIPEDATE"));
				swipePresentSearchList.add(record);
				System.out.println("AttendanceDao.getPresentList() date is ---> "+rs.getString("SWIPEDATE"));
			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	 
    	 
    	
    	return swipePresentSearchList;
    }
    
    
    public  List<AttendanceRecord> getAbscentList(AttendSearchCriteria searchCriteria){
    	List<AttendanceRecord> swipeAbscentSearchList = null;
    	
    	return swipeAbscentSearchList;
    }
    
    
    public void getPersonDetails(){
    	 String query = "select * from ATT_PERSON";
    	 
    	 System.out.println("AttendanceDao.getPersonDetails() query --> "+query);
    	 
    	 Connection conn = null;
         String dburl = "jdbc:oracle:thin:@localhost:1521/XE";
         Properties props = new Properties();
         props.put("user", "nagarjun");
         props.put("password", "nagarjun");
         try {
             Class.forName("oracle.jdbc.OracleDriver");
             conn = DriverManager.getConnection(dburl, props);
             if(conn != null){
            	 System.out.println("Database Connected");
             }
             conn.setAutoCommit(false);
             
             
             
            PreparedStatement ps = conn.prepareCall(query);
            
 			ResultSet rs = ps.executeQuery();
 			while(rs.next()){
 				 System.out.println("AttendanceDao.getPersonDetails() -- Firstname: "+rs.getString("FIRSTNAME"));
 			}
         } catch (Exception ex) {
             ex.printStackTrace();
         }
         
         
         	
         
    	
    }
    
    
    public static void main(String[] args){
    	
    	AttendanceRecord searchCriteria = new AttendanceRecord();
    	
    	System.out.println("AttendanceDao.main() "+searchCriteria.getFirstName());
    	
    	char firstChar =' ';
    	searchCriteria.setFirstName("");
    	if(null != searchCriteria.getFirstName()){
    		firstChar = searchCriteria.getFirstName().charAt(0);
    	}
    	
    	System.out.println("AttendanceDao.main() "+firstChar);
    	System.out.println("AttendanceDao.main() ---> Completed");
    	
    	
    }
    
}
