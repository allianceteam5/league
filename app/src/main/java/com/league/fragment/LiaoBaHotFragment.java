package com.league.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.league.adapter.LiaoBaAdapter;
import com.league.bean.LiaoBaUserInfo;
import com.league.view.MyListView;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;


public class LiaoBaHotFragment extends Fragment {
    private View layout;
    private MyListView listView;

    private List<LiaoBaUserInfo> list=new ArrayList<LiaoBaUserInfo>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout=inflater.inflate(R.layout.fragment_liao_ba_hot,container,false);
        initView();
        return layout;
    }
    private void initView(){
        listView= (MyListView) layout.findViewById(R.id.liaoba_hot_list);
        for(int i=0;i<10;i++){
            LiaoBaUserInfo lbi=new LiaoBaUserInfo();
            lbi.setName("王思聪"+i);
            lbi.setTime(i+"分钟前");
            lbi.setHot_new_flag(2);
            lbi.setTitle("标题"+i);
            lbi.setContent("内容"+i);
            lbi.setFlag_concern(i%2);
            lbi.setDianzannum(i);
            lbi.setCommentnum(i);
            list.add(lbi);
        }
        listView.setAdapter(new LiaoBaAdapter(list,getActivity().getApplication()));
    }
}
