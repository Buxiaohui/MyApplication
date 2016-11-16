package com.example.buxiaohui.myapplication.contract;

import org.jivesoftware.smack.roster.RosterGroup;

import java.util.List;

/**
 * Created by bxh on 11/16/16.
 */

public interface AddGroupContract {
    interface View {
        void addGroup(RosterGroup account);

        void addGroups(List<RosterGroup> accountList);
    }

}
