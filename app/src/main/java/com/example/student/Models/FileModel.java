package com.example.student.Models;

public class FileModel {
    private String uid,filename,path;
    private String title;

    private  String fileId;

    public FileModel(String uid, String filename, String path, String title) {
        this.uid = uid;
        this.filename = filename;
        this.path = path;
        this.title = title;
    }

    public FileModel() {
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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
