package com.mobileserver.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobileserver.util.DBUtil;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = URLDecoder.decode(request.getParameter("userName"),"UTF-8");
		String password = URLDecoder.decode(request.getParameter("password"),"UTF-8");
		String sql = "select * from admin where username= '"+userName +"' and password='"+password+"'";
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		String result = "0";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				result= userName;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}	
		PrintWriter out = response.getWriter();
		out.print(result);
	}

}
