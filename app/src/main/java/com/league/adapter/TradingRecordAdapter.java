package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.league.bean.TradingDetailBean;
import com.mine.league.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liug on 16/1/14.
 */
public class TradingRecordAdapter extends BaseAdapter {
    private Context context;
    private List<TradingDetailBean> list;

    public TradingRecordAdapter(Context context, List<TradingDetailBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tradingrecord, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvPaytime.setText(list.get(position).getPayTime());
        holder.tvPaydate.setText(list.get(position).getPayDate());
        holder.tvPaymoney.setText(list.get(position).getPayMoney());
        holder.tvPaycontent.setText(list.get(position).getPayContent());
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_tradingrecord.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.tv_paytime)
        TextView tvPaytime;
        @Bind(R.id.tv_paydate)
        TextView tvPaydate;
        @Bind(R.id.tv_paymoney)
        TextView tvPaymoney;
        @Bind(R.id.tv_paycontent)
        TextView tvPaycontent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
