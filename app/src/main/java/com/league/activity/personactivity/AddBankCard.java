package com.league.activity.personactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.bean.SucessBean;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddBankCard extends BaseActivity implements View.OnClickListener{

    private ImageView back2, titleright, right1, right2;
    private TextView title;
    @Bind(R.id.inputbankid)
    EditText mInputBankID;
    @Bind(R.id.inputname)
    TextView mInputName;
    @Bind(R.id.inputpersonid)
    TextView mInputPersonID;
    @Bind(R.id.inputphone)
    EditText mInputPhone;
    @Bind(R.id.openarea)
    EditText mInputArea;
    @Bind(R.id.next)
    Button mNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);
        ButterKnife.bind(this);
        initView();

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
        title.setText("添加银行卡");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                if(TextUtils.isEmpty(mInputBankID.getText())|| TextUtils.isEmpty(mInputPhone.getText())||TextUtils.isEmpty(mInputArea.getText())){
                    ToastUtils.showShortToast(AddBankCard.this,"请输入完整信息");
                }else{
                    showProgressDialog();
                    ApiUtil.addBankCard(AddBankCard.this,mInputBankID.getText().toString(),mInputName.getText().toString(),
                            mInputPersonID.getText().toString(),mInputPhone.getText().toString(),mInputArea.getText().toString(),
                            new BaseJsonHttpResponseHandler<SucessBean>(){
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                    closeProgressDialog();
                                    if(response.getFlag().equals("1")){
                                        onBackPressed();
                                        finish();

                                    }else{
                                        ToastUtils.showShortToast(AddBankCard.this,"添加失败了");
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                    closeProgressDialog();
                                    ToastUtils.showShortToast(AddBankCard.this,"添加失败");
                                }

                                @Override
                                protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                    return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                    });
                                }
                            });
                }
                break;
        }
    }
}
