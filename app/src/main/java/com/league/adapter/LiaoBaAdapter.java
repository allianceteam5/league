package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.LiaoBaUserInfo;
import com.mine.league.R;

import java.util.List;

/**
 * Created by liug on 15/11/10.
 */
public class LiaoBaAdapter extends BaseAdapter{

    private List<LiaoBaUserInfo> list;
    private Context ctx;

    public LiaoBaAdapter(List<LiaoBaUserInfo> list, Context ctx) {
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
            convertView= LayoutInflater.from(ctx).inflate(R.layout.item_liaoba_user,null);
            holder=new ViewHolder();
            holder.thumb= (ImageView) convertView.findViewById(R.id.thumb);
            holder.name= (TextView) convertView.findViewById(R.id.username);
            holder.time= (TextView) convertView.findViewById(R.id.pushtime);
            holder.new_hot= (ImageView) convertView.findViewById(R.id.new_hot);
            holder.title= (TextView) convertView.findViewById(R.id.title);
            holder.content= (TextView) convertView.findViewById(R.id.comment);
            holder.yiguanzhu= (TextView) convertView.findViewById(R.id.yiguanzhu);
            holder.guanzhu=convertView.findViewById(R.id.guanzhu);
            holder.like= (ImageView) convertView.findViewById(R.id.dianzan);
            holder.dianzanshu= (TextView) convertView.findViewById(R.id.dianzannum);
            holder.commentnum= (TextView) convertView.findViewById(R.id.comnum);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());

        return convertView;
    }
    class ViewHolder{
        ImageView thumb;
        TextView name,time;
        ImageView new_hot;
        TextView title;
        TextView content;
        TextView yiguanzhu;
        View guanzhu;
        ImageView like;
        TextView dianzanshu;
        TextView commentnum;
    }
}
