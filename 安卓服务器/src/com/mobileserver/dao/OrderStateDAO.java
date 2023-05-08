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
	/* 传入订单状态对象，进行订单状态的添加业务 */
	public String AddOrderState(OrderState orderState) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新订单状态 */
			String sqlString = "insert into OrderState(orderStateName) values (";
			sqlString += "'" + orderState.getOrderStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "订单状态添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单状态添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除订单状态 */
	public String DeleteOrderState(int orderStateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from OrderState where orderStateId=" + orderStateId;
			db.executeUpdate(sqlString);
			result = "订单状态删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单状态删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据订单状态id获取到订单状态 */
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
	/* 更新订单状态 */
	public String UpdateOrderState(OrderState orderState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update OrderState set ";
			sql += "orderStateName='" + orderState.getOrderStateName() + "'";
			sql += " where orderStateId=" + orderState.getOrderStateId();
			db.executeUpdate(sql);
			result = "订单状态更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "订单状态更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
