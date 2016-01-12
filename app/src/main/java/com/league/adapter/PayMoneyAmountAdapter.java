package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mine.league.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liug on 16/1/10.
 */
public class PayMoneyAmountAdapter extends BaseAdapter {
    private List<Integer> list;
    private boolean[] control;
    private Context context;

    public PayMoneyAmountAdapter(List<Integer> list, Context context, boolean[] control) {
        this.list = list;
        this.context = context;
        this.control = control;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_paymoneyamout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvAmount.setText(list.get(position) + "");
        if (control[position]) {
            holder.tvAmount.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvAmount.setBackgroundColor(context.getResources().getColor(R.color.red));
        } else {
            holder.tvAmount.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvAmount.setBackgroundColor(context.getResources().getColor(R.color.black2));
        }
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'layout_item_paymoneyamout.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.tv_amount)
        TextView tvAmount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
