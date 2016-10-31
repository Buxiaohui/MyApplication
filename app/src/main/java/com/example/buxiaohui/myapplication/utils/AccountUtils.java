package com.example.buxiaohui.myapplication.utils;

import com.example.buxiaohui.myapplication.Config;
import com.example.buxiaohui.myapplication.callback.AccountListener;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by buxiaohui on 11/5/16.
 */

public class AccountUtils {
    private static final String TAG = "AccountUtils";
    private static AccountUtils instance;
    private static XMPPTCPConnection xmppConnection;

    public AccountUtils() {
        super();
        xmppConnection = getConnection();
    }

    public static AccountUtils getInstance() {
        if (instance == null) {
            return new AccountUtils();
        }
        return instance;
    }

    public static void changePsw(String newPsw, AccountListener listener) {
        try {
            AccountManager.getInstance(xmppConnection).changePassword(newPsw);
        } catch (SmackException.NoResponseException | XMPPException | SmackException.NotConnectedException e) {
            e.printStackTrace();
            LogUtils.D(TAG, "--register createAccount error");
        }

    }

    public static XMPPTCPConnection getConnection() {
        String server = Config.HOST_XAMPP;
        int port = Config.PORT_XAMPP;
        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        builder.setServiceName(server);
        builder.setHost(server);
        builder.setPort(port);
        builder.setCompressionEnabled(false);
        builder.setDebuggerEnabled(true);
        builder.setSendPresence(true);
        builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        XMPPTCPConnection connection = new XMPPTCPConnection(builder.build());
        return connection;
    }

    public static void login(String userName, String psw, AccountListener listener) {
        try {
            xmppConnection.login(userName, psw);
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
            LogUtils.D(TAG, "--connect error");
        }

    }

    public static void register(String userName, String psw, AccountListener listener) {
        AccountManager accountManager = AccountManager.getInstance(xmppConnection);
        try {
            accountManager.createAccount(userName, psw);
        } catch (SmackException.NoResponseException | XMPPException | SmackException.NotConnectedException e) {
            e.printStackTrace();
            LogUtils.D(TAG, "--register createAccount error");
        }

//        Registration reg = new Registration();
//        reg.setType(IQ.Type.set);
//        // 设置注册到的服务器名称
//        reg.setTo("hx1401016");
//        // Use an anonymous inner class to define a stanza filter that returns
//        // all stanzas that have a stanza ID of "RS145".
//        StanzaFilter myFilter = new StanzaFilter() {
//            public boolean accept(Stanza stanza) {
//                return "RS145".equals(stanza.getStanzaId());
//            }
//        };
//        // Create a new stanza collector using the filter we created.
//        PacketCollector collector = xmppConnection.createPacketCollector(myFilter);
//        try {
//            xmppConnection.sendStanza(reg);
//        } catch (SmackException.NotConnectedException e) {
//            //TODO
//            return;
//        }
//
//        //获取返回信息
//        IQ result = (IQ) collector.nextResult(SmackConfiguration.getDefaultPacketReplyTimeout());
//        // 取消收集
//        collector.cancel();
//        //通过返回信息判断
//        if (result == null) {
//            Toast.makeText(Global.APP_CONTEXT, "服务器异常", Toast.LENGTH_SHORT).show();
//        } else if (result.getType() == IQ.Type.error) {
//            if (result.getError().toString().equalsIgnoreCase("conflict(409)")) {
//                Toast.makeText(Global.APP_CONTEXT, "注册失败，用户已存在", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(Global.APP_CONTEXT, "注册失败", Toast.LENGTH_SHORT).show();
//            }
//        } else if (result.getType() == IQ.Type.result) {
//            Toast.makeText(Global.APP_CONTEXT, "注册成功", Toast.LENGTH_SHORT).show();
//        }
    }


    public static void connect(AccountListener listener) {
        XMPPTCPConnection.setUseStreamManagementDefault(true);

        // Enable automatic reconnection
        ReconnectionManager.getInstanceFor(xmppConnection).enableAutomaticReconnection();

        // Handle reconnection and connection errors
        xmppConnection.addConnectionListener(new ConnectionListener() {

            @Override
            public void reconnectionSuccessful() {

                LogUtils.D(TAG, "Reconnection successful ...");
                // TODO: handle the reconnecting successful
            }

            @Override
            public void reconnectionFailed(Exception e) {
                LogUtils.D(TAG, "Reconnection failed: " + e.getMessage());
                // TODO: handle the reconnection failed
            }

            @Override
            public void reconnectingIn(int seconds) {
                LogUtils.D(TAG, "Reconnecting in %d secs" + seconds);
                // TODO: handle the reconnecting in
            }

            @Override
            public void connectionClosedOnError(Exception e) {
                LogUtils.D(TAG, "Connection closed on error");
                // TODO: handle the connection closed on error
            }

            @Override
            public void connectionClosed() {
                LogUtils.D(TAG, "Connection closed");
                // TODO: handle the connection closed
            }

            @Override
            public void authenticated(XMPPConnection arg0, boolean arg1) {
                LogUtils.D(TAG, "User authenticated");
                // TODO: handle the authentication
            }

            @Override
            public void connected(XMPPConnection arg0) {
                LogUtils.D(TAG, "Connection established");
                // TODO: handle the connection
            }
        });

        try {
            xmppConnection.connect();
            xmppConnection.login();
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
            LogUtils.D(TAG, "--connect error");
        }
    }

    public void test() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {

            }
        });
    }
}