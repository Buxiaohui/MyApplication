package com.example.buxiaohui.myapplication.bean;

import com.example.buxiaohui.myapplication.callback.AccountListener;

/**
 * Created by bxh on 11/6/16.
 */

public class RegisterBean {
    private AccountListener accountListener;

    public AccountListener getAccountListener() {
        return accountListener;
    }

    public void setAccountListener(AccountListener accountListener) {
        this.accountListener = accountListener;
    }

    public String getUserName() {
        return userName;
    }

    public RegisterBean setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPsw() {
        return psw;
    }

    public RegisterBean setPsw(String psw) {
        this.psw = psw;
        return  this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterBean setEmail(String email) {
        this.email = email;
        return this;
    }

    private String userName;
    private String psw;
    private String email;
}
