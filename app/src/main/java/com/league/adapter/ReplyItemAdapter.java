package com.league.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.league.bean.CircleMessageBean;
import com.mine.league.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * @author liugang
 * @date 2015年9月19日
 */
public class ReplyItemAdapter extends BaseAdapter {

    private List<CircleMessageBean.ReplysEntity> list;
    private Context ctx;

    public ReplyItemAdapter(Context context, List<CircleMessageBean.ReplysEntity> list) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.layout_item_reply, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CircleMessageBean.ReplysEntity replysEntity = list.get(position);
        if (!TextUtils.isEmpty(replysEntity.getTophone())) {
            holder.tvReply.setText(replysEntity.getFromnickname() + " 回复 " + replysEntity.getTonickname() + "：");
            Link toLink = new Link(replysEntity.getTonickname()).setTextColor(ctx.getResources().getColor(R.color.red)).setUnderlined(false);
            Link fromLink = new Link(replysEntity.getFromnickname()).setTextColor(ctx.getResources().getColor(R.color.red)).setUnderlined(false);
            LinkBuilder.on(holder.tvReply).addLink(fromLink).addLink(toLink).build();
        } else {
            holder.tvReply.setText(replysEntity.getFromnickname() + "：");
            Link fromLink = new Link(replysEntity.getFromnickname()).setTextColor(ctx.getResources().getColor(R.color.red)).setUnderlined(false);
            LinkBuilder.on(holder.tvReply).addLink(fromLink).build();
        }

        if (!TextUtils.isEmpty(replysEntity.getContent()))
            holder.tvReply.append(replysEntity.getContent());
        return convertView;
    }

    public void setData(List<CircleMessageBean.ReplysEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder {
        @Bind(R.id.tv_reply)
        TextView tvReply;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
