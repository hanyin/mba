package com.hy.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.mvp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：hanyin on 2017/8/15 10:50
 * 邮箱：hanyinit@163.com
 */

public class RecycleMoreItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> mDatas;
    private int[] dataIcon;
    private LayoutInflater layoutInflater;

    public RecycleMoreItemAdapter(Context context, List<String> mDatas,int[] dataIcon) {
        this.context = context;
        this.mDatas = mDatas;
        this.dataIcon = dataIcon;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_more_tools_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            if (mOnItemClickLitener != null) {
                myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLitener.onItemClick(view, position);
                    }
                });
            }
            String title = mDatas.get(position);
            int icon = dataIcon[position];
            myViewHolder.tv_title_more_item.setText(title);
            myViewHolder.iv_more_item.setImageResource(icon);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_more_item)
        ImageView iv_more_item;
        @BindView(R.id.tv_title_more_item)
        TextView tv_title_more_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private OnItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
