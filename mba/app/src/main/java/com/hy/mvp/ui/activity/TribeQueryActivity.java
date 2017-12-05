package com.hy.mvp.ui.activity;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;

import butterknife.ButterKnife;

public class TribeQueryActivity extends BaseActivity {

    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_tribe_query);
        showTitleBack();
        setTitleText("查找部落");
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
