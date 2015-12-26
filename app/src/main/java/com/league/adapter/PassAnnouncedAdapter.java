package com.league.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.PassAnnouncedBean;
import com.league.utils.Utils;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liug on 15/11/7.
 */
public class PassAnnouncedAdapter extends BaseAdapter{
    private List<PassAnnouncedBean> list;
    private Context ctx;

    public PassAnnouncedAdapter(List<PassAnnouncedBean> list, Context ctx) {
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
            holder=new ViewHolder();
            convertView= LayoutInflater.from(ctx).inflate(R.layout.item_passannounced,null);

            holder.period= (TextView) convertView.findViewById(R.id.period);
            holder.announTime= (TextView) convertView.findViewById(R.id.announcedtime);
            holder.thumb= (ImageView) convertView.findViewById(R.id.thumb);
            holder.holderName= (TextView) convertView.findViewById(R.id.holdername);
            holder.holderId= (TextView) convertView.findViewById(R.id.holderid);
            holder.holderNum= (TextView) convertView.findViewById(R.id.holdernum);
            holder.takNum= (TextView) convertView.findViewById(R.id.taknum);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.period.setText(list.get(position).getVersion());
        holder.announTime.setText(Utils.TimeStamp2SystemNotificationDate(Long.valueOf(list.get(position).getEnd_at()) * 1000));

        holder.holderName.setText(list.get(position).getNickname());
        holder.holderId.setText(list.get(position).getWinneruserid());
        holder.holderNum.setText(list.get(position).getWinnernumber());
        holder.takNum.setText(list.get(position).getCount());
        if (!TextUtils.isEmpty(list.get(position).getThumb()))
            Picasso.with(ctx).load(list.get(position).getThumb()).into(holder.thumb);
        return convertView;
    }
    class ViewHolder{
        TextView period,announTime,holderName,holderId,holderNum,takNum;
        ImageView thumb;
    }
}
