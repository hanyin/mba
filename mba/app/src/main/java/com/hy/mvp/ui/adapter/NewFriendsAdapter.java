package com.hy.mvp.ui.adapter;

import android.content.Context;
import android.icu.text.DateFormat;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hy.mvp.R;
import com.hy.mvp.ui.bean.Friend;
import com.hy.mvp.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hy.mvp.R.id.llFriendIcon;

/**
 * Created by 韩银 on 2017/10/24 11:02
 * hanyinit@163.com
 */

public class NewFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    LayoutInflater layoutInflater;
    List<Friend> friends;

    public NewFriendsAdapter(Context mContext, List<Friend> friends) {
        this.mContext = mContext;
        this.friends = friends;
        layoutInflater  = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.new_friends_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Friend friend = friends.get(position);
        if(holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            if(onItemClickListener!=null){
                myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(view,position);
                    }
                });
                GlideUtils.load(mContext,friend.getProtrait_url(),myViewHolder.imageHead);
                myViewHolder.tvRealName.setText(friend.getRealname());
                myViewHolder.tvNickName.setText(friend.getNickname());
            }

        }
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.llFriendIcon)
        LinearLayout llFriendIcon;
        @BindView(R.id.imageHead)
        ImageView imageHead;
        @BindView(R.id.tvRealName)
        TextView  tvRealName;
        @BindView(R.id.tvNickName)
        TextView tvNickName;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
