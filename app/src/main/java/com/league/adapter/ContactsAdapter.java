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

import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.league.activity.UserHomePageActivity;
import com.league.bean.ContactBean;
import com.league.bean.RegisterUserBean;
import com.league.bean.SearchUserBean;
import com.league.utils.IContants;
import com.league.utils.ToastUtils;
import com.league.widget.CircleImageView;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liug on 15/12/6.
 */
public class ContactsAdapter extends BaseAdapter {
    private List<RegisterUserBean> list;
    private Context context;

    public ContactsAdapter(Context context, List<RegisterUserBean> list) {
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_contact, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final RegisterUserBean registerUserBean = list.get(position);
        holder.tvNickname.setText(registerUserBean.getNickname());
//        holder.tvNickname.setText(contactBean.getPhone());
        if (!TextUtils.isEmpty(registerUserBean.getThumb()))
            Picasso.with(context).load(registerUserBean.getThumb()).resize(100,100).centerCrop().into(holder.ivThumb);
        else
            Picasso.with(context).load(R.drawable.example).into(holder.ivThumb);

        if (registerUserBean.getIsfriend() == 1){
            holder.btnAddfriend.setText("已添加");
            holder.btnAddfriend.setEnabled(false);
        }else {
            holder.btnAddfriend.setText("添加");
            holder.btnAddfriend.setEnabled(true);
        }

        holder.btnAddfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EMContactManager.getInstance().addContact(registerUserBean.getId(), "请加我好友吧");
                    holder.btnAddfriend.setEnabled(false);
                    ToastUtils.showShortToast(context, "已发出请求");
                } catch (EaseMobException e) {
                    e.printStackTrace();
                }
            }
        });
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, UserHomePageActivity.class);
//                intent.putExtra(IContants.PHONE, searchUserBean.getPhone());
//                context.startActivity(intent);
//            }
//        });
        return convertView;
    }

    public void setData(List<RegisterUserBean> list){
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
        @Bind(R.id.tv_nickname)
        TextView tvNickname;
        @Bind(R.id.btn_addfriend)
        Button btnAddfriend;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
