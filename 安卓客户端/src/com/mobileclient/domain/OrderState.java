package com.mobileclient.domain;

import java.io.Serializable;

public class OrderState implements Serializable {
    /*订单状态id*/
    private int orderStateId;
    public int getOrderStateId() {
        return orderStateId;
    }
    public void setOrderStateId(int orderStateId) {
        this.orderStateId = orderStateId;
    }

    /*订单状态名称*/
    private String orderStateName;
    public String getOrderStateName() {
        return orderStateName;
    }
    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }

}