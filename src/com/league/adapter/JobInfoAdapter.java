package com.league.adapter;

import java.util.List;

import com.league.bean.JobInfoBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**  
 *   
 * @author liugang  
 * @date 2015年9月19日   
 */
public class JobInfoAdapter extends BaseAdapter{

	private List<JobInfoBean> list;
	private Context ctx;
	public JobInfoAdapter(Context context,List<JobInfoBean> list){
		ctx=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		return null;
	}

}
