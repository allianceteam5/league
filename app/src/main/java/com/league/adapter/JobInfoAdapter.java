package com.league.adapter;

import java.util.List;

import com.league.bean.JobInfoBean;
import com.league.widget.CircleImageView;
import com.mine.league.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(ctx).inflate(R.layout.infojob_item, null);
			holder.thumb=(CircleImageView) convertView.findViewById(R.id.near_userthumb);
			holder.userNickname=(TextView) convertView.findViewById(R.id.near_username);
			holder.fullorpart_timejob=(TextView) convertView.findViewById(R.id.near_partjobtype);
			holder.profession=(TextView) convertView.findViewById(R.id.near_userprofession);
			holder.lasttime=(TextView) convertView.findViewById(R.id.lasttime);
			holder.infoContent=(TextView) convertView.findViewById(R.id.near_usercontent);
			holder.eduction=(TextView) convertView.findViewById(R.id.near_usereducation);
			holder.worktime=(TextView) convertView.findViewById(R.id.near_userworktime);
			holder.current_status=(TextView) convertView.findViewById(R.id.near_usercurrentstaus);
			holder.leave_message=(TextView) convertView.findViewById(R.id.near_userleavemessage);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
//		holder.thumb.setImageResource(resId);
		holder.userNickname.setText(list.get(position).getUserNickname());
		if(list.get(position).getFullorpart_timejob()==0){
			holder.fullorpart_timejob.setText("兼职");
		}else{
			holder.fullorpart_timejob.setText("全职");
		}
		
		holder.profession.setText(list.get(position).getProfession());
		holder.lasttime.setText(list.get(position).getLasttime());
		holder.infoContent.setText(list.get(position).getInfoContent());
		holder.eduction.setText(list.get(position).getEduction());
		holder.worktime.setText(list.get(position).getWorktime());
		holder.current_status.setText(list.get(position).getCurrent_status());
		holder.leave_message.setText(list.get(position).getLeave_message());
		return convertView;
	}
	class ViewHolder{
		CircleImageView thumb;
		TextView userNickname;
		TextView fullorpart_timejob;
		TextView profession;
		TextView lasttime;
		TextView infoContent;
		TextView eduction;
		TextView worktime;
		TextView current_status;
		TextView leave_message;
	}
}
