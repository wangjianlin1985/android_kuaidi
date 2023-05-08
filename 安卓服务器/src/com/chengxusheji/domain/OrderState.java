package com.chengxusheji.domain;

import java.sql.Timestamp;
public class OrderState {
    /*¶©µ¥×´Ì¬id*/
    private int orderStateId;
    public int getOrderStateId() {
        return orderStateId;
    }
    public void setOrderStateId(int orderStateId) {
        this.orderStateId = orderStateId;
    }

    /*¶©µ¥×´Ì¬Ãû³Æ*/
    private String orderStateName;
    public String getOrderStateName() {
        return orderStateName;
    }
    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }

}