package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ExpressTake {
    /*订单id*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*代拿任务*/
    private String taskTitle;
    public String getTaskTitle() {
        return taskTitle;
    }
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    /*物流公司*/
    private Company companyObj;
    public Company getCompanyObj() {
        return companyObj;
    }
    public void setCompanyObj(Company companyObj) {
        this.companyObj = companyObj;
    }

    /*运单号码*/
    private String waybill;
    public String getWaybill() {
        return waybill;
    }
    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    /*收货人*/
    private String receiverName;
    public String getReceiverName() {
        return receiverName;
    }
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /*收货电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*收货备注*/
    private String receiveMemo;
    public String getReceiveMemo() {
        return receiveMemo;
    }
    public void setReceiveMemo(String receiveMemo) {
        this.receiveMemo = receiveMemo;
    }

    /*送达地址*/
    private String takePlace;
    public String getTakePlace() {
        return takePlace;
    }
    public void setTakePlace(String takePlace) {
        this.takePlace = takePlace;
    }

    /*代拿报酬*/
    private float giveMoney;
    public float getGiveMoney() {
        return giveMoney;
    }
    public void setGiveMoney(float giveMoney) {
        this.giveMoney = giveMoney;
    }

    /*代拿状态*/
    private String takeStateObj;
    public String getTakeStateObj() {
        return takeStateObj;
    }
    public void setTakeStateObj(String takeStateObj) {
        this.takeStateObj = takeStateObj;
    }

    /*任务发布人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*发布时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}