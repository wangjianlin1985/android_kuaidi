package com.chengxusheji.domain;

import java.sql.Timestamp;
public class TakeOrder {
    /*����id*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*���õĿ��*/
    private ExpressTake expressTakeObj;
    public ExpressTake getExpressTakeObj() {
        return expressTakeObj;
    }
    public void setExpressTakeObj(ExpressTake expressTakeObj) {
        this.expressTakeObj = expressTakeObj;
    }

    /*��������*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*�ӵ�ʱ��*/
    private String takeTime;
    public String getTakeTime() {
        return takeTime;
    }
    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    /*����״̬*/
    private OrderState orderStateObj;
    public OrderState getOrderStateObj() {
        return orderStateObj;
    }
    public void setOrderStateObj(OrderState orderStateObj) {
        this.orderStateObj = orderStateObj;
    }

    /*ʵʱ��̬*/
    private String ssdt;
    public String getSsdt() {
        return ssdt;
    }
    public void setSsdt(String ssdt) {
        this.ssdt = ssdt;
    }

    /*�û�����*/
    private String evaluate;
    public String getEvaluate() {
        return evaluate;
    }
    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

}