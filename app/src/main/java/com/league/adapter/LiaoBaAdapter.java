package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.LiaoBaUserInfo;
import com.league.interf.ListItemClickHelp;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liug on 15/11/10.
 */
public class LiaoBaAdapter extends BaseAdapter{

    private List<LiaoBaUserInfo> list;
    private Context ctx;
    private int type=0;//0表示最新 1表示最热
    private ListItemClickHelp callBack;
    public LiaoBaAdapter(List<LiaoBaUserInfo> list, Context ctx,int type,ListItemClickHelp callBack) {
        this.list = list;
        this.ctx = ctx;
        this.type=type;
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(ctx).inflate(R.layout.item_liaoba_user,null);
            holder=new ViewHolder();
            holder.thumb= (ImageView) convertView.findViewById(R.id.thumb);
            holder.name= (TextView) convertView.findViewById(R.id.username);
            holder.time= (TextView) convertView.findViewById(R.id.pushtime);
            holder.new_hot= (ImageView) convertView.findViewById(R.id.new_hot);
            holder.title= (TextView) convertView.findViewById(R.id.title);
            holder.content= (TextView) convertView.findViewById(R.id.comment);
            holder.yiguanzhu= (TextView) convertView.findViewById(R.id.yiguanzhu);
            holder.guanzhu=convertView.findViewById(R.id.guanzhu);
            holder.like= (ImageView) convertView.findViewById(R.id.dianzan);
            holder.dianzanshu= (TextView) convertView.findViewById(R.id.dianzannum);
            holder.commentnum= (TextView) convertView.findViewById(R.id.comnum);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        Picasso.with(ctx).load(list.get(position).getThumb()).into(holder.thumb);
        holder.name.setText(list.get(position).getNickname());
        holder.time.setText(list.get(position).getCreated_at());
        if(type==0){
            holder.new_hot.setImageResource(R.drawable.liaoba_new);
        }else if(type==1){
            holder.new_hot.setImageResource(R.drawable.liaoba_hot);
        }else holder.new_hot.setVisibility(View.GONE);

        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());

        if("1".equals(list.get(position).getIsconcerned())){
            holder.yiguanzhu.setVisibility(View.VISIBLE);
            holder.guanzhu.setVisibility(View.GONE);
        }else{
            holder.yiguanzhu.setVisibility(View.GONE);
            holder.guanzhu.setVisibility(View.VISIBLE);
        }
        holder.dianzanshu.setText(list.get(position).getLikecount());
        holder.commentnum.setText(list.get(position).getReplycount());
        if("1".equals(list.get(position).getIsliked())){
           holder.like.setImageResource(R.drawable.liaoba_like);
        }else{
            holder.like.setImageResource(R.drawable.liaoba_dianzan);
        }
        final View view=convertView;
        final int pos=position;
        final int one=holder.like.getId();
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClick(view,parent,pos,one);
            }
        });
        return convertView;
    }
    class ViewHolder{
        ImageView thumb;
        TextView name,time;
        ImageView new_hot;
        TextView title;
        TextView content;
        TextView yiguanzhu;
        View guanzhu;
        ImageView like;
        TextView dianzanshu;
        TextView commentnum;
    }
}
