package com.hy.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hy.mvp.R;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.ui.bean.Friend;
import com.hy.mvp.ui.bean.Invitation;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 韩银 on 2017/11/1 17:01
 * hanyinit@163.com
 */

public class InvitationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    LayoutInflater layoutInflater;
    List<Invitation> invitations;

    public InvitationAdapter(Context mContext, List<Invitation> invitations) {
        this.mContext = mContext;
        this.invitations = invitations;
        layoutInflater  = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.invitation_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Invitation invitation = invitations.get(position);
        if(holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
                GlideUtils.load(mContext,invitation.getFriend_portrait().getUrl(),myViewHolder.imageHead);
                myViewHolder.tvRealName.setText(invitation.getFriend_name());
                myViewHolder.tvNickName.setText(invitation.getFriend_name());
            String userId = AppConfigHelper.getConfig(AppConfigDef.userId);
            int status =  invitation.getStatus();
            //邀请人
            if(invitation.getWayType()==1){
                myViewHolder.tvVerify.setBackground(null);
                myViewHolder.tvVerify.setTextColor(mContext.getResources().getColor(R.color.black));
                if(status == 0){
                    myViewHolder.tvVerify.setText("拒绝");
                }else if(status == 1){
                    myViewHolder.tvVerify.setText("验证中");
                }else if(status == 2){
                    myViewHolder.tvVerify.setText("通过验证");
                }
            }else if(invitation.getWayType()==2){//被邀请人
                myViewHolder.tvVerify.setBackground(mContext.getResources().getDrawable(R.drawable.button_border_blue));
                myViewHolder.tvVerify.setTextColor(mContext.getResources().getColor(R.color.white));
                 if(status == 1){
                    myViewHolder.tvVerify.setText("接受");
                     myViewHolder.tvVerify.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             onViewClickListener.onViewClick(v,position);
                         }
                     });
                }
            }

        }
    }



    @Override
    public int getItemCount() {
        return invitations.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.llFriendIcon)
        LinearLayout llFriendIcon;
        @BindView(R.id.imageHead)
        ImageView imageHead;
        @BindView(R.id.tvRealName)
        TextView tvRealName;
        @BindView(R.id.tvNickName)
        TextView tvNickName;
        @BindView(R.id.tvVerify)
        TextView tvVerify;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public OnViewClickListener onViewClickListener;


    public void setOnViewClickLisrtener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }
}
