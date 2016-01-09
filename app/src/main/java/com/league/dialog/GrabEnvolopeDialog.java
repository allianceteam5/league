package com.league.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.bean.EnvelopeBean;
import com.league.utils.Constants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

public class GrabEnvolopeDialog extends BaseActivity implements View.OnClickListener {

    private ImageButton close;
    private TextView result;
    private Button share;
    private TextView num, numbite;
    private View viewGet, viewLoss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_envolope_dialog);
        showProgressDialog();
        initView();
        initData();
    }

    private void initView() {
        close = (ImageButton) findViewById(R.id.close);
        close.setOnClickListener(this);
        result = (TextView) findViewById(R.id.result);
        share = (Button) findViewById(R.id.share);
        share.setOnClickListener(this);
        num = (TextView) findViewById(R.id.num);
        numbite = (TextView) findViewById(R.id.numbyte);
        viewGet = findViewById(R.id.get);
        viewLoss = findViewById(R.id.loss);
    }

    private void initData() {
        ApiUtil.getGrabEnvelope(getApplicationContext(),new BaseJsonHttpResponseHandler<EnvelopeBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, EnvelopeBean response) {
                closeProgressDialog();
                if (response.getFlag() == 1) {
                    if (response.getCount() == 0) {
                        result.setText("很遗憾您没有抢到红包！");
                        viewLoss.setVisibility(View.VISIBLE);
                        viewGet.setVisibility(View.GONE);
                    } else {
                        if (response.getType() == 1) {
                            result.setText("恭喜您！");
                            viewLoss.setVisibility(View.GONE);
                            viewGet.setVisibility(View.VISIBLE);
                            num.setText("" + response.getCount());
                            numbite.setText("元现金");
                        }
                        if (response.getType() == 2) {
                            result.setText("恭喜您！");
                            viewLoss.setVisibility(View.GONE);
                            viewGet.setVisibility(View.VISIBLE);
                            num.setText("" + response.getCount());
                            numbite.setText("个夺宝币");
                        }
                    }
                } else if (response.getFlag() == 2) {
                    result.setText("今天红包已经抢完了");
                    viewLoss.setVisibility(View.VISIBLE);
                    viewGet.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, EnvelopeBean errorResponse) {
                closeProgressDialog();
                ToastUtils.showShortToast(getApplicationContext(), "网络连接失败，再试试");
            }

            @Override
            protected EnvelopeBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<EnvelopeBean>() {
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.share:
                ShareDialog shareDialog=new ShareDialog(GrabEnvolopeDialog.this,GrabEnvolopeDialog.this);
                shareDialog.show();
                break;

        }
    }
}
