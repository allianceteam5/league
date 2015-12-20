package com.league.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.adapter.LiaoBaAdapter;
import com.league.bean.LiaoBaMessageBean;
import com.league.interf.ListItemClickHelp;
import com.league.utils.IContants;
import com.league.utils.api.ApiUtil;
import com.league.widget.pulltorefreshandload.PullToRefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LiaoBaConcernFragment extends Fragment implements ListItemClickHelp, IContants {
    private View layout;
    private ListView listView;
    private List<LiaoBaMessageBean> list = new ArrayList<LiaoBaMessageBean>();
    private int totalPage;
    private int currentPage = 1;
    private PullToRefreshLayout pullToRefreshLayout;
    private LiaoBaAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_liao_ba_concern, container, false);
        initView();
        initData(currentPage);
        return layout;
    }

    private void initView() {
        listView = (ListView) layout.findViewById(R.id.liaoba_concern_list);
        adapter = new LiaoBaAdapter(list, getActivity().getApplication(), 0);
        listView.setAdapter(adapter);
        pullToRefreshLayout = (PullToRefreshLayout) layout.findViewById(R.id.refresh_view);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        pullToRefreshLayout.setVisibility(View.GONE);

    }

    private void initData(final int currentPage) {
        ApiUtil.liaobaGetConcern(getActivity(), currentPage, new BaseJsonHttpResponseHandler<ArrayList<LiaoBaMessageBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<LiaoBaMessageBean> response) {
                if (currentPage == 1) {
                    list.clear();
                }
                list.addAll(response);
                adapter.notifyDataSetChanged();
                pullToRefreshLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<LiaoBaMessageBean> errorResponse) {
                Toast.makeText(getActivity(), "哎呀网络不好", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected ArrayList<LiaoBaMessageBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                totalPage = jsonObject.optJSONObject("_meta").optInt("pageCount");
                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<LiaoBaMessageBean>>() {
                });
            }
        });
    }

    @Override
    public void onClick(View item, View widget, int position, int which) {

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
}
