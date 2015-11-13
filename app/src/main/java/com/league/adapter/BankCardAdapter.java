package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.BankCardInfo;
import com.mine.league.R;

import java.util.List;

/**
 * Created by liug on 15/11/12.
 */
public class BankCardAdapter extends BaseAdapter{
    private List<BankCardInfo> list;
    private Context ctx;

    public BankCardAdapter(List<BankCardInfo> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
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
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(ctx).inflate(R.layout.item_bankcard,null);
            holder.icon_bank= (ImageView) convertView.findViewById(R.id.iconbank);
            holder.bankname= (TextView) convertView.findViewById(R.id.bankname);
            holder.banknum= (TextView) convertView.findViewById(R.id.banknum);
            holder.banktype= (TextView) convertView.findViewById(R.id.banktype);
            holder.icon_select= (ImageView) convertView.findViewById(R.id.icon_selected);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.bankname.setText(list.get(position).getBankName());
        holder.banknum.setText(list.get(position).getCardID().substring(list.get(position).getCardID().length()-2,list.get(position).getCardID().length()));
        holder.banktype.setText(list.get(position).getBankType());
        return  convertView;
    }
    class ViewHolder{
        ImageView icon_bank;
        TextView bankname;
        TextView banknum;
        TextView banktype;
        ImageView icon_select;
    }
}
