package com.league.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.activity.ShowBigImgActivity;
import com.league.bean.OtherMessageBean;
import com.league.utils.IContants;
import com.league.utils.Utils;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liug on 15/11/10.
 */
public class OtherMessageAdapter extends BaseAdapter implements IContants {

    private List<OtherMessageBean> list;
    private Context ctx;

    public OtherMessageAdapter(Context ctx, List<OtherMessageBean> list) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_near_other, null);
            holder = new ViewHolder();
            holder.thumb = (ImageView) convertView.findViewById(R.id.thumb);
            holder.name = (TextView) convertView.findViewById(R.id.username);
            holder.time = (TextView) convertView.findViewById(R.id.pushtime);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.comment);
            holder.gridview = (GridView) convertView.findViewById(R.id.gridview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!TextUtils.isEmpty(list.get(position).getThumb()))
            Picasso.with(ctx).load(list.get(position).getThumb()).resize(80, 80).centerCrop().into(holder.thumb);
        else
            Picasso.with(ctx).load(R.drawable.example).into(holder.thumb);
        holder.name.setText(list.get(position).getNickname());
        holder.time.setText(Utils.generateStringByTime(list.get(position).getCreated_at()));

        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());



        final List<String> imgList = list.get(position).getPictureList();
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

        return convertView;
    }

    class ViewHolder {
        ImageView thumb;
        TextView name, time;
        TextView title;
        TextView content;
        GridView gridview;
    }
}
