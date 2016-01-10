package com.league.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

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
import com.umeng.socialize.weixin.controller.UMWXHandler;

/**
 * Created by liug on 15/12/3.
 */
public class ShareDialog extends Dialog implements android.view.View.OnClickListener {
    private Context context;
    View localView;
    Activity activity;
    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    public ShareDialog(Context context, Activity activity) {
        super(context);
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 这句代码换掉dialog默认背景，否则dialog的边缘发虚透明而且很宽
        // 总之达不到想要的效果
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = LayoutInflater.from(context);
        //((AnimationActivity) context).getLayoutInflater();
        localView = inflater.inflate(R.layout.sharedialog, null);
        localView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_bottom_to_top));
        setContentView(localView);
        // 这句话起全屏的作用
        getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        initViews();
        setOnclick();
    }

    private void initViews() {
        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();
        //参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();
        //添加微博
        SinaSsoHandler sinaSsoHandler = new SinaSsoHandler();
        sinaSsoHandler.addToSocialSDK();
        // 添加短信
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appID = "wx98e6c27800ad7a60";
        String appSecret = "35978744f081736d47fca4042c8cd4f4";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(activity, appID, appSecret);
        wxHandler.addToSocialSDK();
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(activity, appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 设置分享内容
        mController.setShareContent("我在自己人联盟里抢到红包了，你也快来跟我一起抢啊");
    }

    private void setOnclick() {
        localView.findViewById(R.id.qqshare).setOnClickListener(this);
        localView.findViewById(R.id.wxshare).setOnClickListener(this);
        localView.findViewById(R.id.wbshare).setOnClickListener(this);
        localView.findViewById(R.id.qqzoneshare).setOnClickListener(this);
        localView.findViewById(R.id.wxcircleshare).setOnClickListener(this);
        localView.findViewById(R.id.dxshare).setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.dismiss();
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qqzoneshare:
                mController.postShare(activity, SHARE_MEDIA.QZONE,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
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
                mController.postShare(activity, SHARE_MEDIA.WEIXIN_CIRCLE,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
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
                mController.postShare(activity, SHARE_MEDIA.QQ,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
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
                mController.postShare(activity, SHARE_MEDIA.WEIXIN,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
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
                mController.postShare(activity, SHARE_MEDIA.SINA,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
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
                mController.postShare(activity, SHARE_MEDIA.SMS,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(InviteFriendActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
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
}
