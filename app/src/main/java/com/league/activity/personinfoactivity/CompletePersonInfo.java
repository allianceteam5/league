package com.league.activity.personinfoactivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.SucessBean;
import com.league.bean.UserInfoBean;
import com.league.interf.OnAllComplete;
import com.league.otto.BusProvider;
import com.league.otto.RefreshEvent;
import com.league.utils.Constants;
import com.league.utils.ImgUtils;
import com.league.utils.PersonInfoBaseActivity;
import com.league.utils.StoreUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.league.widget.PickImgPopWindow;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mine.league.R;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class CompletePersonInfo extends PersonInfoBaseActivity implements View.OnClickListener {
    @Bind(R.id.thumbnail)
    ImageView mThumb;
    @Bind(R.id.nickname)
    EditText mNickname;
    @Bind(R.id.gender)
    TextView mGender;
    @Bind(R.id.area)
    TextView mArea;
    @Bind(R.id.login)
    Button login;

    private boolean progressShow;
    private PickImgPopWindow pickImgPopWindow;
    private Uri imgUri;
    private StringBuffer picture = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("完善个人信息");
        login.setOnClickListener(this);

        pickImgPopWindow = new PickImgPopWindow(this, login, new PickImgPopWindow.PopClickListener() {
            @Override
            public void onClick(int index) {
                switch (index) {
                    case 0:
                        File tempFile = new File(Environment.getExternalStorageDirectory(),
                                ImgUtils.getImageFileName());
                        imgUri = Uri.fromFile(tempFile);
                        Intent camerInetent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        camerInetent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                        pickImgPopWindow.dismissPopWindow();
                        startActivityForResult(camerInetent, SELECT_CAMER);
                        break;
                    case 1:
                        Intent picIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        picIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        picIntent.setType("image/*");
                        pickImgPopWindow.dismissPopWindow();
                        startActivityForResult(picIntent, SELECT_PICTURE);
                        break;
                    case 2:
                        pickImgPopWindow.dismissPopWindow();
                        break;
                }
            }
        });
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
                pickImgPopWindow.showPopWindow();
                break;
            case R.id.mygender:
                startActivity(new Intent(this, MyGenderActivity.class));
                break;
            case R.id.myarea:
                startActivity(new Intent(this, MyAreaActivity.class));
                break;
            case R.id.login:
                String nickname = mNickname.getText().toString();

                if (!TextUtils.isEmpty(nickname)){
                    ToastUtils.showShortToast(CompletePersonInfo.this, "请输入昵称");
                    return;
                }

                ApiUtil.modifyUserDetailNickname(getApplicationContext(), nickname,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    }
                });

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
                EMChatManager.getInstance().login(StoreUtils.getHuanXinId(), StoreUtils.getHuanXinPwd(), new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        if (!progressShow) {
                            return;
                        }
                        // 登陆成功，保存用户名密码
                        DemoApplication.getInstance().setUserName(StoreUtils.getHuanXinId());
                        DemoApplication.getInstance().setPassword(StoreUtils.getHuanXinPwd());

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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        reloadData();
//    }
//
//    private void reloadData() {
//        userInfoBean = StoreUtils.getUserInfo();
//        if (userInfoBean == null) {
//            userInfoBean = new UserInfoBean();
//        }
//        mNickname.setText(userInfoBean.getNickname());
//        mGender.setText(userInfoBean.getGender() == 0 ? "女" : "男");
//        mArea.setText(userInfoBean.getArea());
//    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_CAMER:
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imgUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                    startActivityForResult(intent, CROP_PHOTO);
                    break;
                case CROP_PHOTO:
                    updateAvatar(imgUri);
                    break;
                case SELECT_PICTURE:
                    updateAvatar(data.getData());
                    break;
            }
        } else {
            Toast.makeText(CompletePersonInfo.this, "选择图片失败,请重新选择", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void updateAvatar(Uri uri) {
        String imagePath = ImgUtils.getRealFilePath(getApplicationContext(), uri);
//        Log.d("url", imagePath);
        //本地为什么放不进去 实在不行先要上传到七牛 再用七牛的链接地址更新头像
//        Picasso.with(this).load("file://" + ImgUtils.getRealFilePath(getApplicationContext(), uri)).resize(160, 160).centerCrop().into(mThumbnail);
        if (!TextUtils.isEmpty(imagePath)) {
            String zoomedImgePath = ImgUtils.saveBitmapToSDCard(ImgUtils.zoomBitmap(CompletePersonInfo.this, uri, 160, 160));
//            Picasso.with(this).load("file://" + zoomedImgePath).resize(160, 160).centerCrop().into(mThumbnail);
            uploadAvatar(zoomedImgePath);
        } else
            ToastUtils.showShortToast(this, "头像获取失败");
    }

    public void uploadAvatar(final String imagePath) {
        final UploadManager uploadManager = new UploadManager();
        showProgressDialog();
        ApiUtil.getQiniuToken(getApplicationContext(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                final String key = "items/" + System.currentTimeMillis() + ".png";
                String token = response.optString("token");
                picture.append(QINIU_PREFIX + key);
                uploadManager.put(imagePath, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                                if (responseInfo.error == null) {
                                    onAllComplete.allComplete(true);
                                } else {
                                    closeProgressDialog();
                                    return;
                                }
                            }
                        }, null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                closeProgressDialog();
            }
        });
    }

    private OnAllComplete onAllComplete = new OnAllComplete() {
        @Override
        public void allComplete(boolean result) {
            if (!result) {
                closeProgressDialog();
                return;
            }
            //头像地址放在picture里
            if (!TextUtils.isEmpty(picture))
                Picasso.with(getApplicationContext()).load(picture.toString()).resize(160, 160).centerCrop().into(mThumb);
            else
                Picasso.with(getApplicationContext()).load(R.drawable.example).into(mThumb);
//            加上修改头像的接口
            ApiUtil.modifyUserThumb(getApplicationContext(), picture.toString(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    closeProgressDialog();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    closeProgressDialog();
                }
            });
        }
    };
}
