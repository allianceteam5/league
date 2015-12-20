package com.league.activity.personinfoactivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chatuidemo.Constant;
import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.easemob.chatuidemo.db.UserDao;
import com.easemob.chatuidemo.domain.User;
import com.league.bean.UserInfoBean;
import com.league.utils.Constants;
import com.league.utils.PersonInfoBaseActivity;
import com.league.widget.CircleImageView;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class CompletePersonInfo extends PersonInfoBaseActivity implements View.OnClickListener {
    @Bind(R.id.thumbnail)
    CircleImageView mThumb;
    @Bind(R.id.nickname)
    EditText mNickname;
    @Bind(R.id.gender)
    TextView mGender;
    @Bind(R.id.area)
    TextView mArea;
    @Bind(R.id.login)
    Button login;

    private boolean progressShow;
    private UserInfoBean userInfoBean = new UserInfoBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("完善个人信息");
        mNickname.addTextChangedListener(watcher);
        login.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complete_person_info;
    }

    @Override
    protected void initView() {
        findViewById(R.id.changethumb).setOnClickListener(this);
        findViewById(R.id.mygender).setOnClickListener(this);
        findViewById(R.id.myarea).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changethumb:
                break;
            case R.id.mygender:
                startActivity(new Intent(this, MyGenderActivity.class));
                break;
            case R.id.myarea:
                startActivity(new Intent(this, MyAreaActivity.class));
                break;
            case R.id.login:
                progressShow = true;
                final ProgressDialog pd = new ProgressDialog(this);
                pd.setCanceledOnTouchOutside(false);
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        progressShow = false;
                    }
                });
                pd.setMessage(getString(R.string.Is_landing));
                pd.show();

                final long start = System.currentTimeMillis();
                // 调用sdk登陆方法登陆聊天服务器
                EMChatManager.getInstance().login(Constants.HuanXinID, Constants.HuanxinPwd, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        if (!progressShow) {
                            return;
                        }
                        // 登陆成功，保存用户名密码
                        DemoApplication.getInstance().setUserName(Constants.HuanXinID);
                        DemoApplication.getInstance().setPassword(Constants.HuanxinPwd);

                        try {
                            // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                            // ** manually load all local groups and
                            EMGroupManager.getInstance().loadAllGroups();
                            EMChatManager.getInstance().loadAllConversations();
                            // 处理好友和群组
                            initializeContacts();
                        } catch (Exception e) {
                            e.printStackTrace();
                            // 取好友或者群聊失败，不让进入主页面
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    pd.dismiss();
                                    DemoHXSDKHelper.getInstance().logout(true, null);
                                    Toast.makeText(getApplicationContext(), R.string.login_failure_failed, Toast.LENGTH_LONG).show();
                                }
                            });
                            return;
                        }
                        // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                        boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
                                DemoApplication.currentUserNick.trim());
                        if (!updatenick) {
                            Log.e("LoginActivity", "update current user nick fail");
                        }
                        if (!CompletePersonInfo.this.isFinishing() && pd.isShowing()) {
                            pd.dismiss();
                        }
                        // 进入主页面
                        Intent intent = new Intent(CompletePersonInfo.this, com.league.activity.MainActivity.class);
                        startActivity(intent);
                        Constants.finishAllActivities();
                        finish();

                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, final String s) {
                        if (!progressShow) {
                            return;
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + s,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadData();
    }

    private void reloadData() {
        userInfoBean = Paper.book().read("UserInfoBean");
        if (userInfoBean == null) {
            userInfoBean = new UserInfoBean();
        }
        mNickname.setText(userInfoBean.getNickname());
        mGender.setText(userInfoBean.getGender() == 0 ? "女" : "男");
        mArea.setText(userInfoBean.getArea());
    }

    private void initializeContacts() {
        Map<String, User> userlist = new HashMap<String, User>();
        // 添加user"申请与通知"
        User newFriends = new User();
        newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
        String strChat = getResources().getString(
                R.string.Application_and_notify);
        newFriends.setNick(strChat);

        userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
        // 添加"群聊"
        User groupUser = new User();
        String strGroup = getResources().getString(R.string.group_chat);
        groupUser.setUsername(Constant.GROUP_USERNAME);
        groupUser.setNick(strGroup);
        groupUser.setHeader("");
        userlist.put(Constant.GROUP_USERNAME, groupUser);

        // 添加"Robot"
        User robotUser = new User();
        String strRobot = getResources().getString(R.string.robot_chat);
        robotUser.setUsername(Constant.CHAT_ROBOT);
        robotUser.setNick(strRobot);
        robotUser.setHeader("");
//		userlist.put(Constant.CHAT_ROBOT, robotUser);

        //添加"朋友圈"
        User circle = new User();
        String strCircle = getResources().getString(R.string.friend_circle);
        circle.setUsername(Constant.FRIEDN_CIRCLE);
        circle.setNick(strCircle);
        circle.setHeader("");
        userlist.put(Constant.FRIEDN_CIRCLE, circle);

        // 存入内存
        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).setContactList(userlist);
        // 存入db
        UserDao dao = new UserDao(this);
        List<User> users = new ArrayList<User>(userlist.values());
        dao.saveContactList(users);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub


        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            userInfoBean.setNickname(mNickname.getText().toString());
            Paper.book().write("UserInfoBean", userInfoBean);
        }
    };
}
