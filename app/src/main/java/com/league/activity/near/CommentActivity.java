package com.league.activity.near;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.activity.BaseActivity;
import com.league.utils.IContants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentActivity extends BaseActivity implements View.OnClickListener, IContants {
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
    @Bind(R.id.content)
    EditText etContent;
    String content;
    int recommendationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        nearBack.setOnClickListener(this);
        nearTiRight.setVisibility(View.GONE);
        nearCentertitle.setText("评论");
        nearRight.setVisibility(View.GONE);
        nearSave.setVisibility(View.VISIBLE);
        nearSave.setText("提交");
        nearSave.setOnClickListener(this);

        recommendationId = getIntent().getIntExtra(COMMENT_ID, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_save:
                content = etContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_comment));
                    return;
                }
                showProgressDialog();
                ApiUtil.recommendationCommentCreated(getApplicationContext(), recommendationId, content, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        closeProgressDialog();
                        ToastUtils.showShortToast(getApplicationContext(), "评价成功");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        closeProgressDialog();
                        ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_internet));
                    }
                });
                break;
        }
    }
}
