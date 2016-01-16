package com.league.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.league.activity.UserHomePageActivity;
import com.league.bean.SearchUserBean;
import com.league.utils.ActivityUtils;
import com.league.utils.IContants;
import com.league.widget.CircleImageView;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.message.BasicNameValuePair;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liug on 15/12/6.
 */
public class SearchUsersAdapter extends BaseAdapter {
    private List<SearchUserBean> list;
    private Context context;

    public SearchUsersAdapter( Context context, List<SearchUserBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_users_search, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final SearchUserBean searchUserBean = list.get(position);
        holder.tvName.setText(searchUserBean.getNickname());
        holder.tvFriendcount.setText(searchUserBean.getFriendcount() + "");
        holder.tvLikecount.setText(searchUserBean.getConcerncount() + "");
        if (!TextUtils.isEmpty(searchUserBean.getThumb()))
            Picasso.with(context).load(searchUserBean.getThumb()).resize(100,100).centerCrop().into(holder.ivThumb);
        else
            Picasso.with(context).load(R.drawable.example).into(holder.ivThumb);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserHomePageActivity.class);
                intent.putExtra(IContants.PHONE, searchUserBean.getPhone());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void setData(List<SearchUserBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'layout_item_users_search.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.iv_thumb)
        CircleImageView ivThumb;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_friendcount)
        TextView tvFriendcount;
        @Bind(R.id.tv_likecount)
        TextView tvLikecount;
        @Bind(R.id.btn_addfriend)
        Button btnAddfriend;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
