package com.chengxusheji.domain;

import java.sql.Timestamp;
public class OrderState {
    /*����״̬id*/
    private int orderStateId;
    public int getOrderStateId() {
        return orderStateId;
    }
    public void setOrderStateId(int orderStateId) {
        this.orderStateId = orderStateId;
    }

    /*����״̬����*/
    private String orderStateName;
    public String getOrderStateName() {
        return orderStateName;
    }
    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }

}