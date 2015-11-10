package com.league.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mine.league.R;


public class LiaoBaLatestFragment extends Fragment {

    private View layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout=inflater.inflate(R.layout.fragment_liao_ba_latest,container,false);
        return layout;
    }
}
