package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.LiaoBaUserInfo;
import com.league.widget.CircleImageView;
import com.mine.league.R;

import java.util.List;

/**
 * Created by liug on 15/11/13.
 */
public class LiaobaPopularityAdapter extends BaseAdapter{
    private List<LiaoBaUserInfo> list;
    private Context ctx;

    public LiaobaPopularityAdapter(List<LiaoBaUserInfo> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(ctx).inflate(R.layout.item_liaoba_popularity,null);
            holder=new ViewHolder();
            holder.iconLeft= (ImageView) convertView.findViewById(R.id.icon_left);
            holder.headPortrait= (CircleImageView) convertView.findViewById(R.id.head_portrait);
            holder.usrName= (TextView) convertView.findViewById(R.id.usrname);
            holder.iconRight= (ImageView) convertView.findViewById(R.id.iconright);
            holder.funNum= (TextView) convertView.findViewById(R.id.funnum);
            holder.concern= (Button) convertView.findViewById(R.id.concern);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iconLeft;
        CircleImageView headPortrait;
        TextView usrName;
        ImageView iconRight;
        TextView funNum;
        Button concern;
    }
}
