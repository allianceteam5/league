package com.league.activity.near;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.BaseActivity;
import com.league.adapter.HobbyInfoAdapter;
import com.league.adapter.JobInfoAdapter;
import com.league.adapter.OtherMessageAdapter;
import com.league.adapter.RecommendationInfoAdapter;
import com.league.bean.HobbyInfoBean;
import com.league.bean.JobInfoBean;
import com.league.bean.KindBean;
import com.league.bean.OtherMessageBean;
import com.league.bean.RecommendationInfoBean;
import com.league.dialog.NearRightDialog;
import com.league.otto.RefreshEvent;
import com.league.utils.Constants;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.league.widget.pulltorefreshandload.PullToRefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.otto.Subscribe;

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

import butterknife.Bind;
import butterknife.ButterKnife;
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
    private List<HobbyInfoBean> listMaFrData = new ArrayList<HobbyInfoBean>();
    private JobInfoAdapter jobInfoAdapter;
    private RecommendationInfoAdapter recommendationInfoAdapter;
    private HobbyInfoAdapter hobbyInfoAdapter;
    private OtherMessageAdapter otherMessageAdapter;
    private ArrayList<KindBean> kinds;
    private List<String> items;
    private List<JobInfoBean> jobInfoList = new ArrayList<>();
    private List<HobbyInfoBean> hobbyInfoList = new ArrayList<>();
    private List<RecommendationInfoBean> recommendationInfoList = new ArrayList<>();
    private List<OtherMessageBean> otherInfoList = new ArrayList<>();
    private int currentPage = 1;
    private int sumPage;
    private int kindid = 0;
    private Boolean firstWindowLoad = false;
    private String searchString = "";
    DialogBuilder builder;
    ListMasterDialog dialog;

    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout pullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_searchinfo);
        ButterKnife.bind(this);

        Flag = getIntent().getIntExtra("mode", -1);
        if (Flag == -1) {
            return;
        }

        init();
        initData();
    }

    private void init() {
        near_back = (ImageButton) findViewById(R.id.near_back);
        near_title = (TextView) findViewById(R.id.near_title);
        near_t_rig = (ImageView) findViewById(R.id.near_title_right);
        near_right = (ImageButton) findViewById(R.id.near_right);
        ll_more = (LinearLayout) findViewById(R.id.near_more);

        View headerView = getLayoutInflater().inflate(R.layout.info_search, null);
        listview.addHeaderView(headerView);
        etSearch = (EditText) headerView.findViewById(R.id.et_search_search);
        btnSure = (Button) headerView.findViewById(R.id.btn_search_sure);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());

        near_back.setVisibility(View.VISIBLE);
        near_back.setOnClickListener(this);
        near_right.setOnClickListener(this);
        ll_more.setOnClickListener(this);
        btnSure.setOnClickListener(this);

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
                        if (response != null && response.size() > 0) {
                            response.add(0, new KindBean(-1, "兼职"));
                            response.add(0, new KindBean(-2, "全职"));
                        }
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
                closeProgressDialog();
                near_title.setText("其他");
                near_t_rig.setVisibility(View.INVISIBLE);
                ll_more.setClickable(false);
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
                int jobproperty = -1;
                if (kindid == -1)
                    jobproperty = 1;
                if (kindid == -2)
                    jobproperty = 0;
                ApiUtil.applyjobSearch(getApplicationContext(), false, kindid, searchString, jobproperty, currentPage, new BaseJsonHttpResponseHandler<ArrayList<JobInfoBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<JobInfoBean> response) {
                        jobInfoList.addAll(response);
                        updateApplyJobView();
                        closeProgressDialog();
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
                ApiUtil.recommendationsSearch(getApplicationContext(), false, kindid, searchString, 0, currentPage, new BaseJsonHttpResponseHandler<ArrayList<RecommendationInfoBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<RecommendationInfoBean> response) {
                        recommendationInfoList.addAll(response);
                        updateRecommendationView();
                        closeProgressDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<RecommendationInfoBean> errorResponse) {
                        closeProgressDialog();
                        ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_internet));
                    }

                    @Override
                    protected ArrayList<RecommendationInfoBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        Log.d("response", rawJsonData);
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        sumPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                        return new ObjectMapper().readValue(jsonObject.optJSONArray("items").toString(), new TypeReference<ArrayList<RecommendationInfoBean>>() {
                        });
                    }
                });
                break;
            case 3:
                ApiUtil.hobbySearch(getApplicationContext(), false, kindid, searchString, currentPage, new BaseJsonHttpResponseHandler<ArrayList<HobbyInfoBean>>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<HobbyInfoBean> response) {
                        hobbyInfoList.addAll(response);
                        updateHobbyView();
                        closeProgressDialog();
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
            case 4:
                ApiUtil.othersList(getApplicationContext(), false, searchString, currentPage, new BaseJsonHttpResponseHandler<ArrayList<OtherMessageBean>>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<OtherMessageBean> response) {
                        otherInfoList.addAll(response);
                        updateOtherView();
                        closeProgressDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<OtherMessageBean> errorResponse) {
                        closeProgressDialog();
                        ToastUtils.showShortToast(getApplicationContext(), getString(R.string.warning_internet));
                    }

                    @Override
                    protected ArrayList<OtherMessageBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        JSONObject jsonObject = new JSONObject(rawJsonData);
                        sumPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                        return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<OtherMessageBean>>() {
                        });
                    }
                });
                break;
            default:
                break;
        }
    }

    public void updateApplyJobView() {
        if (jobInfoAdapter == null) {
            jobInfoAdapter = new JobInfoAdapter(getApplicationContext(), jobInfoList);
            listview.setAdapter(jobInfoAdapter);
            listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getApplicationContext(), JobInfoActivity.class);
                    Paper.book().write(Constants.SingleInfoName, jobInfoList.get(position - 1));
                    startActivity(intent);
                }
            });
        } else {
            jobInfoAdapter.notifyDataSetChanged();
        }
    }

    public void updateRecommendationView() {
        if (recommendationInfoAdapter == null) {
            recommendationInfoAdapter = new RecommendationInfoAdapter(getApplicationContext(), recommendationInfoList);
            listview.setAdapter(recommendationInfoAdapter);
            listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getApplicationContext(), RecommendationInfoActivity.class);
                    Paper.book().write(Constants.SingleInfoName, recommendationInfoList.get(position - 1));
                    startActivity(intent);
                }
            });
        } else {
            recommendationInfoAdapter.notifyDataSetChanged();
        }
    }

    public void updateHobbyView() {
        if (hobbyInfoAdapter == null) {
            hobbyInfoAdapter = new HobbyInfoAdapter(getApplicationContext(), hobbyInfoList);
            listview.setAdapter(hobbyInfoAdapter);
            listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getApplicationContext(), HobbyInfoActivity.class);
                    Paper.book().write(Constants.SingleInfoName, hobbyInfoList.get(position - 1));
                    startActivity(intent);
                }
            });
        } else {
            hobbyInfoAdapter.notifyDataSetChanged();
        }
    }

    public void updateOtherView() {
        if (otherMessageAdapter == null) {
            otherMessageAdapter = new OtherMessageAdapter(getApplicationContext(), otherInfoList);
            listview.setAdapter(otherMessageAdapter);
        } else {
            otherMessageAdapter.notifyDataSetChanged();
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
                recommendationInfoList.clear();
                hobbyInfoList.clear();
                otherInfoList.clear();
                kindid = 0;
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
        recommendationInfoList.clear();
        hobbyInfoList.clear();
        firstWindowLoad = false;
        searchString = "";
        initData();
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    currentPage = 1;
                    switch (Flag) {
                        case 1:
                            jobInfoList.clear();
                            break;
                        case 2:
                            recommendationInfoList.clear();
                            break;
                        case 3:
                            hobbyInfoList.clear();
                            break;
                        case 4:
                            otherInfoList.clear();
                            break;
                    }
                    // 更新数据
                    initData();
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    currentPage++;
                    if (currentPage <= sumPage)
                        initData();
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Subscribe
    public void refreshEvent(RefreshEvent event) {
        currentPage = 1;
        switch (Flag) {
            case 1:
                jobInfoList.clear();
                break;
            case 2:
                recommendationInfoList.clear();
                break;
            case 3:
                hobbyInfoList.clear();
            case 4:
                otherInfoList.clear();
                break;
        }
        // 更新数据
        initData();
    }
}
