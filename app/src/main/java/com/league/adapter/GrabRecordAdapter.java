package com.league.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.league.bean.SucessBean;
import com.league.utils.Constants;
import com.league.utils.Utils;
import com.league.utils.api.ApiUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by liug on 15/11/22.
 */
public class GrabRecordAdapter extends BaseAdapter{
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
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(ctx).inflate(R.layout.item_grabrecords,null);
            holder.picture= (ImageView) convertView.findViewById(R.id.picture);
            holder.version= (TextView) convertView.findViewById(R.id.period);
            holder.title= (TextView) convertView.findViewById(R.id.title);
            holder.total= (TextView) convertView.findViewById(R.id.totalpeople);
            holder.thiscount= (TextView) convertView.findViewById(R.id.thistake);
            holder.endtime= (TextView) convertView.findViewById(R.id.endtime);
            holder.winnername= (TextView) convertView.findViewById(R.id.winname);
            holder.winnercount= (TextView) convertView.findViewById(R.id.count);
            holder.winnernumber= (TextView) convertView.findViewById(R.id.winnumber);
            holder.linearlayout=convertView.findViewById(R.id.winner);
            holder.twobutton=convertView.findViewById(R.id.twobutton);
            holder.onebutton=convertView.findViewById(R.id.onebutton);
            holder.applyfor= (Button) convertView.findViewById(R.id.applyfortake);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(ctx).load(list.get(position).getPicture()).into(holder.picture);
        holder.version.setText(list.get(position).getVersion());
        holder.title.setText(list.get(position).getTitle());
        holder.total.setText(list.get(position).getNeeded());
        holder.thiscount.setText(list.get(position).getCount());
        if(list.get(position).getEnd_at().equals("0")){
            holder.endtime.setText("还未揭晓");
            holder.endtime.setTextColor(Color.RED);
        }else {
            holder.endtime.setText(Utils.TimeStamp2SystemNotificationDate(Long.valueOf(list.get(position).getEnd_at()) * 1000));
        }

        if(list.get(position).getWinnercount()==null){
            holder.linearlayout.setVisibility(View.GONE);
        }else{
            holder.linearlayout.setVisibility(View.VISIBLE);
            holder.winnername.setText(list.get(position).getNickname());
            holder.winnernumber.setText(list.get(position).getWinnernumber());
            holder.winnercount.setText(list.get(position).getWinnercount());
        }
        if(list.get(position).getFlag()!=null&&list.get(position).getIsgot().equals("0")){
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
                    ApiUtil.applyForCorns(ctx, Constants.PHONENUM, list.get(position).getGrabcornid(), new BaseJsonHttpResponseHandler<SucessBean>() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SucessBean response) {
                            if(response.getFlag().equals("1")){
                                holder.onebutton.setVisibility(View.GONE);
                                holder.twobutton.setVisibility(View.VISIBLE);
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
        }else if(list.get(position).getFlag()!=null&&!list.get(position).getIsgot().equals("0")){
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
        TextView version,title,total,thiscount,endtime,winnername,winnercount,winnernumber;
        View linearlayout,twobutton,onebutton;
        Button applyfor;
    }
}
