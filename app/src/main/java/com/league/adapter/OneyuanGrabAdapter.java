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

import com.league.bean.OneYuanBean;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liug on 15/11/1.
 */
public class OneyuanGrabAdapter extends BaseAdapter {
    private Context ctx;
    private List<OneYuanBean> list;

    public OneyuanGrabAdapter(Context ctx, List<OneYuanBean> list) {
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
            holder.thumb = (ImageView) convertView.findViewById(R.id.one_image);
            holder.mName = (TextView) convertView.findViewById(R.id.name);
            holder.totalPeo = (TextView) convertView.findViewById(R.id.totalpeo);
            holder.leavePeo = (TextView) convertView.findViewById(R.id.leavepeo);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressbar);
            holder.progress = (TextView) convertView.findViewById(R.id.progress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OneYuanBean oneYuanBean = list.get(position);
        if (!TextUtils.isEmpty(list.get(position).getPicture()))
            Picasso.with(ctx).load(list.get(position).getPicture()).into(holder.thumb);
        holder.mName.setText(oneYuanBean.getTitle());
        holder.totalPeo.setText(oneYuanBean.getNeeded() + "人次");
        holder.leavePeo.setText(oneYuanBean.getRemain());
        float need = Float.valueOf(oneYuanBean.getNeeded());
        float remain = Float.valueOf(oneYuanBean.getRemain());
        holder.progress.setText((int) (((need - remain) / need) * 100) + "%");
        holder.progressBar.setProgress((int)( ((need - remain) / need) * 100));

        return convertView;
    }

    class ViewHolder {
        ImageView thumb;
        TextView mName;
        TextView totalPeo;
        TextView leavePeo;
        ProgressBar progressBar;
        TextView progress;
    }
}
