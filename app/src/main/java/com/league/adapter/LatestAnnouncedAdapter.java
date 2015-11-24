package com.league.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.OneYuanBean;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liug on 15/11/24.
 */
public class LatestAnnouncedAdapter extends BaseAdapter{
    private List<OneYuanBean> list;
    private Context context;

    public LatestAnnouncedAdapter(List<OneYuanBean> list, Context context) {
        this.list = list;
        this.context = context;
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
        final ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_latestannounce,null);
            holder.picture= (ImageView) convertView.findViewById(R.id.latest_picture);
            holder.title= (TextView) convertView.findViewById(R.id.latest_title);
            holder.time= (TextView) convertView.findViewById(R.id.txt_countdown);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(list.get(position).getPicture()).into(holder.picture);
        holder.title.setText(list.get(position).getTitle());
        TimeCount count=new TimeCount(60000,1000,holder.time);
        count.start();
        return convertView;
    }
    class ViewHolder{
        ImageView picture;
        TextView title,time;
    }
    class TimeCount extends CountDownTimer {

        private TextView tv;
        public TimeCount(long millisInFuture, long countDownInterval,TextView tv) {
            super(millisInFuture, countDownInterval);
            this.tv=tv;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            tv.setText("还剩 " + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
        }
    }
}
