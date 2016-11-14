package com.example.buxiaohui.myapplication.utils;

import com.example.buxiaohui.myapplication.Config;
import com.example.buxiaohui.myapplication.bean.Account;
import com.example.buxiaohui.myapplication.bean.RegisterBean;
import com.example.buxiaohui.myapplication.callback.AccountListener;
import com.google.gson.Gson;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.address.provider.MultipleAddressesProvider;
import org.jivesoftware.smackx.chatstates.packet.ChatStateExtension;
import org.jivesoftware.smackx.delay.provider.DelayInformationProvider;
import org.jivesoftware.smackx.disco.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.disco.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.iqlast.packet.LastActivity;
import org.jivesoftware.smackx.iqprivate.PrivateDataManager;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.muc.packet.GroupChatInvitation;
import org.jivesoftware.smackx.muc.provider.MUCAdminProvider;
import org.jivesoftware.smackx.muc.provider.MUCOwnerProvider;
import org.jivesoftware.smackx.muc.provider.MUCUserProvider;
import org.jivesoftware.smackx.offline.packet.OfflineMessageInfo;
import org.jivesoftware.smackx.offline.packet.OfflineMessageRequest;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearch;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.sharedgroups.packet.SharedGroupsInfo;
import org.jivesoftware.smackx.si.provider.StreamInitiationProvider;
import org.jivesoftware.smackx.vcardtemp.provider.VCardProvider;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.provider.DataFormProvider;
import org.jivesoftware.smackx.xhtmlim.provider.XHTMLExtensionProvider;
import org.jxmpp.util.XmppStringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
    private  XMPPTCPConnection xmppConnection;

    public AccountUtils() {
        super();
        //xmppConnection = getConnection();
    }

    public static AccountUtils getInstance() {
        if (instance == null) {
            instance = new AccountUtils();
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
        ProviderManager pm = new ProviderManager();
        configure(pm);
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
//        xmppConnection.addPacketSendingListener(new StanzaListener() {
//            @Override
//            public void processPacket(Stanza packet) throws SmackException.NotConnectedException {
//                LogUtils.D(TAG, "processPacket:" + packet.getFrom());
//            }
//        }, new StanzaFilter() {
//            @Override
//            public boolean accept(Stanza stanza) {
//                LogUtils.D(TAG, "accept:" + stanza.getFrom());
//                return false;
//            }
//        });
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
                subscriber.onNext(null);

            }
        }).subscribeOn(Schedulers.io());

        Subscriber<Object> s = new Subscriber<Object>() {
            @Override
            public void onNext(Object o) {
                LogUtils.D(TAG, "---loginAsync onNext isAuthenticated = " + xmppConnection.isAuthenticated());
                //searchUsersSync("bxh");
            }

            @Override
            public void onCompleted() {
                LogUtils.D(TAG, "-loginAsync--onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.D(TAG, "-loginAsync--onError e:" + e.toString());

            }
        };
        o.subscribe(s);

    }

    public void getContacts() {
        //Roster roster = ((XMPPConnection)getConnection()).get
    }

    public boolean isConnected() {
        if (xmppConnection == null) {
            xmppConnection = getConnection();
        }
        LogUtils.D(TAG, "isConnected()=" + xmppConnection.isConnected());
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

    public List<RosterEntry> getEntriesByGroup(String groupName) {
        List<RosterEntry> Entrieslist = new ArrayList<RosterEntry>();
        RosterGroup rosterGroup = Roster.getInstanceFor(xmppConnection).getGroup(groupName);
        Collection<RosterEntry> rosterEntry = rosterGroup.getEntries();
        Iterator<RosterEntry> i = rosterEntry.iterator();
        while (i.hasNext()) {
            Entrieslist.add(i.next());
        }
        return Entrieslist;
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

    public void getAllFriendsSync() {
        Observable o = Observable.create(new Observable.OnSubscribe<Set>() {
            @Override
            public void call(Subscriber<? super Set> subscriber) {
                Set s = getAllFriends();
                LogUtils.D(TAG, "---getAllFriendsSync call result=" + new Gson().toJson(s));
                subscriber.onNext(s);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());

        Subscriber<Set> s = new Subscriber<Set>() {
            @Override
            public void onNext(Set o) {
                LogUtils.D(TAG, "---getAllFriendsSync onNext result=" + new Gson().toJson(o));
            }

            @Override
            public void onCompleted() {
                LogUtils.D(TAG, "---getAllFriendsSync onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.D(TAG, "---getAllFriendsSync onError e:" + e.toString());

            }
        };
        o.subscribe(s);

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

    /**
     * 更改用户状态
     */
    public void setPresence(int code) {
        Presence presence = null;
        try {
            switch (code) {
                case 0:
                    presence = new Presence(Presence.Type.available);
                    xmppConnection.sendStanza(presence);
                    ToastUtils.show("设置在线");
                    break;
                case 1:
                    presence = new Presence(Presence.Type.available);
                    presence.setMode(Presence.Mode.chat);
                    xmppConnection.sendStanza(presence);
                    ToastUtils.show("设置Q我吧");
                    break;
                case 2:
                    presence = new Presence(Presence.Type.available);
                    presence.setMode(Presence.Mode.dnd);
                    xmppConnection.sendStanza(presence);
                    ToastUtils.show("设置忙碌");
                    break;
                case 3:
                    presence = new Presence(Presence.Type.available);
                    presence.setMode(Presence.Mode.away);
                    xmppConnection.sendStanza(presence);
                    ToastUtils.show("设置离开");
                    break;
                case 4:
                    Collection<RosterEntry> entries = Roster.getInstanceFor(xmppConnection).getEntries();
                    for (RosterEntry entry : entries) {
                        presence = new Presence(Presence.Type.unavailable);
                        //TODO
                        //presence.setPacketID(Packet.ID_NOT_AVAILABLE);
                        presence.setStanzaId(null);
                        presence.setFrom(xmppConnection.getUser());
                        presence.setTo(entry.getUser());
                        xmppConnection.sendStanza(presence);
                    }
                    // 向同一用户的其他客户端发送隐身状态
                    presence = new Presence(Presence.Type.unavailable);
                    //TODO
                    //presence.setPacketID(Packet.ID_NOT_AVAILABLE);
                    presence.setStanzaId(null);
                    presence.setFrom(xmppConnection.getUser());
                    //TODO
                    presence.setTo(XmppStringUtils.parseBareJid(xmppConnection.getUser()));
                    xmppConnection.sendStanza(presence);
                    ToastUtils.show("设置隐身");
                    break;
                case 5:
                    presence = new Presence(Presence.Type.unavailable);
                    xmppConnection.sendStanza(presence);
                    ToastUtils.show("设置离线");
                    break;
                default:
                    break;
            }
        } catch (SmackException.NotConnectedException e) {

        }

    }

    public ChatManager getChatManager() {
        if (isConnected()) {
            ChatManager chatManager = ChatManager.getInstanceFor(xmppConnection);
            return chatManager;
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    public List<Account> searchUsers(String userName) {
        if (isConnected()) {
            LogUtils.D(TAG, "---searchUsers xmppConnection.isAuthenticated()=" + xmppConnection.isAuthenticated());

            if (xmppConnection.isAuthenticated()) {
                login("test01","123");
            }
            List<Account> results = new ArrayList<Account>();
            try {
                //new ServiceDiscoveryManager(xmppConnection);
                UserSearchManager usm = new UserSearchManager(xmppConnection);
                String serverDomain = "search." + xmppConnection.getServiceName();
                LogUtils.D(TAG, "---searchUsers serverDomain=" + serverDomain);
                LogUtils.D(TAG, "---searchUsers xmppConnection.getUser()=" + xmppConnection.getUser());
                Form searchForm = usm.getSearchForm(serverDomain);
                Form answerForm = searchForm.createAnswerForm();
                //answerForm.setAnswer("Username", true);
                answerForm.setAnswer("userName", userName);
                ReportedData data = usm.getSearchResults(answerForm, serverDomain);

                List<ReportedData.Row> list = data.getRows();
                if (list != null) {
                    Iterator<ReportedData.Row> it = list.iterator();
                    ReportedData.Row row = null;
                    Account user = null;
                    while (it.hasNext()) {
                        row = it.next();
                        String uName = row.getValues("Username").toString();
                        String Name = row.getValues("Name").toString();
                        String Email = row.getValues("Email").toString();
                        user = new Account(uName, Name, Email);
                        results.add(user);
                    }
                }
                return results;
            } catch (XMPPException | SmackException.NotConnectedException | SmackException.NoResponseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public void searchUsersSync(final String userName) {
        Observable o = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {

                List<Account> list = searchUsers(userName);
                subscriber.onNext(list);

            }
        }).subscribeOn(Schedulers.io());

        Subscriber<List<Account>> s = new Subscriber<List<Account>>() {
            @Override
            public void onNext(List<Account> o) {
                LogUtils.D(TAG, "---searchUsersSync onNext result=" + (o != null ? new Gson().toJson(o) : "null"));
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

    public void configure(ProviderManager pm) {

        // Private Data Storage
        pm.addIQProvider("query", "jabber:iq:private",
                new PrivateDataManager.PrivateDataIQProvider());

        // Time
        try {
            pm.addIQProvider("query", "jabber:iq:time",
                    Class.forName("org.jivesoftware.smackx.packet.Time"));
        } catch (ClassNotFoundException e) {
        }

        // XHTML
        pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im",
                new XHTMLExtensionProvider());

//        // Roster Exchange
//        pm.addExtensionProvider("x", "jabber:x:roster",
//                new RosterExchangeProvider());
//        // Message Events
//        pm.addExtensionProvider("x", "jabber:x:event",
//                new MessageEventProvider());
        // Chat State
        pm.addExtensionProvider("active",
                "http://jabber.org/protocol/chatstates",
                new ChatStateExtension.Provider());
        pm.addExtensionProvider("composing",
                "http://jabber.org/protocol/chatstates",
                new ChatStateExtension.Provider());
        pm.addExtensionProvider("paused",
                "http://jabber.org/protocol/chatstates",
                new ChatStateExtension.Provider());
        pm.addExtensionProvider("inactive",
                "http://jabber.org/protocol/chatstates",
                new ChatStateExtension.Provider());
        pm.addExtensionProvider("gone",
                "http://jabber.org/protocol/chatstates",
                new ChatStateExtension.Provider());

        // FileTransfer
        pm.addIQProvider("si", "http://jabber.org/protocol/si",
                new StreamInitiationProvider());

        // Group Chat Invitations
        pm.addExtensionProvider("x", "jabber:x:conference",
                new GroupChatInvitation.Provider());
        // Service Discovery # Items
        pm.addIQProvider("query", "http://jabber.org/protocol/disco#items",
                new DiscoverItemsProvider());
        // Service Discovery # Info
        pm.addIQProvider("query", "http://jabber.org/protocol/disco#info",
                new DiscoverInfoProvider());
        // Data Forms
        pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());
        // MUC User
        pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user",
                new MUCUserProvider());
        // MUC Admin
        pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin",
                new MUCAdminProvider());
        // MUC Owner
        pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner",
                new MUCOwnerProvider());
        // Delayed Delivery
        pm.addExtensionProvider("x", "jabber:x:delay",
                new DelayInformationProvider());
        // Version
        try {
            pm.addIQProvider("query", "jabber:iq:version",
                    Class.forName("org.jivesoftware.smackx.packet.Version"));
        } catch (ClassNotFoundException e) {
        }
        // VCard
        pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());
        // Offline Message Requests
        pm.addIQProvider("offline", "http://jabber.org/protocol/offline",
                new OfflineMessageRequest.Provider());
        // Offline Message Indicator
        pm.addExtensionProvider("offline",
                "http://jabber.org/protocol/offline",
                new OfflineMessageInfo.Provider());
        // Last Activity
        pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());
        // User Search
        pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());
        // SharedGroupsInfo
        pm.addIQProvider("sharedgroup",
                "http://www.jivesoftware.org/protocol/sharedgroup",
                new SharedGroupsInfo.Provider());
        // JEP-33: Extended Stanza Addressing
        pm.addExtensionProvider("addresses",
                "http://jabber.org/protocol/address",
                new MultipleAddressesProvider());

    }

}