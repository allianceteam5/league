package com.league.activity.treasure;

import android.app.Activity;
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
import com.league.adapter.OneyuanGridAdapter;
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

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class OneYuan extends Activity implements View.OnClickListener {

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private MyGridView gridView;

    private ImageView tenImage1, tenImage2, tenImage3;
    private TextView money1, money2, money3;
    private TextView txtProgress1, txtProgress2, txtProgress3;
    private ProgressBar progressbar1, progressbar2, progressbar3;
    private List<TenYuanGrabBean> listTenYuanGrab = new ArrayList<TenYuanGrabBean>();
    private List<AnnouncedTheLatestBean> listAnnounced = new ArrayList<AnnouncedTheLatestBean>();
    private List<OneYuanBean> listGrid = new ArrayList<OneYuanBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_yuan);
        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        titleright = (ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_centertitle);
        title.setText("一元夺宝");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        gridView = (MyGridView) findViewById(R.id.gridview);
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

        for (int i = 0; i < 5; i++) {
            OneYuanBean oyb = new OneYuanBean();
//            oyb.setmPeriod(i);
//            oyb.setmName("name" + i);
//            oyb.setmTotalPeo((long) i);i
            listGrid.add(oyb);
        }
        gridView.setAdapter(new OneyuanGridAdapter(getApplication(), listGrid));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OneYuan.this, OneYuanGrabItem.class);
                startActivity(intent);
            }
        });
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
        txtProgress1.setText((int) ((need1 - remain1) / need1) * 100 + "%");
        float need2 = Float.valueOf(listTenYuanGrab.get(1).getNeeded());
        float remain2 = Float.valueOf(listTenYuanGrab.get(1).getRemain());
        txtProgress2.setText((int) ((need2 - remain2) / need1) * 100 + "%");
        float need3 = Float.valueOf(listTenYuanGrab.get(2).getNeeded());
        float remain3 = Float.valueOf(listTenYuanGrab.get(2).getRemain());
        txtProgress3.setText((int) ((need3 - remain3) / need1) * 100 + "%");
        progressbar1.setProgress((int) ((need1 - remain1) / need1) * 100);
        progressbar2.setProgress((int) ((need2 - remain2) / need1) * 100);
        progressbar3.setProgress((int) ((need3 - remain3) / need1) * 100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            case R.id.tengrb_more:
            case R.id.One_tengrab:
                Intent intent1 = new Intent(OneYuan.this, TenyuanGrid.class);
                startActivity(intent1);
                break;
            case R.id.last_more:
            case R.id.One_Yuan:
                Intent intent2 = new Intent(OneYuan.this, OneYuanGrab.class);
                startActivity(intent2);
                break;
            case R.id.One_record:
                Toast.makeText(this, "rec", Toast.LENGTH_LONG).show();
                break;
            case R.id.One_question:
                Toast.makeText(this, "que", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
