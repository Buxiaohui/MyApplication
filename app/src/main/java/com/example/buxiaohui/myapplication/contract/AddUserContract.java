package com.example.buxiaohui.myapplication.contract;

import com.example.buxiaohui.myapplication.bean.Account;

import java.util.List;

/**
 * Created by bxh on 11/16/16.
 */

public interface AddUserContract {
    interface ViewInterface {
        void addUser(Account account);

        void addUsers(List<Account> accountList);
    }

}
