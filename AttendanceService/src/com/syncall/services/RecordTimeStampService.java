package com.syncall.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.syncall.dao.AttendanceDao;

/**
 * Servlet implementation class RecordTimeStampService
 */
@WebServlet("/RecordTimeStampService")
public class RecordTimeStampService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordTimeStampService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getQueryString();
		AttendanceDao dao = new AttendanceDao();
		dao.insertRecordToAttendance(query);
		System.out.println("RecordTimeStampService.doGet() the query string is ---> "+query);
		PrintWriter out = response.getWriter();
		out.println("$RFID=0#");
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
