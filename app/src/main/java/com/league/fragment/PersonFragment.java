package com.league.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.league.activity.personactivity.AlianceReward;
import com.league.activity.personactivity.PersonInformationSetup;
import com.league.activity.personactivity.PersonSetup;
import com.mine.league.R;

/**
 * @author liugang
 * @date 2015年9月15日
 */
public class PersonFragment extends Fragment implements View.OnClickListener {

    private View layout;
    private Activity ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (layout == null) {
            ctx = this.getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_person, null);
            setOnClickListener();
        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        return layout;
    }

    private void setOnClickListener() {

        layout.findViewById(R.id.setup).setOnClickListener(this);
        layout.findViewById(R.id.myown).setOnClickListener(this);
        layout.findViewById(R.id.aliancereward).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setup:
                Intent intent = new Intent(ctx, PersonSetup.class);
                startActivity(intent);
                break;
            case R.id.myown:
                Intent intent1=new Intent(ctx, PersonInformationSetup.class);
                startActivity(intent1);
                break;
            case R.id.aliancereward:
                Intent intent2 = new Intent(ctx, AlianceReward.class);
                startActivity(intent2);
                break;
        }
    }
}
