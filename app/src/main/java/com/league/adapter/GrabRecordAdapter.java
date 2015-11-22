package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.GrabBean;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liug on 15/11/22.
 */
public class GrabRecordAdapter extends BaseAdapter{
    private Context ctx;
    private List<GrabBean> list;

    public GrabRecordAdapter(Context ctx, List<GrabBean> list) {
        this.ctx = ctx;
        this.list = list;
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
            holder=new ViewHolder();
            convertView= LayoutInflater.from(ctx).inflate(R.layout.item_grabrecords,null);
            holder.picture= (ImageView) convertView.findViewById(R.id.picture);
            holder.version= (TextView) convertView.findViewById(R.id.period);
            holder.title= (TextView) convertView.findViewById(R.id.title);
            holder.total= (TextView) convertView.findViewById(R.id.totalpeople);
            holder.thiscount= (TextView) convertView.findViewById(R.id.thistake);
            holder.endtime= (TextView) convertView.findViewById(R.id.endtime);
            holder.winnername= (TextView) convertView.findViewById(R.id.winname);
            holder.winnercount= (TextView) convertView.findViewById(R.id.count);
            holder.winnernumber= (TextView) convertView.findViewById(R.id.winnumber);
            holder.linearlayout=convertView.findViewById(R.id.winner);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(ctx).load(list.get(position).getPicture()).into(holder.picture);
        holder.version.setText(list.get(position).getVersion());
        holder.title.setText(list.get(position).getTitle());
        holder.total.setText(list.get(position).getNeeded());
        holder.thiscount.setText(list.get(position).getCount());
        holder.endtime.setText(list.get(position).getEnd_at());
        if(list.get(position).getWinnercount()==null){
            holder.linearlayout.setVisibility(View.GONE);
        }else{
            holder.linearlayout.setVisibility(View.VISIBLE);
            holder.winnername.setText(list.get(position).getNickname());
            holder.winnernumber.setText(list.get(position).getWinnernumber());
            holder.winnercount.setText(list.get(position).getWinnercount());
        }
        return convertView;
    }
    class ViewHolder {
        ImageView picture;
        TextView version,title,total,thiscount,endtime,winnername,winnercount,winnernumber;
        View linearlayout;
    }
}
