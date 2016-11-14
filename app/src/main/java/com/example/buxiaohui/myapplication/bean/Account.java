package com.example.buxiaohui.myapplication.bean;

/**
 * Created by buxiaohui on 12/10/2016.
 */
public class Account {
    private String userName;
    private String name;
    private String email;

    public Account(String userName, String name, String email) {
        this.userName = userName;
        this.name = name;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

