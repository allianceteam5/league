package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.league.bean.MyRecordGrabBean;
import com.mine.league.R;

import java.util.List;

/**
 * Created by liug on 15/11/22.
 */
public class DetailMyRecords extends BaseAdapter{
    private List<MyRecordGrabBean> list;
    private Context ctx;

    public DetailMyRecords(List<MyRecordGrabBean> list, Context ctx) {
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
            convertView= LayoutInflater.from(ctx).inflate(R.layout.item_detail_myrecords,null);
            holder.count= (TextView) convertView.findViewById(R.id.myrecordscount);
            holder.number= (TextView) convertView.findViewById(R.id.myrecordsnumber);
            holder.more= (Button) convertView.findViewById(R.id.getmore);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.count.setText(list.get(position).getCount());
        holder.number.setText(list.get(position).getNumbers());
        return convertView;
    }
    class ViewHolder{
        TextView count,number;
        Button more;
    }
}
