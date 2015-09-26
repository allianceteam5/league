package com.league.adapter;

import java.util.List;

import com.league.bean.MakingFriendInfo;
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
 * @date 2015年9月26日   
 */
public class MakingFriendAdapter extends BaseAdapter{

	private Context ctx;
	private List<MakingFriendInfo> list;
	public MakingFriendAdapter(Context c,List<MakingFriendInfo> l) {
		// TODO Auto-generated constructor stub
		ctx=c;
		list=l;
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
			convertView=LayoutInflater.from(ctx).inflate(R.layout.makingfriend_item, null);
			holder=new ViewHolder();
			holder.thumb=(ImageView) convertView.findViewById(R.id.near_friend_userthumb);
			holder.username=(TextView) convertView.findViewById(R.id.near_friend_username);
			holder.sex=(ImageView) convertView.findViewById(R.id.near_firend_usersex);
			holder.intest=(TextView) convertView.findViewById(R.id.near_friend_intes);
			holder.leavemessage=(TextView) convertView.findViewById(R.id.near_friend_leave);
			convertView.setTag(holder);
			
		}else{
			holder=(ViewHolder) convertView.getTag();
			
		}
		holder.username.setText(list.get(position).getUserNickName());
		if(list.get(position).getSex().equals("男")){
			holder.sex.setImageResource(R.drawable.friend_sex_man);
		}else
			holder.sex.setImageResource(R.drawable.friend_sex_woman);
		holder.intest.setText(list.get(position).getInteresting());
		holder.leavemessage.setText(list.get(position).getLeaveMessage());
		
		
		return convertView;
	}
	class ViewHolder{
		ImageView thumb;
		TextView username;
		ImageView sex;
		TextView intest;
		TextView leavemessage;
	}
}
