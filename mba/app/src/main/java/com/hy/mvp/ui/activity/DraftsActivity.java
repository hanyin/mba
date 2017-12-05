package com.hy.mvp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  2017/10/16 14:30
 *  hanyin
 *  hanyinit@163.com
 *  草稿箱
 */

public class DraftsActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_drafts);
        setTitleText("草稿箱");
        setNavigationIcon(R.mipmap.btn_back);
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
