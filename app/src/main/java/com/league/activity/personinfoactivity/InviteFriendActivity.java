package com.league.activity.personinfoactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.league.utils.CreateQRImage;
import com.league.utils.PersonInfoBaseActivity;
import com.mine.league.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class InviteFriendActivity extends PersonInfoBaseActivity implements View.OnClickListener{
    private ImageView mQRCode;
    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("邀请好友");
        CreateQRImage mCreateQR=new CreateQRImage(mQRCode);
        mCreateQR.createQRImage("https://www.baidu.com/");
        com.umeng.socialize.utils.Log.LOG = true;

        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();
        //添加微博
        SinaSsoHandler sinaSsoHandler=new SinaSsoHandler();
        sinaSsoHandler.addToSocialSDK();
        // 添加短信
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appID = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this,appID,appSecret);
        wxHandler.addToSocialSDK();
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 设置分享内容
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    protected void initView() {
        mQRCode= (ImageView) findViewById(R.id.qrcode);
        setOnclickListener();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.qqshare:
                mController.postShare(InviteFriendActivity.this,SHARE_MEDIA.QQ,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode,SocializeEntity entity) {
                                if (eCode == 200) {
                                    Toast.makeText(InviteFriendActivity.this, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101){
                                        eMsg = "没有授权";
                                    }
                                    Toast.makeText(InviteFriendActivity.this, "分享失败[" + eCode + "] " +
                                            eMsg,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.wxshare:
                mController.postShare(InviteFriendActivity.this,SHARE_MEDIA.WEIXIN,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode,SocializeEntity entity) {
                                if (eCode == 200) {
                                    Toast.makeText(InviteFriendActivity.this, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101){
                                        eMsg = "没有授权";
                                    }
                                    Toast.makeText(InviteFriendActivity.this, "分享失败[" + eCode + "] " +
                                            eMsg,Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    Toast.makeText(InviteFriendActivity.this, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101) {
                                        eMsg = "没有授权";
                                    }
                                    Toast.makeText(InviteFriendActivity.this, "分享失败[" + eCode + "] " +
                                            eMsg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;
            case R.id.dxshare:
                mController.postShare(InviteFriendActivity.this,SHARE_MEDIA.SMS,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode,SocializeEntity entity) {
                                if (eCode == 200) {
                                    Toast.makeText(InviteFriendActivity.this, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101){
                                        eMsg = "没有授权";
                                    }
                                    Toast.makeText(InviteFriendActivity.this, "分享失败[" + eCode + "] " +
                                            eMsg,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }
    private void setOnclickListener(){
        findViewById(R.id.qqshare).setOnClickListener(this);
        findViewById(R.id.wxshare).setOnClickListener(this);
        findViewById(R.id.wbshare).setOnClickListener(this);
        findViewById(R.id.dxshare).setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
