package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ExpressTake;
import com.mobileserver.util.DB;

public class ExpressTakeDAO {

	public List<ExpressTake> QueryExpressTake(String taskTitle,int companyObj,String waybill,String receiverName,String telephone,String takePlace,String takeStateObj,String userObj,String addTime) {
		List<ExpressTake> expressTakeList = new ArrayList<ExpressTake>();
		DB db = new DB();
		String sql = "select * from ExpressTake where 1=1";
		if (!taskTitle.equals(""))
			sql += " and taskTitle like '%" + taskTitle + "%'";
		if (companyObj != 0)
			sql += " and companyObj=" + companyObj;
		if (!waybill.equals(""))
			sql += " and waybill like '%" + waybill + "%'";
		if (!receiverName.equals(""))
			sql += " and receiverName like '%" + receiverName + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		if (!takePlace.equals(""))
			sql += " and takePlace like '%" + takePlace + "%'";
		if (!takeStateObj.equals(""))
			sql += " and takeStateObj like '%" + takeStateObj + "%'";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!addTime.equals(""))
			sql += " and addTime like '%" + addTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ExpressTake expressTake = new ExpressTake();
				expressTake.setOrderId(rs.getInt("orderId"));
				expressTake.setTaskTitle(rs.getString("taskTitle"));
				expressTake.setCompanyObj(rs.getInt("companyObj"));
				expressTake.setWaybill(rs.getString("waybill"));
				expressTake.setReceiverName(rs.getString("receiverName"));
				expressTake.setTelephone(rs.getString("telephone"));
				expressTake.setReceiveMemo(rs.getString("receiveMemo"));
				expressTake.setTakePlace(rs.getString("takePlace"));
				expressTake.setGiveMoney(rs.getFloat("giveMoney"));
				expressTake.setTakeStateObj(rs.getString("takeStateObj"));
				expressTake.setUserObj(rs.getString("userObj"));
				expressTake.setAddTime(rs.getString("addTime"));
				expressTakeList.add(expressTake);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return expressTakeList;
	}
	/* 传入快递代拿对象，进行快递代拿的添加业务 */
	public String AddExpressTake(ExpressTake expressTake) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新快递代拿 */
			String sqlString = "insert into ExpressTake(taskTitle,companyObj,waybill,receiverName,telephone,receiveMemo,takePlace,giveMoney,takeStateObj,userObj,addTime) values (";
			sqlString += "'" + expressTake.getTaskTitle() + "',";
			sqlString += expressTake.getCompanyObj() + ",";
			sqlString += "'" + expressTake.getWaybill() + "',";
			sqlString += "'" + expressTake.getReceiverName() + "',";
			sqlString += "'" + expressTake.getTelephone() + "',";
			sqlString += "'" + expressTake.getReceiveMemo() + "',";
			sqlString += "'" + expressTake.getTakePlace() + "',";
			sqlString += expressTake.getGiveMoney() + ",";
			sqlString += "'" + expressTake.getTakeStateObj() + "',";
			sqlString += "'" + expressTake.getUserObj() + "',";
			sqlString += "'" + expressTake.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "快递代拿添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "快递代拿添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除快递代拿 */
	public String DeleteExpressTake(int orderId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ExpressTake where orderId=" + orderId;
			db.executeUpdate(sqlString);
			result = "快递代拿删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "快递代拿删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据订单id获取到快递代拿 */
	public ExpressTake GetExpressTake(int orderId) {
		ExpressTake expressTake = null;
		DB db = new DB();
		String sql = "select * from ExpressTake where orderId=" + orderId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				expressTake = new ExpressTake();
				expressTake.setOrderId(rs.getInt("orderId"));
				expressTake.setTaskTitle(rs.getString("taskTitle"));
				expressTake.setCompanyObj(rs.getInt("companyObj"));
				expressTake.setWaybill(rs.getString("waybill"));
				expressTake.setReceiverName(rs.getString("receiverName"));
				expressTake.setTelephone(rs.getString("telephone"));
				expressTake.setReceiveMemo(rs.getString("receiveMemo"));
				expressTake.setTakePlace(rs.getString("takePlace"));
				expressTake.setGiveMoney(rs.getFloat("giveMoney"));
				expressTake.setTakeStateObj(rs.getString("takeStateObj"));
				expressTake.setUserObj(rs.getString("userObj"));
				expressTake.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return expressTake;
	}
	/* 更新快递代拿 */
	public String UpdateExpressTake(ExpressTake expressTake) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ExpressTake set ";
			sql += "taskTitle='" + expressTake.getTaskTitle() + "',";
			sql += "companyObj=" + expressTake.getCompanyObj() + ",";
			sql += "waybill='" + expressTake.getWaybill() + "',";
			sql += "receiverName='" + expressTake.getReceiverName() + "',";
			sql += "telephone='" + expressTake.getTelephone() + "',";
			sql += "receiveMemo='" + expressTake.getReceiveMemo() + "',";
			sql += "takePlace='" + expressTake.getTakePlace() + "',";
			sql += "giveMoney=" + expressTake.getGiveMoney() + ",";
			sql += "takeStateObj='" + expressTake.getTakeStateObj() + "',";
			sql += "userObj='" + expressTake.getUserObj() + "',";
			sql += "addTime='" + expressTake.getAddTime() + "'";
			sql += " where orderId=" + expressTake.getOrderId();
			db.executeUpdate(sql);
			result = "快递代拿更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "快递代拿更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
