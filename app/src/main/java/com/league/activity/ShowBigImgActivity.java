package com.league.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mine.league.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowBigImgActivity extends BaseActivity {
    @Bind(R.id.viewpager)
    ViewPager viewpager;
//    @Bind(R.id.indicator)
//    CirclePageIndicator indicator;

    private SimpleImgAdapter adapter;
    private ArrayList<String> data;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_img);
        ButterKnife.bind(this);

//        data = getIntent().getStringArrayListExtra(PARAMS_IMG_LIST);

//        index = getIntent().getIntExtra(PARAMS_INDEX, 0);
        viewpager.setOffscreenPageLimit(1);
        adapter=new SimpleImgAdapter(this, data);
        viewpager.setAdapter(adapter);
//        indicator.setViewPager(viewpager);
        viewpager.setCurrentItem(index);
    }


    private static class SimpleImgAdapter extends PagerAdapter {
        private List<String> list;
        private Context mContext;
        private LayoutInflater inflater;

        public SimpleImgAdapter(Context context, List<String> list) {
            this.mContext = context;
            this.list = list;
            this.inflater= LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((View) object));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view=inflater.inflate(R.layout.item_big_img,null);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            final ProgressBar progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
            String path=list.get(position);
            if(path.startsWith("http")){
//                path=AppUtil.qiniuResize(path);
            }else {
                path="file://"+path;
            }
            //Log.i("qiniu", "qiniu resizeAndWater=" + path);
            Picasso.with(mContext).load(path).into(img, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    progressBar.setVisibility(View.GONE);
                }
            });
            ((ViewPager) container).addView(view,0);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                }
            });
            return view;
        }
    }
}
