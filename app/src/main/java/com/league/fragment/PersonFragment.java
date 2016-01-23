package com.league.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.RuleActivity;
import com.league.activity.circle.CircleActivity;
import com.league.activity.personactivity.AlianceReward;
import com.league.activity.personactivity.MyCollection;
import com.league.activity.personactivity.MyMoneyBag;
import com.league.activity.personactivity.PersonInformationSetup;
import com.league.activity.personactivity.PersonSetup;
import com.league.activity.personinfoactivity.InviteFriendActivity;
import com.league.bean.UserInfoBean;
import com.league.interf.OnAllComplete;
import com.league.utils.ActivityUtils;
import com.league.utils.IContants;
import com.league.utils.ImgUtils;
import com.league.utils.StoreUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.league.widget.PickImgPopWindow;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mine.league.R;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;

import io.paperdb.Paper;

/**
 * @author liugang
 * @date 2015年9月15日
 */
public class PersonFragment extends Fragment implements View.OnClickListener, IContants {
    protected Dialog loadingDialog;
    private View layout;
    private Activity ctx;
    private UserInfoBean userInfoBean;
    //    @Bind(R.id.mythumb)
    ImageView mThumb;
    //    @Bind(R.id.nickname)
    TextView mNickname;
    //    @Bind(R.id.phonenumber)
    TextView mPhone;
    //    @Bind(R.id.zhijiealliancenum)
//    @Bind(R.id.fivefloartotal)
    TextView mAllFive;
    //    @Bind(R.id.award)
    TextView mAward;

    TextView tvFriendCount,tvFansCount,tvSignature;

    ImageView ivBackground;
    private PickImgPopWindow pickImgPopWindow;
    private Uri imgUri;
    private StringBuffer picture = new StringBuffer();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (layout == null) {
            ctx = this.getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_person, null);

        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
//        ButterKnife.bind(ctx,layout);
        mThumb = (ImageView) layout.findViewById(R.id.iv_thumb);
        mNickname = (TextView) layout.findViewById(R.id.tv_nickname);
//        mPhone = (TextView) layout.findViewById(R.id.phonenumber);
        mAllFive = (TextView) layout.findViewById(R.id.fivefloartotal);
        mAward = (TextView) layout.findViewById(R.id.award);
        tvFansCount = (TextView) layout.findViewById(R.id.tv_fanscount);
        tvFriendCount = (TextView) layout.findViewById(R.id.tv_friendcount);
        tvSignature = (TextView) layout.findViewById(R.id.tv_signature);
        ivBackground = (ImageView) layout.findViewById(R.id.iv_background);

        pickImgPopWindow = new PickImgPopWindow(ctx, ivBackground, new PickImgPopWindow.PopClickListener() {
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

        showProgressDialog();
        initData();
        getUrl();
        setOnClickListener();
        return layout;
    }

    private void setOnClickListener() {
        layout.findViewById(R.id.setup).setOnClickListener(this);
        layout.findViewById(R.id.myown).setOnClickListener(this);
        layout.findViewById(R.id.aliancereward).setOnClickListener(this);
        layout.findViewById(R.id.mycollection).setOnClickListener(this);
        layout.findViewById(R.id.mymoneybag).setOnClickListener(this);
        layout.findViewById(R.id.invitefriend).setOnClickListener(this);
        layout.findViewById(R.id.mycircle).setOnClickListener(this);
        layout.findViewById(R.id.rl_allancecount).setOnClickListener(this);
        ivBackground.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pickImgPopWindow.showPopWindow();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setup:
                Intent intent = new Intent(ctx, PersonSetup.class);
                startActivity(intent);
                break;
            case R.id.myown:
                Intent intent1 = new Intent(ctx, PersonInformationSetup.class);
                startActivity(intent1);
                break;
            case R.id.aliancereward:
                Intent intent2 = new Intent(ctx, AlianceReward.class);
                startActivity(intent2);
                break;
            case R.id.mycollection:
                Intent intent3 = new Intent(ctx, MyCollection.class);
                startActivity(intent3);
                break;
            case R.id.mycircle:
                ActivityUtils.start_Activity(ctx, CircleActivity.class);
                break;
            case R.id.mymoneybag:
                Intent intent4 = new Intent(ctx, MyMoneyBag.class);
                startActivity(intent4);
                break;
            case R.id.invitefriend:
                startActivity(new Intent(ctx, InviteFriendActivity.class));
                break;
            case R.id.rl_allancecount:
                Intent intent5 = new Intent(ctx, RuleActivity.class);
                intent5.putExtra(RuleType, 5);
                startActivity(intent5);
                break;
        }
    }

    private void initData() {
        ApiUtil.getUserDetail(ctx, StoreUtils.getPhone(), new BaseJsonHttpResponseHandler<UserInfoBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, UserInfoBean response) {
                StoreUtils.setUserInfo(response);
                updateView();
                closeProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, UserInfoBean errorResponse) {
                if (Paper.book().exist(StoreUtils.UserInfo))
                    updateView();
                closeProgressDialog();
            }

            @Override
            protected UserInfoBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<UserInfoBean>() {
                });
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void updateView(){
        userInfoBean = StoreUtils.getUserInfo();

        if (!TextUtils.isEmpty(userInfoBean.getThumb()))
            Picasso.with(ctx).load(userInfoBean.getThumb()).resize(120, 120).centerCrop().placeholder(R.drawable.example).into(mThumb);
        else
            Picasso.with(ctx).load(R.drawable.example).into(mThumb);

        if (!TextUtils.isEmpty(userInfoBean.getBackground()))
            Picasso.with(ctx).load(userInfoBean.getBackground()).placeholder(R.drawable.mybackground).into(ivBackground);
        else
            Picasso.with(ctx).load(R.drawable.mybackground).into(ivBackground);

        mNickname.setText(userInfoBean.getNickname());
        mAllFive.setText(userInfoBean.getAllalliancecount() + "");
        mAward.setText(userInfoBean.getAlliancerewards() + "");
        tvFriendCount.setText(userInfoBean.getFriendcount() + "");
        tvFansCount.setText(userInfoBean.getConcerncount() + "");
        tvSignature.setText(userInfoBean.getSignature());
    }

    private Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;

    }

    /**
     * 显示等待对话框
     */
    public void showProgressDialog() {
        loadingDialog = createLoadingDialog(ctx);
        loadingDialog.show();
    }

    /**
     * 关闭等待对话框
     */
    public void closeProgressDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    //这个有问题啊 ！！！！！
    private void getUrl() {
        ApiUtil.getSignupUrl(ctx, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                String url = responseString.substring(1, responseString.length() - 1);
                Paper.book().write("signupurl", url);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SELECT_CAMER:
//                    updateAvatar(imgUri);
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
            Toast.makeText(ctx, "选择图片失败,请重新选择", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void updateAvatar(Uri uri) {
        String imagePath = ImgUtils.getRealFilePath(ctx, uri);
//        Log.d("url", imagePath);
        //本地为什么放不进去 实在不行先要上传到七牛 再用七牛的链接地址更新头像
//        Picasso.with(this).load("file://" + ImgUtils.getRealFilePath(getApplicationContext(), uri)).resize(160, 160).centerCrop().into(mThumbnail);
        if (!TextUtils.isEmpty(imagePath)) {
            String zoomedImgePath = ImgUtils.saveBitmapToSDCard(ImgUtils.zoomBitmap(ctx, uri, 800, 600));
//            Picasso.with(this).load("file://" + zoomedImgePath).resize(160, 160).centerCrop().into(mThumbnail);
            uploadAvatar(zoomedImgePath);
        } else
            ToastUtils.showShortToast(ctx, "头像获取失败");
    }

    public void uploadAvatar(final String imagePath) {
        final UploadManager uploadManager = new UploadManager();
        showProgressDialog();
        ApiUtil.getQiniuToken(ctx, new JsonHttpResponseHandler() {
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
                Picasso.with(ctx).load(picture.toString()).placeholder(R.drawable.mybackground).into(ivBackground);
            else
                Picasso.with(ctx).load(R.drawable.mybackground).into(ivBackground);
//            加上修改背景的接口
            ApiUtil.modifyUserBackground(ctx, picture.toString(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    closeProgressDialog();
                    picture = new StringBuffer();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    closeProgressDialog();
                }
            });
        }
    };
}
