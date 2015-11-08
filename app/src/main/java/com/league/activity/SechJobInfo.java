package com.league.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.league.adapter.FeatureComAdapter;
import com.league.adapter.JobInfoAdapter;
import com.league.adapter.MakingFriendAdapter;
import com.league.adapter.SearchPeoAdapter;
import com.league.bean.FeatureComInfo;
import com.league.bean.JobInfoBean;
import com.league.bean.MakingFriendInfo;
import com.league.bean.SearchPeopleInfo;
import com.league.dialog.NearRightDialog;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liugang
 * @date 2015年9月19日
 */
public class SechJobInfo extends Activity implements OnClickListener {

    private ImageButton near_back, near_right;
    private TextView near_title;
    private ImageView near_t_rig;
    private LinearLayout ll_more;
    private int Flag;
    private ListView infoseach;
    private List<JobInfoBean> listdata = new ArrayList<JobInfoBean>();
    private List<FeatureComInfo> listFeaCData = new ArrayList<FeatureComInfo>();
    private List<MakingFriendInfo> listMaFrData = new ArrayList<MakingFriendInfo>();
    private List<SearchPeopleInfo> listSearchPeoData = new ArrayList<SearchPeopleInfo>();
    private JobInfoAdapter adapter;
    private FeatureComAdapter feaComAdapter;
    private MakingFriendAdapter makFriAdapter;
    private SearchPeoAdapter searchPeoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_searchinfo);
        Flag = getIntent().getIntExtra("mode", -1);
        if (Flag == -1) {
            return;
        }

        init();
        initData();
    }

    private void init() {
        near_back = (ImageButton) findViewById(R.id.near_back);
        near_title = (TextView) findViewById(R.id.near_centertitle);
        near_t_rig = (ImageView) findViewById(R.id.near_ti_right);
        near_right = (ImageButton) findViewById(R.id.near_right);
        infoseach = (ListView) findViewById(R.id.infosearch_list);
        ll_more = (LinearLayout) findViewById(R.id.near_more);

        switch (Flag) {
            case 1:
                near_title.setText("求职信息");
                break;
            case 2:
                near_title.setText("特色推荐");
                break;
            case 3:
                near_title.setText("爱好交友");
                break;
//            case 4:
//                near_title.setText("顺风车");
//                near_t_rig.setVisibility(View.INVISIBLE);
//                break;
//            case 5:
//                near_title.setText("寻人启事");
//                break;
            case 6:
                near_title.setText("其他");
                near_t_rig.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
        near_back.setOnClickListener(this);
        near_right.setOnClickListener(this);
        ll_more.setOnClickListener(this);
    }

    public void initData() {
        switch (Flag) {
            case 1:
                //从服务器拉取数据
                for (int i = 0; i < 10; i++) {
                    JobInfoBean jib = new JobInfoBean();
                    jib.setUserNickname("nickname " + i);
                    jib.setFullorpart_timejob(i % 2);
                    jib.setProfession("profession " + i);
                    jib.setLasttime("lasttime " + i);
                    jib.setInfoContent("infocontent " + i);
                    jib.setEduction("edution " + i);
                    jib.setWorktime("worktime " + i);
                    jib.setCurrent_status("status " + i);
                    jib.setLeave_message("leave message " + i);
                    listdata.add(jib);

                }
                adapter = new JobInfoAdapter(getApplicationContext(), listdata);
                infoseach.setAdapter(adapter);
                infoseach.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(getApplicationContext(), NearItemActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            case 2:
                //从服务器拉取数据
                for (int i = 0; i < 10; i++) {
                    FeatureComInfo fci = new FeatureComInfo();
                    fci.setUserNickname("userNickname" + i);
                    fci.setFea_location("fea_location" + i);
                    fci.setType("type" + i);
                    fci.setLasttime("lasttime" + i);
                    fci.setComnumber("" + i);
                    fci.setInfoContent("infoContent" + i);
                    fci.setSecContent("secContent" + i);
                    listFeaCData.add(fci);
                }
                feaComAdapter = new FeatureComAdapter(getApplicationContext(), listFeaCData);
                infoseach.setAdapter(feaComAdapter);
                infoseach.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                        // TODO Auto-generated method stub
                        Intent intent2 = new Intent(getApplicationContext(), CommandItem.class);
                        startActivity(intent2);

                    }
                });
                break;
            case 3:
                //从服务器拉取数据
                for (int i = 0; i < 10; i++) {
                    MakingFriendInfo mfi = new MakingFriendInfo();
                    mfi.setUserNickName("userNickName" + i);
                    mfi.setSex(i % 2 == 0 ? "男" : "女");
                    mfi.setInteresting("interesting" + i);
                    mfi.setLeaveMessage("leaveMessage" + i);
                    listMaFrData.add(mfi);
                }
                makFriAdapter = new MakingFriendAdapter(getApplicationContext(), listMaFrData);
                infoseach.setAdapter(makFriAdapter);
                infoseach.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                        // TODO Auto-generated method stub
                        Intent intent3 = new Intent(getApplicationContext(), MakingFriItem.class);
                        startActivity(intent3);

                    }
                });
                break;
//            case 4:
//                break;
//            case 5:
//                //从服务器拉取数据
//                for (int i = 0; i < 10; i++) {
//                    SearchPeopleInfo fci = new SearchPeopleInfo();
//                    fci.setUserNickname("userNickname" + i);
//                    fci.setSear_location("sear_location" + i);
//                    fci.setLasttime("lasttime" + i);
//                    fci.setComnumber("" + i);
//                    fci.setInfoContent("infoContent" + i);
//                    fci.setSecContent("secContent" + i);
//                    listSearchPeoData.add(fci);
//                }
//                searchPeoAdapter = new SearchPeoAdapter(getApplicationContext(), listSearchPeoData);
//                infoseach.setAdapter(searchPeoAdapter);
//
//                break;
            case 6:
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.near_back:
                onBackPressed();
                finish();
                break;
            //选择分类刷选
            case R.id.near_more:
                break;
            case R.id.near_right:
                switch (Flag) {
                    case 1:
                        NearRightDialog dialog = new NearRightDialog(this, 1);
                        Window dialogWindow = dialog.getWindow();
                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        dialogWindow.setGravity(Gravity.TOP | Gravity.RIGHT);
                        lp.x = -10; // 新位置X坐标
                        lp.y = 90; // 新位置Y坐标
                        lp.alpha = 0.7f; // 透明度
                        dialogWindow.setAttributes(lp);
                        dialog.show();
                        break;
                    case 2:
                        NearRightDialog dialog2 = new NearRightDialog(this, 2);
                        Window dialogWindow2 = dialog2.getWindow();
                        WindowManager.LayoutParams lp2 = dialogWindow2.getAttributes();
                        dialogWindow2.setGravity(Gravity.TOP | Gravity.RIGHT);
                        lp2.x = 0; // 新位置X坐标
                        lp2.y = 90; // 新位置Y坐标
                        lp2.alpha = 0.7f; // 透明度
                        dialogWindow2.setAttributes(lp2);
                        dialog2.show();
                        break;
                }


        }
    }
}
