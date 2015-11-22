package com.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.bean.PopularityBean;
import com.league.bean.SucessBean;
import com.league.interf.ListItemClickHelp;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by liug on 15/11/13.
 */
public class LiaobaConcernListAdapter extends BaseAdapter {
    private List<PopularityBean> list;
    private Context ctx;
    private ListItemClickHelp callBack;

    public LiaobaConcernListAdapter(List<PopularityBean> list, Context ctx) {
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_liaoba_concern, null);
            holder = new ViewHolder();
            holder.headPortrait = (CircleImageView) convertView.findViewById(R.id.head_portrait);
            holder.usrName = (TextView) convertView.findViewById(R.id.usrname);
            holder.funNum = (TextView) convertView.findViewById(R.id.funnum);
            holder.concern = (Button) convertView.findViewById(R.id.concern);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(ctx).load(list.get(position).getThumb()).into(holder.headPortrait);
        holder.usrName.setText(list.get(position).getNickname());
        holder.funNum.setText(list.get(position).getConcerncount() + "");
        if (0 == list.get(position).getIsconcerned()) {
            holder.concern.setVisibility(View.GONE);
        }
        holder.concern.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ApiUtil.liaobaConcern(ctx, list.get(position).getPhone(), 0, new BaseJsonHttpResponseHandler<SucessBean>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                        if ("1".equals(response.getFlag())) {
                            list.get(position).setIsconcerned(1);
                            holder.concern.setVisibility(View.GONE);
                            list.get(position).setIsconcerned(0);
                            int count = list.get(position).getConcerncount();
                            list.get(position).setConcerncount(count - 1);
                            holder.funNum.setText(list.get(position).getConcerncount() + "");
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
        });

        return convertView;
    }

    class ViewHolder {
        CircleImageView headPortrait;
        TextView usrName;
        TextView funNum;
        Button concern;
    }
}
