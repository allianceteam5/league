package com.league.activity.treasure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.LatestAnnouncedAdapter;
import com.league.adapter.OneyuanGrabAdapter;
import com.league.bean.AnnouncedTheLatestBean;
import com.league.bean.OneYuanBean;
import com.league.bean.TenYuanGrabBean;
import com.league.utils.Constants;
import com.league.utils.api.ApiUtil;
import com.league.view.MyGridView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class OneYuan extends BaseActivity implements View.OnClickListener {

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private MyGridView gridView, latestGridView;

    private ImageView tenImage1, tenImage2, tenImage3;
    private TextView money1, money2, money3;
    private TextView txtProgress1, txtProgress2, txtProgress3;
    private ProgressBar progressbar1, progressbar2, progressbar3;
    private List<TenYuanGrabBean> listTenYuanGrab = new ArrayList<TenYuanGrabBean>();
    private List<AnnouncedTheLatestBean> announcedList = new ArrayList<>();

    private List<OneYuanBean> list = new ArrayList<OneYuanBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_yuan);
        showProgressDialog();
        initView();
        initData();
        title.setFocusable(true);
        title.setFocusableInTouchMode(true);
        title.requestFocus();
        title.requestFocusFromTouch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        titleright = (ImageView) findViewById(R.id.near_title_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("一元夺宝");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        gridView = (MyGridView) findViewById(R.id.gridview);
        latestGridView = (MyGridView) findViewById(R.id.gridview_latest);
        tenImage1 = (ImageView) findViewById(R.id.ten_image1);
        tenImage2 = (ImageView) findViewById(R.id.ten_image2);
        tenImage3 = (ImageView) findViewById(R.id.ten_image3);
        money1 = (TextView) findViewById(R.id.money1);
        money2 = (TextView) findViewById(R.id.money2);
        money3 = (TextView) findViewById(R.id.money3);
        txtProgress1 = (TextView) findViewById(R.id.txt_progress1);
        txtProgress2 = (TextView) findViewById(R.id.txt_progress2);
        txtProgress3 = (TextView) findViewById(R.id.txt_progress3);
        progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
        progressbar1.setProgress(50);
        progressbar2 = (ProgressBar) findViewById(R.id.progressbar2);
        progressbar3 = (ProgressBar) findViewById(R.id.progressbar3);


    }

    void initData() {
        ApiUtil.grabCommoditiesSearch(getApplication(), 0, 1, new BaseJsonHttpResponseHandler<ArrayList<OneYuanBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<OneYuanBean> response) {

                if (response.size() > 6) {
                    for (int i = 0; i < 6; i++) {
                        list.add(response.get(i));
                    }
                } else {
                    list.addAll(response);
                }
                gridView.setAdapter(new OneyuanGrabAdapter(getApplication(), list));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(OneYuan.this, OneYuanGrabItem.class);
                        intent.putExtra("id", list.get(position).getId());
                        startActivity(intent);
                    }
                });
                closeProgressDialog();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<OneYuanBean> errorResponse) {
                closeProgressDialog();
            }

            @Override
            protected ArrayList<OneYuanBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<OneYuanBean>>() {
                });
            }
        });
        ApiUtil.grabcornsGetthree(getApplication(), new BaseJsonHttpResponseHandler<ArrayList<TenYuanGrabBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<TenYuanGrabBean> response) {

                listTenYuanGrab.addAll(response);
                Paper.book().write(Constants.TenYuanThree, listTenYuanGrab);
                updateTenYuan();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<TenYuanGrabBean> errorResponse) {
                listTenYuanGrab = Paper.book().read(Constants.TenYuanThree);
                if (listTenYuanGrab != null && listTenYuanGrab.size() == 3)
                    updateTenYuan();
            }

            @Override
            protected ArrayList<TenYuanGrabBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<TenYuanGrabBean>>() {
                });
            }
        });
        //新接口获取即将揭晓
        ApiUtil.getTheLatest(getApplication(),1, new BaseJsonHttpResponseHandler<ArrayList<AnnouncedTheLatestBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<AnnouncedTheLatestBean> response) {
                announcedList.clear();
                if(response.size()<3){
                    announcedList.addAll(response);
                }else{
                    for (int i = 0; i < 3; i++) {
                        announcedList.add(response.get(i));
                    }
                }

                latestGridView.setAdapter(new LatestAnnouncedAdapter(announcedList, getApplication()));
                latestGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    Intent intent;
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(announcedList.get(position).getTbk().equals("1")){
                            intent = new Intent(OneYuan.this, OneYuanGrabItem.class);
                        }else{
                            intent=new Intent(OneYuan.this,TenYuanGrabItem.class);
                        }
                        intent.putExtra("id", announcedList.get(position).getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<AnnouncedTheLatestBean> errorResponse) {

            }

            @Override
            protected ArrayList<AnnouncedTheLatestBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<AnnouncedTheLatestBean>>() {
                });
            }
        });
        //type=1时是获取最新揭晓的信息
//        ApiUtil.getGrabLatestAnnounced(getApplication(), new BaseJsonHttpResponseHandler<ArrayList<OneYuanBean>>() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<OneYuanBean> response) {
//                if(response.size()<3){
//                    announcedList.addAll(response);
//                }else{
//                    for (int i = 0; i < 3; i++) {
//                        announcedList.add(response.get(i));
//                    }
//                }
//
//                latestGridView.setAdapter(new LatestAnnouncedAdapter(announcedList, getApplication()));
//                latestGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent(OneYuan.this, OneYuanGrabItem.class);
//                        intent.putExtra("id", announcedList.get(position).getId());
//                        startActivity(intent);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<OneYuanBean> errorResponse) {
//
//            }
//
//            @Override
//            protected ArrayList<OneYuanBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                JSONObject jsonObject = new JSONObject(rawJsonData);
//                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<OneYuanBean>>() {
//                });
//            }
//        });

    }


    private void updateTenYuan() {
        Picasso.with(getApplication()).load(listTenYuanGrab.get(0).getPicture()).into(tenImage1);
        Picasso.with(getApplication()).load(listTenYuanGrab.get(1).getPicture()).into(tenImage2);
        Picasso.with(getApplication()).load(listTenYuanGrab.get(2).getPicture()).into(tenImage3);
        money1.setText(listTenYuanGrab.get(0).getTitle());
        money2.setText(listTenYuanGrab.get(1).getTitle());
        money3.setText(listTenYuanGrab.get(2).getTitle());
        float need1 = Float.valueOf(listTenYuanGrab.get(0).getNeeded());
        float remain1 = Float.valueOf(listTenYuanGrab.get(0).getRemain());
        txtProgress1.setText((int)((need1 - remain1) / need1 * 100) + "%");
        float need2 = Float.valueOf(listTenYuanGrab.get(1).getNeeded());
        float remain2 = Float.valueOf(listTenYuanGrab.get(1).getRemain());
        txtProgress2.setText((int)((need2 - remain2) / need2 * 100) + "%");
        float need3 = Float.valueOf(listTenYuanGrab.get(2).getNeeded());
        float remain3 = Float.valueOf(listTenYuanGrab.get(2).getRemain());
        txtProgress3.setText((int)((need3 - remain3) / need3 * 100) + "%");
        progressbar1.setProgress((int) ((need1 - remain1) / need1 * 100));
        progressbar2.setProgress((int) ((need2 - remain2) / need2 * 100));
        progressbar3.setProgress((int) ((need3 - remain3) / need3 * 100));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.latest_more:
                Intent latest = new Intent(OneYuan.this, LatestAnnounce.class);
                startActivity(latest);
                break;
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            case R.id.tengrb_more:
            case R.id.One_tengrab:
                Intent intent1 = new Intent(OneYuan.this, TenyuanGrab.class);
                startActivity(intent1);
                break;
            case R.id.last_more:
            case R.id.One_Yuan:
                Intent intent2 = new Intent(OneYuan.this, OneYuanGrab.class);
                startActivity(intent2);
                break;
            case R.id.One_record:
                Intent record = new Intent(getApplication(), GrabRecord.class);
                startActivity(record);
                break;
            case R.id.One_question:

                Toast.makeText(this, "que", Toast.LENGTH_LONG).show();
                break;
            case R.id.ten1:
                Intent intent = new Intent(getApplication(), TenYuanGrabItem.class);
                intent.putExtra("id", listTenYuanGrab.get(0).getId());
                startActivity(intent);
                break;
            case R.id.ten2:
                Intent ten2 = new Intent(getApplication(), TenYuanGrabItem.class);
                ten2.putExtra("id", listTenYuanGrab.get(1).getId());
                startActivity(ten2);
                break;
            case R.id.ten3:
                Intent ten3 = new Intent(getApplication(), TenYuanGrabItem.class);
                ten3.putExtra("id", listTenYuanGrab.get(2).getId());
                startActivity(ten3);
                break;

        }
    }


}
