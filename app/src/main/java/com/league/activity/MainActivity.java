package com.league.activity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMEventListener;
import com.easemob.EMGroupChangeListener;
import com.easemob.EMNotifierEvent;
import com.easemob.EMValueCallBack;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.chatuidemo.Constant;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.easemob.chatuidemo.activity.AddContactActivity;
import com.easemob.chatuidemo.activity.BaseActivity;
import com.easemob.chatuidemo.activity.ChatActivity;
import com.easemob.chatuidemo.activity.ChatAllHistoryFragment;
import com.easemob.chatuidemo.activity.ContactlistFragment;
import com.easemob.chatuidemo.activity.GroupsActivity;
import com.easemob.chatuidemo.activity.LoginActivity;
import com.easemob.chatuidemo.db.InviteMessgeDao;
import com.easemob.chatuidemo.db.UserDao;
import com.easemob.chatuidemo.domain.InviteMessage;
import com.easemob.chatuidemo.domain.User;
import com.easemob.chatuidemo.utils.CommonUtils;
import com.easemob.util.EMLog;
import com.easemob.util.HanziToPinyin;
import com.easemob.util.NetUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.UserBasicInfo;
import com.league.fragment.PersonFragment;
import com.league.fragment.ResourceFragment;
import com.league.utils.Constants;
import com.league.utils.StoreUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class MainActivity extends BaseActivity implements EMEventListener {

    private int index;
    private TextView title;//当前页标题
    private ImageView[] tabButtons;//下方四个tab
    private TextView[] tabText;
    private int currentTabIndex = 3;//当前tab

    private Fragment[] fragments;
    private ChatAllHistoryFragment chatHistoryFragment;
    private ContactlistFragment contactListFragment;
    private ResourceFragment resourceFragment;
    private PersonFragment personFragment;

    private ImageView addContactView;

    protected static final String TAG = "MainActivity";
    // 未读消息textview
    private TextView unreadLabel;
    // 未读通讯录textview
    private TextView unreadAddressLable;

    private Button[] mTabs;
    // 账号在别处登录
    public boolean isConflict = false;
    // 账号被移除
    private boolean isCurrentAccountRemoved = false;

    private MyConnectionListener connectionListener = null;
    private MyGroupChangeListener groupChangeListener = null;

    /**
     * 检查当前用户是否被删除
     */
    public boolean getCurrentAccountRemoved() {
        return isCurrentAccountRemoved;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
            // 防止被移除后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            DemoHXSDKHelper.getInstance().logout(true, null);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            // 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        setContentView(R.layout.activity_main);
        Constants.finishAllActivities();
        Constants.addIntoCollection(this);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        StoreUtils.setWidthScreen(metrics.widthPixels);
//        metaSpUtil.setScreenHeight(this, metrics.heightPixels);

        init();

        MobclickAgent.updateOnlineConfig(this);

        if (getIntent().getBooleanExtra("conflict", false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }

        inviteMessgeDao = new InviteMessgeDao(this);
        userDao = new UserDao(this);

        // setContactListener监听联系人的变化等
        EMContactManager.getInstance().setContactListener(new MyContactListener());

        // 注册一个监听连接状态的listener
        connectionListener = new MyConnectionListener();
        EMChatManager.getInstance().addConnectionListener(connectionListener);

        // 注册群聊相关的listener
        groupChangeListener = new MyGroupChangeListener();
        EMGroupManager.getInstance().addGroupChangeListener(groupChangeListener);

        //异步获取当前用户的昵称和头像
        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getUserProfileManager().asyncGetCurrentUserInfo();
//        getAvatar();
    }

    public void init() {
        title = (TextView) findViewById(R.id.tv_title);
        addContactView = (ImageView) findViewById(R.id.iv_new_contact);
        addContactView.setVisibility(View.GONE);

        // 进入添加好友页
        addContactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddContactActivity.class));
            }
        });

        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);
        tabButtons = new ImageView[4];
        tabButtons[0] = (ImageView) findViewById(R.id.ib_home);
        tabButtons[1] = (ImageView) findViewById(R.id.ib_interact);
        tabButtons[2] = (ImageView) findViewById(R.id.ib_recom);
        tabButtons[3] = (ImageView) findViewById(R.id.ib_personal);
        tabButtons[3].setSelected(true);//默认第四个tab被选中
        title.setText("我");

        tabText = new TextView[4];
        tabText[0] = (TextView) findViewById(R.id.tv_home);
        tabText[1] = (TextView) findViewById(R.id.tv_interact);
        tabText[2] = (TextView) findViewById(R.id.tv_recom);
        tabText[3] = (TextView) findViewById(R.id.tv_personal);
        tabText[3].setTextColor(Color.RED);

        chatHistoryFragment = new ChatAllHistoryFragment();
        contactListFragment = new ContactlistFragment();
        resourceFragment = new ResourceFragment();
        personFragment = new PersonFragment();
        fragments = new Fragment[]{chatHistoryFragment, contactListFragment, resourceFragment, personFragment};
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, resourceFragment)
                .add(R.id.fragment_container, chatHistoryFragment)
                .add(R.id.fragment_container, contactListFragment)
                .add(R.id.fragment_container, personFragment)
                .hide(contactListFragment)
                .hide(resourceFragment)
                .hide(chatHistoryFragment).show(personFragment).commit();
    }

    public void onTabClicked(View view) {

        addContactView.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.re_home:
                title.setText("消息");
                index = 0;
                break;
            case R.id.re_interact:
                index = 1;
                title.setText("好友");
                addContactView.setVisibility(View.VISIBLE);
                break;
            case R.id.re_recommend:
                index = 2;
                title.setText("资源");
                break;
            case R.id.re_personal:
                index = 3;
                title.setText("我");
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getFragmentManager()
                    .beginTransaction();

            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }

            trx.hide(fragments[currentTabIndex]).show(fragments[index])
                    .commit();
        }
        tabButtons[currentTabIndex].setSelected(false);
        tabText[currentTabIndex].setTextColor(Color.BLACK);
        tabButtons[index].setSelected(true);// 把当前tab设为选中状态
        tabText[index].setTextColor(Color.RED);
        currentTabIndex = index;
    }

    static void asyncFetchGroupsFromServer() {
        HXSDKHelper.getInstance().asyncFetchGroupsFromServer(new EMCallBack() {

            @Override
            public void onSuccess() {
                HXSDKHelper.getInstance().noitifyGroupSyncListeners(true);

                if (HXSDKHelper.getInstance().isContactsSyncedWithServer()) {
                    HXSDKHelper.getInstance().notifyForRecevingEvents();
                }
            }

            @Override
            public void onError(int code, String message) {
                HXSDKHelper.getInstance().noitifyGroupSyncListeners(false);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

        });
    }

    void asyncFetchContactsFromServer() {

        final Context context = HXSDKHelper.getInstance().getAppContext();
        final Map<String, User> userlist = new HashMap<String, User>();
        // 添加user"申请与通知"
        User newFriends = new User();
        newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
        String strChat = context.getString(R.string.Application_and_notify);
        newFriends.setNick(strChat);

        userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
        // 添加"群聊"
        User groupUser = new User();
        String strGroup = context.getString(R.string.group_chat);
        groupUser.setUsername(Constant.GROUP_USERNAME);
        groupUser.setNick(strGroup);
        groupUser.setHeader("");
//        userlist.put(Constant.GROUP_USERNAME, groupUser);

        // 添加"聊天室"
        User chatRoomItem = new User();
        String strChatRoom = context.getString(R.string.chat_room);
        chatRoomItem.setUsername(Constant.CHAT_ROOM);
        chatRoomItem.setNick(strChatRoom);
        chatRoomItem.setHeader("");
//                userlist.put(Constant.CHAT_ROOM, chatRoomItem);

        // 添加"Robot"
        User robotUser = new User();
        String strRobot = context.getString(R.string.robot_chat);
        robotUser.setUsername(Constant.CHAT_ROBOT);
        robotUser.setNick(strRobot);
        robotUser.setHeader("");
//                userlist.put(Constant.CHAT_ROBOT, robotUser);

        //添加"朋友圈"
        User circle = new User();
        String strCircle = context.getString(R.string.friend_circle);
        circle.setUsername(Constant.FRIEDN_CIRCLE);
        circle.setNick(strCircle);
        circle.setHeader("");
        userlist.put(Constant.FRIEDN_CIRCLE, circle);

        //修改avatar
        getAvatar(new AvatarFetchListener() {
            @Override
            public void success(List<UserBasicInfo> users) {
                for (UserBasicInfo userBasicInfo : users) {
                    User user = new User();
                    user.setNick(userBasicInfo.getNickname());
                    user.setUsername(userBasicInfo.getHuanxinid());
                    user.setAvatar(userBasicInfo.getThumb());
                    user.setPhone(userBasicInfo.getPhone());
                    setUserHearder(userBasicInfo.getHuanxinid(), user);
                    userlist.put(userBasicInfo.getHuanxinid(), user);
                }


                // 存入内存
                ((DemoHXSDKHelper) HXSDKHelper.getInstance()).setContactList(userlist);
                // 存入db
                UserDao dao = new UserDao(context);
                final List<User> userList = new ArrayList<User>(userlist.values());
                dao.saveContactList(userList);

                HXSDKHelper.getInstance().notifyContactsSyncListener(true);
//                        User user = new User();
//                        user.setUsername(users);
//                        setUserHearder(username, user);
//                        userlist.put(username, user);
            }

            @Override
            public void error() {

            }
        });
//                for (String username : usernames) {
//                    User user = new User();
//                    user.setUsername(username);
//                    setUserHearder(username, user);
//                    userlist.put(username, user);
//                }


//                HXSDKHelper.getInstance().notifyContactsSyncListener(true);
//
//                if (HXSDKHelper.getInstance().isGroupsSyncedWithServer()) {
//                    HXSDKHelper.getInstance().notifyForRecevingEvents();
//                }
//
//                ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getUserProfileManager().asyncFetchContactInfosFromServer(usernames, new EMValueCallBack<List<User>>() {
//
//                    @Override
//                    public void onSuccess(List<User> uList) {
//                        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).updateContactList(uList);
//                        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getUserProfileManager().notifyContactInfosSyncListener(true);
//                    }
//
//                    @Override
//                    public void onError(int error, String errorMsg) {
//                    }
//                });
    }

    static void asyncFetchBlackListFromServer() {
        HXSDKHelper.getInstance().asyncFetchBlackListFromServer(new EMValueCallBack<List<String>>() {

            @Override
            public void onSuccess(List<String> value) {
                EMContactManager.getInstance().saveBlackList(value);
                HXSDKHelper.getInstance().notifyBlackListSyncListener(true);
            }

            @Override
            public void onError(int error, String errorMsg) {
                HXSDKHelper.getInstance().notifyBlackListSyncListener(false);
            }

        });
    }

    public interface AvatarFetchListener {
        public void success(List<UserBasicInfo> users);

        public void error();
    }

    public void getAvatar(final AvatarFetchListener listener) {
        ApiUtil.friendList(getApplicationContext(), new BaseJsonHttpResponseHandler<ArrayList<UserBasicInfo>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<UserBasicInfo> response) {
                if (listener != null)
                    listener.success(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<UserBasicInfo> errorResponse) {
                if (listener != null)
                    listener.error();
            }

            @Override
            protected ArrayList<UserBasicInfo> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                Log.d("response", rawJsonData);
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<UserBasicInfo>>() {
                });
            }
        });
    }

    public void getAvatar() {
//        new Thread(){
//            @Override
//            public void run() {
        ApiUtil.friendList(getApplicationContext(), new BaseJsonHttpResponseHandler<ArrayList<UserBasicInfo>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<UserBasicInfo> response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<UserBasicInfo> errorResponse) {
            }

            @Override
            protected ArrayList<UserBasicInfo> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                Log.d("response", rawJsonData);
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<UserBasicInfo>>() {
                });
            }
        });
//            }
//        }.start();

    }

    /**
     * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
     *
     * @param username
     * @param user
     */
    private static void setUserHearder(String username, User user) {
        String headerName = null;
        if (!TextUtils.isEmpty(user.getNick())) {
            headerName = user.getNick();
        } else {
            headerName = user.getUsername();
        }
        if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
            user.setHeader("");
        } else if (Character.isDigit(headerName.charAt(0))) {
            user.setHeader("#");
        } else {
            user.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target.substring(0, 1)
                    .toUpperCase());
            char header = user.getHeader().toLowerCase().charAt(0);
            if (header < 'a' || header > 'z') {
                user.setHeader("#");
            }
        }
    }

    /**
     * 监听事件
     */
    @Override
    public void onEvent(EMNotifierEvent event) {
        Log.d("main", "new message arrived");
        switch (event.getEvent()) {
            case EventNewMessage: // 普通消息
            {
                EMMessage message = (EMMessage) event.getData();

                refreshUI();
                // 提示新消息
                HXSDKHelper.getInstance().getNotifier().onNewMsg(message);

                break;
            }

            case EventOfflineMessage: {
                refreshUI();
                break;
            }

            case EventConversationListChanged: {
                refreshUI();
                break;
            }

            default:
                break;
        }
    }

    private void refreshUI() {
        runOnUiThread(new Runnable() {
            public void run() {
                // 刷新bottom bar消息未读数
                updateUnreadLabel();
                if (currentTabIndex == 0) {
                    // 当前页面如果为聊天历史页面，刷新此页面
                    if (chatHistoryFragment != null) {
                        chatHistoryFragment.refresh();
                    }
                }
            }
        });
    }

    /**
     * 刷新未读消息数
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 刷新申请与通知消息数
     */
    public void updateUnreadAddressLable() {
        runOnUiThread(new Runnable() {
            public void run() {
                int count = getUnreadAddressCountTotal();
                if (count > 0) {
                    unreadAddressLable.setText(String.valueOf(count));
                    unreadAddressLable.setVisibility(View.VISIBLE);
                } else {
                    unreadAddressLable.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    /**
     * 获取未读申请与通知消息
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        if (((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList().get(Constant.NEW_FRIENDS_USERNAME) != null)
            unreadAddressCountTotal = ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList().get(Constant.NEW_FRIENDS_USERNAME)
                    .getUnreadMsgCount();
        return unreadAddressCountTotal;
    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
        for (EMConversation conversation : EMChatManager.getInstance().getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }

    private InviteMessgeDao inviteMessgeDao;
    private UserDao userDao;

    /***
     * 好友变化listener
     */
    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactAdded(List<String> usernameList) {
            // 保存增加的联系`人
//            final Map<String, User> localUsers = ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList();
            final Map<String, User> localUsers = ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList();
            final Map<String, User> toAddUsers = new HashMap<String, User>();
//            for (String username : usernameList) {
//                User user = setUserHead(username);
//                // 添加好友时可能会回调added方法两次
//                if (!localUsers.containsKey(username)) {
//                    userDao.saveContact(user);
//                }
//                toAddUsers.put(username, user);
//            }
//            localUsers.putAll(toAddUsers);
//            // 刷新ui
//            if (currentTabIndex == 1)
//                contactListFragment.refresh();

            //修改avatar
            getAvatar(new AvatarFetchListener() {
                @Override
                public void success(List<UserBasicInfo> users) {
                    List<User> serverUsers = new ArrayList<User>();
                    for (UserBasicInfo userBasicInfo : users) {
                        User user = new User();
                        user.setNick(userBasicInfo.getNickname());
                        user.setUsername(userBasicInfo.getHuanxinid());
                        user.setAvatar(userBasicInfo.getThumb());
                        user.setPhone(userBasicInfo.getPhone());
                        setUserHearder(userBasicInfo.getHuanxinid(), user);
//                        toAddUsers.put(userBasicInfo.getHuanxinid(), user);
                        serverUsers.add(user);
                    }

                    for (User user : serverUsers) {
                        // 添加好友时可能会回调added方法两次
                        if (!localUsers.containsKey(user.getUsername())) {
                            userDao.saveContact(user);
                        }
                        toAddUsers.put(user.getUsername(), user);
                    }
                    localUsers.putAll(toAddUsers);
                    // 刷新ui
                    if (currentTabIndex == 1)
                        contactListFragment.refresh();
                }

                @Override
                public void error() {

                }
            });
        }

        @Override
        public void onContactDeleted(final List<String> usernameList) {
            // 被删除
            Map<String, User> localUsers = ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList();
            for (String username : usernameList) {
                localUsers.remove(username);
                userDao.deleteContact(username);
                inviteMessgeDao.deleteMessage(username);
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    // 如果正在与此用户的聊天页面
                    String st10 = getResources().getString(R.string.have_you_removed);
                    if (ChatActivity.activityInstance != null
                            && usernameList.contains(ChatActivity.activityInstance.getToChatUsername())) {
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                    updateUnreadLabel();
                    // 刷新ui
                    contactListFragment.refresh();
                    chatHistoryFragment.refresh();
                }
            });

        }

        @Override
        public void onContactInvited(String username, String reason) {

            // 接到邀请的消息，如果不处理(同意或拒绝)，掉线后，服务器会自动再发过来，所以客户端不需要重复提醒
            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();

            for (InviteMessage inviteMessage : msgs) {
                if (inviteMessage.getGroupId() == null && inviteMessage.getFrom().equals(username)) {
                    inviteMessgeDao.deleteMessage(username);
                }
            }
            // 自己封装的javabean
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
            msg.setReason(reason);
            Log.d(TAG, username + "请求加你为好友,reason: " + reason);
            // 设置相应status
            msg.setStatus(InviteMessage.InviteMesageStatus.BEINVITEED);
            notifyNewIviteMessage(msg);

        }

        @Override
        public void onContactAgreed(String username) {
            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
            for (InviteMessage inviteMessage : msgs) {
                if (inviteMessage.getFrom().equals(username)) {
                    return;
                }
            }
            // 自己封装的javabean
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
            Log.d(TAG, username + "同意了你的好友请求");
            msg.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
            notifyNewIviteMessage(msg);

        }

        @Override
        public void onContactRefused(String username) {

            // 参考同意，被邀请实现此功能,demo未实现
            Log.d(username, username + "拒绝了你的好友请求");
        }

    }

    /**
     * 连接监听listener
     */
    public class MyConnectionListener implements EMConnectionListener {

        @Override
        public void onConnected() {
            boolean groupSynced = HXSDKHelper.getInstance().isGroupsSyncedWithServer();
            boolean contactSynced = HXSDKHelper.getInstance().isContactsSyncedWithServer();

            new Thread() {
                @Override
                public void run() {
                    HXSDKHelper.getInstance().notifyForRecevingEvents();
                }
            }.start();

            // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
            if (groupSynced && contactSynced) {
                new Thread() {
                    @Override
                    public void run() {
                        HXSDKHelper.getInstance().notifyForRecevingEvents();
                    }
                }.start();
            } else {
                if (!groupSynced) {
                    asyncFetchGroupsFromServer();
                }

                if (!contactSynced) {
                    asyncFetchContactsFromServer();
                }

                if (!HXSDKHelper.getInstance().isBlackListSyncedWithServer()) {
                    asyncFetchBlackListFromServer();
                }
            }

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    chatHistoryFragment.errorItem.setVisibility(View.GONE);
                }

            });
        }

        @Override
        public void onDisconnected(final int error) {
            final String st1 = getResources().getString(R.string.can_not_connect_chat_server_connection);
            final String st2 = getResources().getString(R.string.the_current_network);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        showAccountRemovedDialog();
                    } else if (error == EMError.CONNECTION_CONFLICT) {
                        // 显示帐号在其他设备登陆dialog
                        showConflictDialog();
                    } else {
                        chatHistoryFragment.errorItem.setVisibility(View.VISIBLE);
                        if (NetUtils.hasNetwork(MainActivity.this))
                            chatHistoryFragment.errorText.setText(st1);
                        else
                            chatHistoryFragment.errorText.setText(st2);

                    }
                }

            });
        }
    }

    /**
     * MyGroupChangeListener
     */
    public class MyGroupChangeListener implements EMGroupChangeListener {

        @Override
        public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {

            boolean hasGroup = false;
            for (EMGroup group : EMGroupManager.getInstance().getAllGroups()) {
                if (group.getGroupId().equals(groupId)) {
                    hasGroup = true;
                    break;
                }
            }
            if (!hasGroup)
                return;

            // 被邀请
            String st3 = getResources().getString(R.string.Invite_you_to_join_a_group_chat);
            EMMessage msg = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
            msg.setChatType(EMMessage.ChatType.GroupChat);
            msg.setFrom(inviter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new TextMessageBody(inviter + " " + st3));
            // 保存邀请消息
            EMChatManager.getInstance().saveMessage(msg);
            // 提醒新消息
            HXSDKHelper.getInstance().getNotifier().viberateAndPlayTone(msg);

            runOnUiThread(new Runnable() {
                public void run() {
                    updateUnreadLabel();
                    // 刷新ui
                    if (currentTabIndex == 0)
                        chatHistoryFragment.refresh();
                    if (CommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
            });

        }

        @Override
        public void onInvitationAccpted(String groupId, String inviter, String reason) {

        }

        @Override
        public void onInvitationDeclined(String groupId, String invitee, String reason) {

        }

        @Override
        public void onUserRemoved(String groupId, String groupName) {

            // 提示用户被T了，demo省略此步骤
            // 刷新ui
            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        updateUnreadLabel();
                        if (currentTabIndex == 0)
                            chatHistoryFragment.refresh();
                        if (CommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
                            GroupsActivity.instance.onResume();
                        }
                    } catch (Exception e) {
                        EMLog.e(TAG, "refresh exception " + e.getMessage());
                    }
                }
            });
        }

        @Override
        public void onGroupDestroy(String groupId, String groupName) {

            // 群被解散
            // 提示用户群被解散,demo省略
            // 刷新ui
            runOnUiThread(new Runnable() {
                public void run() {
                    updateUnreadLabel();
                    if (currentTabIndex == 0)
                        chatHistoryFragment.refresh();
                    if (CommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
            });

        }

        @Override
        public void onApplicationReceived(String groupId, String groupName, String applyer, String reason) {

            // 用户申请加入群聊
            InviteMessage msg = new InviteMessage();
            msg.setFrom(applyer);
            msg.setTime(System.currentTimeMillis());
            msg.setGroupId(groupId);
            msg.setGroupName(groupName);
            msg.setReason(reason);
            Log.d(TAG, applyer + " 申请加入群聊：" + groupName);
            msg.setStatus(InviteMessage.InviteMesageStatus.BEAPPLYED);
            notifyNewIviteMessage(msg);
        }

        @Override
        public void onApplicationAccept(String groupId, String groupName, String accepter) {

            String st4 = getResources().getString(R.string.Agreed_to_your_group_chat_application);
            // 加群申请被同意
            EMMessage msg = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
            msg.setChatType(EMMessage.ChatType.GroupChat);
            msg.setFrom(accepter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new TextMessageBody(accepter + " " + st4));
            // 保存同意消息
            EMChatManager.getInstance().saveMessage(msg);
            // 提醒新消息
            HXSDKHelper.getInstance().getNotifier().viberateAndPlayTone(msg);

            runOnUiThread(new Runnable() {
                public void run() {
                    updateUnreadLabel();
                    // 刷新ui
                    if (currentTabIndex == 0)
                        chatHistoryFragment.refresh();
                    if (CommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
            });
        }

        @Override
        public void onApplicationDeclined(String groupId, String groupName, String decliner, String reason) {
            // 加群申请被拒绝，demo未实现
        }
    }

    /**
     * 保存提示新消息
     *
     * @param msg
     */
    private void notifyNewIviteMessage(InviteMessage msg) {
        saveInviteMsg(msg);
        // 提示有新消息
        HXSDKHelper.getInstance().getNotifier().viberateAndPlayTone(null);

        // 刷新bottom bar消息未读数
        updateUnreadAddressLable();
        // 刷新好友页面ui
        if (currentTabIndex == 1)
            contactListFragment.refresh();
    }

    /**
     * 保存邀请等msg
     *
     * @param msg
     */
    private void saveInviteMsg(InviteMessage msg) {
        // 保存msg
        inviteMessgeDao.saveMessage(msg);
        // 未读数加1
        User user = ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList().get(Constant.NEW_FRIENDS_USERNAME);
        if (user.getUnreadMsgCount() == 0)
            user.setUnreadMsgCount(user.getUnreadMsgCount() + 1);
    }

    /**
     * set head
     *
     * @param username
     * @return
     */
    User setUserHead(String username) {
        User user = new User();
        user.setUsername(username);
        String headerName = null;
        if (!TextUtils.isEmpty(user.getNick())) {
            headerName = user.getNick();
        } else {
            headerName = user.getUsername();
        }
        if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
            user.setHeader("");
        } else if (Character.isDigit(headerName.charAt(0))) {
            user.setHeader("#");
        } else {
            user.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target.substring(0, 1)
                    .toUpperCase());
            char header = user.getHeader().toLowerCase().charAt(0);
            if (header < 'a' || header > 'z') {
                user.setHeader("#");
            }
        }
        return user;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
            EMChatManager.getInstance().activityResumed();
        }

        // unregister this event listener when this activity enters the
        // background
        DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper) DemoHXSDKHelper.getInstance();
        sdkHelper.pushActivity(this);

        // register the event listener when enter the foreground
        EMChatManager.getInstance().registerEventListener(this,
                new EMNotifierEvent.Event[]{EMNotifierEvent.Event.EventNewMessage, EMNotifierEvent.Event.EventOfflineMessage, EMNotifierEvent.Event.EventConversationListChanged});
    }

    @Override
    protected void onStop() {
        EMChatManager.getInstance().unregisterEventListener(this);
        DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper) DemoHXSDKHelper.getInstance();
        sdkHelper.popActivity(this);

        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(false);
//            onBackPressed();
//            Constants.finishAllActivities();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private android.app.AlertDialog.Builder conflictBuilder;
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    private BroadcastReceiver internalDebugReceiver;

    /**
     * 帐号被移除的dialog
     */
    private void showAccountRemovedDialog() {
        isAccountRemovedDialogShow = true;
        DemoHXSDKHelper.getInstance().logout(true, null);
        String st5 = getResources().getString(R.string.Remove_the_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (accountRemovedBuilder == null)
                    accountRemovedBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                accountRemovedBuilder.setTitle(st5);
                accountRemovedBuilder.setMessage(R.string.em_user_remove);
                accountRemovedBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        accountRemovedBuilder = null;
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
                accountRemovedBuilder.setCancelable(false);
                accountRemovedBuilder.create().show();
                isCurrentAccountRemoved = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color userRemovedBuilder error" + e.getMessage());
            }

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getIntent().getBooleanExtra("conflict", false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
    }

    /**
     * 内部测试代码，开发者请忽略
     */
    private void registerInternalDebugReceiver() {
        internalDebugReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                DemoHXSDKHelper.getInstance().logout(true, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // 重新显示登陆页面
                                finish();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));

                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }

                    @Override
                    public void onError(int code, String message) {
                    }
                });
            }
        };
        IntentFilter filter = new IntentFilter(getPackageName() + ".em_internal_debug");
        registerReceiver(internalDebugReceiver, filter);
    }

    /**
     * 显示帐号在别处登录dialog
     */
    private void showConflictDialog() {
        isConflictDialogShow = true;
        DemoHXSDKHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (conflictBuilder == null)
                    conflictBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                conflictBuilder.setTitle(st);
                conflictBuilder.setMessage(R.string.connect_conflict);
                conflictBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conflictBuilder = null;
                        StoreUtils.destory();
                        Constants.finishAllActivities();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
                conflictBuilder.setCancelable(false);
                conflictBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color conflictBuilder error" + e.getMessage());
            }

        }

    }
}
