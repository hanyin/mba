package com.hy.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hy.mvp.R;
import com.hy.mvp.ui.adapter.OnClickListener;
import com.hy.mvp.ui.adapter.OnItemClickListener;

/**
 * Created by 韩银 on 2017/10/9 15:59
 * hanyinit@163.com
 */

public class ConfirmDialog extends BaseNiceDialog {
    private String type;
    static ConfirmDialog dialog;

    public static ConfirmDialog newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        if(dialog ==null){
            dialog = new ConfirmDialog();
           }
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        type = bundle.getString("type");
    }

    @Override
    public int intLayoutId() {
        return R.layout.confirm_layout;
    }


    @Override
    public void convertView(BaseViewHolder holder, final BaseNiceDialog dialog) {
            holder.setText(R.id.title, "提示");
            holder.setText(R.id.message, "是否保存草稿?");
        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onClickListener.onBackClick(v);
            }
        });

        holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onClickListener.onBackClick(v);
            }
        });
    }
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
