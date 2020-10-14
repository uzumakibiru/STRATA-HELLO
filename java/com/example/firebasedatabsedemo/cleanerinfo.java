package com.example.firebasedatabsedemo;

public class cleanerinfo {
    private String issue;
    private String name;

    public cleanerinfo() {
    }

    public cleanerinfo(String issue, String name) {
        this.issue = issue;
        this.name = name;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
