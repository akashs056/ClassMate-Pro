package com.example.student.Models;

public class sem2Model {
    public sem2Model() {
    }

    private String Python,dbms,Microprocessor;

    public sem2Model(String python, String dbms, String microprocessor) {
        Python = python;
        this.dbms = dbms;
        Microprocessor = microprocessor;
    }

    public String getPython() {
        return Python;
    }

    public void setPython(String python) {
        Python = python;
    }

    public String getDbms() {
        return dbms;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    public String getMicroprocessor() {
        return Microprocessor;
    }

    public void setMicroprocessor(String microprocessor) {
        Microprocessor = microprocessor;
    }
}
