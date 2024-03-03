package com.example.student.Models;

public class ParentsModel {
    private String studentUID,StudentRollNo,email,password,uid;
    private  String fcmToken;

    public ParentsModel(String studentUID, String studentRollNo, String email, String password, String uid) {
        this.studentUID = studentUID;
        StudentRollNo = studentRollNo;
        this.email = email;
        this.password = password;
        this.uid = uid;
    }

    public ParentsModel() {
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getStudentUID() {
        return studentUID;
    }

    public void setStudentUID(String studentUID) {
        this.studentUID = studentUID;
    }

    public String getStudentRollNo() {
        return StudentRollNo;
    }

    public void setStudentRollNo(String studentRollNo) {
        StudentRollNo = studentRollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
