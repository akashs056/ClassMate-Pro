package com.example.student.Models;

public class GroupChatModel {
    private String message,sentBy;
    private long sentAt;

    public GroupChatModel(String message, String sentBy, long sentAt) {
        this.message = message;
        this.sentBy = sentBy;
        this.sentAt = sentAt;
    }

    public GroupChatModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public long getSentAt() {
        return sentAt;
    }

    public void setSentAt(long sentAt) {
        this.sentAt = sentAt;
    }
}
