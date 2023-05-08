package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.UserInfo;
import com.mobileserver.util.DB;

public class UserInfoDAO {

	public List<UserInfo> QueryUserInfo(String user_name,String userType,String name,Timestamp birthDate,String telephone,String shenHeState) {
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		DB db = new DB();
		String sql = "select * from UserInfo where 1=1";
		if (!user_name.equals(""))
			sql += " and user_name like '%" + user_name + "%'";
		if (!userType.equals(""))
			sql += " and userType like '%" + userType + "%'";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if(birthDate!=null)
			sql += " and birthDate='" + birthDate + "'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		if (!shenHeState.equals(""))
			sql += " and shenHeState like '%" + shenHeState + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUser_name(rs.getString("user_name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setUserType(rs.getString("userType"));
				userInfo.setName(rs.getString("name"));
				userInfo.setGender(rs.getString("gender"));
				userInfo.setBirthDate(rs.getTimestamp("birthDate"));
				userInfo.setUserPhoto(rs.getString("userPhoto"));
				userInfo.setTelephone(rs.getString("telephone"));
				userInfo.setEmail(rs.getString("email"));
				userInfo.setAddress(rs.getString("address"));
				userInfo.setAuthFile(rs.getString("authFile"));
				userInfo.setShenHeState(rs.getString("shenHeState"));
				userInfo.setRegTime(rs.getString("regTime"));
				userInfoList.add(userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userInfoList;
	}
	/* �����û����󣬽����û������ҵ�� */
	public String AddUserInfo(UserInfo userInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в������û� */
			String sqlString = "insert into UserInfo(user_name,password,userType,name,gender,birthDate,userPhoto,telephone,email,address,authFile,shenHeState,regTime) values (";
			sqlString += "'" + userInfo.getUser_name() + "',";
			sqlString += "'" + userInfo.getPassword() + "',";
			sqlString += "'" + userInfo.getUserType() + "',";
			sqlString += "'" + userInfo.getName() + "',";
			sqlString += "'" + userInfo.getGender() + "',";
			sqlString += "'" + userInfo.getBirthDate() + "',";
			sqlString += "'" + userInfo.getUserPhoto() + "',";
			sqlString += "'" + userInfo.getTelephone() + "',";
			sqlString += "'" + userInfo.getEmail() + "',";
			sqlString += "'" + userInfo.getAddress() + "',";
			sqlString += "'" + userInfo.getAuthFile() + "',";
			sqlString += "'" + userInfo.getShenHeState() + "',";
			sqlString += "'" + userInfo.getRegTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�û���ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�û����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���û� */
	public String DeleteUserInfo(String user_name) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from UserInfo where user_name='" + user_name + "'";
			db.executeUpdate(sqlString);
			result = "�û�ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�û�ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* �����û�����ȡ���û� */
	public UserInfo GetUserInfo(String user_name) {
		UserInfo userInfo = null;
		DB db = new DB();
		String sql = "select * from UserInfo where user_name='" + user_name + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				userInfo = new UserInfo();
				userInfo.setUser_name(rs.getString("user_name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setUserType(rs.getString("userType"));
				userInfo.setName(rs.getString("name"));
				userInfo.setGender(rs.getString("gender"));
				userInfo.setBirthDate(rs.getTimestamp("birthDate"));
				userInfo.setUserPhoto(rs.getString("userPhoto"));
				userInfo.setTelephone(rs.getString("telephone"));
				userInfo.setEmail(rs.getString("email"));
				userInfo.setAddress(rs.getString("address"));
				userInfo.setAuthFile(rs.getString("authFile"));
				userInfo.setShenHeState(rs.getString("shenHeState"));
				userInfo.setRegTime(rs.getString("regTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userInfo;
	}
	/* �����û� */
	public String UpdateUserInfo(UserInfo userInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update UserInfo set ";
			sql += "password='" + userInfo.getPassword() + "',";
			sql += "userType='" + userInfo.getUserType() + "',";
			sql += "name='" + userInfo.getName() + "',";
			sql += "gender='" + userInfo.getGender() + "',";
			sql += "birthDate='" + userInfo.getBirthDate() + "',";
			sql += "userPhoto='" + userInfo.getUserPhoto() + "',";
			sql += "telephone='" + userInfo.getTelephone() + "',";
			sql += "email='" + userInfo.getEmail() + "',";
			sql += "address='" + userInfo.getAddress() + "',";
			sql += "authFile='" + userInfo.getAuthFile() + "',";
			sql += "shenHeState='" + userInfo.getShenHeState() + "',";
			sql += "regTime='" + userInfo.getRegTime() + "'";
			sql += " where user_name='" + userInfo.getUser_name() + "'";
			db.executeUpdate(sql);
			result = "�û����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�û�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
