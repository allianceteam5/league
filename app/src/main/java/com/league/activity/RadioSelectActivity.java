package com.league.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.league.utils.Contants;
import com.mine.league.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RadioSelectActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.txt_back)
    ImageView ivBack;
    @Bind(R.id.txt_title)
    TextView tvTitle;
    @Bind(R.id.lv_position)
    ListView lvItems;
    RadioSelectAdapter radioSelectAdapter;
    List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_select);
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
        int kind = getIntent().getIntExtra(Contants.RADIOSELECTKIND, 0);
        int selectedIndex = getIntent().getIntExtra(Contants.RADIOSELECEDTINDEX, 0);
        if (selectedIndex < 0)
            selectedIndex = 0;
        switch (kind) {
            case Contants.RADIODEDREE:
//                items = Arrays.asList(getResources().getStringArray(R.array.degree));
                items = Contants.DEGREEITEMS;
                tvTitle.setText("学历");
                break;
            default:
                items = new ArrayList<String>();
                break;
        }
        radioSelectAdapter = new RadioSelectAdapter(getApplicationContext(), items);
        radioSelectAdapter.setSelectedIndex(selectedIndex);
        lvItems.setAdapter(radioSelectAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                setResult(RESULT_OK, new Intent().putExtra(Contants.RADIOSELECEDTINDEX, radioSelectAdapter.getSelectedIndex()));
                finish();
                break;
        }
    }

    class RadioSelectAdapter extends BaseAdapter {
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
}
