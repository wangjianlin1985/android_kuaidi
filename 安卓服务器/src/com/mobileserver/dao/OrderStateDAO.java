package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.OrderState;
import com.mobileserver.util.DB;

public class OrderStateDAO {

	public List<OrderState> QueryOrderState() {
		List<OrderState> orderStateList = new ArrayList<OrderState>();
		DB db = new DB();
		String sql = "select * from OrderState where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				OrderState orderState = new OrderState();
				orderState.setOrderStateId(rs.getInt("orderStateId"));
				orderState.setOrderStateName(rs.getString("orderStateName"));
				orderStateList.add(orderState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderStateList;
	}
	/* ���붩��״̬���󣬽��ж���״̬�����ҵ�� */
	public String AddOrderState(OrderState orderState) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����¶���״̬ */
			String sqlString = "insert into OrderState(orderStateName) values (";
			sqlString += "'" + orderState.getOrderStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "����״̬��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����״̬���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������״̬ */
	public String DeleteOrderState(int orderStateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from OrderState where orderStateId=" + orderStateId;
			db.executeUpdate(sqlString);
			result = "����״̬ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����״̬ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݶ���״̬id��ȡ������״̬ */
	public OrderState GetOrderState(int orderStateId) {
		OrderState orderState = null;
		DB db = new DB();
		String sql = "select * from OrderState where orderStateId=" + orderStateId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				orderState = new OrderState();
				orderState.setOrderStateId(rs.getInt("orderStateId"));
				orderState.setOrderStateName(rs.getString("orderStateName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderState;
	}
	/* ���¶���״̬ */
	public String UpdateOrderState(OrderState orderState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update OrderState set ";
			sql += "orderStateName='" + orderState.getOrderStateName() + "'";
			sql += " where orderStateId=" + orderState.getOrderStateId();
			db.executeUpdate(sql);
			result = "����״̬���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����״̬����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
