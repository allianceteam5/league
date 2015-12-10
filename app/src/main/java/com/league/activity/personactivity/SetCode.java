package com.league.activity.personactivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mine.league.R;

import java.util.ArrayList;

public class SetCode extends Activity {

    private ImageView back2, titleright, right1, right2;
    private TextView title;
    private ArrayList<EditText> mEditList = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_code);
        initView();

    }

    private void initView() {

        back2 = (ImageView) findViewById(R.id.near_back);

        back2.setVisibility(View.VISIBLE);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        titleright = (ImageView) findViewById(R.id.near_title_right);
        titleright.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.near_title);
        title.setText("设置密码");
        right1 = (ImageView) findViewById(R.id.near_right);
        right1.setVisibility(View.GONE);
        right2 = (ImageView) findViewById(R.id.near_right_item);
        right2.setVisibility(View.GONE);
        mEditList.add((EditText) findViewById(R.id.edt1));
        mEditList.add((EditText) findViewById(R.id.edt2));
        mEditList.add((EditText) findViewById(R.id.edt3));
        mEditList.add((EditText) findViewById(R.id.edt4));
        mEditList.add((EditText) findViewById(R.id.edt5));
        mEditList.add((EditText) findViewById(R.id.edt6));
        for(int i=1;i<mEditList.size();i++){
            mEditList.get(i).setEnabled(false);
        }
        for (int i = 0; i < mEditList.size(); i++) {
            mEditList.get(i).addTextChangedListener(tw);
        }

    }
    TextWatcher tw = new TextWatcher(){
        //@Override
        public void beforeTextChanged(CharSequence s, int start, int count,int after){
        }
        //@Override
        public void onTextChanged(CharSequence s, int start, int before, int count){
        }
        //@Override
        public void afterTextChanged(Editable s){
            if(s.toString().length() == 1)
            {
                if(mEditList.get(0).isFocused())
                {
                    mEditList.get(0).clearFocus();
                    mEditList.get(0).setEnabled(false);
                    mEditList.get(1).setEnabled(true);
                    mEditList.get(1).requestFocus();
                }
                else if(mEditList.get(1).isFocused())
                {
                    mEditList.get(1).clearFocus();
                    mEditList.get(1).setEnabled(false);
                    mEditList.get(2).setEnabled(true);
                    mEditList.get(2).requestFocus();
                }
                else if(mEditList.get(2).isFocused())
                {
                    mEditList.get(2).clearFocus();
                    mEditList.get(2).setEnabled(false);
                    mEditList.get(3).setEnabled(true);
                    mEditList.get(3).requestFocus();
                }
                else if(mEditList.get(3).isFocused())
                {
                    mEditList.get(3).clearFocus();
                    mEditList.get(3).setEnabled(false);
                    mEditList.get(4).setEnabled(true);
                    mEditList.get(4).requestFocus();
                }
                else if(mEditList.get(4).isFocused())
                {
                    mEditList.get(4).clearFocus();
                    mEditList.get(4).setEnabled(false);
                    mEditList.get(5).setEnabled(true);
                    mEditList.get(5).requestFocus();
                }
            }
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode()==KeyEvent.KEYCODE_DEL){
            if(mEditList.get(5).isFocused())
            {
                mEditList.get(5).clearFocus();
                mEditList.get(5).setEnabled(false);
                mEditList.get(4).setEnabled(true);
                mEditList.get(4).requestFocus();
            }
            else if(mEditList.get(4).isFocused())
            {
                mEditList.get(4).clearFocus();
                mEditList.get(4).setEnabled(false);
                mEditList.get(3).setEnabled(true);
                mEditList.get(3).requestFocus();
            }
            else if(mEditList.get(3).isFocused())
            {
                mEditList.get(3).clearFocus();
                mEditList.get(3).setEnabled(false);
                mEditList.get(2).setEnabled(true);
                mEditList.get(2).requestFocus();
            }
            else if(mEditList.get(2).isFocused())
            {
                mEditList.get(2).clearFocus();
                mEditList.get(2).setEnabled(false);
                mEditList.get(1).setEnabled(true);
                mEditList.get(1).requestFocus();
            }
            else if(mEditList.get(1).isFocused())
            {
                mEditList.get(1).clearFocus();
                mEditList.get(1).setEnabled(false);
                mEditList.get(0).setEnabled(true);
                mEditList.get(0).requestFocus();
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
