package com.league.activity.postbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.activity.ShowBigImgActivity;
import com.league.adapter.ImgGridAdapter;
import com.league.adapter.LiaobaCommentAdapter;
import com.league.bean.LiaoBaMessageBean;
import com.league.bean.RecommendationInfoBean;
import com.league.bean.ReplysEntity;
import com.league.utils.IContants;
import com.league.utils.ToastUtils;
import com.league.utils.Utils;
import com.league.utils.api.ApiUtil;
import com.league.widget.RefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;

public class TopicContent extends BaseActivity implements IContants, View.OnClickListener {

    @Bind(R.id.near_back)
    ImageButton nearBack;
    @Bind(R.id.near_title)
    TextView nearTitle;
    @Bind(R.id.near_title_right)
    ImageView nearTitleRight;
    @Bind(R.id.near_right)
    ImageButton nearRight;
    @Bind(R.id.thumb)
    ImageView thumb;
    @Bind(R.id.ll_like)
    LinearLayout llLike;
    @Bind(R.id.ll_comment)
    LinearLayout llComment;
    @Bind(R.id.dianzan)
    ImageView ivDianzan;
    @Bind(R.id.dianzannum)
    TextView tvDianzannum;
    @Bind(R.id.username)
    TextView tvUsername;
    @Bind(R.id.pushtime)
    TextView tvPushtime;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.content)
    TextView tvContent;
    @Bind(R.id.commentnum)
    TextView tvCommentnum;
    @Bind(R.id.iv_like)
    ImageView ivLike;
    @Bind(R.id.gridview)
    GridView gridview;
    @Bind(R.id.comment_listview)
    ListView listview;
    @Bind(R.id.swipe_layout)
    RefreshLayout refreshLayout;

    private String tbmessageid;
    private LiaoBaMessageBean liaoBaMessageBean;
    private int currentPage = 1;
    private int sumPage;

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
        llLike.setOnClickListener(this);
        llComment.setOnClickListener(this);

        refreshLayout
                .setColorScheme(R.color.black,
                        R.color.blue, R.color.greenn,
                        R.color.red);

        // 加载监听器
        refreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentPage++;
                        if (currentPage < sumPage) {
                            loadData();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.loading_done), Toast.LENGTH_SHORT).show();
                        }
                        // 加载完后调用该方法
                        refreshLayout.setLoading(false);
                    }
                }, 1500);

            }
        });
    }

    private void loadData() {

    }

    private void getData() {
        ApiUtil.liaobaTbmessagesView(getApplicationContext(), tbmessageid, new BaseJsonHttpResponseHandler<LiaoBaMessageBean>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, LiaoBaMessageBean response) {
                liaoBaMessageBean = response;
                closeProgressDialog();
                updateView();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, LiaoBaMessageBean errorResponse) {
                closeProgressDialog();
                Toast.makeText(getApplicationContext(), "网络不好", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected LiaoBaMessageBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<LiaoBaMessageBean>() {
                });
            }
        });
    }

    private void updateView() {
        Picasso.with(getApplication()).load(liaoBaMessageBean.getThumb()).placeholder(R.drawable.default_avatar).resize(100, 100).centerCrop().into(thumb);
        tvUsername.setText(liaoBaMessageBean.getNickname());
        tvPushtime.setText(Utils.generateStringByTime(liaoBaMessageBean.getCreated_at()));
        tvTitle.setText(liaoBaMessageBean.getTitle());
        tvContent.setText(liaoBaMessageBean.getContent());
        tvCommentnum.setText(liaoBaMessageBean.getReplycount() + "");
        tvDianzannum.setText(liaoBaMessageBean.getLikecount() + "");
        if (liaoBaMessageBean.getIsliked() == 1) {
            ivDianzan.setImageResource(R.drawable.liaoba_like);
            ivLike.setImageResource(R.drawable.liaoba_like);
        }
        final List<String> imgList = liaoBaMessageBean.getPictureList();
        ImgGridAdapter adapter = new ImgGridAdapter(TopicContent.this, imgList);
        if (imgList == null)
            gridview.setVisibility(View.GONE);
        else
            gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> imgs = new ArrayList<>(imgList);
                Intent intent = new Intent(TopicContent.this, ShowBigImgActivity.class);
                intent.putStringArrayListExtra(PARAMS_IMG_LIST, imgs);
                intent.putExtra(PARAMS_INDEX, position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                TopicContent.this.startActivity(intent);
            }
        });
        List<ReplysEntity> replys = liaoBaMessageBean.getReplys();
        listview.setAdapter(new LiaobaCommentAdapter(TopicContent.this, replys));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_comment:
                Intent intent = new Intent(TopicContent.this, LiaobaCommentActivity.class);
                intent.putExtra(COMMENT_ID, liaoBaMessageBean.getId());
                startActivityForResult(intent, ONE);
                break;
            case R.id.ll_like:
                int type = 1 - liaoBaMessageBean.getIsliked();
                liaoBaMessageBean.setIsliked(type);
                if (type == 0) {
                    ApiUtil.liaobaCancellike(TopicContent.this, liaoBaMessageBean.getId(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            int flag = response.optInt("flag");
                            if (flag == 1) {
                                int num = liaoBaMessageBean.getLikecount();
                                num--;
                                liaoBaMessageBean.setLikecount(num);
                                ivDianzan.setImageResource(R.drawable.liaoba_dianzan);
                                ivLike.setImageResource(R.drawable.liaoba_dianzan);
                                tvDianzannum.setText(liaoBaMessageBean.getLikecount() + "");
                            } else {
                                ToastUtils.showShortToast(TopicContent.this, "网络不太好哦");
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            ToastUtils.showShortToast(TopicContent.this, "网络不太好哦");
                        }
                    });
                } else {
                    ApiUtil.liaobaLike(TopicContent.this, liaoBaMessageBean.getId(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            int flag = response.optInt("flag");
                            if (flag == 1) {
                                int num = liaoBaMessageBean.getLikecount();
                                num++;
                                liaoBaMessageBean.setLikecount(num);
                                ivDianzan.setImageResource(R.drawable.liaoba_like);
                                ivLike.setImageResource(R.drawable.liaoba_like);
                                tvDianzannum.setText(liaoBaMessageBean.getLikecount() + "");
                            } else {
                                ToastUtils.showShortToast(TopicContent.this, "网络不太好哦");
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            ToastUtils.showShortToast(TopicContent.this, "网络不太好哦");
                        }
                    });
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ONE:
                    //评论更新
                    getData();
                    break;
            }
        }
    }
}
