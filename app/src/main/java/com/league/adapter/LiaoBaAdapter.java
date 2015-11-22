package com.league.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.activity.ShowBigImgActivity;
import com.league.activity.liaobaactivity.TopicContent;
import com.league.bean.LiaoBaUserInfo;
import com.league.bean.SucessBean;
import com.league.utils.Constants;
import com.league.utils.IContants;
import com.league.utils.Utils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liug on 15/11/10.
 */
public class LiaoBaAdapter extends BaseAdapter implements IContants {

    private List<LiaoBaUserInfo> list;
    private Context ctx;
    private int type = 0;//0表示最新 1表示最热

    public LiaoBaAdapter(List<LiaoBaUserInfo> list, Context ctx, int type) {
        this.list = list;
        this.ctx = ctx;
        this.type = type;
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_liaoba_user, null);
            holder = new ViewHolder();
            holder.thumb = (ImageView) convertView.findViewById(R.id.thumb);
            holder.name = (TextView) convertView.findViewById(R.id.username);
            holder.time = (TextView) convertView.findViewById(R.id.pushtime);
            holder.new_hot = (ImageView) convertView.findViewById(R.id.new_hot);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.comment);
            holder.yiguanzhu = (TextView) convertView.findViewById(R.id.yiguanzhu);
            holder.guanzhu = convertView.findViewById(R.id.guanzhu);
            holder.like = (ImageView) convertView.findViewById(R.id.dianzan);
            holder.dianzanshu = (TextView) convertView.findViewById(R.id.dianzannum);
            holder.commentnum = (TextView) convertView.findViewById(R.id.comnum);
            holder.llDianzan = (LinearLayout) convertView.findViewById(R.id.ll_dianzan);
            holder.llDetail = (LinearLayout) convertView.findViewById(R.id.ll_detail);
            holder.gridview = (GridView) convertView.findViewById(R.id.gridview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(ctx).load(list.get(position).getThumb()).resize(80, 80).centerCrop().into(holder.thumb);
        holder.name.setText(list.get(position).getNickname());
        holder.time.setText(Utils.generateStringByTime(list.get(position).getCreated_at()));
        if (type == 0) {
            holder.new_hot.setImageResource(R.drawable.liaoba_new);
        } else if (type == 1) {
            holder.new_hot.setImageResource(R.drawable.liaoba_hot);
        } else holder.new_hot.setVisibility(View.GONE);

        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());

        if (1 == list.get(position).getIsconcerned()) {
            holder.guanzhu.setEnabled(false);
            holder.yiguanzhu.setVisibility(View.VISIBLE);
            holder.guanzhu.setVisibility(View.GONE);
        } else {
            holder.yiguanzhu.setVisibility(View.GONE);
            holder.guanzhu.setVisibility(View.VISIBLE);
        }
        holder.dianzanshu.setText(list.get(position).getLikecount() + "");
        holder.commentnum.setText(list.get(position).getReplycount() + "");

        if (1 == list.get(position).getIsliked()) {
            holder.like.setImageResource(R.drawable.liaoba_like);
        } else {
            holder.like.setImageResource(R.drawable.liaoba_dianzan);
        }

        final List<String> imgList = list.get(position).getPictureList();
        ImgGridAdapter adapter = new ImgGridAdapter(ctx, imgList);
        if (imgList == null)
            holder.gridview.setVisibility(View.GONE);
        else
            holder.gridview.setAdapter(adapter);

        holder.llDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, TopicContent.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

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

        holder.guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (0 == list.get(position).getIsconcerned())
                    ApiUtil.liaobaConcern(ctx, list.get(position).getPhone(), 1, new BaseJsonHttpResponseHandler<SucessBean>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                            if ("1".equals(response.getFlag())) {
                                list.get(position).setIsconcerned(1);
                                holder.yiguanzhu.setVisibility(View.VISIBLE);
                                holder.guanzhu.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {

                        }

                        @Override
                        protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                            });
                        }
                    });
            }
        });

        holder.llDianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == list.get(position).getIsliked()) {
                    ApiUtil.liaobaCancellike(ctx, list.get(position).getId(), new BaseJsonHttpResponseHandler<SucessBean>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                            if ("1".equals(response.getFlag())) {
                                int num = list.get(position).getLikecount();
                                num--;
                                list.get(position).setLikecount(num);
                                list.get(position).setIsliked(0);
                                holder.like.setImageResource(R.drawable.liaoba_dianzan);
                                holder.dianzanshu.setText(list.get(position).getLikecount() + "");
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {

                        }

                        @Override
                        protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                            });
                        }
                    });
                } else {
                    ApiUtil.liaobaLike(ctx, list.get(position).getId(), new BaseJsonHttpResponseHandler<SucessBean>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                            if ("1".equals(response.getFlag())) {
                                int num = list.get(position).getLikecount();
                                num++;
                                list.get(position).setLikecount(num);
                                list.get(position).setIsliked(1);
                                holder.like.setImageResource(R.drawable.liaoba_like);
                                holder.dianzanshu.setText(list.get(position).getLikecount() + "");
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {

                        }

                        @Override
                        protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                            });
                        }
                    });
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView thumb;
        TextView name, time;
        ImageView new_hot;
        TextView title;
        TextView content;
        TextView yiguanzhu;
        View guanzhu;
        ImageView like;
        TextView dianzanshu;
        TextView commentnum;
        LinearLayout llDianzan,llDetail;
        GridView gridview;
    }
}
