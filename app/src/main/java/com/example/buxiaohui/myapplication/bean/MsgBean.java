package com.example.buxiaohui.myapplication.bean;

import java.io.Serializable;

/**
 * Created by bxh on 11/27/16.
 */

public class MsgBean implements Serializable {
    int type;
    boolean isSelf;
    private String time;
    private String from;
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
