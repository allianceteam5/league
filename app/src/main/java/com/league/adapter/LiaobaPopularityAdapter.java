package com.league.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.PopularityBean;
import com.league.bean.SucessBean;
import com.league.interf.ListItemClickHelp;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by liug on 15/11/13.
 */
public class LiaobaPopularityAdapter extends BaseAdapter {
    private List<PopularityBean> list;
    private Context ctx;
    private ListItemClickHelp callBack;

    public LiaobaPopularityAdapter(List<PopularityBean> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_liaoba_popularity, null);
            holder = new ViewHolder();
            holder.iconLeftRank = (ImageView) convertView.findViewById(R.id.icon_left);
            holder.headPortrait = (CircleImageView) convertView.findViewById(R.id.head_portrait);
            holder.usrName = (TextView) convertView.findViewById(R.id.usrname);
            holder.iconRight = (ImageView) convertView.findViewById(R.id.iconright);
            holder.funNum = (TextView) convertView.findViewById(R.id.funnum);
            holder.concern = (Button) convertView.findViewById(R.id.concern);
            holder.tvLeftRank = (TextView) convertView.findViewById(R.id.tv_left);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.iconLeftRank.setVisibility(View.VISIBLE);
        holder.iconRight.setVisibility(View.VISIBLE);
        holder.tvLeftRank.setVisibility(View.GONE);
        if (position == 0) {
            holder.iconLeftRank.setImageResource(R.drawable.icon_popularity_1);
            holder.iconRight.setImageResource(R.drawable.icon_popularity_1_copy);
        }
        if (position == 1) {
            holder.iconLeftRank.setImageResource(R.drawable.icon_popularity_2);
            holder.iconRight.setImageResource(R.drawable.icon_popularity_2_copy);
        }
        if (position == 2) {
            holder.iconLeftRank.setImageResource(R.drawable.icon_popularity_3);
            holder.iconRight.setImageResource(R.drawable.icon_popularity_3_copy);
        }
        if (position > 2 && position < 10) {
            holder.iconLeftRank.setVisibility(View.GONE);
            holder.iconRight.setVisibility(View.GONE);
            holder.tvLeftRank.setVisibility(View.VISIBLE);
            holder.tvLeftRank.setText((position + 1) + "");
        }
        if (position > 9) {
            holder.iconLeftRank.setVisibility(View.GONE);
            holder.iconRight.setVisibility(View.GONE);
            holder.tvLeftRank.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(list.get(position).getThumb())) {
            Picasso.with(ctx).load(list.get(position).getThumb()).into(holder.headPortrait);
        } else
            Picasso.with(ctx).load(R.drawable.example).into(holder.headPortrait);

        holder.usrName.setText(list.get(position).getNickname());
        holder.funNum.setText(list.get(position).getConcerncount() + "");
        if (1 == list.get(position).getIsconcerned()) {
            holder.concern.setText("已关注");
            holder.concern.setEnabled(false);
            holder.concern.setBackgroundResource(R.drawable.liaobaconcerned);
        }
        holder.concern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtil.liaobaConcern(ctx, list.get(position).getPhone(), 1, new BaseJsonHttpResponseHandler<SucessBean>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                        if ("1".equals(response.getFlag())) {
                            list.get(position).setIsconcerned(1);
                            holder.concern.setText("已关注");
                            holder.concern.setEnabled(false);
                            holder.concern.setBackgroundResource(R.drawable.liaobaconcerned);
                            list.get(position).setIsconcerned(1);
                            int count = list.get(position).getConcerncount();
                            list.get(position).setConcerncount(count + 1);
                            holder.funNum.setText(list.get(position).getConcerncount() + "");
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

        return convertView;
    }

    class ViewHolder {
        ImageView iconLeftRank;
        CircleImageView headPortrait;
        TextView usrName;
        ImageView iconRight;
        TextView funNum;
        Button concern;
        TextView tvLeftRank;
    }
}
