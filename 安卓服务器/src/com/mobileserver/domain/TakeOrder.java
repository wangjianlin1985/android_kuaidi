package com.mobileserver.domain;

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
    private int expressTakeObj;
    public int getExpressTakeObj() {
        return expressTakeObj;
    }
    public void setExpressTakeObj(int expressTakeObj) {
        this.expressTakeObj = expressTakeObj;
    }

    /*��������*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
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
    private int orderStateObj;
    public int getOrderStateObj() {
        return orderStateObj;
    }
    public void setOrderStateObj(int orderStateObj) {
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