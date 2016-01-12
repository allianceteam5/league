package com.league.activity.near;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.league.activity.ReportActivity;
import com.league.bean.JobInfoBean;
import com.league.utils.ActivityUtils;
import com.league.utils.Constants;
import com.league.utils.DateFormatUtils;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

/**
 * @author liugang
 * @date 2015年9月26日
 */
public class JobInfoActivity extends Activity implements OnClickListener {
    @Bind(R.id.near_back)
    ImageView back;
    @Bind(R.id.near_title_right)
    ImageView titleright;
    @Bind(R.id.near_right)
    ImageView right1;
    @Bind(R.id.near_right_item)
    ImageView right2;
    @Bind(R.id.near_title)
    TextView title;
    @Bind(R.id.addfriend)
    Button addFriend;
    @Bind(R.id.near_userthumb)
    ImageView ivThumb;
    @Bind(R.id.title)
    TextView tvTitle;
    @Bind(R.id.near_username)
    TextView tvNickname;
    @Bind(R.id.jobtype)
    TextView tvJobproperty;
    @Bind(R.id.profession)
    TextView tvProfession;
    @Bind(R.id.education)
    TextView tvDegree;
    @Bind(R.id.jobtime)
    TextView tvWorktime;
    @Bind(R.id.connection)
    TextView tvPhone;
    @Bind(R.id.current)
    TextView tvStatus;
    @Bind(R.id.leavemessage)
    TextView tvIntro;
    @Bind(R.id.near_report)
    TextView tvReport;

    JobInfoBean jobinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobinfo);
        ButterKnife.bind(this);
        jobinfo = Paper.book().read(Constants.SingleInfoName, new JobInfoBean());
        initView();
    }

    private void initView() {
        back.setOnClickListener(this);
        titleright.setVisibility(View.GONE);
        title.setText("求职详情");
        right1.setVisibility(View.GONE);
        right2.setVisibility(View.GONE);
        addFriend.setOnClickListener(this);
        if (!TextUtils.isEmpty(jobinfo.getThumb()))
            Picasso.with(getApplicationContext()).load(jobinfo.getThumb()).resize(120, 120).centerCrop().into(ivThumb);
        else
            Picasso.with(getApplicationContext()).load(R.drawable.example).into(ivThumb);
        tvNickname.setText(jobinfo.getNickname());
        tvTitle.setText(jobinfo.getTitle());
        tvJobproperty.setText(jobinfo.getJobPropertyName());
        tvNickname.setText(jobinfo.getNickname());
        tvProfession.setText(jobinfo.getProfession());
        tvDegree.setText(Constants.DEGREEITEMS.get(jobinfo.getDegree()));
        tvWorktime.setText(DateFormatUtils.TimeStamp2Date(jobinfo.getWorktime()));
        tvStatus.setText(jobinfo.getStatus());
        tvPhone.setText(jobinfo.getHerphone());
        tvIntro.setText(jobinfo.getIntro());
        tvReport.setVisibility(View.VISIBLE);
        tvReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                break;
            case R.id.addfriend:
                if (addFriend.getText().equals("已申请")) {
                    Toast.makeText(getApplicationContext(), "已申请", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    EMContactManager.getInstance().addContact(jobinfo.getUserid(), "请添加我为好友吧");
                } catch (EaseMobException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "已申请", Toast.LENGTH_LONG).show();
                addFriend.setText("已申请");
                break;
            case R.id.near_report:
                ActivityUtils.start_Activity(this, ReportActivity.class);
                break;
        }

    }
}
