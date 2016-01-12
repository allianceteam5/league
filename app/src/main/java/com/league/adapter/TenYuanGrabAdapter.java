package com.league.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.league.bean.TenYuanGrabBean;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liug on 15/11/2.
 */
public class TenYuanGrabAdapter extends BaseAdapter {
    private Context ctx;
    private List<TenYuanGrabBean> list;

    public TenYuanGrabAdapter(Context ctx, List<TenYuanGrabBean> list) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.gridview_tenyuan_item, null);
            holder.thumb = (ImageView) convertView.findViewById(R.id.ten_image);
            holder.period = (TextView) convertView.findViewById(R.id.period);
            holder.mName_Monery = (TextView) convertView.findViewById(R.id.name);
            holder.progress = (TextView) convertView.findViewById(R.id.progress);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressbar);
            holder.totalPeo = (TextView) convertView.findViewById(R.id.totalpeo);
            holder.leavePeo = (TextView) convertView.findViewById(R.id.leavepeo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(list.get(position).getPicture()))
            Picasso.with(ctx).load(list.get(position).getPicture()).into(holder.thumb);
        else
            Picasso.with(ctx).load(R.drawable.example).into(holder.thumb);
        holder.period.setText("(第" + list.get(position).getVersion() + "期)");
        holder.mName_Monery.setText(list.get(position).getTitle());
        float need = Float.valueOf(list.get(position).getNeeded());
        float remain = Float.valueOf(list.get(position).getRemain());
        holder.progress.setText((int) ((need - remain) / need * 100) + "%");
        holder.totalPeo.setText(list.get(position).getNeeded() + "人次");
        holder.leavePeo.setText(list.get(position).getRemain() + "");
        holder.progressBar.setProgress((int) ((need - remain) / need * 100));
        return convertView;
    }

    class ViewHolder {
        ImageView thumb;
        TextView period;
        TextView mName_Monery;
        TextView progress;
        TextView totalPeo;
        ProgressBar progressBar;
        TextView leavePeo;
    }
}
