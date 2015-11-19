package com.league.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.league.activity.treasure.BuyList;
import com.mine.league.R;

/**
 * Created by liug on 15/11/19.
 */

public class TakeInDialog extends Dialog implements android.view.View.OnClickListener {

    private Button submit;
    private ImageView substract,add,cancle;
    private TextView number;
    Context context;
    View localView;
    private RelativeLayout clearallpan;
    private String coinID;

    public TakeInDialog(Context context,String coinID) {
        super(context);
        this.context = context;
        this.coinID=coinID;
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
        localView = inflater.inflate(R.layout.animtakeindialog, null);
        localView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_bottom_to_top));
        setContentView(localView);
        // 这句话起全屏的作用
        getWindow().setLayout(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);

        initView();
        initListener();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.dismiss();
        return super.onTouchEvent(event);
    }

    private void initListener() {

        clearallpan.setOnClickListener(this);
        cancle.setOnClickListener(this);
        submit.setOnClickListener(this);
        substract.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    private void initView() {
        cancle= (ImageView) findViewById(R.id.cancle);
        substract= (ImageView) findViewById(R.id.substract);
        number= (TextView) findViewById(R.id.number);
        add= (ImageView) findViewById(R.id.add);
        submit= (Button) findViewById(R.id.submit);
        clearallpan = (RelativeLayout) findViewById(R.id.clearallpan);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
                this.dismiss();
                break;
            case R.id.substract:
                int num=Integer.valueOf(number.getText().toString());
                num--;
                if(num<=0){
                    number.setText(1+"");
                }else{
                    number.setText(num+"");
                }
                break;
            case R.id.add:
                number.setText(Integer.valueOf(number.getText().toString())+1+"");
                break;
            case R.id.submit:
                Intent inten=new Intent(context,BuyList.class);
                inten.putExtra("number",number.getText().toString());
                inten.putExtra("id",coinID);
                inten.putExtra("buytype",0);
                context.startActivity(inten);
                this.dismiss();
                break;
        }
    }
}
