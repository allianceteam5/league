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
import android.provider.MediaStore;
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

import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class PersonInformationSetup extends BaseActivity implements View.OnClickListener,IContants{

    private ImageView back2, titleright, right1, right2;
    private TextView title;
    private UserInfoBean userInfoBean;
    @Bind(R.id.thumbnail)
    CircleImageView mThumbnail;
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
    private Uri uri;
    public final int SELECT_CAMER = 1; // 用相机拍摄照片
    public final int SELECT_PICTURE = 2; // 从图库中选择图片
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

        pickImgPopWindow = new PickImgPopWindow(this, findViewById(android.R.id.content), new PickImgPopWindow.PopClickListener() {
            @Override
            public void onClick(int index) {
                switch (index) {
                    case 0:
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
                    Bundle bundle = data.getExtras();
                    bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                    uri = data.getData();
                    if (uri == null)
                        uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
                    Log.i("picture uri", uri.getPath());
                    mThumbnail.setImageURI(uri);
                    uploadAvatar(getRealFilePath(getApplicationContext(),uri));
                    break;
                case SELECT_PICTURE:
                    uri = data.getData();
                    ContentResolver cr = this.getContentResolver();
                    try {
                        if (bitmap != null) {
                            bitmap.recycle();
                            bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        }
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.i("picture uri", uri.getPath());
                    mThumbnail.setImageURI(uri);
                    uploadAvatar(getRealFilePath(getApplicationContext(),uri));
                    break;
            }
        } else {
            Toast.makeText(PersonInformationSetup.this, "选择图片失败,请重新选择", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public  String getRealFilePath(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void uploadAvatar(final String imagePath){
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
