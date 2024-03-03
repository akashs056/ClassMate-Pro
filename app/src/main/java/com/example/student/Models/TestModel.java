package com.example.student.Models;

public class TestModel {
    private String testId,subjectName,title,uid,filename,path;

    public TestModel(String testId, String subjectName, String title, String uid, String filename, String path) {
        this.testId = testId;
        this.subjectName = subjectName;
        this.title = title;
        this.uid = uid;
        this.filename = filename;
        this.path = path;
    }

    public TestModel() {
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
