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
import com.league.adapter.LiaobaPopularityAdapter;
import com.league.bean.PopularityBean;
import com.league.interf.ListItemClickHelp;
import com.league.utils.Constants;
import com.league.utils.api.ApiUtil;
import com.league.widget.pulltorefreshandload.PullToRefreshLayout;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LiaoBaPopularityFragment extends Fragment{
    private View layout;
    private ListView listView;
    private List<PopularityBean> list =new ArrayList<PopularityBean>();
    private LiaobaPopularityAdapter adapter;
    private int totalPage;
    private int currentPage=1;
    private PullToRefreshLayout pullToRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout=inflater.inflate(R.layout.fragment_liao_ba_popularity,container,false);
        initView();
        initData(currentPage);
        return layout;
    }
    private void initView(){
        listView= (ListView) layout.findViewById(R.id.liaoba_popularity_list);
        adapter=new LiaobaPopularityAdapter(list,getActivity().getApplication());
        listView.setAdapter(adapter);
        pullToRefreshLayout = (PullToRefreshLayout) layout.findViewById(R.id.refresh_view);
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        pullToRefreshLayout.setVisibility(View.GONE);
    }

    private void initData(final int currentPage){
        ApiUtil.liaobaGetPopularity(getActivity(), currentPage, new BaseJsonHttpResponseHandler<ArrayList<PopularityBean>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<PopularityBean> response) {
                if(currentPage==1){
                    list.clear();
                }
                list.addAll(response);
                adapter.notifyDataSetChanged();
                pullToRefreshLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<PopularityBean> errorResponse) {
                Toast.makeText(getActivity(), "哎呀网络不好", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected ArrayList<PopularityBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject=new JSONObject(rawJsonData);
                totalPage=jsonObject.optJSONObject("_meta").optInt("pageCount");
                return new ObjectMapper().readValue(jsonObject.optString("items"), new TypeReference<ArrayList<PopularityBean>>() {});
            }
        });
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener
    {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
        {
            // 下拉刷新操作
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    currentPage = 1;
                    initData(currentPage);
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);

        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
        {
            // 加载操作
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    currentPage++;
                    if(currentPage <= totalPage)
                        initData(currentPage);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

    }
}
