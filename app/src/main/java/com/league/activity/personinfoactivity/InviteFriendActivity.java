package com.league.activity.personinfoactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.UserInfoBean;
import com.league.utils.CreateQRImage;
import com.league.utils.PersonInfoBaseActivity;
import com.league.utils.StoreUtils;
import com.mine.league.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import io.paperdb.Paper;

public class InviteFriendActivity extends PersonInfoBaseActivity implements View.OnClickListener {
    private ImageView mQRCode;
    private TextView tvInviteCode;
    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("邀请好友");
        CreateQRImage mCreateQR = new CreateQRImage(mQRCode);
        String url = Paper.book().read("signupurl").toString();
        mCreateQR.createQRImage(url);
        com.umeng.socialize.utils.Log.LOG = true;

        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();
        //参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();
        //添加微博
        SinaSsoHandler sinaSsoHandler = new SinaSsoHandler();
        sinaSsoHandler.addToSocialSDK();
        // 添加短信
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appID = "wxa2d6e9ec950af3d1";
        String appSecret = "77efbe96aa7b2b65d998fed09fa920fd";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
        wxHandler.addToSocialSDK();
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 设置分享内容
        mController.setShareContent("欢迎下载自己人联盟APP " + url);
        mController.getConfig().closeToast();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    protected void initView() {
        mQRCode = (ImageView) findViewById(R.id.qrcode);
        tvInviteCode = (TextView) findViewById(R.id.tv_invitecode);
        UserInfoBean userInfoBean = StoreUtils.getUserInfo();
        if (!TextUtils.isEmpty(userInfoBean.getInvitecode()))
            tvInviteCode.setText(userInfoBean.getInvitecode());
        setOnclickListener();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qqzoneshare:
                mController.postShare(InviteFriendActivity.this, SHARE_MEDIA.QZONE,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    StoreUtils.setGrabNum(2);
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101) {
                                        eMsg = "没有授权";
                                    }
                                }
                            }
                        });
                break;
            case R.id.wxcircleshare:
                mController.postShare(InviteFriendActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    StoreUtils.setGrabNum(0);
//                                    Toast.makeText(InviteFriendActivity.this, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101) {
                                        eMsg = "没有授权";
                                    }
//                                    Toast.makeText(InviteFriendActivity.this, "分享失败[" + eCode + "] " +
//                                            eMsg,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.qqshare:
                mController.postShare(InviteFriendActivity.this, SHARE_MEDIA.QQ,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    StoreUtils.setGrabNum(3);
//                                    Toast.makeText(InviteFriendActivity.this, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101) {
                                        eMsg = "没有授权";
                                    }
//                                    Toast.makeText(InviteFriendActivity.this, "分享失败[" + eCode + "] " +
//                                            eMsg,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.wxshare:
                mController.postShare(InviteFriendActivity.this, SHARE_MEDIA.WEIXIN,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    StoreUtils.setGrabNum(1);
//                                    Toast.makeText(InviteFriendActivity.this, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101) {
                                        eMsg = "没有授权";
                                    }
//                                    Toast.makeText(InviteFriendActivity.this, "分享失败[" + eCode + "] " +
//                                            eMsg,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.wbshare:
                // 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
                mController.postShare(InviteFriendActivity.this, SHARE_MEDIA.SINA,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    StoreUtils.setGrabNum(4);
//                                    Toast.makeText(InviteFriendActivity.this, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101) {
                                        eMsg = "没有授权";
                                    }
//                                    Toast.makeText(InviteFriendActivity.this, "分享失败[" + eCode + "] " +
//                                            eMsg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;
            case R.id.dxshare:
                mController.postShare(InviteFriendActivity.this, SHARE_MEDIA.SMS,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    StoreUtils.setGrabNum(5);
//                                    Toast.makeText(InviteFriendActivity.this, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101) {
                                        eMsg = "没有授权";
                                    }
//                                    Toast.makeText(InviteFriendActivity.this, "分享失败[" + eCode + "] " +
//                                            eMsg,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }

    private void setOnclickListener() {
        findViewById(R.id.wxcircleshare).setOnClickListener(this);
        findViewById(R.id.qqshare).setOnClickListener(this);
        findViewById(R.id.wxshare).setOnClickListener(this);
        findViewById(R.id.wbshare).setOnClickListener(this);
        findViewById(R.id.dxshare).setOnClickListener(this);
        findViewById(R.id.qqzoneshare).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
