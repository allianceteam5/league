package com.league.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.AnnouncedTheLatestBean;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liug on 15/11/24.
 */
public class LatestAnnouncedAdapter extends BaseAdapter {
    private List<AnnouncedTheLatestBean> list;
    private Context context;

    public LatestAnnouncedAdapter(List<AnnouncedTheLatestBean> list, Context context) {
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_latestannounce, null);
            holder.picture = (ImageView) convertView.findViewById(R.id.latest_picture);
            holder.title = (TextView) convertView.findViewById(R.id.latest_title);
            holder.timeMinu = (TextView) convertView.findViewById(R.id.countminu);
            holder.timeMill = (TextView) convertView.findViewById(R.id.countmill);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(list.get(position).getPicture()))
            Picasso.with(context).load(list.get(position).getPicture()).into(holder.picture);
        holder.title.setText(list.get(position).getTitle());
        TimeCount count = new TimeCount(Long.valueOf(list.get(position).getEnd_at())*1000-System.currentTimeMillis(), 1000, holder.timeMinu,holder.timeMill);
        count.start();
        return convertView;
    }

    class ViewHolder {
        ImageView picture;
        TextView title, timeMinu, timeMill;
    }

    class TimeCount extends CountDownTimer {

        private TextView timeMinu,timeMill;

        public TimeCount(long millisInFuture, long countDownInterval, TextView timeMinu,TextView timeMill) {
            super(millisInFuture, countDownInterval);
            this.timeMinu=timeMinu;
            this.timeMill=timeMill;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            long minutes=millisUntilFinished / 60000;
            long mill=(millisUntilFinished %60000)/1000;
            if(timeMinu.getText().toString().equals(minutes+"")){
                timeMill.setText(mill+"");
            }else{
                timeMill.setText(mill + "");
                timeMinu.setText(minutes+"");
            }
        }

        @Override
        public void onFinish() {

        }
    }
}
