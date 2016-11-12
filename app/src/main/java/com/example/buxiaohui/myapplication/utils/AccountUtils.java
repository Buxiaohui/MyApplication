package com.example.buxiaohui.myapplication.utils;

import com.example.buxiaohui.myapplication.Config;
import com.example.buxiaohui.myapplication.bean.RegisterBean;
import com.example.buxiaohui.myapplication.callback.AccountListener;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.io.IOException;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by buxiaohui on 11/5/16.
 * Code is suck,I know,will improve it later
 */

public class AccountUtils {
    public static final String KEY_NAME = "name";
    public static final String KEY_PSW = "psw";
    public static final String KEY_EMAIL = "email";
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

    public void login(String userName, String psw) {
        if (isConnected()) {
            try {
                xmppConnection.login(userName, psw);
            } catch (SmackException | IOException | XMPPException e) {
                e.printStackTrace();
                LogUtils.D(TAG, "--connect error");
            }
        } else {
            ToastUtils.show("not connected!");
        }

    }

    public int register(final RegisterBean registerBean) {
        if (isConnected()) {
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
        return -1;
    }


    public boolean connect(AccountListener listener) {
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
            return true;
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
            LogUtils.D(TAG, "--connect error");

        }
        return false;
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

    public void loginAsync(final String userName, final String psw) {
        Observable o = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                login(userName, psw);

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

    public void getContacts() {
        //Roster roster = ((XMPPConnection)getConnection()).get
    }

    public boolean isConnected() {
        if (xmppConnection == null) {
            return false;
        }
        if (!xmppConnection.isConnected()) {
            return connect(null);
        }
        return true;

    }

    /**
     * 获取账户所有属性信息
     */
    public Set getAccountAttributes() {
        if (isConnected()) {
            try {
                return AccountManager.getInstance(xmppConnection).getAccountAttributes();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    /**
     * 获取当前登录用户的所有好友信息
     *
     * @return
     */
    public Set getAllFriends() {
        if (isConnected()) {
            return Roster.getInstanceFor(xmppConnection).getEntries();
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    /**
     * 获取指定账号的好友信息
     */
    public RosterEntry getFriend(String user) {
        if (isConnected()) {
            return Roster.getInstanceFor(xmppConnection).getEntry(user);
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }


    /**
     * 添加好友
     *
     * @param user      用户账号
     * @param nickName  用户昵称
     * @param groupName 所属组名
     * @return
     */
    public boolean addFriend(String user, String nickName, String groupName) {
        if (isConnected()) {
            try {
                Roster.getInstanceFor(xmppConnection).createEntry(user, nickName, new String[]{groupName});
                return true;
            } catch (SmackException.NotLoggedInException | SmackException.NoResponseException | XMPPException.XMPPErrorException
                    | SmackException.NotConnectedException e) {
                return false;
            }
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    /**
     * 注销
     *
     * @return
     */
    public boolean logout() {
        if (!isConnected()) {
            return false;
        }
        try {
            xmppConnection.instantShutdown();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建聊天窗口
     */
    public Chat createChat(String jid) {
        if (isConnected()) {
            ChatManager chatManager = ChatManager.getInstanceFor(xmppConnection);
            return chatManager.createChat(jid);
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

}