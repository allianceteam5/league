package com.league.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.CommentBean;
import com.league.bean.ReplysEntity;
import com.league.utils.Utils;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author liugang
 * @date 2015年9月19日
 */
public class LiaobaCommentAdapter extends BaseAdapter {

    private List<ReplysEntity> list;
    private Context ctx;

    public LiaobaCommentAdapter(Context context, List<ReplysEntity> list) {
        ctx = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (list == null)
            return 0;
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.layout_item_recommendationcomment, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ReplysEntity replysEntity = list.get(position);
        holder.nikename.setText(replysEntity.getFromnickname());
        holder.content.setText(replysEntity.getContent());
        holder.lasttime.setText(Utils.generateStringByTime(replysEntity.getCreated_at()));
        if (!TextUtils.isEmpty(replysEntity.getFromthumb()))
            Picasso.with(ctx).load(replysEntity.getFromthumb()).placeholder(R.drawable.example).resize(80,80).centerCrop().into(holder.thumb);
        else
            Picasso.with(ctx).load(R.drawable.example).into(holder.thumb);
        return convertView;
    }

    public void setData(List<ReplysEntity> list){
        this.list = list;
    }
    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'layout_item_recommendationcomment.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.thumb)
        ImageView thumb;
        @Bind(R.id.nikename)
        TextView nikename;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.lasttime)
        TextView lasttime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
