package com.example.student.Models;

public class ClassRoomModel {
    private  String createdBy,className,key;

    public ClassRoomModel() {
    }

    public ClassRoomModel(String createdBy, String className, String key) {
        this.createdBy = createdBy;
        this.className = className;
        this.key = key;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
