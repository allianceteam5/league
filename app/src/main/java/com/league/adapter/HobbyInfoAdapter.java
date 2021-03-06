package com.league.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.HobbyInfoBean;
import com.league.utils.Utils;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author liugang
 * @date 2015年9月26日
 */
public class HobbyInfoAdapter extends BaseAdapter {

    private Context ctx;
    private List<HobbyInfoBean> list;

    public HobbyInfoAdapter(Context c, List<HobbyInfoBean> l) {
        // TODO Auto-generated constructor stub
        ctx = c;
        list = l;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (list == null)
            return 0;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.layout_item_hobbyinfo, null);
            holder = new ViewHolder();
            holder.thumb = (ImageView) convertView.findViewById(R.id.near_friend_userthumb);
            holder.username = (TextView) convertView.findViewById(R.id.near_friend_username);
            holder.sex = (ImageView) convertView.findViewById(R.id.near_firend_usersex);
            holder.intest = (TextView) convertView.findViewById(R.id.near_friend_intes);
            holder.leavemessage = (TextView) convertView.findViewById(R.id.near_friend_leave);
            holder.lasttime = (TextView) convertView.findViewById(R.id.lasttime);
            holder.distance = (TextView) convertView.findViewById(R.id.tv_distance);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        if (!TextUtils.isEmpty(list.get(position).getPicture()))
            Picasso.with(ctx).load(list.get(position).getPicture()).resize(120, 120).centerCrop().into(holder.thumb);
        else
            Picasso.with(ctx).load(R.drawable.example).into(holder.thumb);
        holder.username.setText(list.get(position).getNickname());
        if (list.get(position).getSex() == 1) {
            holder.sex.setImageResource(R.drawable.friend_sex_man);
        } else
            holder.sex.setImageResource(R.drawable.friend_sex_woman);
        holder.intest.setText(list.get(position).getHobby());
        holder.leavemessage.setText(list.get(position).getContent());
        holder.lasttime.setText(Utils.generateStringByTime(list.get(position).getCreated_at()));

        HobbyInfoBean hobbyInfoBean = list.get(position);
        if (hobbyInfoBean.getDistance() < 1000)
            holder.distance.setText("1公里内");
        else
            holder.distance.setText(String.valueOf(hobbyInfoBean.getDistance()/1000) + "公里");
        return convertView;
    }

    class ViewHolder {
        ImageView thumb;
        TextView username;
        ImageView sex;
        TextView intest;
        TextView leavemessage;
        TextView lasttime;
        TextView distance;
    }
}
