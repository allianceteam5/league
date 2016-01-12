package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.ShippingAddressBean;
import com.mine.league.R;

import java.util.List;

/**
 * Created by liug on 15/12/6.
 */
public class AddressAdapter extends BaseAdapter {
    private List<ShippingAddressBean> list;
    private Context context;

    public AddressAdapter(List<ShippingAddressBean> list, Context context) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shippingaddress, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.addressname);
            holder.number = (TextView) convertView.findViewById(R.id.addressnumber);
            holder.deaulttext = (TextView) convertView.findViewById(R.id.defaulttext);
            holder.detail = (TextView) convertView.findViewById(R.id.addressdetail);
            holder.selected = (ImageView) convertView.findViewById(R.id.default_selected);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());
        holder.number.setText(list.get(position).getAphone());
        if (list.get(position).getIsdefault() == 1) {
            holder.deaulttext.setVisibility(View.VISIBLE);
            holder.selected.setVisibility(View.VISIBLE);
        } else {
            holder.deaulttext.setVisibility(View.GONE);
            holder.selected.setVisibility(View.INVISIBLE);
        }
        String temp = new String(list.get(position).getAddress());
        holder.detail.setText(temp.replace(" ", ""));
        return convertView;
    }

    class ViewHolder {
        TextView name, number, deaulttext, detail;
        ImageView selected;
    }
}
