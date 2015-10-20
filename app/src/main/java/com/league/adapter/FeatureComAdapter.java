package com.league.adapter;

import java.util.ArrayList;
import java.util.List;

import com.league.bean.FeatureComInfo;
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
 * @date 2015年9月21日   
 */
public class FeatureComAdapter extends BaseAdapter{

	private Context ctx;
	private List<FeatureComInfo> list;
	public FeatureComAdapter(Context context,List<FeatureComInfo> l){
		ctx=context;
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
			convertView=LayoutInflater.from(ctx).inflate(R.layout.featurecom_item, null);
			holder=new ViewHolder();
			holder.thumb=(CircleImageView) convertView.findViewById(R.id.near_feauserthumb);
			holder.userNickname=(TextView) convertView.findViewById(R.id.near_feausername);
			holder.fea_location=(TextView) convertView.findViewById(R.id.near_fea_location);
			holder.type=(TextView) convertView.findViewById(R.id.near_fea_type);
			holder.lasttime=(TextView) convertView.findViewById(R.id.lasttime);
			holder.comnum=(TextView) convertView.findViewById(R.id.commentnum);
			holder.infoContent=(TextView) convertView.findViewById(R.id.near_fea_content);
			holder.secondContent=(TextView) convertView.findViewById(R.id.fea_seccontent);
			
//			holder.list[0]=(ImageView) convertView.findViewById(R.id.feature_pic_1);
//			holder.list[1]=(ImageView) convertView.findViewById(R.id.feature_pic_2);
//			holder.list[2]=(ImageView) convertView.findViewById(R.id.feature_pic_3);
//			holder.list[3]=(ImageView) convertView.findViewById(R.id.feature_pic_4);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();			
		}
		//头像
		//
		holder.userNickname.setText(list.get(position).getUserNickname());
		holder.fea_location.setText(list.get(position).getFea_location());
		holder.type.setText(list.get(position).getType());
		holder.lasttime.setText(list.get(position).getLasttime());
		holder.comnum.setText(list.get(position).getComnumber());
		holder.infoContent.setText(list.get(position).getInfoContent());
		holder.secondContent.setText(list.get(position).getSecContent());
		//设置四张图片
		return convertView;
	}
	class ViewHolder{
		CircleImageView thumb;
		TextView userNickname;
		TextView fea_location;
		TextView type;
		TextView lasttime;
		TextView comnum;
		TextView infoContent;
		TextView secondContent;
		ImageView[] list;
	}
}
