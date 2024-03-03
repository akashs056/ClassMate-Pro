package com.example.student.Models;

public class FacultyModel {
    private  String collegeName,fullName,email,phone,age,gender,profileImage,facultyUid;
    private String fcmToken; // Add this line for fcmToken
    private boolean profileSetup;

    public FacultyModel(String collegeName, String fullName, String email, String phone, String age, String gender, String profileImage, String facultyUid) {
        this.collegeName = collegeName;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.profileImage = profileImage;
        this.facultyUid = facultyUid;
    }

    public FacultyModel() {
    }

    public String getFcmToken() {
        return fcmToken;
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

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getFacultyUid() {
        return facultyUid;
    }

    public void setFacultyUid(String facultyUid) {
        this.facultyUid = facultyUid;
    }
}
