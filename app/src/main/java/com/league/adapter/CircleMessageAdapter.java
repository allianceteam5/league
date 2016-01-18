package com.league.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.league.activity.ShowBigImgActivity;
import com.league.bean.CircleMessageBean;
import com.league.otto.BusProvider;
import com.league.otto.CircleMessageEvent;
import com.league.otto.DeleteIndexEvent;
import com.league.utils.IContants;
import com.league.utils.StoreUtils;
import com.league.utils.ToastUtils;
import com.league.utils.Utils;
import com.league.utils.api.ApiUtil;
import com.league.widget.CircleImageView;
import com.league.widget.NoScrollGridView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mine.league.R;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liug on 15/11/10.
 */
public class CircleMessageAdapter extends BaseAdapter implements IContants {

    private List<CircleMessageBean> list;
    private Context ctx;
    private View parentView;

    private EditText etInput;
    private TextView tvSend;
    private PopupWindow popupWindow;

    public CircleMessageAdapter(Context ctx, List<CircleMessageBean> list, View parentView) {
        this.ctx = ctx;
        this.list = list;
        this.parentView = parentView;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final int index = position;
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.layout_item_circle_message, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CircleMessageBean circleMessageBean = list.get(position);
        if (!TextUtils.isEmpty(circleMessageBean.getThumb()))
            Picasso.with(ctx).load(circleMessageBean.getThumb()).placeholder(R.drawable.example).resize(80, 80).centerCrop().into(holder.ivThumb);
        else
            Picasso.with(ctx).load(R.drawable.example).into(holder.ivThumb);
        holder.tvName.setText(circleMessageBean.getNickname());
        holder.tvTime.setText(Utils.generateStringByTime(circleMessageBean.getCreated_at()));
        holder.tvContent.setText(circleMessageBean.getContent());

        if (1 == circleMessageBean.getIszaned()) {
            holder.ivLike.setImageResource(R.drawable.icon_like);
        } else {
            holder.ivLike.setImageResource(R.drawable.icon_like_concel);
        }

        if (1 == circleMessageBean.getIscollected())
            holder.ivCollect.setImageResource(R.drawable.icon_collect);
        else
            holder.ivCollect.setImageResource(R.drawable.icon_collect_concel);

        if (1 == circleMessageBean.getIsmy())
            holder.tvDelete.setVisibility(View.VISIBLE);
        else
            holder.tvDelete.setVisibility(View.GONE);

        final List<String> imgList = circleMessageBean.getPictureList();
        ImgGridAdapter adapter = new ImgGridAdapter(ctx, imgList);
        if (imgList != null) {
            holder.gridview.setVisibility(View.VISIBLE);
            holder.gridview.setAdapter(adapter);
        }else {
            holder.gridview.setVisibility(View.GONE);
        }

        holder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> imgs = new ArrayList<>(imgList);
                Intent intent = new Intent(ctx, ShowBigImgActivity.class);
                intent.putStringArrayListExtra(PARAMS_IMG_LIST, imgs);
                intent.putExtra(PARAMS_INDEX, position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        List<CircleMessageBean.ZansEntity> zansEntityList = circleMessageBean.getZans();
        if (zansEntityList == null || zansEntityList.size() == 0) {
            //数据要清零
            holder.llLike.setVisibility(View.GONE);
            holder.tvPeoplename.setText("");
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(zansEntityList.get(0).getNickname());
            for (int i = 1; i < zansEntityList.size(); i++)
                sb.append("，").append(zansEntityList.get(i).getNickname());
            holder.tvPeoplename.setText(sb.toString());
            holder.llLike.setVisibility(View.VISIBLE);
        }

        final List<CircleMessageBean.ReplysEntity> replysEntityList = circleMessageBean.getReplys();
        if (replysEntityList == null || replysEntityList.size() == 0) {
            holder.listview.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);
        } else {
            ReplyItemAdapter replyItemAdapter = new ReplyItemAdapter(ctx, replysEntityList);
            holder.listview.setAdapter(replyItemAdapter);
            holder.divider.setVisibility(View.VISIBLE);
            holder.listview.setVisibility(View.VISIBLE);
        }

        holder.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (replysEntityList.get(position).getFromphone().equals(StoreUtils.getPhone()))
                    return;
                showPopupWindow(circleMessageBean.getId(), replysEntityList.get(position).getFromphone(), replysEntityList.get(position).getFromnickname(), index);
            }
        });

        holder.ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(circleMessageBean.getId(), "", "", index);
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtil.circleMessageDelete(ctx, circleMessageBean.getId(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        int flag = response.optInt("flag");
                        if (flag == 1) {
                            ToastUtils.showShortToast(ctx, "删除成功");
                            BusProvider.getInstance().post(new DeleteIndexEvent(position));
                        } else {
                            ToastUtils.showShortToast(ctx, "网络不太好哦");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        ToastUtils.showShortToast(ctx, "网络不太好哦");
                    }
                });
            }
        });

        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int type = 1 - circleMessageBean.getIszaned();
                circleMessageBean.setIszaned(type);
                ApiUtil.circleMessageZanOrCancel(ctx, circleMessageBean.getId(), type, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        int flag = response.optInt("flag");
                        if (flag == 1) {
                            String str = holder.tvPeoplename.getText().toString();
                            if (type == 1) {
                                if (TextUtils.isEmpty(str)) {
                                    str = str + StoreUtils.getNickname();
                                    holder.llLike.setVisibility(View.VISIBLE);
                                } else
                                    str = str + "，" + StoreUtils.getNickname();
                                holder.tvPeoplename.setText(str);
                                holder.ivLike.setImageResource(R.drawable.icon_like);
                            }
                            if (type == 0) {
                                String nickname = StoreUtils.getNickname();
                                if (str.equals(nickname)) {
                                    str = str.replace(nickname, "");
                                    holder.llLike.setVisibility(View.GONE);
                                } else if (str.startsWith(nickname + "，")) {
                                    str = str.replace(nickname + "，", "");
                                } else
                                    str = str.replace("，" + StoreUtils.getNickname(), "");
                                holder.tvPeoplename.setText(str);
                                holder.ivLike.setImageResource(R.drawable.icon_like_concel);
                            }
                        } else {
                            ToastUtils.showShortToast(ctx, "网络不太好哦");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        ToastUtils.showShortToast(ctx, "网络不太好哦");
                    }
                });
            }
        });

        holder.ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int type = 1 - circleMessageBean.getIscollected();
                circleMessageBean.setIscollected(type);
                ApiUtil.circleMessageCollectOrCancel(ctx, circleMessageBean.getId(), type, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        int flag = response.optInt("flag");
                        if (flag == 1) {
                            if (type == 1)
                                holder.ivCollect.setImageResource(R.drawable.icon_collect);
                            if (type == 0)
                                holder.ivCollect.setImageResource(R.drawable.icon_collect_concel);
                            BusProvider.getInstance().post(new CircleMessageEvent());
                        } else {
                            ToastUtils.showShortToast(ctx, "网络不太好哦");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        ToastUtils.showShortToast(ctx, "网络不太好哦");
                    }
                });
            }
        });

        return convertView;
    }

    public void setData(List<CircleMessageBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'layout_item_circle_message.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.iv_thumb)
        CircleImageView ivThumb;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.gridview)
        NoScrollGridView gridview;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.iv_like)
        ImageView ivLike;
        @Bind(R.id.iv_comment)
        ImageView ivComment;
        @Bind(R.id.iv_collect)
        ImageView ivCollect;
        @Bind(R.id.tv_delete)
        TextView tvDelete;
        @Bind(R.id.tv_peoplename)
        TextView tvPeoplename;
        @Bind(R.id.listview)
        ListView listview;
        @Bind(R.id.ll_like)
        LinearLayout llLike;
        @Bind(R.id.divider)
        View divider;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void closePopupWindow() {
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
    }

    public PopupWindow createPopupWindow() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_item_popup_comment, null);
        tvSend = (TextView) view.findViewById(R.id.tv_send);
        etInput = (EditText) view
                .findViewById(R.id.et_input);
        etInput.setFocusableInTouchMode(true);
        etInput.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) etInput
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        return popupWindow;
    }

    public void showPopupWindow(final String messageid, final String toPhone, final String toNickname, final int position) {
        popupWindow = createPopupWindow();
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        if (!TextUtils.isEmpty(toPhone)) {
            etInput.setHint("回复 " + toNickname + "：");
        } else {
            etInput.setHint("评论：");
        }

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = etInput.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showShortToast(ctx, "请输入内容");
                    return;
                }

                ApiUtil.circleMessageComment(ctx, messageid, content, toPhone, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        int flag = response.optInt("flag");
                        if (flag == 1) {
                            closePopupWindow();
                            ToastUtils.showShortToast(ctx, "评价成功");
                            list.get(position).getReplys().add(new CircleMessageBean.ReplysEntity(StoreUtils.getPhone(), StoreUtils.getNickname(), toPhone, toNickname, content));
                            notifyDataSetChanged();
                        } else {
                            ToastUtils.showShortToast(ctx, "网络不太好哦");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        ToastUtils.showShortToast(ctx, "网络不太好哦");
                    }
                });

            }
        });
    }
}
