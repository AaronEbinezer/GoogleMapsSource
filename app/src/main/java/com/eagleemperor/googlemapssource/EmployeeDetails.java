package com.eagleemperor.googlemapssource;

/**
 * Created by Aaron on 7/14/2018.
 */

public class EmployeeDetails {

    String name,userId,phoneNo,password;

    public EmployeeDetails(String name, String userId, String phoneNo, String password) {
        this.name = name;
        this.userId = userId;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
