package com.league.activity.near;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.league.activity.BaseActivity;
import com.league.bean.KindBean;
import com.league.interf.OnAllComplete;
import com.league.utils.Constants;
import com.league.utils.IContants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
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

import io.paperdb.Paper;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class HobbyInfoPublishActivity extends BaseActivity implements View.OnClickListener, IContants {

    private final int REQUEST_IMAGE = 2;
    private ArrayList<String> mSelectPath = new ArrayList<>();
    private ImageView back, title_right, right, ivPhone;
    private TextView title, save, tvSex, tvAge, tvHobby;
    private RelativeLayout rl_sex, rl_age, rl_hobby;
    private EditText etMessage;
    private int selectedSexIndex = 0;
    private int selectedAgeIndex = -1;
    private int selectedHobbyIndex = -1;
    private int selectedHobbyId = -1;
    private StringBuffer picture = new StringBuffer();
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hobbyinfo_publish);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        title_right = (ImageView) findViewById(R.id.near_title_right);
        title_right.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("交友发帖");
        right = (ImageView) findViewById(R.id.near_right);
        right.setVisibility(View.GONE);
        save = (TextView) findViewById(R.id.near_save);
        save.setVisibility(View.VISIBLE);
        save.setOnClickListener(this);
        ivPhone = (ImageView) findViewById(R.id.iv_phone);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvAge = (TextView) findViewById(R.id.tv_age);
        tvHobby = (TextView) findViewById(R.id.tv_hobby);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        rl_age = (RelativeLayout) findViewById(R.id.rl_age);
        rl_hobby = (RelativeLayout) findViewById(R.id.rl_hobby);
        ivPhone.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_age.setOnClickListener(this);
        rl_hobby.setOnClickListener(this);
        etMessage = (EditText) findViewById(R.id.et_leave);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(HobbyInfoPublishActivity.this, RadioSelectActivity.class);
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            case R.id.iv_phone:
                Intent intent1 = new Intent(HobbyInfoPublishActivity.this, MultiImageSelectorActivity.class);
                // 是否显示调用相机拍照
                intent1.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
//                // 最大图片选择数量
//                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
                // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
                intent1.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                startActivityForResult(intent1, REQUEST_IMAGE);
                break;
            case R.id.rl_sex:
                intent.putExtra(Constants.RADIOSELECEDTINDEX, selectedSexIndex);
                intent.putExtra(Constants.RADIOSELECTKIND, Constants.RADIOSEX);
                startActivityForResult(intent, Constants.RADIOSEX);
                break;
            case R.id.rl_age:
                intent.putExtra(Constants.RADIOSELECEDTINDEX, selectedAgeIndex);
                intent.putExtra(Constants.RADIOSELECTKIND, Constants.RADIOAGE);
                startActivityForResult(intent, Constants.RADIOAGE);
                break;
            case R.id.rl_hobby:
                intent.putExtra(Constants.RADIOSELECEDTINDEX, selectedHobbyIndex);
                intent.putExtra(Constants.RADIOSELECTKIND, Constants.RADIOHOBBY);
                startActivityForResult(intent, Constants.RADIOHOBBY);
                break;
            case R.id.near_save:
                if (mSelectPath.size() == 0) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_image));
                    return;
                }
                if (selectedAgeIndex < 0) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_age));
                    return;
                }
                if (selectedHobbyIndex < 0) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_hobby));
                    return;
                }
                message = etMessage.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_message));
                    return;
                }

                final UploadManager uploadManager = new UploadManager();
                showProgressDialog();
                ApiUtil.getQiniuToken(getApplicationContext(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        final String key = "items/" + System.currentTimeMillis() + ".jpg";
                        String token = response.optString("token");
                        picture.append(QINIU_PREFIX + key);
                        uploadManager.put(mSelectPath.get(0), key, token,
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

                break;
        }
    }

    private OnAllComplete onAllComplete = new OnAllComplete() {
        @Override
        public void allComplete(boolean result) {
            if (!result) {
                closeProgressDialog();
                return;
            }

            ApiUtil.hobbyCreated(getApplicationContext(), picture.toString(), selectedSexIndex, selectedAgeIndex, selectedHobbyId, message, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    closeProgressDialog();
                    if (response.optInt("flag") == 1) {
                        Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    closeProgressDialog();
                    Toast.makeText(getApplicationContext(), "发布失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (mSelectPath.size() == 1) {
                    Picasso.with(getApplicationContext()).load(new File(mSelectPath.get(0))).resize(140, 140).centerCrop().into(ivPhone);
                }
            }
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.RADIOSEX:
                    selectedSexIndex = data.getIntExtra(Constants.RADIOSELECEDTINDEX, 0);
                    tvSex.setText(Constants.SEXITEMS.get(selectedSexIndex));
                    break;
                case Constants.RADIOAGE:
                    selectedAgeIndex = data.getIntExtra(Constants.RADIOSELECEDTINDEX, 0);
                    tvAge.setText(Constants.AGEITEMS.get(selectedAgeIndex));
                    break;
                case Constants.RADIOHOBBY:
                    selectedHobbyIndex = data.getIntExtra(Constants.RADIOSELECEDTINDEX, 0);
                    KindBean hobby = Paper.book().read(Constants.HobbyListName, new ArrayList<KindBean>()).get(selectedHobbyIndex);
                    selectedHobbyId = hobby.getId();
                    tvHobby.setText(hobby.getName());
                    break;
            }
        }
    }
}
