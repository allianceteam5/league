package com.league.activity.near;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.activity.ReportActivity;
import com.league.activity.UserHomePageActivity;
import com.league.bean.HobbyInfoBean;
import com.league.utils.ActivityUtils;
import com.league.utils.Constants;
import com.league.utils.IContants;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.message.BasicNameValuePair;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class HobbyInfoActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.near_back)
    ImageButton nearBack;
    @Bind(R.id.near_title)
    TextView nearCentertitle;
    @Bind(R.id.near_title_right)
    ImageView nearTiRight;
    @Bind(R.id.near_right)
    ImageButton nearRight;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.sex)
    TextView sex;
    @Bind(R.id.age)
    TextView age;
    @Bind(R.id.hobby)
    TextView hobby;
    @Bind(R.id.message)
    TextView message;
    @Bind(R.id.near_report)
    TextView tvReport;
    @Bind(R.id.addfriend)
    Button addFriend;

    private HobbyInfoBean hobbyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobbyinfo);
        ButterKnife.bind(this);

        nearBack.setOnClickListener(this);
        nearTiRight.setVisibility(View.GONE);
        nearCentertitle.setText("交友详情");
        nearRight.setVisibility(View.GONE);
        hobbyInfo = Paper.book().read(Constants.SingleInfoName, new HobbyInfoBean());
        addFriend.setOnClickListener(this);
        initView();
    }

    void initView() {
        if (!TextUtils.isEmpty(hobbyInfo.getThumb()))
            Picasso.with(getApplicationContext()).load(hobbyInfo.getPicture()).resize(140, 140).centerCrop().into(img);
        else
            Picasso.with(getApplicationContext()).load(R.drawable.example).into(img);
        sex.setText(Constants.SEXITEMS.get(hobbyInfo.getSex()));
        if (hobbyInfo.getAge() > 6)
            age.setText(Constants.AGEITEMS.get(3));
        else
            age.setText(Constants.AGEITEMS.get(hobbyInfo.getAge()));
        hobby.setText(hobbyInfo.getHobby());
        message.setText(hobbyInfo.getContent());
        tvReport.setVisibility(View.VISIBLE);
        tvReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            case R.id.near_report:
                ActivityUtils.start_Activity(this, ReportActivity.class);
                break;
            case R.id.addfriend:
                ActivityUtils.start_Activity(HobbyInfoActivity.this, UserHomePageActivity.class, new BasicNameValuePair(IContants.PHONE, hobbyInfo.getPhone()));
                break;
        }
    }
}
