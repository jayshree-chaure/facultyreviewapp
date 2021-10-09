package com.example.facultyreviewapp.model;

public class ProfessorData {
    private String pid;
    private String pname;
    private String pinfo;
    private String subjectskill;
    private String ds;
    private String ps;
    private String pun;
    private String cs;
    private String count;

    public ProfessorData(String pid, String pname, String pinfo, String subjectskill, String ds, String ps, String pun, String cs,String count) {
        this.pid = pid;
        this.pname = pname;
        this.pinfo = pinfo;
        this.subjectskill = subjectskill;
        this.ds = ds;
        this.ps = ps;
        this.pun = pun;
        this.cs = cs;
        this.count = count;
    }
    public ProfessorData(){}

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPinfo() {
        return pinfo;
    }

    public void setPinfo(String pinfo) {
        this.pinfo = pinfo;
    }

    public String getSubjectskill() {
        return subjectskill;
    }

    public void setSubjectskill(String subjectskill) {
        this.subjectskill = subjectskill;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPun() {
        return pun;
    }

    public void setPun(String pun) {
        this.pun = pun;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    @Override
    public String toString() {
        return "ProfessorData{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", pinfo='" + pinfo + '\'' +
                ", subjectskill='" + subjectskill + '\'' +
                ", ds='" + ds + '\'' +
                ", ps='" + ps + '\'' +
                ", pun='" + pun + '\'' +
                ", cs='" + cs + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}


