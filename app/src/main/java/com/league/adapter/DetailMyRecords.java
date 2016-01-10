package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.MoreNumberBean;
import com.league.bean.MyRecordGrabBean;
import com.league.utils.ToastUtils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by liug on 15/11/22.
 */
public class DetailMyRecords extends BaseAdapter {
    private List<MyRecordGrabBean> list;
    private Context ctx;

    public DetailMyRecords(List<MyRecordGrabBean> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_detail_myrecords, null);
            holder.count = (TextView) convertView.findViewById(R.id.myrecordscount);
            holder.number = (TextView) convertView.findViewById(R.id.myrecordsnumber);
            holder.more = (Button) convertView.findViewById(R.id.getmore);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.count.setText(list.get(position).getCount());
        holder.number.setText(list.get(position).getNumbers());
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getGrabcornid() == null) {
                    ApiUtil.getMoreNumCommodity(ctx, list.get(position).getId(), new BaseJsonHttpResponseHandler<MoreNumberBean>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, MoreNumberBean response) {
                            if (response.getFlag() == 1) {
                                holder.number.setText(response.getNumbers());
                                holder.more.setVisibility(View.GONE);
                            } else {
                                ToastUtils.showShortToast(ctx, "获取失败");
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, MoreNumberBean errorResponse) {
                            ToastUtils.showShortToast(ctx, "获取失败");
                        }

                        @Override
                        protected MoreNumberBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            return new ObjectMapper().readValue(rawJsonData, new TypeReference<MoreNumberBean>() {
                            });
                        }
                    });
                } else if (list.get(position).getGrabcommodityid() == null) {
                    ApiUtil.getMoreNumCorn(ctx, list.get(position).getId(), new BaseJsonHttpResponseHandler<MoreNumberBean>() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, MoreNumberBean response) {
                            if (response.getFlag() == 1) {
                                holder.number.setText(response.getNumbers());
                                holder.more.setVisibility(View.GONE);
                            } else {
                                ToastUtils.showShortToast(ctx, "获取失败");
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, MoreNumberBean errorResponse) {
                            ToastUtils.showShortToast(ctx, "获取失败");
                        }

                        @Override
                        protected MoreNumberBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                            return new ObjectMapper().readValue(rawJsonData, new TypeReference<MoreNumberBean>() {
                            });
                        }
                    });
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView count, number;
        Button more;
    }
}
