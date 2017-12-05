package com.hy.mvp.ui.activity;

import com.hy.mvp.R;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;

import butterknife.ButterKnife;

public class AddFamilyActivity extends BaseActivity {

    @Override
    public void initView() {
        super.initView();
        setMainView(R.layout.activity_add_family);
        setTitleText("家族");
        showTitleBack();
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
