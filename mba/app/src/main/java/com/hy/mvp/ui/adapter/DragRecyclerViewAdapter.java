package com.hy.mvp.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.mvp.R;
import com.hy.mvp.utils.Bimp;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 韩银 on 2017/9/28 16:23
 * hanyinit@163.com
 */

public class DragRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyItemTouchCallback.ItemTouchAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public DragRecyclerViewAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_published_grid, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            myViewHolder.setIsRecyclable(false);
            if (position == Bimp.bmp.size()) {
                myViewHolder.imageView.setImageBitmap(BitmapFactory.decodeResource(
                        context.getResources(), R.mipmap.icon_addpic_unfocused));
                if (position == 9) {
                    myViewHolder.imageView.setVisibility(View.GONE);
                }
            } else {
                myViewHolder.imageView.setImageBitmap(Bimp.bmp.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return (Bimp.bmp.size() + 1);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition==Bimp.bmp.size() || toPosition==Bimp.bmp.size()){
            return;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(Bimp.bmp, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(Bimp.bmp, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        Bimp.bmp.remove(position);
        notifyItemRemoved(position);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
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
