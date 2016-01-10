package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.league.bean.EnvelopWinBean;
import com.mine.league.R;

import java.util.List;

/**
 * Created by liug on 15/12/30.
 */
public class EnvelopeWinnerAdapter extends BaseAdapter {
    private Context context;
    private List<EnvelopWinBean> list;

    public EnvelopeWinnerAdapter(Context context, List<EnvelopWinBean> list) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_redwinner, null);
            holder = new ViewHolder();
            holder.nickName = (TextView) convertView.findViewById(R.id.tv_winnername);
            holder.money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nickName.setText(list.get(position).getNickname());
        holder.money.setText(list.get(position).getCount() + "元");
        return convertView;
    }

    class ViewHolder {
        TextView nickName, money;
    }
}
