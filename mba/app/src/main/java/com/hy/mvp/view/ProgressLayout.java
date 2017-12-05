package com.hy.mvp.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hy.mvp.interfaces.OnProgressListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 作者：hanyin on 2017/8/4 13:07
 * 邮箱：hanyinit@163.com
 */

public class ProgressLayout extends RelativeLayout {

    private OnProgressListener onProgressListener;

    private static ProgressConfig config = null;

    public void setOnProgressLintener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    private static final String TAG_PROGRESS = "ProgressLayout.TAG_PROGRESS";
    private static final String TAG_ERROR = "ProgressLayout.TAG_ERROR";

    public enum State {
        CONTENT, PROGRESS, ERROR
    }

    private ProgressView mProgressView;
    private ErrorView mErrorView;
    private List<View> mContentViews = new ArrayList<View>();

    public void setRetryListener(ErrorView.OnRetryListener listener) {
        if (mErrorView != null) {
            mErrorView.setRetryListener(listener);
        }
    }

    private State mState = State.CONTENT;

    private Handler handler = new Handler();

    public ProgressLayout(Context context) {
        super(context);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mErrorView = new ErrorView(getContext(), config);
        mErrorView.setTag(TAG_ERROR);
        addView(mErrorView, layoutParams);

        mProgressView = new ProgressView(getContext(), config);
        mProgressView.setTag(TAG_PROGRESS);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mProgressView, layoutParams);
        mProgressView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                addContentViewIfPassable(getChildAt(i));
            }
        }
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        addContentViewIfPassable(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        addContentViewIfPassable(child);
    }

    private void addContentViewIfPassable(View child) {
        if (child.getTag() == null || (!child.getTag().equals(TAG_PROGRESS) && !child.getTag().equals(TAG_ERROR))) {
            mContentViews.add(child);
        }
    }

    public void showError() {
        showError(null);
    }

    public void showError(String error) {
        showError(error, false);
    }

    public void showError(String error, boolean retry) {
        switchState(State.ERROR, error, Collections.<Integer>emptyList());
        if (onProgressListener != null) {
            onProgressListener.onStopProgressing();
        }
        if (retry && mErrorView.hasRetryListener()) {
            mErrorView.showRetry();
        } else {
            mErrorView.hideRetry();
        }
    }

    public void showContent() {
        switchState(State.CONTENT, null, Collections.<Integer>emptyList());
        if (onProgressListener != null) {
            onProgressListener.onStopProgressing();
        }
    }

    public void showProgress() {
        showProgress(null);
    }

    public void showProgress(String msg) {
        if (onProgressListener != null) {
            onProgressListener.onStartProgressing();
        }
        mProgressView.setText(msg);
        switchState(State.PROGRESS, null, Collections.<Integer>emptyList());
    }

    private void switchState(final State state, final String errorText, final List<Integer> skipIds) {
        mState = state;
        handler.post(new Runnable() {
            @Override
            public void run() {
                switch (state) {
                    case CONTENT:
                        mErrorView.setVisibility(View.GONE);
                        mProgressView.setVisibility(View.GONE);
                        setContentVisibility(true, skipIds);
                        break;
                    case PROGRESS:
                        mErrorView.setVisibility(View.GONE);
                        mProgressView.setVisibility(View.VISIBLE);
                        setContentVisibility(false, skipIds);
                        break;
                    case ERROR:
                        mErrorView.setError(errorText);
                        mErrorView.setVisibility(View.VISIBLE);
                        mProgressView.setVisibility(View.GONE);
                        setContentVisibility(false, skipIds);
                        break;
                }
            }
        });
    }

    public State getState() {
        return mState;
    }

    public boolean isProgress() {
        return mState == State.PROGRESS;
    }

    public boolean isContent() {
        return mState == State.CONTENT;
    }

    public boolean isError() {
        return mState == State.ERROR;
    }

    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : mContentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    public static void initProgressConfig(ProgressConfig progressConfig) {
        config = progressConfig;
    }
}
