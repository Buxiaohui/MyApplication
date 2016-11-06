package com.example.buxiaohui.myapplication.utils;

import android.widget.Toast;

import com.example.buxiaohui.myapplication.Config;
import com.example.buxiaohui.myapplication.Global;
import com.example.buxiaohui.myapplication.bean.RegisterBean;
import com.example.buxiaohui.myapplication.callback.AccountListener;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.sasl.SASLAnonymous;
import org.jivesoftware.smack.sasl.provided.SASLPlainMechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.iqregister.packet.Registration;

import java.io.IOException;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by buxiaohui on 11/5/16.
 * Code is suck,I know,will improve it later
 */

public class AccountUtils {
    private static final String TAG = "AccountUtils";
    private static AccountUtils instance;
    private static XMPPTCPConnection xmppConnection;
    public static final String KEY_NAME = "name";
    public static final String KEY_PSW = "psw";
    public static final String KEY_EMAIL = "email";

    public AccountUtils() {
        super();
        xmppConnection = getConnection();
        //connect(null);
    }

    public static AccountUtils getInstance() {
        if (instance == null) {
            return new AccountUtils();
        }
        return instance;
    }

    public void changePsw(String newPsw, AccountListener listener) {
        try {
            AccountManager.getInstance(xmppConnection).changePassword(newPsw);
        } catch (SmackException.NoResponseException | XMPPException | SmackException.NotConnectedException e) {
            e.printStackTrace();
            LogUtils.D(TAG, "--register createAccount error");
        }

    }

    public XMPPTCPConnection getConnection() {
        String server = Config.HOST_XAMPP;
        int port = Config.PORT_XAMPP;
        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        builder.setServiceName(Config.SERVER_NAME_XAMPP);
        builder.setHost(server);
        builder.setPort(port);
        builder.setCompressionEnabled(false);
        builder.setDebuggerEnabled(true);
        builder.setSendPresence(true);
        builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        XMPPTCPConnection connection = new XMPPTCPConnection(builder.build());

        return connection;
    }

    public void login(String userName, String psw, AccountListener listener) {
        try {
            xmppConnection.login(userName, psw);
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
            LogUtils.D(TAG, "--connect error");
        }

    }

    public int register(final RegisterBean registerBean) {
        if (!xmppConnection.isConnected()) {
            connect(null);
        }
        AccountManager accountManager = AccountManager.getInstance(xmppConnection);

        try {
            accountManager.createAccount(registerBean.getUserName(), registerBean.getPsw());
        } catch (SmackException.NoResponseException | XMPPException | SmackException.NotConnectedException e) {
            e.printStackTrace();
            LogUtils.D(TAG, "--register createAccount error");
            return -1;
            //return;
        }
        return 0;
    }


    public void connect(AccountListener listener) {
        XMPPTCPConnection.setUseStreamManagementDefault(true);

        // Enable automatic reconnection
        ReconnectionManager.getInstanceFor(xmppConnection).enableAutomaticReconnection();
        xmppConnection.addPacketSendingListener(new StanzaListener() {
            @Override
            public void processPacket(Stanza packet) throws SmackException.NotConnectedException {
                LogUtils.D(TAG, "processPacket:" + packet.getFrom());
            }
        }, new StanzaFilter() {
            @Override
            public boolean accept(Stanza stanza) {
                LogUtils.D(TAG, "accept:" + stanza.getFrom());
                return false;
            }
        });
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
            //xmppConnection.login();
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

    public void disconnect() {
        if (xmppConnection != null) {
            xmppConnection.disconnect();
        }
    }

    public void registerAsync(final RegisterBean registerBean) {
        Observable o = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                LogUtils.D(TAG, "---registerAsync currentThread:" + Thread.currentThread().getName());
                int result = register(registerBean);
                LogUtils.D(TAG, "---registerAsync call:" + result);

            }
        }).subscribeOn(Schedulers.io());

        Subscriber<Object> s = new Subscriber<Object>() {
            @Override
            public void onNext(Object o) {
                LogUtils.D(TAG, "---onNext");
            }

            @Override
            public void onCompleted() {
                LogUtils.D(TAG, "---onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.D(TAG, "---onError e:" + e.toString());

            }
        };
        o.subscribe(s);

    }
}