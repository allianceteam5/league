package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mine.league.R;
import com.squareup.picasso.Picasso;

/**
 * Created by liug on 15/11/22.
 */
public class PicturesDetailAdapter extends BaseAdapter{
    private String[] list;
    private Context context;

    public PicturesDetailAdapter(String[] list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_picturedetail,null);
            imageView= (ImageView) convertView.findViewById(R.id.detail_image);
            convertView.setTag(imageView);
        }else{
            imageView= (ImageView) convertView.getTag();
        }
        Picasso.with(context).load(list[position]).into(imageView);
        return convertView;
    }
}
