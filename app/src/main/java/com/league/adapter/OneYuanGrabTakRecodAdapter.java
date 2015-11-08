package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.OneYuanTakingMember;
import com.mine.league.R;

import java.util.List;

/**
 * Created by liug on 15/11/7.
 */
public class OneYuanGrabTakRecodAdapter extends BaseAdapter{

    private List<OneYuanTakingMember> list;
    private Context ctx;

    public OneYuanGrabTakRecodAdapter(List<OneYuanTakingMember> list, Context ctx) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(ctx).inflate(R.layout.item_taking_record,null);
            holder=new ViewHolder();
            holder.thumb= (ImageView) convertView.findViewById(R.id.thumb);
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.takTime= (TextView) convertView.findViewById(R.id.taktime);
            holder.takNum= (TextView) convertView.findViewById(R.id.taknum);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());
        holder.takTime.setText(list.get(position).getTakeTime());
        holder.takNum.setText(list.get(position).getTakeNum()+"");
        return convertView;
    }
    class ViewHolder{
        ImageView thumb;
        TextView name;
        TextView takTime;
        TextView takNum;

    }
}
