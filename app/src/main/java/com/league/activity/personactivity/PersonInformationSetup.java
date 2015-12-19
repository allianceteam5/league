package com.league.activity.personactivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.league.activity.BaseActivity;
import com.league.activity.personinfoactivity.MyAreaActivity;
import com.league.activity.personinfoactivity.MyGenderActivity;
import com.league.activity.personinfoactivity.NickNameActivity;
import com.league.activity.personinfoactivity.ShippingAddress;
import com.league.activity.personinfoactivity.SignatureActivity;
import com.league.bean.UserInfoBean;
import com.league.interf.OnAllComplete;
import com.league.utils.IContants;
import com.league.utils.ImgUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.league.widget.PickImgPopWindow;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mine.league.R;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class PersonInformationSetup extends BaseActivity implements View.OnClickListener,IContants{

    private ImageView back2, titleright, right1, right2;
    private TextView title;
    private UserInfoBean userInfoBean;
    @Bind(R.id.thumbnail)
    ImageView mThumbnail;
    @Bind(R.id.nickname)
    TextView mNickName;
    @Bind(R.id.numberid)
    TextView mNumberID;
    @Bind(R.id.gender)
    TextView mGender;
    @Bind(R.id.area)
    TextView mArea;
    @Bind(R.id.signature)
    TextView mSignature;
    private PickImgPopWindow pickImgPopWindow;
    private Bitmap bitmap;
    private Uri imgUri;
    public final int SELECT_CAMER = 1; // 用相机拍摄照片
    public final int SELECT_PICTURE = 2; // 从图库中选择图片
    public final int CROP_PHOTO = 3;// 系统的裁剪图片
    private StringBuffer picture = new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_information_setup);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

        back2 = (ImageView) findViewById(R.id.near_back);

        back2.setVisibility(View.VISIBLE);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        titleright = (ImageView) findViewById(R.id.near_title_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("个人资料");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);

        pickImgPopWindow = new PickImgPopWindow(this, findViewById(R.id.ll_global), new PickImgPopWindow.PopClickListener() {
            @Override
            public void onClick(int index) {
                switch (index) {
                    case 0:
                        File tempFile = new File(Environment.getExternalStorageDirectory(),
                                ImgUtils.getImageFileName());
                        imgUri = Uri.fromFile(tempFile);
                        Intent camerInetent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(camerInetent, SELECT_CAMER);
                        pickImgPopWindow.dismissPopWindow();
                        break;
                    case 1:
                        Intent picIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        picIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        picIntent.setType("image/*");
                        startActivityForResult(picIntent, SELECT_PICTURE);
                        pickImgPopWindow.dismissPopWindow();
                        break;
                }
            }
        });
    }
    private void initData(){
        userInfoBean= Paper.book().read("UserInfoBean");
        Picasso.with(this).load(userInfoBean.getThumb()).into(mThumbnail);
        mNickName.setText(userInfoBean.getNickname());
        mNumberID.setText(userInfoBean.getPhone());
        mGender.setText(userInfoBean.getGender() == 0 ? "女" : "男");
        mArea.setText(userInfoBean.getArea());
        mSignature.setText(userInfoBean.getSignature());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changeportrail:
                pickImgPopWindow.showPopWindow();
                break;
            case R.id.truenamemake:
                Intent intent=new Intent(PersonInformationSetup.this,Certification.class);
                startActivity(intent);
                break;
            case R.id.shippingaddress:
                Intent address=new Intent(PersonInformationSetup.this, ShippingAddress.class);
                startActivity(address);
                break;
            case R.id.mysignature:
                Intent signature=new Intent(PersonInformationSetup.this, SignatureActivity.class);
                startActivity(signature);
                break;
            case R.id.myarea:
                startActivity(new Intent(PersonInformationSetup.this, MyAreaActivity.class));
                break;
            case R.id.mygender:
                startActivity(new Intent(PersonInformationSetup.this, MyGenderActivity.class));
                break;
            case R.id.mynickname:
                startActivity(new Intent(PersonInformationSetup.this, NickNameActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_CAMER:
                    updateAvatar(imgUri);
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imgUri, "image/*");
//                    intent.putExtra("scale", true);
                    //开启裁剪功能
                    intent.putExtra("crop", "true");
                    //设定宽高的比例
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    //设定裁剪图片宽高
                    intent.putExtra("outputX", 160);
                    intent.putExtra("outputY", 160);
                    intent.putExtra("return-data", true);
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
            Toast.makeText(PersonInformationSetup.this, "选择图片失败,请重新选择", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    public void updateAvatar(Uri uri) {
        String imagePath = ImgUtils.getRealFilePath(getApplicationContext(), uri);
        Log.d("url",imagePath);
        //本地为什么放不进去 实在不行先要上传到七牛 再用七牛的链接地址更新头像
        Picasso.with(getApplicationContext()).load("file://" + ImgUtils.getRealFilePath(getApplicationContext(), uri)).resize(160, 160).centerCrop().into(mThumbnail);
        if (!TextUtils.isEmpty(imagePath)) {
            String zoomedImgePath = ImgUtils.saveBitmapToSDCard(ImgUtils.zoomBitmap(PersonInformationSetup.this,uri, 160, 160));
            Picasso.with(getApplicationContext()).load("file://" + zoomedImgePath).resize(160, 160).centerCrop().into(mThumbnail);
            uploadAvatar(zoomedImgePath);
        } else
            ToastUtils.showShortToast(this, "头像获取失败");
    }

    public void uploadAvatar(final String imagePath){
        final UploadManager uploadManager = new UploadManager();
//        showProgressDialog();
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
            Picasso.with(getApplicationContext()).load(picture.toString()).resize(160, 160).centerCrop().into(mThumbnail);
//            加上修改头像的接口
//            ApiUtil.hobbyCreated(getApplicationContext(), picture.toString(), selectedSexIndex, selectedAgeIndex, selectedHobbyId, message, new JsonHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    closeProgressDialog();
//                    if (response.optInt("flag") == 1) {
//                        Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    closeProgressDialog();
//                    Toast.makeText(getApplicationContext(), "发布失败", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    };
}
