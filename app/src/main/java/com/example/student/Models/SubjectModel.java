package com.example.student.Models;

public class SubjectModel {
    private String subjectName,facultyName,createdBy;

    public SubjectModel(String subjectName, String facultyName, String createdBy) {
        this.subjectName = subjectName;
        this.facultyName = facultyName;
        this.createdBy = createdBy;
    }

    public SubjectModel() {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
