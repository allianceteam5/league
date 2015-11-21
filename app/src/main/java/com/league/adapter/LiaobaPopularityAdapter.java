package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.PopularityBean;
import com.league.interf.ListItemClickHelp;
import com.league.widget.CircleImageView;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liug on 15/11/13.
 */
public class LiaobaPopularityAdapter extends BaseAdapter{
    private List<PopularityBean> list;
    private Context ctx;
    private ListItemClickHelp callBack;

    public LiaobaPopularityAdapter(List<PopularityBean> list, Context ctx,ListItemClickHelp callBack) {
        this.list = list;
        this.ctx = ctx;
        this.callBack=callBack;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(ctx).inflate(R.layout.item_liaoba_popularity,null);
            holder=new ViewHolder();
            holder.iconLeft= (ImageView) convertView.findViewById(R.id.icon_left);
            holder.headPortrait= (CircleImageView) convertView.findViewById(R.id.head_portrait);
            holder.usrName= (TextView) convertView.findViewById(R.id.usrname);
            holder.iconRight= (ImageView) convertView.findViewById(R.id.iconright);
            holder.funNum= (TextView) convertView.findViewById(R.id.funnum);
            holder.concern= (Button) convertView.findViewById(R.id.concern);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        if(position==0){
            holder.iconLeft.setImageResource(R.drawable.icon_popularity_1);
            Picasso.with(ctx).load(list.get(position).getThumb()).into(holder.headPortrait);
            holder.usrName.setText(list.get(position).getNickname());
            holder.iconRight.setImageResource(R.drawable.icon_popularity_1_copy);
            holder.funNum.setText(list.get(position).getConcerncount());
            if("1".equals(list.get(position).getIsconcerned())){
                holder.concern.setText("已关注");
            }else{
                final View view=convertView;
                final int pos=position;
                final int id=holder.concern.getId();
                holder.concern.setText("关注");
                holder.concern.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.onClick(view,parent,pos,id);
                    }
                });
            }
        }else if(position==1){
            holder.iconLeft.setImageResource(R.drawable.icon_popularity_2);
            Picasso.with(ctx).load(list.get(position).getThumb()).into(holder.headPortrait);
            holder.usrName.setText(list.get(position).getNickname());
            holder.iconRight.setImageResource(R.drawable.icon_popularity_2_copy);
            holder.funNum.setText(list.get(position).getConcerncount());
            if("1".equals(list.get(position).getIsconcerned())){
                holder.concern.setText("已关注");
                holder.concern.setEnabled(false);
                holder.concern.setBackgroundResource(R.drawable.liaobaconcerned);
            }else{
                final View view=convertView;
                final int pos=position;
                final int id=holder.concern.getId();
                holder.concern.setText("关注");
                holder.concern.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.onClick(view,parent,pos,id);
                    }
                });
            }
        }else if(position==2){
            holder.iconLeft.setImageResource(R.drawable.icon_popularity_3);
            Picasso.with(ctx).load(list.get(position).getThumb()).into(holder.headPortrait);
            holder.usrName.setText(list.get(position).getNickname());
            holder.iconRight.setImageResource(R.drawable.icon_popularity_3_copy);
            holder.funNum.setText(list.get(position).getConcerncount());
            if("1".equals(list.get(position).getIsconcerned())){
                holder.concern.setText("已关注");
                holder.concern.setEnabled(false);
                holder.concern.setBackgroundResource(R.drawable.liaobaconcerned);
            }else{
                final View view=convertView;
                final int pos=position;
                final int id=holder.concern.getId();
                holder.concern.setText("关注");
                holder.concern.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.onClick(view,parent,pos,id);
                    }
                });
            }
        }else{
            holder.iconLeft.setVisibility(View.INVISIBLE);
            Picasso.with(ctx).load(list.get(position).getThumb()).into(holder.headPortrait);
            holder.usrName.setText(list.get(position).getNickname());
            holder.iconRight.setVisibility(View.INVISIBLE);
            holder.funNum.setText(list.get(position).getConcerncount());
            if("1".equals(list.get(position).getIsconcerned())){
                holder.concern.setText("已关注");
                holder.concern.setEnabled(false);
                holder.concern.setBackgroundResource(R.drawable.liaobaconcerned);
            }else{
                final View view=convertView;
                final int pos=position;
                final int id=holder.concern.getId();
                holder.concern.setText("关注");
                holder.concern.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.onClick(view,parent,pos,id);
                    }
                });
            }
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iconLeft;
        CircleImageView headPortrait;
        TextView usrName;
        ImageView iconRight;
        TextView funNum;
        Button concern;
    }
}
