package com.league.adapter;

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
		}else{
			
		}
		return null;
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
