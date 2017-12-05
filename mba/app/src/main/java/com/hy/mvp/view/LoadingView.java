package com.hy.mvp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hy.mvp.R;


/**
 * 作者：hanyin on 2017/8/22 15:20
 * 邮箱：hanyinit@163.com
 */


public class LoadingView extends LinearLayout {

	private View rootView;
	boolean isShow = false;
	private TextView tv_notice;
	
	public LoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public LoadingView(Context context) {
		super(context);
		initView(context);
	}

	void initView(Context context) {
		rootView = LayoutInflater.from(context).inflate(R.layout.loading, this);
		tv_notice = (TextView) rootView.findViewById(R.id.tv_notice);
		this.setOnClickListener(null);
	}

	public void showLoading(String msg){
		tv_notice.setText(msg);
		if (!isShow) {
			this.setVisibility(View.VISIBLE);
			isShow = true;
		}
	}


	public void showNotice() {
		this.tv_notice.setVisibility(View.INVISIBLE);
	}
	public void hideNotice() {
		this.tv_notice.setVisibility(View.INVISIBLE);
	}

	public void showLoading() {
		showLoading("正在加载中...");
	}

	public void hideLoading() {
		if (isShow) {
			this.setVisibility(View.GONE);
			isShow = false;
		}
	}

}
