package com.league.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.treasure.OneYuanGrabItem;
import com.league.activity.treasure.TenYuanGrabItem;
import com.league.adapter.GrabRecordAdapter;
import com.league.bean.GrabBean;
import com.league.utils.api.ApiUtil;
import com.league.widget.pulltorefreshandload.PullToRefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liug on 15/11/22.
 */
public class RecordFrament extends Fragment {
    private int type;//用来区别三个tab
    private View layout;
    private ListView listView;
    private List<GrabBean> list = new ArrayList<GrabBean>();
    private int totalPage;
    private int currentPage = 1;
    private PullToRefreshLayout pullToRefreshLayout;
    private GrabRecordAdapter adapter;

    protected Dialog loadingDialog;

    public RecordFrament() {
    }

    public RecordFrament(int type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_grabrecord, container, false);
        initView();
        initData(currentPage);
        return layout;
    }

    private void initView() {
        listView = (ListView) layout.findViewById(R.id.grabrecord_list);
        adapter = new GrabRecordAdapter(getActivity().getApplication(), list);
        listView.setAdapter(adapter);
        pullToRefreshLayout = (PullToRefreshLayout) layout.findViewById(R.id.refresh_view);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        pullToRefreshLayout.setVisibility(View.GONE);
    }

    private void initData(final int currentPage) {
        showProgressDialog();
        if (type == 0) {
            ApiUtil.getGrabCoinRecords(getActivity().getApplication(), currentPage, new BaseJsonHttpResponseHandler<ArrayList<GrabBean>>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<GrabBean> response) {
                    closeProgressDialog();
                    if (currentPage == 1) {
                        list.clear();
                    }
                    list.addAll(response);
                    adapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity().getApplication(), TenYuanGrabItem.class);
                            intent.putExtra("id", list.get(position).getGrabcornid());
                            startActivity(intent);
                        }
                    });
                    pullToRefreshLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<GrabBean> errorResponse) {
//                    Toast.makeText(getActivity(), "哎呀网络不好", Toast.LENGTH_SHORT).show();
                    closeProgressDialog();
                }

                @Override
                protected ArrayList<GrabBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    JSONObject jsonObject = new JSONObject(rawJsonData);
                    totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                    return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<GrabBean>>() {
                    });
                }
            });
        } else if (type == 1) {
            ApiUtil.getGrabCommodyRecords(getActivity().getApplication(), currentPage, new BaseJsonHttpResponseHandler<ArrayList<GrabBean>>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<GrabBean> response) {
                    closeProgressDialog();
                    if (currentPage == 1) {
                        list.clear();
                    }
                    list.addAll(response);
                    adapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity().getApplication(), OneYuanGrabItem.class);
                            intent.putExtra("id", list.get(position).getGrabcommodityid());
                            startActivity(intent);
                        }
                    });
                    pullToRefreshLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<GrabBean> errorResponse) {
//                    Toast.makeText(getActivity(), "哎呀网络不好+1", Toast.LENGTH_SHORT).show();
                    closeProgressDialog();
                }

                @Override
                protected ArrayList<GrabBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    JSONObject jsonObject = new JSONObject(rawJsonData);
                    totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                    return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<GrabBean>>() {
                    });
                }
            });
        } else if (type == 2) {
            ApiUtil.getGrabWinRecords(getActivity().getApplication(), currentPage, new BaseJsonHttpResponseHandler<ArrayList<GrabBean>>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<GrabBean> response) {
                    closeProgressDialog();
                    if (currentPage == 1) {
                        list.clear();
                    }
                    list.addAll(response);
                    adapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Intent intent = new Intent(getActivity().getApplication(), TopicContent.class);
//                            startActivity(intent);
                        }
                    });
                    pullToRefreshLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<GrabBean> errorResponse) {
                    closeProgressDialog();
                }

                @Override
                protected ArrayList<GrabBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    JSONObject jsonObject = new JSONObject(rawJsonData);
                    totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                    return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<GrabBean>>() {
                    });
                }
            });
        }
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    currentPage = 1;
                    initData(currentPage);
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
                    if (currentPage <= totalPage)
                        initData(currentPage);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

    }

    /**
     * 显示等待对话框
     */
    public void showProgressDialog() {
        loadingDialog = createLoadingDialog(getActivity());
        loadingDialog.show();
    }

    /**
     * 关闭等待对话框
     */
    public void closeProgressDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;

    }
}
