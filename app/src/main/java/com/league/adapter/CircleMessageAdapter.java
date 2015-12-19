package com.league.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.ShowBigImgActivity;
import com.league.activity.liaobaactivity.TopicContent;
import com.league.bean.CircleMessageBean;
import com.league.bean.LiaoBaMessageBean;
import com.league.bean.SucessBean;
import com.league.utils.IContants;
import com.league.utils.Utils;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.league.widget.NoScrollGridView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

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
        CircleMessageBean circleMessageBean = new CircleMessageBean();
        Picasso.with(ctx).load(list.get(position).getThumb()).resize(80, 80).centerCrop().into(holder.ivThumb);
        holder.tvName.setText(circleMessageBean.getNickname());
        holder.tvTime.setText(Utils.generateStringByTime(circleMessageBean.getCreated_at()));
        holder.tvContent.setText(circleMessageBean.getContent());

        if (1 == circleMessageBean.getIszaned()) {
            holder.ivLike.setImageResource(R.drawable.icon_like);
        } else {
            holder.ivLike.setImageResource(R.drawable.icon_like_concel);
        }

        final List<String> imgList = circleMessageBean.getPictureList();
        ImgGridAdapter adapter = new ImgGridAdapter(ctx, imgList);
        if (imgList == null)
            holder.gridview.setVisibility(View.GONE);
        else
            holder.gridview.setAdapter(adapter);

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

//        holder.ivLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (1 == list.get(position).getIsliked()) {
//                    ApiUtil.liaobaCancellike(ctx, list.get(position).getId(), new BaseJsonHttpResponseHandler<SucessBean>() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
//                            if ("1".equals(response.getFlag())) {
//                                int num = list.get(position).getLikecount();
//                                num--;
//                                list.get(position).setLikecount(num);
//                                list.get(position).setIsliked(0);
//                                holder.like.setImageResource(R.drawable.liaoba_dianzan);
//                                holder.dianzanshu.setText(list.get(position).getLikecount() + "");
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
//
//                        }
//
//                        @Override
//                        protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                            return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
//                            });
//                        }
//                    });
//                } else {
//                    ApiUtil.liaobaLike(ctx, list.get(position).getId(), new BaseJsonHttpResponseHandler<SucessBean>() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
//                            if ("1".equals(response.getFlag())) {
//                                int num = list.get(position).getLikecount();
//                                num++;
//                                list.get(position).setLikecount(num);
//                                list.get(position).setIsliked(1);
//                                holder.like.setImageResource(R.drawable.liaoba_like);
//                                holder.dianzanshu.setText(list.get(position).getLikecount() + "");
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
//
//                        }
//
//                        @Override
//                        protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
//                            return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
//                            });
//                        }
//                    });
//                }
//            }
//        });
        return convertView;
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
