package com.chengxusheji.domain;

import java.sql.Timestamp;
public class UserInfo {
    /*�û���*/
    private String user_name;
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*�û�����*/
    private String userType;
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /*����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*�Ա�*/
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    /*��������*/
    private Timestamp birthDate;
    public Timestamp getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /*�û���Ƭ*/
    private String userPhoto;
    public String getUserPhoto() {
        return userPhoto;
    }
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*����*/
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*��ͥ��ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*��֤�ļ�*/
    private String authFile;
    public String getAuthFile() {
        return authFile;
    }
    public void setAuthFile(String authFile) {
        this.authFile = authFile;
    }

    /*���״̬*/
    private String shenHeState;
    public String getShenHeState() {
        return shenHeState;
    }
    public void setShenHeState(String shenHeState) {
        this.shenHeState = shenHeState;
    }

    /*ע��ʱ��*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

}