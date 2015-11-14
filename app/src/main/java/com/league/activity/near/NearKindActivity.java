package com.league.activity.near;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.FeatureComAdapter;
import com.league.adapter.HobbyInfoAdapter;
import com.league.adapter.JobInfoAdapter;
import com.league.adapter.SearchPeoAdapter;
import com.league.bean.FeatureComInfo;
import com.league.bean.HobbyInfoBean;
import com.league.bean.JobInfoBean;
import com.league.bean.KindBean;
import com.league.bean.SearchPeopleInfo;
import com.league.dialog.NearRightDialog;
import com.league.utils.Constants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.league.widget.RefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import net.soulwolf.widget.dialogbuilder.DialogBuilder;
import net.soulwolf.widget.dialogbuilder.MasterDialog;
import net.soulwolf.widget.dialogbuilder.OnItemClickListener;
import net.soulwolf.widget.dialogbuilder.adapter.TextDialogAdapter;
import net.soulwolf.widget.dialogbuilder.dialog.ListMasterDialog;

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
public class NearKindActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

    private ImageButton near_back, near_right;
    private TextView near_title;
    private ImageView near_t_rig;
    private EditText etSearch;
    private Button btnSure;
    private LinearLayout ll_more;
    private int Flag;
    private ListView infoseach;
    private List<FeatureComInfo> listFeaCData = new ArrayList<FeatureComInfo>();
    private List<HobbyInfoBean> listMaFrData = new ArrayList<HobbyInfoBean>();
    private List<SearchPeopleInfo> listSearchPeoData = new ArrayList<SearchPeopleInfo>();
    private JobInfoAdapter jobInfoAdapter;
    private FeatureComAdapter feaComAdapter;
    private HobbyInfoAdapter hobbyInfoAdapter;
    private SearchPeoAdapter searchPeoAdapter;
    private ArrayList<KindBean> kinds;
    private List<String> items;
    private List<JobInfoBean> jobInfoList = new ArrayList<JobInfoBean>();
    private List<HobbyInfoBean> hobbyInfoList = new ArrayList<HobbyInfoBean>();
    private int currentPage = 1;
    private int sumPage;
    private int kindid = 0;
    private Boolean firstWindowLoad = false;
    private String searchString = "";
    DialogBuilder builder;
    ListMasterDialog dialog;


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
        etSearch = (EditText) findViewById(R.id.et_search_search);
        btnSure = (Button) findViewById(R.id.btn_search_sure);
        final RefreshLayout myRefreshListView = (RefreshLayout)
                findViewById(R.id.swipe_layout);
        myRefreshListView
                .setColorScheme(R.color.black,
                        R.color.blue, R.color.greenn,
                        R.color.red);

        near_back.setOnClickListener(this);
        near_right.setOnClickListener(this);
        ll_more.setOnClickListener(this);
        btnSure.setOnClickListener(this);

        // 设置下拉刷新监听器
        myRefreshListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentPage = 1;
                        switch (Flag) {
                            case 1:
                                jobInfoList.clear();
                                break;
                            case 2:
                                break;
                            case 3:
                                hobbyInfoList.clear();
                        }
                        // 更新数据
                        initData();
                        // 更新完后调用该方法结束刷新
                        myRefreshListView.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        // 加载监听器
        myRefreshListView.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                myRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentPage++;
                        if (currentPage < sumPage) {
                            initData();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.loading_done), Toast.LENGTH_SHORT).show();
                        }
                        // 加载完后调用该方法
                        myRefreshListView.setLoading(false);
                    }
                }, 1500);

            }
        });

        builder = new DialogBuilder(this)
                .setAnimation(R.anim.da_slide_in_top, R.anim.da_slide_out_top)
                        //.setIgnoreStatusBar(false)
                .setGravity(Gravity.TOP)
                .setOnItemClickListener(this)
                .setBackgroundResource(R.color.transparent);
        dialog = new ListMasterDialog(builder);
        dialog.setCancelButton(false);

        switch (Flag) {
            case 1:
                near_title.setText("求职信息");
                kinds = Paper.book().read(Constants.ProfessinListName, new ArrayList<KindBean>());

                ApiUtil.professionList(getApplicationContext(), new BaseJsonHttpResponseHandler<ArrayList<KindBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<KindBean> response) {
                        kinds = response;
                        Paper.book().write(Constants.ProfessinListName, kinds);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<KindBean> errorResponse) {

                    }

                    @Override
                    protected ArrayList<KindBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        Log.d("response", rawJsonData);
                        return getKinds(rawJsonData, "profession");
                    }
                });
                break;
            case 2:
                near_title.setText("特色推荐");
                kinds = Paper.book().read(Constants.RecommendationListName, new ArrayList<KindBean>());

                ApiUtil.recommendationList(getApplicationContext(), new BaseJsonHttpResponseHandler<ArrayList<KindBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<KindBean> response) {
                        kinds = response;
                        Paper.book().write(Constants.RecommendationListName, kinds);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<KindBean> errorResponse) {

                    }

                    @Override
                    protected ArrayList<KindBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        Log.d("response", rawJsonData);
                        return getKinds(rawJsonData, "kind");
                    }
                });
                break;
            case 3:
                near_title.setText("爱好交友");
                kinds = Paper.book().read(Constants.HobbyListName, new ArrayList<KindBean>());

                ApiUtil.hobbyList(getApplicationContext(), new BaseJsonHttpResponseHandler<ArrayList<KindBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<KindBean> response) {
                        kinds = response;
                        Paper.book().write(Constants.HobbyListName, kinds);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<KindBean> errorResponse) {

                    }

                    @Override
                    protected ArrayList<KindBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        Log.d("response", rawJsonData);
                        return getKinds(rawJsonData, "hobby");
                    }
                });
                break;
            case 4:
                near_title.setText("其他");
                near_t_rig.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    @NonNull
    private ArrayList<KindBean> getKinds(String rawJsonData, String kindname) throws JSONException {
        JSONArray json = new JSONArray(rawJsonData);
        ArrayList<KindBean> kindList = new ArrayList<KindBean>();
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonObject = json.optJSONObject(i);
            kindList.add(new KindBean(jsonObject.optInt("id"), jsonObject.optString(kindname)));
        }
        return kindList;
    }

    //kindid=0 获取全部数据
    public void initData() {
        if (!firstWindowLoad) {
            showProgressDialog();
            firstWindowLoad = true;
        }
        switch (Flag) {
            case 1:
                ApiUtil.applyjobSearch(getApplicationContext(), kindid, searchString, currentPage, new BaseJsonHttpResponseHandler<ArrayList<JobInfoBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<JobInfoBean> response) {
                        jobInfoList.addAll(response);
                        closeProgressDialog();
                        updateApplyJobView();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<JobInfoBean> errorResponse) {
                        closeProgressDialog();
                        ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_internet));
                    }

                    @Override
                    protected ArrayList<JobInfoBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        Log.d("response", rawJsonData);
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        sumPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
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
                infoseach.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

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
                ApiUtil.hobbySearch(getApplicationContext(), kindid, searchString, currentPage, new BaseJsonHttpResponseHandler<ArrayList<HobbyInfoBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<HobbyInfoBean> response) {
                        hobbyInfoList.addAll(response);
                        closeProgressDialog();
                        updateHobbyView();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<HobbyInfoBean> errorResponse) {
                        closeProgressDialog();
                        ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_internet));
                    }

                    @Override
                    protected ArrayList<HobbyInfoBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        Log.d("response", rawJsonData);
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        sumPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                        return new ObjectMapper().readValue(jsonObject.optJSONArray("items").toString(), new TypeReference<ArrayList<HobbyInfoBean>>() {
                        });
                    }
                });
                break;
            case 6:
                break;
            default:
                break;
        }
    }

    public void updateApplyJobView() {
        if (jobInfoAdapter == null) {
            jobInfoAdapter = new JobInfoAdapter(getApplicationContext(), jobInfoList);
            infoseach.setAdapter(jobInfoAdapter);
            infoseach.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getApplicationContext(), JobInfoActivity.class);
                    Paper.book().write(Constants.SingleJobInfoName, jobInfoList.get(position));
                    startActivity(intent);
                }
            });
        } else {
            jobInfoAdapter.notifyDataSetChanged();
        }
    }

    public void updateHobbyView() {
        if (jobInfoAdapter == null) {
            hobbyInfoAdapter = new HobbyInfoAdapter(getApplicationContext(), hobbyInfoList);
            infoseach.setAdapter(jobInfoAdapter);
            infoseach.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
//                    Intent intent = new Intent(getApplicationContext(), NearItemActivity.class);
//                    Paper.book().write(Constants.SingleHobbyInfoName, hobbyInfoList.get(position));
//                    startActivity(intent);
                }
            });
        } else {
            jobInfoAdapter.notifyDataSetChanged();
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
            case R.id.btn_search_sure:
                searchString = etSearch.getText().toString();
                if (TextUtils.isEmpty(searchString)) {
                    ToastUtils.showShortToast(getApplicationContext(), getString(R.string.input_string));
                    return;
                }
                firstWindowLoad = false;
                currentPage = 1;
                jobInfoList.clear();
                initData();
                break;
            //选择分类刷选
            case R.id.near_more:
                items = getListString(kinds);
                String[] itemstr = new String[items.size()];
                itemstr = items.toArray(itemstr);
                dialog.setAdapter(new TextDialogAdapter(this, itemstr));
                dialog.show();
                break;
            //右上角跳出框选择更多
            case R.id.near_right:
                NearRightDialog dialog = new NearRightDialog(this, Flag);
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                dialogWindow.setGravity(Gravity.TOP | Gravity.RIGHT);
                lp.x = 0; // 新位置X坐标
                lp.y = 90; // 新位置Y坐标
                lp.alpha = 0.7f; // 透明度
                dialogWindow.setAttributes(lp);
                dialog.show();
                break;
        }
    }

    public List<String> getListString(List<KindBean> kinds) {
        if (kinds == null)
            return new ArrayList<String>();
        List<String> items = new ArrayList<String>();
        items.add("全部");
        for (int i = 0; i < kinds.size(); i++)
            items.add(kinds.get(i).getName());
        return items;
    }

    @Override
    public void onItemClick(MasterDialog masterDialog, View view, int i) {
        if (dialog.isShowing())
            dialog.dismiss();
        if (i == 0)
            kindid = 0;
        else
            kindid = kinds.get(i - 1).getId();
        currentPage = 1;
        jobInfoList.clear();
        firstWindowLoad = false;
        initData();
    }
}
