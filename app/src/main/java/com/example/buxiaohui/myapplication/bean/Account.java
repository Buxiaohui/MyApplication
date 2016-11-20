package com.example.buxiaohui.myapplication.bean;

import org.jivesoftware.smack.roster.RosterEntry;

/**
 * Created by buxiaohui on 12/10/2016.
 */
public class Account {
    private String userName;
    private String nikeName;
    private String email;

    public Account(String userName, String name, String email) {
        this.userName = userName;
        this.nikeName = name;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static  Account copy(RosterEntry entry){
        return new Account(entry.getUser(),entry.getName(),"");
    }
}

