package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mine.league.R;

import java.util.List;

/**
 * Created by pfy on 2016/1/10.
 */
public class RadioSelectAdapter extends BaseAdapter {
    private Context context;
    private List<String> items;
    private int selectedIndex = 0;

    public RadioSelectAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(
                R.layout.layout_item_radioselect, null);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tv_radio_content);
        tvContent.setText(items.get(position));
        ImageView ivSeleted = (ImageView) convertView.findViewById(R.id.iv_radio_selected);
        ivSeleted.setVisibility(View.GONE);
        if (position == selectedIndex)
            ivSeleted.setVisibility(View.VISIBLE);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != selectedIndex) {
                    selectedIndex = position;
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

}
