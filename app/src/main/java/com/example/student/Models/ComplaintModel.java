package com.example.student.Models;

public class ComplaintModel {
    private String from,subject,description;

    public ComplaintModel(String from, String subject, String description) {
        this.from = from;
        this.subject = subject;
        this.description = description;
    }

    public ComplaintModel() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
