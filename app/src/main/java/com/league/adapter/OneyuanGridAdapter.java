package com.league.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.league.bean.OneYuanBean;
import com.mine.league.R;

import java.util.List;

/**
 * Created by liug on 15/11/1.
 */
public class OneyuanGridAdapter extends BaseAdapter {
    private Context ctx;
    private List<OneYuanBean> list;

    public OneyuanGridAdapter(Context ctx, List<OneYuanBean> list) {
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
        ViewHolder holder = null;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.gridview_one_item, null);
            holder.period = (TextView) convertView.findViewById(R.id.period);
            holder.mName = (TextView) convertView.findViewById(R.id.name);
            holder.totalPeo = (TextView) convertView.findViewById(R.id.totalpeo);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressbar);
            holder.progress = (TextView) convertView.findViewById(R.id.progress);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.period.setText("(第"+list.get(position).getmPeriod()+"期)");
        holder.mName.setText(list.get(position).getmName());
        holder.totalPeo.setText(list.get(position).getmTotalPeo()+"人次");

        return convertView;
    }

    class ViewHolder {
        ImageView thumb;
        TextView period;
        TextView mName;
        TextView totalPeo;
        ProgressBar progressBar;
        TextView progress;
    }
}
