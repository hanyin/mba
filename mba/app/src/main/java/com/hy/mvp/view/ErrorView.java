package com.hy.mvp.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hy.mvp.R;

/**
 * 作者：hanyin on 2017/8/4 13:21
 * 邮箱：hanyinit@163.com
 */

public class ErrorView extends FrameLayout implements View.OnClickListener {

    public void setConfig(ProgressConfig config) {
        this.config = config;
    }

    /**
     * 重试接口
     *
     * @author wu
     */
    public interface OnRetryListener {
        void onRetry();
    }

    public boolean hasRetryListener() {
        return null != listener;
    }

    private OnRetryListener listener;

    public void setRetryListener(OnRetryListener listener) {
        this.listener = listener;
    }

    // private ImageView ivErrorIcon;
    private TextView tvError;
    private TextView tvRetry;
    private TextView tvErrorSub;
    private ProgressConfig config;

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ErrorView(Context context) {
        super(context);
        initView();
    }

    public ErrorView(Context context, ProgressConfig config) {
        super(context);
        this.config = config;
        initView();
    }

    private void initView() {
        View errorView = LayoutInflater.from(getContext()).inflate(R.layout.progress_window_layout_error, this, false);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(errorView, params);
        // ivErrorIcon = (ImageView) errorView.findViewById(R.id.ivErrorIcon);
        tvError = (TextView) errorView.findViewById(R.id.tvError);
        tvRetry = (TextView) errorView.findViewById(R.id.tvRetry);
        tvErrorSub = (TextView) errorView.findViewById(R.id.tvErrorSub);
        tvRetry.setOnClickListener(this);
        if (null != config) {
            if (config.getTextSize() != -1) {
                tvError.setTextSize(config.getTextSize());
                tvRetry.setTextSize(config.getTextSize());
                tvErrorSub.setTextSize(config.getTextSize());
            }
            if (config.getTextColor() != -1) {
                tvError.setTextColor(config.getTextColor());
//                tvRetry.setTextColor(config.getTextColor());
                tvErrorSub.setTextColor(config.getTextColor());
            }
        }
    }

    /**
     * 设置错误内容
     *
     * @param error
     */
    public void setError(String error) {
        if (!TextUtils.isEmpty(error)) {
            tvError.setText(error);
        }
    }

    /**
     * 设置子错误内容
     *
     * @param subError
     */
    public void setSubError(String subError) {
        if (!TextUtils.isEmpty(subError)) {
            tvErrorSub.setText(subError);
            tvErrorSub.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示重试按钮
     */
    public void showRetry() {
        tvRetry.setVisibility(View.VISIBLE);
    }

    public void hideRetry() {
        tvRetry.setVisibility(View.GONE);
    }

    /**
     * 设置重试提示文本
     *
     * @param retryText
     */
    public void setRetryText(String retryText) {
        tvRetry.setText(retryText);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvRetry && listener != null) {
            listener.onRetry();
        }
    }

}

