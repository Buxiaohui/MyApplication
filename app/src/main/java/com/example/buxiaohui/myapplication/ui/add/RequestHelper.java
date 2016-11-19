package com.example.buxiaohui.myapplication.ui.add;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.buxiaohui.myapplication.Global;
import com.example.buxiaohui.myapplication.utils.LogUtils;

import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

/**
 * Created by bxh on 11/19/16.
 */

public class RequestHelper {
    private static final String TAG = "RequestHelper";
    private static final String ACTION  = Global.REQUEST_ACTION;
    public static void registerListener(XMPPTCPConnection con) {
        if (con != null && con.isConnected() && con.isAuthenticated()) {

            //条件过滤器
            StanzaFilter filter = new AndFilter(new PacketTypeFilter(Presence.class));
            //packet监听器
            StanzaListener listener = new StanzaListener() {

                @Override
                public void processPacket(Stanza packet) {
                    LogUtils.D(TAG,"PresenceService-" + packet.toXML());
                    if (packet instanceof Presence) {
                        Presence presence = (Presence) packet;
                        String from = presence.getFrom();//发送方
                        String to = presence.getTo();//接收方
                        if (presence.getType().equals(Presence.Type.subscribe)) {
                            LogUtils.D(TAG,"收到添加请求！");
                            //发送广播传递发送方的JIDfrom及字符串
                            String acceptAdd = "收到添加请求！";
                            Intent intent = new Intent();
                            intent.putExtra("fromName", from);
                            intent.putExtra("acceptAdd", acceptAdd);
                            intent.setAction(ACTION);
                            sendBroadcast(intent);
                        } else if (presence.getType().equals(
                                Presence.Type.subscribed)) {
                            LogUtils.D(TAG,"恭喜，对方同意添加好友！");
                            //发送广播传递response字符串
                            String responseSubscribed = "恭喜，对方同意添加好友！";
                            Intent intent = new Intent();
                            intent.putExtra("response", responseSubscribed);
                            intent.setAction(ACTION);
                            sendBroadcast(intent);
                        } else if (presence.getType().equals(
                                Presence.Type.unsubscribe)) {
                            LogUtils.D(TAG,"抱歉，对方拒绝添加好友，将你从好友列表移除！");
                            //发送广播传递response字符串
                            String responseUnsubscribe = "抱歉，对方拒绝添加好友，将你从好友列表移除！";
                            Intent intent = new Intent();
                            intent.putExtra("response", responseUnsubscribe);
                            intent.setAction(ACTION);
                            sendBroadcast(intent);
                        } else if (presence.getType().equals(
                                Presence.Type.unsubscribed)) {
                        } else if (presence.getType().equals(
                                Presence.Type.unavailable)) {
                            LogUtils.D(TAG,"好友下线！");
                        } else {
                            LogUtils.D(TAG,"好友上线！");
                        }
                    }
                }
            };
            //添加监听
            con.addAsyncStanzaListener(listener, filter);
            LogUtils.D(TAG,"---PresenceService addAsyncStanzaListener");
        }
    }

    public static void sendBroadcast(Intent intent){
        LocalBroadcastManager.getInstance(Global.APP_CONTEXT).sendBroadcast(intent);
    }
}
