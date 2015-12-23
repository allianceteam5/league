package com.league.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easemob.chatuidemo.activity.LoginActivity;
import com.league.utils.ActivityUtils;
import com.league.utils.StoreUtils;
import com.league.utils.Utils;
import com.mine.league.R;

public class FragmentGuide3 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_guide3, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		TextView textView = (TextView) getView().findViewById(
				R.id.tv_guide_next);

		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 保存数据到SharePreference中以此判断是不是第一次登陆
				StoreUtils.setSkipGuidState(true);
				ActivityUtils.start_Activity(getActivity(), LoginActivity.class);
				getActivity().finish();
			}
		});
	}

}
