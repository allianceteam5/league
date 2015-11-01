package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.league.bean.TenYuanGrabBean;
import com.mine.league.R;

import java.util.List;

/**
 * Created by liug on 15/10/21.
 */
public class HorizonListAdapter extends BaseAdapter {
    private Context context;
    private List<TenYuanGrabBean> listdata;


    public HorizonListAdapter(Context ctx, List<TenYuanGrabBean> list) {
        listdata = list;
        context = ctx;

    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return listdata.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.horizontal_list_item, null);
            holder.thumb = (ImageView) convertView.findViewById(R.id.ten_image);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.process = (TextView) convertView.findViewById(R.id.txt_progress);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressbar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.money.setText(listdata.get(position).getmMoney() + "金币");
        float persent = listdata.get(position).getmTakingPeo() / (float) listdata.get(position).getmTotalPeo();

        holder.process.setText((int) (persent * 100) + "%");
        holder.progressBar.setProgress((int) persent * 100);
        return convertView;
    }

    class ViewHolder {
        ImageView thumb;
        TextView money;
        TextView process;
        ProgressBar progressBar;
    }
}
