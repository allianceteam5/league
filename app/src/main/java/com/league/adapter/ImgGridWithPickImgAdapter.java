package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.league.utils.ComplexPreferences;
import com.league.utils.StoreUtils;
import com.league.widget.SquaredImageView;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 15/4/21.
 */
public class ImgGridWithPickImgAdapter extends BaseAdapter {
    private List<String> data = new ArrayList<String>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int screenW;

    public ImgGridWithPickImgAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.data = data;
        screenW = StoreUtils.getWidthScreen();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            SquaredImageView imageView = new SquaredImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            convertView = imageView;
        }
        if (position == data.size() - 1) {
            Picasso.with(mContext).load(R.drawable.upload_default)./*resize(screenW / 4, screenW / 4).centerCrop().*/into(((ImageView) convertView));
            if (data.size() == 10) {
                convertView.setVisibility(View.GONE);
            }
        } else {
            Picasso.with(mContext).load("file://" + data.get(position)).resize(screenW / 4, screenW / 4).centerCrop().into(((ImageView) convertView));
        }

        return convertView;
    }
}
