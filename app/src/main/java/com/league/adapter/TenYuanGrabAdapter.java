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
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(ctx).inflate(R.layout.gridview_tenyuan_item,null);
            holder.period = (TextView) convertView.findViewById(R.id.period);
            holder.mName_Monery = (TextView) convertView.findViewById(R.id.name_money);
            holder.totalPeo = (TextView) convertView.findViewById(R.id.totalpeo);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressbar);
            holder.takingPeo = (TextView) convertView.findViewById(R.id.takingpeo);
            holder.leavePeo=(TextView) convertView.findViewById(R.id.leavepeo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.period.setText("(第" + list.get(position).getmPeriods() + "期)");
        holder.mName_Monery.setText(list.get(position).getmMoney());
        holder.totalPeo.setText(list.get(position).getmTotalPeo() + "人次");
        holder.takingPeo.setText("已参与"+list.get(position).getmTakingPeo());
        holder.leavePeo.setText("剩余"+list.get(position).getmLessPeo());
        return convertView;
    }

    class ViewHolder {
        ImageView thumb;
        TextView period;
        TextView mName_Monery;
        TextView totalPeo;
        ProgressBar progressBar;
        TextView takingPeo;
        TextView leavePeo;
    }
}
