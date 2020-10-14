package com.example.firebasedatabsedemo;

public class Users {
    private String userId;

    private String userFName;
    private String userLName;
    private String userBuildingNo;
    private String useremail;
    private String userFlatNo;
    private String userDob;
    private String userContactNo;
    private String userSuburb;
    private String userPassword;
    private String userCnfPassword;
     public Users(){

     }

    public Users(String userId, String userFName, String userLName, String userBuildingNo, String useremail, String userFlatNo, String userDob, String userContactNo, String userSuburb, String userPassword, String userCnfPassword) {
        this.userId = userId;
        this.userFName = userFName;
        this.userLName = userLName;
        this.userBuildingNo = userBuildingNo;
        this.useremail = useremail;
        this.userFlatNo = userFlatNo;
        this.userDob = userDob;
        this.userContactNo = userContactNo;
        this.userSuburb = userSuburb;
        this.userPassword = userPassword;
        this.userCnfPassword = userCnfPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserFName() {
        return userFName;
    }

    public String getUserLName() {
        return userLName;
    }

    public String getUserBuildingNo() {
        return userBuildingNo;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getUserFlatNo() {
        return userFlatNo;
    }

    public String getUserDob() {
        return userDob;
    }

    public String getUserContactNo() {
        return userContactNo;
    }

    public String getUserSuburb() {
        return userSuburb;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserCnfPassword() {
        return userCnfPassword;
    }
}
