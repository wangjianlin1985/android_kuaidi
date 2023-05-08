package com.mobileserver.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobileserver.util.DBUtil;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String sql2 = "select count(*) from users where username='"+userName+"'";
		String sql = "insert into users(username,password,phone,address) values('"+userName+"','"+password+"','"+phone+"','"+address+"')";
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		PrintWriter out = response.getWriter();
		String result = "0";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql2);
			ResultSet rs = pstmt.executeQuery();
			int count=0;
			while(rs.next()) {
				count=rs.getInt(1);
			}
			if(count!=0){
				result = "2";
			}else{
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				 result = "1";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "2";
		}finally {
			util.closeConn(conn);
		}	
		out.print(result);
	}
}
