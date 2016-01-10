package com.league.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mine.league.R;

/**
 * @author liugang
 * @date 2015年9月15日
 */
public class FriendsFragment extends Fragment {

    private Activity ctx;
    private View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (layout == null) {
            ctx = this.getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_friends, null);

        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            parent.removeView(layout);
        }
        return layout;
    }

}
