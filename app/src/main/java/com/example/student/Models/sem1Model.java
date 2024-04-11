package com.example.student.Models;

public class sem1Model {
    private String Physics,Math,Java,StudentUid;

    public sem1Model() {
    }

    public sem1Model(String physics, String math, String java, String studentUid) {
        Physics = physics;
        Math = math;
        Java = java;
        StudentUid = studentUid;
    }

    public String getPhysics() {
        return Physics;
    }

    public void setPhysics(String physics) {
        Physics = physics;
    }

    public String getMath() {
        return Math;
    }

    public void setMath(String math) {
        Math = math;
    }

    public String getJava() {
        return Java;
    }

    public void setJava(String java) {
        Java = java;
    }

    public String getStudentUid() {
        return StudentUid;
    }

    public void setStudentUid(String studentUid) {
        StudentUid = studentUid;
    }
}
