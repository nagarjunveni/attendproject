/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syncall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *
 * @author home
 */
public class BaseDao {
	
	private static ResourceBundle ATTENDACE_CONF = ResourceBundle.getBundle("attendanceConf");
    
     public static Connection getDBConnection() {
        Connection conn = null;
        String dburl = "jdbc:oracle:thin:@localhost:1521/XE";
        Properties props = new Properties();
        System.out.println("BaseDao.getDBConnection() username: "+ATTENDACE_CONF.getString("database_username"));
        System.out.println("BaseDao.getDBConnection() password: "+ATTENDACE_CONF.getString("database_password"));
        props.put("user", ATTENDACE_CONF.getString("database_username"));
        props.put("password", ATTENDACE_CONF.getString("database_password"));
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(dburl, props);
            conn.setAutoCommit(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }
    
}
