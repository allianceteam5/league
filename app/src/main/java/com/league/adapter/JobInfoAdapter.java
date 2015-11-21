package com.league.adapter;

import java.util.ArrayList;
import java.util.List;

import com.league.bean.JobInfoBean;
import com.league.utils.Constants;
import com.league.utils.DateFormatUtils;
import com.league.utils.Utils;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author liugang
 * @date 2015年9月19日
 */
public class JobInfoAdapter extends BaseAdapter {

    private List<JobInfoBean> list;
    private Context ctx;

    public JobInfoAdapter(Context context, List<JobInfoBean> list) {
        ctx = context;
        this.list = list;
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
            holder = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.layout_item_infojob, null);
            holder.thumb = (ImageView) convertView.findViewById(R.id.near_userthumb);
            holder.userNickname = (TextView) convertView.findViewById(R.id.near_username);
            holder.fullorpart_timejob = (TextView) convertView.findViewById(R.id.near_partjobtype);
            holder.profession = (TextView) convertView.findViewById(R.id.near_userprofession);
            holder.lasttime = (TextView) convertView.findViewById(R.id.lasttime);
            holder.infoContent = (TextView) convertView.findViewById(R.id.near_usercontent);
            holder.eduction = (TextView) convertView.findViewById(R.id.near_usereducation);
            holder.worktime = (TextView) convertView.findViewById(R.id.near_userworktime);
            holder.current_status = (TextView) convertView.findViewById(R.id.near_usercurrentstaus);
            holder.leave_message = (TextView) convertView.findViewById(R.id.near_userleavemessage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JobInfoBean jobinfo = list.get(position);
        Picasso.with(ctx).load(jobinfo.getThumb()).into(holder.thumb);
        holder.fullorpart_timejob.setText(jobinfo.getJobPropertyName());
        holder.userNickname.setText(jobinfo.getNickname());
        holder.current_status.setText(jobinfo.getStatus());
        holder.profession.setText(jobinfo.getProfession());
        holder.lasttime.setText(Utils.generateStringByTime(jobinfo.getCreatedtime()));
        holder.infoContent.setText(jobinfo.getTitle());
        holder.eduction.setText(Constants.DEGREEITEMS.get(jobinfo.getDegree()));
        holder.worktime.setText(DateFormatUtils.TimeStamp2Date(jobinfo.getWorktime()));
        holder.leave_message.setText(jobinfo.getIntro());
        return convertView;
    }

    public void setData(ArrayList<JobInfoBean> list){
        this.list = list;
    }

    class ViewHolder {
        ImageView thumb;
        TextView userNickname;
        TextView fullorpart_timejob;
        TextView profession;
        TextView lasttime;
        TextView infoContent;
        TextView eduction;
        TextView worktime;
        TextView current_status;
        TextView leave_message;
    }
}
