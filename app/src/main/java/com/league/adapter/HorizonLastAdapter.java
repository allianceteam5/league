package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.league.bean.AnnouncedTheLatestBean;
import com.mine.league.R;

import java.util.List;

/**
 * Created by liug on 15/11/1.
 */
public class HorizonLastAdapter extends BaseAdapter{
    private Context ctx;
    private List<AnnouncedTheLatestBean> list;

    public HorizonLastAdapter(Context ctx,List<AnnouncedTheLatestBean> list) {
        this.ctx=ctx;
        this.list=list;
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
            convertView= LayoutInflater.from(ctx).inflate(R.layout.horizontal_last_item,null);
            holder.imageView=(ImageView)convertView.findViewById(R.id.lastnew_image);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;

    }
}
