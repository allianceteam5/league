package com.league.activity.near;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.HobbyInfoBean;
import com.league.utils.Constants;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class HobbyInfoActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.near_back)
    ImageButton nearBack;
    @Bind(R.id.near_centertitle)
    TextView nearCentertitle;
    @Bind(R.id.near_ti_right)
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
        initView();
    }

    void initView() {
        Picasso.with(getApplicationContext()).load(hobbyInfo.getPicture()).resize(140, 140).centerCrop().into(img);
        sex.setText(Constants.SEXITEMS.get(hobbyInfo.getSex()));
        age.setText(Constants.AGEITEMS.get(hobbyInfo.getAge()));
        hobby.setText(hobbyInfo.getHobby());
        message.setText(hobbyInfo.getContent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
        }
    }
}
