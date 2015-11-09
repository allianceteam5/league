package com.league.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.adapter.FeatureComAdapter;
import com.league.adapter.JobInfoAdapter;
import com.league.adapter.MakingFriendAdapter;
import com.league.adapter.SearchPeoAdapter;
import com.league.bean.FeatureComInfo;
import com.league.bean.JobInfoBean;
import com.league.bean.MakingFriendInfo;
import com.league.bean.Kind;
import com.league.bean.SearchPeopleInfo;
import com.league.dialog.NearRightDialog;
import com.league.utils.Constants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * @author liugang
 * @date 2015年9月19日
 */
public class SechJobInfo extends BaseActivity implements OnClickListener {

    private ImageButton near_back, near_right;
    private TextView near_title;
    private ImageView near_t_rig;
    private LinearLayout ll_more;
    private int Flag;
    private ListView infoseach;
    private List<FeatureComInfo> listFeaCData = new ArrayList<FeatureComInfo>();
    private List<MakingFriendInfo> listMaFrData = new ArrayList<MakingFriendInfo>();
    private List<SearchPeopleInfo> listSearchPeoData = new ArrayList<SearchPeopleInfo>();
    private JobInfoAdapter adapter;
    private FeatureComAdapter feaComAdapter;
    private MakingFriendAdapter makFriAdapter;
    private SearchPeoAdapter searchPeoAdapter;
    private ArrayList<Kind> kinds;
    private List<JobInfoBean> jobInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_searchinfo);
        showProgressDialog();
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
                kinds = Paper.book().read(Constants.ProfessinListName, new ArrayList<Kind>());

                ApiUtil.professionList(getApplicationContext(), new BaseJsonHttpResponseHandler<ArrayList<Kind>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<Kind> response) {
                        kinds = response;
                        Paper.book().write(Constants.ProfessinListName, kinds);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<Kind> errorResponse) {

                    }

                    @Override
                    protected ArrayList<Kind> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        Log.d("response", rawJsonData);
                        return getKinds(rawJsonData, "profession");
                    }
                });
                break;
            case 2:
                near_title.setText("特色推荐");
                kinds = Paper.book().read(Constants.RecommendationListName, new ArrayList<Kind>());

                ApiUtil.recommendationList(getApplicationContext(), new BaseJsonHttpResponseHandler<ArrayList<Kind>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<Kind> response) {
                        kinds = response;
                        Paper.book().write(Constants.RecommendationListName, kinds);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<Kind> errorResponse) {

                    }

                    @Override
                    protected ArrayList<Kind> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        Log.d("response", rawJsonData);
                        return getKinds(rawJsonData, "kind");
                    }
                });
                break;
            case 3:
                near_title.setText("爱好交友");
                kinds = Paper.book().read(Constants.HobbyListName, new ArrayList<Kind>());

                ApiUtil.hobbyList(getApplicationContext(), new BaseJsonHttpResponseHandler<ArrayList<Kind>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<Kind> response) {
                        kinds = response;
                        Paper.book().write(Constants.HobbyListName, kinds);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<Kind> errorResponse) {

                    }

                    @Override
                    protected ArrayList<Kind> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        Log.d("response", rawJsonData);
                        return getKinds(rawJsonData, "hobby");
                    }
                });
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

    @NonNull
    private ArrayList<Kind> getKinds(String rawJsonData, String kindname) throws JSONException {
        JSONArray json = new JSONArray(rawJsonData);
        ArrayList<Kind> kindList = new ArrayList<Kind>();
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonObject = json.optJSONObject(i);
            kindList.add(new Kind(jsonObject.optInt("id"), jsonObject.optString(kindname)));
        }
        return kindList;
    }

    public void initData() {
        switch (Flag) {
            case 1:
                ApiUtil.applyjobSearchAll(getApplicationContext(), new BaseJsonHttpResponseHandler<ArrayList<JobInfoBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<JobInfoBean> response) {
                        jobInfoList = response;
                        closeProgressDialog();
                        updateApplyJobView();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<JobInfoBean> errorResponse) {
                        closeProgressDialog();
                        ToastUtils.showShortToast(getApplicationContext(),getString(R.string.warning_internet));
                    }

                    @Override
                    protected ArrayList<JobInfoBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        return new ObjectMapper().readValue(jsonObject.optJSONArray("items").toString(), new TypeReference<ArrayList<JobInfoBean>>() {
                        });
                    }
                });
                break;
            case 2:
                closeProgressDialog();
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
                closeProgressDialog();
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
            case 6:
                break;
            default:
                break;
        }

    }

    public void updateApplyJobView(){
        adapter = new JobInfoAdapter(getApplicationContext(), jobInfoList);
        infoseach.setAdapter(adapter);
        infoseach.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), NearItemActivity.class);
                Paper.book().write(Constants.SingleJobInfoName,jobInfoList.get(position));
                startActivity(intent);
            }
        });
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
