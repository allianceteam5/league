package com.league.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.league.bean.CountDetailBean;
import com.league.utils.Utils;
import com.league.widget.CircleImageView;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liug on 16/1/20.
 */
public class CountDetailAdapter extends BaseAdapter {
    private Context context;
    private List<CountDetailBean> list;

    public CountDetailAdapter(Context context, List<CountDetailBean> list) {
        this.context = context;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_countdetail, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(list.get(position).getNickname());
        String t = list.get(position).getCreated_at();
        holder.tvTime.setText(Utils.TimeStamp2SystemNotificationDate(Long.valueOf(t.substring(0,t.indexOf("."))) * 1000)+t.substring(t.indexOf("."),t.indexOf(".")+3));
        if(!TextUtils.isEmpty(list.get(position).getThumb())){
            Picasso.with(context).load(list.get(position).getThumb()).into(holder.avatar);
        }

        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'layout_item_countdetail.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.avatar)
        CircleImageView avatar;
        @Bind(R.id.tv_name)
        TextView tvName;

        @Bind(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
