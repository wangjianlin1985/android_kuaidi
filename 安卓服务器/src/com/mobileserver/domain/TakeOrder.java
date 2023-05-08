package com.mobileserver.domain;

public class TakeOrder {
    /*订单id*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*代拿的快递*/
    private int expressTakeObj;
    public int getExpressTakeObj() {
        return expressTakeObj;
    }
    public void setExpressTakeObj(int expressTakeObj) {
        this.expressTakeObj = expressTakeObj;
    }

    /*接任务人*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*接单时间*/
    private String takeTime;
    public String getTakeTime() {
        return takeTime;
    }
    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    /*订单状态*/
    private int orderStateObj;
    public int getOrderStateObj() {
        return orderStateObj;
    }
    public void setOrderStateObj(int orderStateObj) {
        this.orderStateObj = orderStateObj;
    }

    /*实时动态*/
    private String ssdt;
    public String getSsdt() {
        return ssdt;
    }
    public void setSsdt(String ssdt) {
        this.ssdt = ssdt;
    }

    /*用户评价*/
    private String evaluate;
    public String getEvaluate() {
        return evaluate;
    }
    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

}