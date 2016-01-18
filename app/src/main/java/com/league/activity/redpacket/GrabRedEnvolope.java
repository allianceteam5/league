package com.league.activity.redpacket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.RuleActivity;
import com.league.adapter.EnvelopeWinnerAdapter;
import com.league.bean.EnvelopWinBean;
import com.league.dialog.GrabEnvolopeDialog;
import com.league.utils.IContants;
import com.league.utils.StoreUtils;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GrabRedEnvolope extends Activity implements OnClickListener, IContants {


    private ImageView back, titleright, right1, right2;
    private TextView title, rules;
    private ListView listview;
    private Button ivGrabMoney;
    private EnvelopeWinnerAdapter adapter;
    private List<EnvelopWinBean> data = new ArrayList<>();
    private Timer autoScroll = new Timer();
    private int index = 0;
    private final Timer timer = new Timer();
    private TimerTask autoUpdateTask;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 要做的事情
            switch (msg.what) {
                case 1:
                    initData();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabredenvolope);
        initView();
        autoUpdateTask = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(autoUpdateTask, 500, 15000);
        autoScroll.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        index += 1;
                        if (index >= listview.getCount()) {
                            index = listview.getCount();
                        }
                        listview.smoothScrollToPosition(index);
                    }
                });
            }
        }, 0, 1000);
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        titleright = (ImageView) findViewById(R.id.near_title_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("抢红包");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        ivGrabMoney = (Button) findViewById(R.id.grab);
        ivGrabMoney.setOnClickListener(this);
        listview = (ListView) findViewById(R.id.lv_winner);
        adapter = new EnvelopeWinnerAdapter(this, data);
        listview.setAdapter(adapter);
        rules = (TextView) findViewById(R.id.near_rule);
        rules.setVisibility(View.VISIBLE);
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rule = new Intent(getApplication(), RuleActivity.class);
                rule.putExtra(RuleType, 2);
                startActivity(rule);
            }
        });
    }

    private void initData() {
        ApiUtil.getEnvelopeWinnerList(this, new BaseJsonHttpResponseHandler<List<EnvelopWinBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, List<EnvelopWinBean> response) {
                data.addAll(response);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, List<EnvelopWinBean> errorResponse) {

            }

            @Override
            protected List<EnvelopWinBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValue(rawJsonData, new TypeReference<List<EnvelopWinBean>>() {
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            case R.id.grab:
                if(StoreUtils.getGrabNum()>0){
                    Intent intent = new Intent(GrabRedEnvolope.this, GrabEnvolopeDialog.class);
                    startActivity(intent);
                }else{
                    ToastUtils.showShortToast(GrabRedEnvolope.this,"今天抢红包次数用完了");
                }

                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}
