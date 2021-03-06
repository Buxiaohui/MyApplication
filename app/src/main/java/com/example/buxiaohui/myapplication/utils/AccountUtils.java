package com.example.buxiaohui.myapplication.utils;

import android.text.TextUtils;

import com.example.buxiaohui.myapplication.Config;
import com.example.buxiaohui.myapplication.bean.Account;
import com.example.buxiaohui.myapplication.bean.RegisterBean;
import com.example.buxiaohui.myapplication.callback.AccountListener;
import com.example.buxiaohui.myapplication.ui.add.RequestHelper;
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
    private XMPPTCPConnection xmppConnection;

    public AccountUtils() {
        super();
    }

    public static AccountUtils getInstance() {
        if (instance == null) {
            instance = new AccountUtils();
        }
        return instance;
    }

    public void registerListener() {
        RequestHelper.registerListener(xmppConnection);
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
        return xmppConnection;
    }

    public XMPPTCPConnection initConnection() {

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
        //RequestHelper.registerListener(connection);
        return connection;
    }

    public int login(String userName, String psw) {
        if (isConnected()) {
            try {
                if (!isKeepLoginState()) {
                    xmppConnection.login(userName, psw);
                }
                return 0;
            } catch (SmackException | IOException | XMPPException e) {
                e.printStackTrace();
                LogUtils.D(TAG, "--connect error e=" + e.toString());
                return -1;
            }
        } else {
            LogUtils.D(TAG, "not connected!");
            return -1;
        }

    }

    public void loginAsync(final String userName, final String psw, Subscriber<Integer> s) {
        Observable o = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int result = AccountUtils.getInstance().login(userName, psw);
                LogUtils.D(TAG, "loginAsync result=" + result);
                subscriber.onNext(result);

            }
        }).subscribeOn(Schedulers.io());

        o.subscribe(s);

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


    public void getContacts() {
        //Roster roster = ((XMPPConnection)initConnection()).get
    }

    public boolean isConnect() {
        if (xmppConnection == null) {
            return false;
        }
        return xmppConnection.isConnected();
    }

    public void ensureConnect() {
        if (xmppConnection == null) {
            xmppConnection = initConnection();
        }
        if (!isConnect()) {
            connect(null);
        }
    }

    public boolean isConnected() {
        if (xmppConnection == null) {
            xmppConnection = initConnection();
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
    public List<RosterEntry> getAllFriendsList() {
        if (isConnected()) {
            Set<RosterEntry> set = Roster.getInstanceFor(xmppConnection).getEntries();
            if(set!=null && set.size()>0){
                Iterator<RosterEntry> iterator = set.iterator();
                List<RosterEntry> rosterEntries = new ArrayList<RosterEntry>();
                while (iterator.hasNext()){
                       rosterEntries.add(iterator.next());
                }
                return rosterEntries;
            }
            return null;
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }
    /**
     * 获取指定账号的好友信息
     */
    public RosterEntry getFriend(String user) {
        LogUtils.D(TAG, "getFriend queryStr=" + user);
        if (isConnected()) {
            try {
                return Roster.getInstanceFor(xmppConnection).getEntry(user);
            } catch (Exception e) {
                LogUtils.D(TAG, "getFriend e=" + e.toString());
                return null;
            }

        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }
    /**
     * 获得所有的联系人列表
     *
     * @return
     */
    public static List<Account> getNoGroupUserList(Roster roster) {
        List<Account> userList = new ArrayList<Account>();

        // 服务器的用户信息改变后，不会通知到unfiledEntries
        for (RosterEntry entry : roster.getUnfiledEntries()) {
            userList.add(Account.copy(entry));
        }

        return userList;
    }

    /**
     * 添加好友
     *
     * @param user     用户账号
     * @param nickName 用户昵称
     * @param groups   所属组名 可以为null
     * @return
     */
    public boolean addFriend(String user, String nickName, String[] groups) {
        LogUtils.D(TAG, "addFriend user=" + user + "--nikeName=" + nickName + "--groups=" + groups);
        if (isConnected()) {
            try {
                Roster.getInstanceFor(xmppConnection).createEntry(user+"@"+Config.SERVER_NAME_XAMPP, nickName, groups);
                return true;
            } catch (SmackException.NotLoggedInException | SmackException.NoResponseException | XMPPException.XMPPErrorException
                    | SmackException.NotConnectedException e) {
                LogUtils.D(TAG, "addFriend e=" + e.toString());
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

    /**
     * all groups
     */
    public List<RosterGroup> searchAllGroups() {
        LogUtils.D(TAG, "-----searchGroups");
        if (isConnected()) {
            try {
                Collection<RosterGroup> list = Roster.getInstanceFor(xmppConnection).getGroups();
                List<RosterGroup> list1 = new ArrayList<RosterGroup>();
                Iterator<RosterGroup> i = list.iterator();
                while (i.hasNext()) {
                    RosterGroup group = i.next();
                    if (group != null) {
                        list1.add(group);
                        LogUtils.D(TAG, "searchGroups=" + group.getName());
                    }
                }
                return list1;
            } catch (Exception e) {
                LogUtils.D(TAG, "searchAllGroups e=" + e.toString());
                return null;
            }

        } else {
            LogUtils.D(TAG, "searchAllGroups" +
                    " connect error!");
        }
        return null;
    }

    public List<RosterGroup> searchGroups(String query) {
        LogUtils.D(TAG, "-----searchGroups query=" + query);
        if ("9527".equalsIgnoreCase(query)) {
            return searchAllGroups();
        } else {
            RosterGroup r = searchGroupsByName(query);
            if (r == null) {
                return null;
            }
            ArrayList<RosterGroup> list = new ArrayList<RosterGroup>();
            list.add(r);
            return list;
        }
    }

    public RosterGroup searchGroupsByName(String groupName) {
        LogUtils.D(TAG, "-----searchGroupsByName");
        if (isConnected()) {
            try {
                RosterGroup group = Roster.getInstanceFor(xmppConnection).getGroup(groupName);
                if (group != null) {
                    LogUtils.D(TAG, "searchGroupsByName=" + group.getName());
                }
                return group;
            } catch (Exception e) {
                LogUtils.D(TAG, "searchGroupsByName e=" + e.toString());
                return null;
            }

        } else {
            LogUtils.D(TAG, "searchGroupsByName connect error!");
        }
        return null;
    }

    public boolean isKeepLoginState() {
        return xmppConnection != null && xmppConnection.isAuthenticated();
    }

    public List<Account> searchUsers(String userName) {
        if (isConnected()) {
            LogUtils.D(TAG, "---searchUsers xmppConnection.isAuthenticated()=" + xmppConnection.isAuthenticated());
            List<Account> results = new ArrayList<Account>();
            try {
                //new ServiceDiscoveryManager(xmppConnection);
                UserSearchManager usm = new UserSearchManager(xmppConnection);
                String serverDomain = "search." + xmppConnection.getServiceName();
                Form searchForm = usm.getSearchForm(serverDomain);
                LogUtils.D(TAG, "---searchUsers searchForm=" + searchForm);
                Form answerForm = searchForm.createAnswerForm();
                //answerForm.setAnswer("Username", true);
                answerForm.setAnswer("Username", true);
                answerForm.setAnswer("Name", true);
                answerForm.setAnswer("search", userName);
                ReportedData data = usm.getSearchResults(answerForm, serverDomain);

                List<ReportedData.Row> list = data.getRows();
                if (list != null) {
                    Iterator<ReportedData.Row> it = list.iterator();
                    ReportedData.Row row = null;
                    Account user = null;
                    while (it.hasNext()) {
                        row = it.next();
                        String uName = row.getValues("Username") != null && row.getValues("Username").size() > 0 ? row.getValues("Username").get(0).toString() : "";
                        String Name = row.getValues("Name") != null && row.getValues("Name").size() > 0 ? row.getValues("Name").get(0).toString() : "";
                        String Email = row.getValues("Email") != null && row.getValues("Email").size() > 0 ? row.getValues("Email").get(0).toString() : "";
                        if (TextUtils.isEmpty(uName) || TextUtils.isEmpty(Name)) {
                            continue;
                        }
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