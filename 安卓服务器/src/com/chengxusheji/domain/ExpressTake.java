package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ExpressTake {
    /*����id*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*��������*/
    private String taskTitle;
    public String getTaskTitle() {
        return taskTitle;
    }
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    /*������˾*/
    private Company companyObj;
    public Company getCompanyObj() {
        return companyObj;
    }
    public void setCompanyObj(Company companyObj) {
        this.companyObj = companyObj;
    }

    /*�˵�����*/
    private String waybill;
    public String getWaybill() {
        return waybill;
    }
    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    /*�ջ���*/
    private String receiverName;
    public String getReceiverName() {
        return receiverName;
    }
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /*�ջ��绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*�ջ���ע*/
    private String receiveMemo;
    public String getReceiveMemo() {
        return receiveMemo;
    }
    public void setReceiveMemo(String receiveMemo) {
        this.receiveMemo = receiveMemo;
    }

    /*�ʹ��ַ*/
    private String takePlace;
    public String getTakePlace() {
        return takePlace;
    }
    public void setTakePlace(String takePlace) {
        this.takePlace = takePlace;
    }

    /*���ñ���*/
    private float giveMoney;
    public float getGiveMoney() {
        return giveMoney;
    }
    public void setGiveMoney(float giveMoney) {
        this.giveMoney = giveMoney;
    }

    /*����״̬*/
    private String takeStateObj;
    public String getTakeStateObj() {
        return takeStateObj;
    }
    public void setTakeStateObj(String takeStateObj) {
        this.takeStateObj = takeStateObj;
    }

    /*���񷢲���*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*����ʱ��*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}