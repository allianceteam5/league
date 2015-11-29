package com.league.activity.redpacket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.league.dialog.GrabEnvolopeDialog;
import com.mine.league.R;

public class GrabRedEnvolope extends Activity implements OnClickListener {


    private ImageView back, titleright, right1, right2;
    private TextView title;
    private ListView listview;
    private Button ivGrabMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabredenvolope);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.near_back);
        back.setOnClickListener(this);
        titleright = (ImageView) findViewById(R.id.near_ti_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_centertitle);
        title.setText("抢红包");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        ivGrabMoney = (Button) findViewById(R.id.grab);
        ivGrabMoney.setOnClickListener(this);
        listview = (ListView) findViewById(R.id.lv_winner);
        listview.setAdapter(new WinnerAdapter(getApplicationContext()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            case R.id.grab:
                Intent intent=new Intent(GrabRedEnvolope.this, GrabEnvolopeDialog.class);
                startActivity(intent);
                break;
        }
    }

    class WinnerAdapter extends BaseAdapter {
        private Context context;

        public WinnerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int arg0) {
            return arg0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.layout_item_redwinner, null);
            TextView tvWinnername = (TextView) convertView.findViewById(R.id.tv_winnername);
            TextView tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            return convertView;
        }
    }

    public String getMoneyString(int money) {
        return "抢到" + money + "元红包";
    }
}
