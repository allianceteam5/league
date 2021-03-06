package com.league.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.GrabBean;
import com.league.bean.ShippingAddressBean;
import com.league.bean.SucessBean;
import com.league.utils.ToastUtils;
import com.league.utils.Utils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liug on 15/11/22.
 */
public class GrabRecordAdapter extends BaseAdapter {
    private Context ctx;
    private List<GrabBean> list;

    public GrabRecordAdapter(Context ctx, List<GrabBean> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_grabrecords, null);
            holder.picture = (ImageView) convertView.findViewById(R.id.picture);
            holder.version = (TextView) convertView.findViewById(R.id.period);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.total = (TextView) convertView.findViewById(R.id.totalpeople);
            holder.thiscount = (TextView) convertView.findViewById(R.id.thistake);
            holder.endtime = (TextView) convertView.findViewById(R.id.endtime);
            holder.winnername = (TextView) convertView.findViewById(R.id.winname);
            holder.winnercount = (TextView) convertView.findViewById(R.id.count);
            holder.winnernumber = (TextView) convertView.findViewById(R.id.winnumber);
            holder.linearlayout = convertView.findViewById(R.id.winner);
            holder.twobutton = convertView.findViewById(R.id.twobutton);
            holder.onebutton = convertView.findViewById(R.id.onebutton);
            holder.applyfor = (Button) convertView.findViewById(R.id.applyfortake);
            holder.llRedeem = convertView.findViewById(R.id.ll_redeem);
            holder.redeem = (Button) convertView.findViewById(R.id.redeem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(list.get(position).getPicture()))
            Picasso.with(ctx).load(list.get(position).getPicture()).into(holder.picture);
        holder.version.setText(list.get(position).getVersion());
        holder.title.setText(list.get(position).getTitle());
        holder.total.setText(list.get(position).getNeeded());
        holder.thiscount.setText(list.get(position).getCount());
        if (list.get(position).getEnd_at().equals("0")) {
            holder.endtime.setText("还未揭晓");
            holder.endtime.setTextColor(Color.RED);
            if(list.get(position).getGrabcornid()!=null){
                holder.llRedeem.setVisibility(View.GONE);
            }
        } else {
            holder.endtime.setText(Utils.TimeStamp2SystemNotificationDate(Long.valueOf(list.get(position).getEnd_at()) * 1000));
            if(list.get(position).getGrabcornid()!=null){
                holder.llRedeem.setVisibility(View.VISIBLE);
                if(list.get(position).getIsgotback().equals("0")){
                    holder.redeem.setText("申请赎回");
                    holder.redeem.setBackground(ctx.getResources().getDrawable(R.drawable.applyfor));
                    holder.redeem.setTextColor(ctx.getResources().getColor(R.color.white));
                    holder.redeem.setEnabled(true);
                }else{
                    holder.redeem.setText("已赎回");
                    holder.redeem.setBackground(ctx.getResources().getDrawable(R.drawable.checkstate));
                    holder.redeem.setTextColor(ctx.getResources().getColor(R.color.black1));
                    holder.redeem.setEnabled(false);
                }
                holder.redeem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ApiUtil.redeem(ctx, list.get(position).getGrabcornid(), new BaseJsonHttpResponseHandler<SucessBean>() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                if (response.getFlag().equals("1")) {
                                    ToastUtils.showShortToast(ctx,"发送申请赎回成功");

                                }else{
                                    ToastUtils.showShortToast(ctx,"发送申请赎回失败");
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {
                                ToastUtils.showShortToast(ctx,"发送申请赎回失败，网络问题");
                            }

                            @Override
                            protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                });
                            }
                        });
                    }
                });
            }
        }

        if (list.get(position).getWinnercount() == null) {
            holder.linearlayout.setVisibility(View.GONE);
        } else {
            holder.linearlayout.setVisibility(View.VISIBLE);
            holder.winnername.setText(list.get(position).getNickname());
            holder.winnernumber.setText(list.get(position).getWinnernumber());
            holder.winnercount.setText(list.get(position).getWinnercount());

        }
        if (list.get(position).getFlag() != null && list.get(position).getIsgot().equals("0")) {
            holder.linearlayout.setVisibility(View.VISIBLE);
            holder.linearlayout.setBackgroundResource(R.drawable.winnerbackground);
            holder.onebutton.setVisibility(View.VISIBLE);
            holder.winnername.setText("恭喜您获得本次奖品");
            holder.winnernumber.setText(list.get(position).getWinnernumber());
            holder.winnercount.setText(list.get(position).getCount());
            holder.onebutton.setVisibility(View.VISIBLE);
            holder.twobutton.setVisibility(View.GONE);
            holder.applyfor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(list.get(position).getTbk().equals("0")){
                        ApiUtil.applyForCorns(ctx, list.get(position).getGrabid(), new BaseJsonHttpResponseHandler<SucessBean>() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                if (response.getFlag().equals("1")) {
                                    holder.onebutton.setVisibility(View.GONE);
                                    holder.twobutton.setVisibility(View.VISIBLE);
                                }else{
                                    ToastUtils.showShortToast(ctx,"提取申请失败");
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {

                            }

                            @Override
                            protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                });
                            }
                        });
                    }
                    if(list.get(position).getTbk().equals("1")){
                        ApiUtil.getShipAddress(ctx, new BaseJsonHttpResponseHandler<ArrayList<ShippingAddressBean>>() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<ShippingAddressBean> response) {
                                if (response == null || response.size() == 0) {
                                    ToastUtils.showShortToast(ctx, "请去个人中心添加收货地址");
                                } else {
                                    int i;
                                    for (i = 0; i < response.size(); i++) {
                                        if (response.get(i).getIsdefault() == 1) {
                                            ApiUtil.applyForCommodity(ctx, list.get(position).getGrabid(),response.get(i).getId()+"" , new BaseJsonHttpResponseHandler<SucessBean>() {

                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                                                    if (response.getFlag().equals("1")) {
                                                        holder.onebutton.setVisibility(View.GONE);
                                                        holder.twobutton.setVisibility(View.VISIBLE);
                                                    } else {
                                                        ToastUtils.showShortToast(ctx, "提取申请失败");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SucessBean errorResponse) {

                                                }

                                                @Override
                                                protected SucessBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                                    return new ObjectMapper().readValue(rawJsonData, new TypeReference<SucessBean>() {
                                                    });
                                                }
                                            });
                                            break;
                                        }
                                    }
                                    if(i==response.size()){
                                        ToastUtils.showShortToast(ctx, "请去个人中心设置默认收货地址");
                                    }
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<ShippingAddressBean> errorResponse) {
                                ToastUtils.showShortToast(ctx, "网络出错了");
                            }

                            @Override
                            protected ArrayList<ShippingAddressBean> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                                return new ObjectMapper().readValue(rawJsonData, new TypeReference<ArrayList<ShippingAddressBean>>() {
                                });
                            }
                        });

                    }

                }
            });
        } else if (list.get(position).getFlag() != null && !list.get(position).getIsgot().equals("0")) {
            holder.linearlayout.setVisibility(View.VISIBLE);
            holder.linearlayout.setBackgroundResource(R.drawable.winnerbackground);
            holder.twobutton.setVisibility(View.VISIBLE);
            holder.winnername.setText("恭喜您获得本次奖品");
            holder.winnernumber.setText(list.get(position).getWinnernumber());
            holder.winnercount.setText(list.get(position).getCount());
            holder.onebutton.setVisibility(View.GONE);
            holder.twobutton.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView picture;
        TextView version, title, total, thiscount, endtime, winnername, winnercount, winnernumber;
        View linearlayout, twobutton, onebutton,llRedeem;
        Button applyfor,redeem;
    }
}
