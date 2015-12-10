package com.league.activity.postbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.league.activity.BaseActivity;
import com.league.activity.ShowBigImgActivity;
import com.league.adapter.ImgGridWithPickImgAdapter;
import com.league.interf.OnAllComplete;
import com.league.utils.IContants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.league.widget.NoScrollGridView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mine.league.R;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class PostbarPublishActivity extends BaseActivity implements View.OnClickListener, IContants {
    @Bind(R.id.near_back)
    ImageButton nearBack;
    @Bind(R.id.near_title)
    TextView nearCentertitle;
    @Bind(R.id.near_title_right)
    ImageView nearTiRight;
    @Bind(R.id.near_right)
    ImageButton nearRight;
    @Bind(R.id.near_save)
    TextView nearSave;
    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.gridview)
    NoScrollGridView gridview;

    private List<String> imgList;
    private ImgGridWithPickImgAdapter adapter;
    private StringBuilder imgUrls = new StringBuilder();
    private int sucNum = 0;
    private String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postbar_publish);
        ButterKnife.bind(this);

        nearBack.setOnClickListener(this);
        nearTiRight.setVisibility(View.GONE);
        nearCentertitle.setText("发表话题");
        nearRight.setVisibility(View.GONE);
        nearSave.setVisibility(View.VISIBLE);
        nearSave.setText("发送");
        nearSave.setOnClickListener(this);

        initGridView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                imgList.remove(String.valueOf(R.drawable.upload_default));
                imgList.addAll(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT));
                imgList.add(String.valueOf(R.drawable.upload_default));
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            case R.id.near_save:
                publish();
                break;
        }
    }

    private void initGridView() {
        imgList = new ArrayList<String>();
        imgList.add(String.valueOf(R.drawable.upload_default));
        adapter = new ImgGridWithPickImgAdapter(this, imgList);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == imgList.size() - 1) {
                    pickImage();
                } else {
                    ArrayList<String> imgs = new ArrayList<String>(imgList);
                    Intent intent = new Intent(PostbarPublishActivity.this, ShowBigImgActivity.class);
                    imgs.remove(imgList.size() - 1);
                    intent.putStringArrayListExtra(PARAMS_IMG_LIST, imgs);
                    intent.putExtra(PARAMS_INDEX, position);
                    startActivity(intent);
                }
            }
        });
    }

    private void pickImage() {
        Intent intent = new Intent(PostbarPublishActivity.this, MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 10 - imgList.size());
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void publish() {
        title = etTitle.getText().toString();
        content = etContent.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_title));
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_content));
            return;
        }
        final UploadManager uploadManager = new UploadManager();
        showProgressDialog();
        final int size = imgList.size() - 1;
        for (int i = 0; i < size; i++) {
            final int index = i;
            ApiUtil.getQiniuToken(getApplicationContext(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    final String key = "items/" + System.currentTimeMillis() + ".jpg";
                    String token = response.optString("token");
                    imgUrls.append(QINIU_PREFIX + key);
                    imgUrls.append(" ");
                    uploadManager.put(imgList.get(index), key, token,
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                                    if (responseInfo.error == null) {
                                        Log.i("qiniu", key + "  success!!!");
                                        sucNum++;
                                        if (sucNum == imgList.size() - 1) {
                                            Log.i("qiniu", "allComplete");
                                            onAllComplete.allComplete(true);
                                        }
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
    }

    private OnAllComplete onAllComplete = new OnAllComplete() {
        @Override
        public void allComplete(boolean result) {
            if (!result) {
                closeProgressDialog();
                return;
            }

            String imgStr = imgUrls.toString();
            imgStr = imgStr.trim();
            ApiUtil.liaobaTbmessagesCreated(getApplicationContext(), title, content, imgStr, new JsonHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    closeProgressDialog();
                    Toast.makeText(getApplicationContext(), "发布失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    closeProgressDialog();
//                    sharePopWindow.showPopWindow();
//                    BusProvider.getBus().post(new NewDynamicEvent());
                    if (response.optInt("flag") == 1) {
                        Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
                        imgList.clear();
                        finish();
                    }
                }
            });
        }
    };
}
