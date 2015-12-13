package com.league.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mine.league.R;

/**
 * Created by liug on 15/12/3.
 */
public class ShareDialog extends Dialog implements android.view.View.OnClickListener{
    private ImageView weixin,qq,friends;
    private Context context;
    View localView;

    public ShareDialog(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 这句代码换掉dialog默认背景，否则dialog的边缘发虚透明而且很宽
        // 总之达不到想要的效果
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = LayoutInflater.from(context);
        //((AnimationActivity) context).getLayoutInflater();
        localView = inflater.inflate(R.layout.sharedialog, null);
        localView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_bottom_to_top));
        setContentView(localView);
        // 这句话起全屏的作用
        getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        initViews();
        setOnclick();
    }
    private void initViews(){
        weixin= (ImageView) findViewById(R.id.weixin);
        qq= (ImageView) findViewById(R.id.qq);
        friends= (ImageView) findViewById(R.id.friends);
    }
    private void setOnclick(){
        weixin.setOnClickListener(this);
        qq.setOnClickListener(this);
        friends.setOnClickListener(this);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.dismiss();
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {

    }
}
