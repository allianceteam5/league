package com.league.activity.near;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.activity.ShowBigImgActivity;
import com.league.adapter.ImgGridAdapter;
import com.league.adapter.RecommendationCommentAdapter;
import com.league.bean.CommentBean;
import com.league.bean.RecommendationInfoBean;
import com.league.utils.Constants;
import com.league.utils.IContants;
import com.league.utils.ToastUtils;
import com.league.utils.Utils;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.league.widget.NoScrollListView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class RecommendationInfoActivity extends BaseActivity implements View.OnClickListener,IContants {

    @Bind(R.id.near_back)
    ImageButton nearBack;
    @Bind(R.id.near_title)
    TextView nearCentertitle;
    @Bind(R.id.near_title_right)
    ImageView nearTiRight;
    @Bind(R.id.near_right)
    ImageButton nearRight;
    @Bind(R.id.near_userthumb)
    CircleImageView nearUserthumb;
    @Bind(R.id.near_username)
    TextView nearUsername;
    @Bind(R.id.lasttime)
    TextView lasttime;
    @Bind(R.id.command_type)
    TextView commandType;
    @Bind(R.id.command_location)
    TextView commandLocation;
    @Bind(R.id.command_tel)
    TextView commandTel;
    @Bind(R.id.near_reason)
    TextView nearReason;
    @Bind(R.id.comment_num)
    TextView commentNum;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.bottom)
    LinearLayout bottom;
    @Bind(R.id.gridview)
    GridView gridview;
    ImgGridAdapter imgGridAdapter;
    RecommendationInfoBean recommendationInfo;
    List<String> imgList;
    List<CommentBean> commentList;
    RecommendationCommentAdapter recommendationCommentAdapter;
    int recommendationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendationinfo);
        ButterKnife.bind(this);

        nearBack.setOnClickListener(this);
        nearTiRight.setVisibility(View.GONE);
        nearCentertitle.setText("详细信息");
        nearRight.setVisibility(View.GONE);
        bottom.setOnClickListener(this);

        recommendationInfo = Paper.book().read(Constants.SingleInfoName,new RecommendationInfoBean());
        recommendationId = recommendationInfo.getId();
        initView();
    }

    private void initView() {
        nearUsername.setText(recommendationInfo.getNickname());
        if (!TextUtils.isEmpty(recommendationInfo.getThumb()))
            Picasso.with(getApplicationContext()).load(recommendationInfo.getThumb()).resize(60,60).centerCrop().into(nearUserthumb);
        lasttime.setText(Utils.generateStringByTime(recommendationInfo.getCreated_at()));
        commandType.setText(recommendationInfo.getKind());
        commandLocation.setText(recommendationInfo.getLocation());
        commandTel.setText(recommendationInfo.getSellerphone());
        nearReason.setText(recommendationInfo.getReason());

        imgList = recommendationInfo.getPictureList();
        imgGridAdapter = new ImgGridAdapter(getApplicationContext(), imgList);
        if (imgList == null)
            gridview.setVisibility(View.GONE);
        else
            gridview.setAdapter(imgGridAdapter);

		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(getApplicationContext(), ShowBigImgActivity.class);
					intent.putStringArrayListExtra(PARAMS_IMG_LIST, new ArrayList<String>(imgList));
					intent.putExtra(PARAMS_INDEX, position);
					startActivity(intent);
			}
		});

        commentList = recommendationInfo.getComments();
        recommendationCommentAdapter = new RecommendationCommentAdapter(getApplicationContext(),commentList);
        commentNum.setText(String.valueOf(commentList.size()));
        listview.setAdapter(recommendationCommentAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            case R.id.bottom:
                Intent intent = new Intent(RecommendationInfoActivity.this, CommentActivity.class);
                intent.putExtra(COMMENT_ID, recommendationInfo.getId());
                startActivityForResult(intent, ONE);
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
                    ApiUtil.recommendationsSearch(getApplicationContext(), false, 0, "", recommendationId, 1, new BaseJsonHttpResponseHandler<RecommendationInfoBean>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, RecommendationInfoBean response) {
                            commentList = response.getComments();
                            commentNum.setText(String.valueOf(commentList.size()));
                            recommendationCommentAdapter.setData(commentList);
                            recommendationCommentAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, RecommendationInfoBean errorResponse) {
                            ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_internet));
                        }

                        @Override
                        protected RecommendationInfoBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            Log.d("response", rawJsonData);
                            return new ObjectMapper().readValue(rawJsonData, new TypeReference<RecommendationInfoBean>() {
                            });
                        }
                    });
                    break;
            }
        }
    }
}
