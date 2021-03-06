package com.league.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.bean.RecommendationInfoBean;
import com.league.utils.Utils;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author liugang
 * @date 2015年9月21日
 */
public class RecommendationInfoAdapter extends BaseAdapter {

    private Context ctx;
    private List<RecommendationInfoBean> list;

    public RecommendationInfoAdapter(Context context, List<RecommendationInfoBean> list) {
        this.ctx = context;
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.layout_item_recommendationinfo, null);
            holder = new ViewHolder();
            holder.thumb = (ImageView) convertView.findViewById(R.id.near_feauserthumb);
            holder.userNickname = (TextView) convertView.findViewById(R.id.near_feausername);
            holder.fea_location = (TextView) convertView.findViewById(R.id.near_fea_location);
            holder.type = (TextView) convertView.findViewById(R.id.near_fea_type);
            holder.lasttime = (TextView) convertView.findViewById(R.id.lasttime);
            holder.comnum = (TextView) convertView.findViewById(R.id.commentnum);
            holder.infoContent = (TextView) convertView.findViewById(R.id.near_fea_content);
            holder.secondContent = (TextView) convertView.findViewById(R.id.fea_seccontent);
            holder.gridview = (GridView) convertView.findViewById(R.id.gridview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.userNickname.setText(list.get(position).getNickname());
        if (!TextUtils.isEmpty(list.get(position).getThumb()))
            Picasso.with(ctx).load(list.get(position).getThumb()).resize(60, 60).centerCrop().into(holder.thumb);
        else
            Picasso.with(ctx).load(R.drawable.example).into(holder.thumb);
        holder.fea_location.setText(list.get(position).getLocation());
        holder.type.setText(list.get(position).getKind());
        holder.lasttime.setText(Utils.generateStringByTime(list.get(position).getCreated_at()));
        holder.comnum.setText(list.get(position).getComments().size() + "");
        holder.infoContent.setText(list.get(position).getTitle());
        holder.secondContent.setText(list.get(position).getReason());
        //设置四张图片
        List<String> imgList = list.get(position).getPictureLimitFour();
        if (imgList == null) {
            holder.gridview.setVisibility(View.GONE);
        } else {
            ImgGridAdapter adapter = new ImgGridAdapter(ctx, imgList);
            holder.gridview.setAdapter(adapter);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView thumb;
        TextView userNickname;
        TextView fea_location;
        TextView type;
        TextView lasttime;
        TextView comnum;
        TextView infoContent;
        TextView secondContent;
        GridView gridview;
    }
}
