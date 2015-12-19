package com.league.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mine.league.R;


public class PickImgPopWindow implements View.OnClickListener {
    private Context context;
    private PopupWindow popupWindow;
    private PopClickListener popClickListener;
    private View archor;

    public PickImgPopWindow(Context context, View archor, PopClickListener popClickListener) {
        createPopWindow(context);
        this.popClickListener=popClickListener;
        this.archor=archor;
    }

    private void createPopWindow(Context context) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        View contentView = mLayoutInflater.inflate(R.layout.pop_img_choose, null);
        TextView takePhoto = (TextView) contentView.findViewById(R.id.take_photo);
        TextView getFromAlbum = (TextView) contentView.findViewById(R.id.get_from_album);
        TextView cancel = (TextView) contentView.findViewById(R.id.cancel);

        takePhoto.setOnClickListener(this);
        getFromAlbum.setOnClickListener(this);
        cancel.setOnClickListener(this);

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
    }

    public void showPopWindow() {
        if (popupWindow == null) {
            return;
        }
        popupWindow.showAtLocation(archor, Gravity.NO_GRAVITY, 0, 0);
    }

    public void dismissPopWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_photo:
                if (popClickListener!=null){
                    popClickListener.onClick(0);
                }
                break;
            case R.id.get_from_album:
                if (popClickListener!=null){
                    popClickListener.onClick(1);
                }
                break;
            case R.id.cancel:
                dismissPopWindow();
                break;
        }
    }

    public interface PopClickListener{
        void onClick(int index);
    }
}

