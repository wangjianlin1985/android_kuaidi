package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.TakeOrder;
import com.mobileserver.util.DB;

public class TakeOrderDAO {

	public List<TakeOrder> QueryTakeOrder(int expressTakeObj,String userObj,String takeTime,int orderStateObj) {
		List<TakeOrder> takeOrderList = new ArrayList<TakeOrder>();
		DB db = new DB();
		String sql = "select * from TakeOrder where 1=1";
		if (expressTakeObj != 0)
			sql += " and expressTakeObj=" + expressTakeObj;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!takeTime.equals(""))
			sql += " and takeTime like '%" + takeTime + "%'";
		if (orderStateObj != 0)
			sql += " and orderStateObj=" + orderStateObj;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				TakeOrder takeOrder = new TakeOrder();
				takeOrder.setOrderId(rs.getInt("orderId"));
				takeOrder.setExpressTakeObj(rs.getInt("expressTakeObj"));
				takeOrder.setUserObj(rs.getString("userObj"));
				takeOrder.setTakeTime(rs.getString("takeTime"));
				takeOrder.setOrderStateObj(rs.getInt("orderStateObj"));
				takeOrder.setSsdt(rs.getString("ssdt"));
				takeOrder.setEvaluate(rs.getString("evaluate"));
				takeOrderList.add(takeOrder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return takeOrderList;
	}
	/* ������ö������󣬽��д��ö��������ҵ�� */
	public String AddTakeOrder(TakeOrder takeOrder) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����´��ö��� */
			String sqlString = "insert into TakeOrder(expressTakeObj,userObj,takeTime,orderStateObj,ssdt,evaluate) values (";
			sqlString += takeOrder.getExpressTakeObj() + ",";
			sqlString += "'" + takeOrder.getUserObj() + "',";
			sqlString += "'" + takeOrder.getTakeTime() + "',";
			sqlString += takeOrder.getOrderStateObj() + ",";
			sqlString += "'" + takeOrder.getSsdt() + "',";
			sqlString += "'" + takeOrder.getEvaluate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���ö�����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ö������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�����ö��� */
	public String DeleteTakeOrder(int orderId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from TakeOrder where orderId=" + orderId;
			db.executeUpdate(sqlString);
			result = "���ö���ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ö���ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݶ���id��ȡ�����ö��� */
	public TakeOrder GetTakeOrder(int orderId) {
		TakeOrder takeOrder = null;
		DB db = new DB();
		String sql = "select * from TakeOrder where orderId=" + orderId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				takeOrder = new TakeOrder();
				takeOrder.setOrderId(rs.getInt("orderId"));
				takeOrder.setExpressTakeObj(rs.getInt("expressTakeObj"));
				takeOrder.setUserObj(rs.getString("userObj"));
				takeOrder.setTakeTime(rs.getString("takeTime"));
				takeOrder.setOrderStateObj(rs.getInt("orderStateObj"));
				takeOrder.setSsdt(rs.getString("ssdt"));
				takeOrder.setEvaluate(rs.getString("evaluate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return takeOrder;
	}
	/* ���´��ö��� */
	public String UpdateTakeOrder(TakeOrder takeOrder) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update TakeOrder set ";
			sql += "expressTakeObj=" + takeOrder.getExpressTakeObj() + ",";
			sql += "userObj='" + takeOrder.getUserObj() + "',";
			sql += "takeTime='" + takeOrder.getTakeTime() + "',";
			sql += "orderStateObj=" + takeOrder.getOrderStateObj() + ",";
			sql += "ssdt='" + takeOrder.getSsdt() + "',";
			sql += "evaluate='" + takeOrder.getEvaluate() + "'";
			sql += " where orderId=" + takeOrder.getOrderId();
			db.executeUpdate(sql);
			result = "���ö������³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ö�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
