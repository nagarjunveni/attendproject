/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syncall.backingbean;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.syncall.dao.BaseDao;

/**
 *
 * @author Nagarjun_v
 */
@ManagedBean(name="loginBackingBean")
@SessionScoped
public class LoginBackingBean {

    private String strUserName;
    private String chaitanya;
    private boolean boolIsAdmin;
    private boolean boolLogin;

    public boolean isBoolLogin() {
        return boolLogin;
    }

    public void setBoolLogin(boolean boolLogin) {
        this.boolLogin = boolLogin;
    }

    public boolean isBoolIsAdmin() {
        return boolIsAdmin;
    }

    public void setBoolIsAdmin(boolean boolIsAdmin) {
        this.boolIsAdmin = boolIsAdmin;
    }
    
    

    public String getChaitanya() {
        return chaitanya;
    }

    public void setChaitanya(String chaitanya) {
        this.chaitanya = chaitanya;
    }
    private String strPassword;
    private boolean boolError = false;
    private String strErrorMesg;

    public String getStrErrorMesg() {
        return strErrorMesg;
    }

    public void setStrErrorMesg(String strErrorMesg) {
        this.strErrorMesg = strErrorMesg;
    }

    public boolean isBoolError() {
        return boolError;
    }

    public void setBoolError(boolean boolError) {
        this.boolError = boolError;
    }

   
    /***
     * 
     * @return 
     */
    public String login() {
       
        Connection conn = BaseDao.getDBConnection();
        try {
            String strUserQuery = "select * from ATT_USERS where username='" + this.strUserName + "'";
            System.out.println("--- Login Query ---> "+strUserQuery);
            PreparedStatement ps = conn.prepareCall(strUserQuery);
            ResultSet resultset = ps.executeQuery();
            if (resultset.next()) {
                String password = resultset.getString(2);
                String role = resultset.getString(5);
                int active = resultset.getInt(6);
                if(0 == active){
                    this.boolError = true;
                    this.strErrorMesg = "Please contact administrator to activate your account";
                    return null;
                }                
                if (null != password && this.strPassword.equals(password)) {
                    this.setRoleInSession(role);
                    if("ADMIN".equals(role)){
                        this.boolIsAdmin = true;
                    }else{
                        this.boolIsAdmin = false;
                    }
                    this.boolError = false;
                    this.boolLogin = true;
                    return "welcomepage";
                } else {
                    this.boolError = true;
                    this.strErrorMesg = "Please provide valid credentials";
                }
            } else {
                this.boolError = true;
                this.strErrorMesg = "The user does not exist in the system";
                return null;
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

    /**
     * 
     * @param role 
     */
    public void setRoleInSession(String role) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute("role", role);
    }

    /**
     * 
     * @return 
     */
    public String reset(){
        this.strErrorMesg = "";
        this.strUserName = "";
        this.strPassword = "";
        return null;
    }
    
    public String logout(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        this.strUserName = "";
        this.boolLogin=false;
        session.invalidate();
        return "loginpage";
    }
    
    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public String getStrUserName() {
        return strUserName;
    }
    
}
