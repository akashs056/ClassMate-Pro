package com.example.student.Models;

public class UserModel {
    private String college,name,email,rollNo,program,uid,key,phone,profileImage,fcmToken;
    private boolean profileSetup;
    private String parentFcmToken;

    public UserModel(String college, String name, String email, String rollNo, String program, String uid,String key,String phone, String profileImage) {
        this.college = college;
        this.name = name;
        this.email = email;
        this.rollNo = rollNo;
        this.program = program;
        this.uid = uid;
        this.profileImage = profileImage;
        this.key=key;
        this.phone=phone;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public String getParentFcmToken() {
        return parentFcmToken;
    }

    public void setParentFcmToken(String parentFcmToken) {
        this.parentFcmToken = parentFcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public boolean isProfileSetup() {
        return profileSetup;
    }

    public void setProfileSetup(boolean profileSetup) {
        this.profileSetup = profileSetup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UserModel() {
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
