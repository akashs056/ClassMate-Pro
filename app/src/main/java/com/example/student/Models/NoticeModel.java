package com.example.student.Models;

public class NoticeModel {
    private String noticeTitle, noticeDescription;

    public NoticeModel(String title, String description) {
        this.noticeTitle = title;
        this.noticeDescription = description;
    }

    public NoticeModel() {
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeDescription() {
        return noticeDescription;
    }

    public void setNoticeDescription(String noticeDescription) {
        this.noticeDescription = noticeDescription;
    }
}
