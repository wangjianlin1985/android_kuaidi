package com.mobileserver.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//一个用于查找数据源的工具类。
public class DB {

	private Connection conn = null;

	private Statement stmt = null;

	ResultSet rs = null;

	private ConnectionPool connPool = null; /* 数据库连接池对象 */

	public DB() {
		connPool = ConnectionPoolUtils.GetPoolInstance();
	}

	public ResultSet executeQuery(String sql) throws Exception {
		try {
			/*
			 * Class.forName("org.logicalcobwebs.proxool.ProxoolDriver"); con =
			 * DriverManager.getConnection("proxool.xml-test");
			 */
			conn = connPool.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			throw e;
		}
		// catch(NamingException e){throw e;}

		return rs;
	}

	// 执行Insert,Update语句
	public int executeUpdate(String sql) throws Exception {
		// stmt = null;
		// rs=null;
		int result = 0;
		 
			// Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			// conn = DriverManager.getConnection("proxool.xml-test");// 读取配置文件
			conn = connPool.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = stmt.executeUpdate(sql);

		 
		return result;
	}

	/* 根据查询的sql语句得到符合条件的记录条数 */
	public int getRecordCount(String sqlString) throws Exception {
		int recordCount = 0;
		try {
			/*
			 * Class.forName("org.logicalcobwebs.proxool.ProxoolDriver"); con =
			 * DriverManager.getConnection("proxool.xml-test");
			 */
			conn = connPool.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlString);
			rs.next();
			recordCount = rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		}
		return recordCount;
	}

	/* 根据查询sql语句得到第一行第一列的数据 */
	public String GetScalarString(String sqlString) {
		String result = "";
		try {
			/*
			 * Class.forName("org.logicalcobwebs.proxool.ProxoolDriver"); con =
			 * DriverManager.getConnection("proxool.xml-test");
			 */
			conn = connPool.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sqlString);
			// if(rs.next())
			if (rs.next())
				result = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/* 执行事务操作:几条sql更新语句组成一个事务 */
	public boolean excuteSqlStrings(String[] sqlStrings) {
		Connection conn = null;
		Statement stmt = null;
		boolean flag = false;
		try {
			conn = connPool.getConnection();
			stmt = conn.createStatement();
			conn.setAutoCommit(false);
			for(int i=0;i<sqlStrings.length;i++) {
				stmt.addBatch(sqlStrings[i]);
			}
			stmt.executeBatch();
			conn.commit();
			flag = true;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
			e.printStackTrace();
		} finally {
			 this.all_close();
		}
		return flag;

	}

	// 关闭stmt和关闭连接
	public void all_close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}

			if (conn != null) {
				connPool.returnConnection(conn);
				conn = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
