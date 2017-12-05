package com.hy.mvp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hy.mvp.R;


/**
 * 作者：hanyin on 2017/8/4 13:07
 * 邮箱：hanyinit@163.com
 */

public class ProgressView extends RelativeLayout {

    private TextView tvProgress;
    private ProgressConfig config;

    public ProgressView(Context context) {
        super(context);
        init();
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ProgressView(Context context, ProgressConfig config) {
        super(context);
        this.config = config;
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.progress_window_layout_process, this);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        if (null != config) {
            if (config.getTextSize() != -1) {
                tvProgress.setTextSize(config.getTextSize());
            }
            if (config.getTextColor() != -1) {
                tvProgress.setTextColor(config.getTextColor());
            }
        }
    }

    public void setText(String str) {
        tvProgress.setText(str);
    }

    public void setConfig(ProgressConfig config) {
        this.config = config;
    }
}
