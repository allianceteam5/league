package com.league.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.league.activity.ShowBigImgActivity;
import com.league.bean.CircleMessageBean;
import com.league.utils.IContants;
import com.league.utils.ToastUtils;
import com.league.utils.Utils;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.league.widget.NoScrollGridView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liug on 15/11/10.
 */
public class CircleMessageAdapter extends BaseAdapter implements IContants {

    private List<CircleMessageBean> list;
    private Context ctx;

    public CircleMessageAdapter( Context ctx, List<CircleMessageBean> list) {
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.layout_item_circle_message, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CircleMessageBean circleMessageBean = list.get(position);
        if (!TextUtils.isEmpty(circleMessageBean.getThumb()))
            Picasso.with(ctx).load(circleMessageBean.getThumb()).placeholder(R.drawable.example).resize(80, 80).centerCrop().into(holder.ivThumb);
        else
            Picasso.with(ctx).load(R.drawable.example).into(holder.ivThumb);
        holder.tvName.setText(circleMessageBean.getNickname());
        holder.tvTime.setText(Utils.generateStringByTime(circleMessageBean.getCreated_at()));
        holder.tvContent.setText(circleMessageBean.getContent());

        if (1 == circleMessageBean.getIszaned()) {
            holder.ivLike.setImageResource(R.drawable.icon_like);
        } else {
            holder.ivLike.setImageResource(R.drawable.icon_like_concel);
        }

        if (1 == circleMessageBean.getIscollected())
            holder.ivCollect.setImageResource(R.drawable.icon_collect);
        else
            holder.ivCollect.setImageResource(R.drawable.icon_collect_concel);

//        if (1 == circleMessageBean.getIsmy())
//            holder.tvDelete.setVisibility(View.VISIBLE);
//        else
        holder.tvDelete.setVisibility(View.GONE);

        final List<String> imgList = circleMessageBean.getPictureList();
        ImgGridAdapter adapter = new ImgGridAdapter(ctx, imgList);
        if (imgList != null){
            holder.gridview.setVisibility(View.VISIBLE);
            holder.gridview.setAdapter(adapter);
        }

        holder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> imgs = new ArrayList<>(imgList);
                Intent intent = new Intent(ctx, ShowBigImgActivity.class);
                intent.putStringArrayListExtra(PARAMS_IMG_LIST, imgs);
                intent.putExtra(PARAMS_INDEX, position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtil.circleMessageDelete(ctx,circleMessageBean.getId(), new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        int flag = response.optInt("flag");
                        if (flag == 1){
                        }else{
                            ToastUtils.showShortToast(ctx,"网络不太好哦");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        ToastUtils.showShortToast(ctx,"网络不太好哦");
                    }
                });
            }
        });

        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int type = 1- circleMessageBean.getIszaned();
                circleMessageBean.setIszaned(type);
                ApiUtil.circleMessageZanOrCancel(ctx,circleMessageBean.getId(),type, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        int flag = response.optInt("flag");
                        if (flag == 1){
                            if (type == 1)
                                holder.ivLike.setImageResource(R.drawable.icon_like);
                            if (type == 0)
                                holder.ivLike.setImageResource(R.drawable.icon_like_concel);
                        }else{
                            ToastUtils.showShortToast(ctx,"网络不太好哦");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        ToastUtils.showShortToast(ctx,"网络不太好哦");
                    }
                });
            }
        });

        holder.ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int type = 1- circleMessageBean.getIscollected();
                circleMessageBean.setIscollected(type);
                ApiUtil.circleMessageCollectOrCancel(ctx, circleMessageBean.getId(), type, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        int flag = response.optInt("flag");
                        if (flag == 1) {
                            if (type == 1)
                                holder.ivCollect.setImageResource(R.drawable.icon_collect);
                            if (type == 0)
                                holder.ivCollect.setImageResource(R.drawable.icon_collect_concel);
                        } else {
                            ToastUtils.showShortToast(ctx, "网络不太好哦");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        ToastUtils.showShortToast(ctx, "网络不太好哦");
                    }
                });
            }
        });

        return convertView;
    }

    public void setData(List<CircleMessageBean> list){
        this.list = list;
        this.notifyDataSetChanged();
    }
    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'layout_item_circle_message.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.iv_thumb)
        CircleImageView ivThumb;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.gridview)
        NoScrollGridView gridview;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.iv_like)
        ImageView ivLike;
        @Bind(R.id.iv_comment)
        ImageView ivComment;
        @Bind(R.id.iv_collect)
        ImageView ivCollect;
        @Bind(R.id.tv_delete)
        TextView tvDelete;
        @Bind(R.id.tv_peoplename)
        TextView tvPeoplename;
        @Bind(R.id.tv_comment1)
        TextView tvComment1;
        @Bind(R.id.tv_comment2)
        TextView tvComment2;
        @Bind(R.id.tv_comment3)
        TextView tvComment3;
        @Bind(R.id.tv_comment4)
        TextView tvComment4;
        @Bind(R.id.ll_comment)
        LinearLayout llComment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
