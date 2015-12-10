package com.league.activity.liaobaactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.league.activity.BaseActivity;
import com.league.bean.LiaoBaMessageBean;
import com.league.utils.IContants;
import com.league.widget.CircleImageView;
import com.league.widget.NoScrollGridView;
import com.league.widget.NoScrollListView;
import com.league.widget.RefreshLayout;
import com.mine.league.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;

public class TopicContent extends BaseActivity implements IContants {

    @Bind(R.id.near_back)
    ImageButton nearBack;
    @Bind(R.id.near_title)
    TextView nearTitle;
    @Bind(R.id.near_title_right)
    ImageView nearTitleRight;
    @Bind(R.id.near_right)
    ImageButton nearRight;
    @Bind(R.id.thumb)
    CircleImageView thumb;
    @Bind(R.id.username)
    TextView username;
    @Bind(R.id.pushtime)
    TextView pushtime;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.comment)
    TextView comment;
    @Bind(R.id.gridview)
    NoScrollGridView gridview;
    @Bind(R.id.linear1)
    LinearLayout linear1;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.comnum)
    TextView comnum;
    @Bind(R.id.dianzannum)
    TextView dianzannum;
    @Bind(R.id.dianzan)
    ImageView dianzan;
    @Bind(R.id.relative1)
    RelativeLayout relative1;
    @Bind(R.id.comment_listview)
    NoScrollListView commentListview;
    @Bind(R.id.swipe_layout)
    RefreshLayout swipeLayout;
    @Bind(R.id.bottom)
    LinearLayout bottom;
    private String tbmessageid;
    private LiaoBaMessageBean liaoBaMessageBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_content);
        ButterKnife.bind(this);
        tbmessageid = getIntent().getStringExtra(TBMESSAGEID);
        showProgressDialog();
        initView();
        getData();
    }

    private void initView() {
        nearBack.setVisibility(VISIBLE);
        nearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        nearTitleRight.setVisibility(View.GONE);
        nearTitle.setText("话题正文");
        nearRight.setVisibility(View.GONE);

    }

    private void getData(){
//        ApiUtil.liaobaTbmessagesMyList(getApplicationContext(), tbmessageid, new BaseJsonHttpResponseHandler<LiaoBaMessageBean>() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, LiaoBaMessageBean response) {
//                updateView();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, LiaoBaMessageBean errorResponse) {
//                closeProgressDialog();
//                Toast.makeText(getApplicationContext(), "网络不好", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            protected LiaoBaMessageBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                return new ObjectMapper().readValue(rawJsonData, new TypeReference<LiaoBaMessageBean>() {
//                });
//            }
//        });
    }

    private void updateView(){
//        if(!TextUtils.isEmpty(liaoBaMessageBean.getThumb()))
//            Picasso.with(getApplicationContext()).load(liaoBaMessageBean.getThumb()).resize(100,100).centerCrop().into(thumb);

    }
}
