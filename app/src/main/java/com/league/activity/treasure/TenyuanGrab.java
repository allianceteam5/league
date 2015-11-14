package com.league.activity.treasure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.adapter.TenYuanGrabAdapter;
import com.league.bean.TenYuanGrabBean;
import com.league.utils.Constants;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class TenyuanGrab extends Activity {

    private ImageView back, titleright, right1, right2;
    private TextView title;
    private GridView gridView;
    private List<TenYuanGrabBean> list = new ArrayList<TenYuanGrabBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenyuan_grid);
        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        titleright = (ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_centertitle);
        title.setText("10元夺宝");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        gridView = (GridView) findViewById(R.id.gridview);
    }

    private void initData() {
        ApiUtil.grabcornsSearch(getApplication(), 0, 1, new BaseJsonHttpResponseHandler<ArrayList<TenYuanGrabBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<TenYuanGrabBean> response) {
                list.addAll(response);
                Paper.book().write(Constants.TenYuanMore, list);
                updateTenYuan();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<TenYuanGrabBean> errorResponse) {
                list = Paper.book().read(Constants.TenYuanMore);
                if (list != null)
                    updateTenYuan();
            }

            @Override
            protected ArrayList<TenYuanGrabBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject=new JSONObject(rawJsonData);

                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<TenYuanGrabBean>>() {
                });
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TenyuanGrab.this, TenYuanGrabItem.class);
                startActivity(intent);
            }
        });

    }
    void updateTenYuan(){
        gridView.setAdapter(new TenYuanGrabAdapter(getApplication(), list));
    }
}
