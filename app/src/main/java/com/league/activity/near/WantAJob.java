package com.league.activity.near;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.league.bean.Kind;
import com.league.utils.Constants;
import com.league.utils.DateFormatUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import io.paperdb.Paper;

public  class WantAJob extends Activity implements View.OnClickListener {

    private ImageView back, title_right, right, jobfull, jobpart;
    private TextView title, save, tvDegree, tvWorkTime,tvProfession;
    private RelativeLayout rl_degree, rl_time, rl_profession;
    private EditText etTitle, etStatus, etContent, etPhone;
    private int selectedJobIndex = 0;
    private int selectedDegreeIndex = -1;
    private int selectedProfessionIndex = -1;
    private int selectedProfessionId = -1;
    private String worktime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wangjob);
        init();
    }

    void init() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        title_right = (ImageView) findViewById(R.id.near_ti_right);
        title_right.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_centertitle);
        title.setText("我要求职");
        right = (ImageView) findViewById(R.id.near_right);
        right.setVisibility(View.GONE);
        save = (TextView) findViewById(R.id.near_save);
        save.setVisibility(View.VISIBLE);
        save.setOnClickListener(this);
        jobfull = (ImageView) findViewById(R.id.img_full);
        jobpart = (ImageView) findViewById(R.id.img_part);
        rl_degree = (RelativeLayout) findViewById(R.id.education);
        rl_degree.setOnClickListener(this);
        rl_time = (RelativeLayout) findViewById(R.id.taketime);
        rl_time.setOnClickListener(this);
        rl_profession = (RelativeLayout) findViewById(R.id.profession);
        rl_profession.setOnClickListener(this);
        etTitle = (EditText) findViewById(R.id.et_title);
        tvDegree = (TextView) findViewById(R.id.tv_degree);
        tvWorkTime = (TextView) findViewById(R.id.worktime);
        tvProfession = (TextView) findViewById(R.id.tv_profession);
        tvWorkTime.setText(DateFormatUtils.NowTimeStamp2Date());
        etStatus = (EditText) findViewById(R.id.etStatus);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etContent = (EditText) findViewById(R.id.img_leave);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            case R.id.fulljob:
                jobfull.setVisibility(View.VISIBLE);
                jobpart.setVisibility(View.INVISIBLE);
                selectedJobIndex = 0;
                break;
            case R.id.parttimejob:
                jobfull.setVisibility(View.INVISIBLE);
                jobpart.setVisibility(View.VISIBLE);
                selectedJobIndex = 1;
                break;
            case R.id.taketime:
                final AlertDialog dialog = new AlertDialog.Builder(WantAJob.this).create();
                dialog.show();
                DatePicker picker = new DatePicker(WantAJob.this);
                Calendar calendar = Calendar.getInstance();
                picker.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
                picker.setMode(DPMode.SINGLE);
                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(String date) {
                        worktime = date;
                        tvWorkTime.setText(worktime);
                        dialog.dismiss();
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(picker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);
                break;
            case R.id.education:
                Intent intent = new Intent(WantAJob.this, RadioSelectActivity.class);
                intent.putExtra(Constants.RADIOSELECEDTINDEX, selectedDegreeIndex);
                intent.putExtra(Constants.RADIOSELECTKIND, Constants.RADIODEDREE);
                startActivityForResult(intent, Constants.RADIODEDREE);
                break;
            //选择行业
            case R.id.profession:
                Intent intent1 = new Intent(WantAJob.this, RadioSelectActivity.class);
                intent1.putExtra(Constants.RADIOSELECEDTINDEX, selectedProfessionIndex);
                intent1.putExtra(Constants.RADIOSELECTKIND, Constants.RADIOPROFESSION);
                startActivityForResult(intent1, Constants.RADIOPROFESSION);
                break;
            //发布
            case R.id.near_save:
                String title = etTitle.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_title));
                    etTitle.requestFocus();
                    return;
                }

                if (selectedProfessionId < 0) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_profession));
                    return;
                }

                if (selectedDegreeIndex < 0) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_degree));
                    return;
                }

                if (TextUtils.isEmpty(worktime)) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.waring_time));
                    return;
                }

                String status = etStatus.getText().toString();
                if (TextUtils.isEmpty(status)) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_status));
                    etStatus.requestFocus();
                    return;
                }

                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_phone));
                    etPhone.requestFocus();
                    return;
                }

                String content = etContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showLongToast(getApplicationContext(), getString(R.string.warning_intro));
                    etContent.requestFocus();
                    return;
                }

                ApiUtil.applyJobCreated(getApplicationContext(), phone, selectedJobIndex, title, selectedDegreeIndex, DateFormatUtils.Date2TimeStamp(worktime), status, 0, content, selectedProfessionId, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        int flag = response.optInt("flag");
                        if (flag == 1)
                            ToastUtils.showLongToast(getApplicationContext(), getString(R.string.publish_success));
                        else
                            ToastUtils.showLongToast(getApplicationContext(), getString(R.string.publish_fail));
                        WantAJob.this.finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        ToastUtils.showLongToast(getApplicationContext(), getString(R.string.publish_fail));
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.RADIODEDREE:
                    selectedDegreeIndex = data.getIntExtra(Constants.RADIOSELECEDTINDEX, 0);
                    tvDegree.setText(Constants.DEGREEITEMS.get(selectedDegreeIndex));
                    break;
                case Constants.RADIOPROFESSION:
                    selectedProfessionIndex = data.getIntExtra(Constants.RADIOSELECEDTINDEX, 0);
                    Kind profession = Paper.book().read(Constants.ProfessinListName, new ArrayList<Kind>()).get(selectedProfessionIndex);
                    selectedProfessionId = profession.getId();
                    tvProfession.setText(profession.getName());
                    break;
            }
        }
    }
}

