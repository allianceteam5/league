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
import com.league.bean.RecommendationInfoBean;
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
public class RecommendationCommentAdapter extends BaseAdapter {

    private List<CommentBean> list;
    private Context ctx;

    public RecommendationCommentAdapter(Context context, List<CommentBean> list) {
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
        CommentBean commentBean = list.get(position);
        holder.nikename.setText(commentBean.getNickname());
        holder.content.setText(commentBean.getContent());
        holder.lasttime.setText(Utils.generateStringByTime(commentBean.getCreated_at()));
        if (!TextUtils.isEmpty(commentBean.getThumb()))
            Picasso.with(ctx).load(commentBean.getThumb()).resize(80,80).centerCrop().into(holder.thumb);
        else
            Picasso.with(ctx).load(R.drawable.example).into(holder.thumb);
        return convertView;
    }

    public void setData(List<CommentBean> list){
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
