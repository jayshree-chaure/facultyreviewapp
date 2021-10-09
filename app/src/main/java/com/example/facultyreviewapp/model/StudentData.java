package com.example.facultyreviewapp.model;

public class StudentData {
    private String BarCodeID;
    private String sname;
    private String sclass;
    public StudentData(String barCodeID, String sname, String sclass) {
        BarCodeID = barCodeID;
        this.sname = sname;
        this.sclass = sclass;
    }
    public StudentData()
    {

    }

    public String getBarCodeID() {
        return BarCodeID;
    }

    public void setBarCodeID(String barCodeID) {
        BarCodeID = barCodeID;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    @Override
    public String toString() {
        return "StudentData{" +
                "BarCodeID='" + BarCodeID + '\'' +
                ", sname='" + sname + '\'' +
                ", sclass='" + sclass + '\'' +
                '}';
    }
}
