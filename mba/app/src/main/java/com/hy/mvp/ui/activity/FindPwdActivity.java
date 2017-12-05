package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.base.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：hanyin on 2017/8/4 10:27
 * 邮箱：hanyinit@163.com
 */

public class FindPwdActivity extends BaseActivity {
    @BindView(R.id.btnNext)
     Button btnNext;
    @Override
    public void initView() {
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);
        showTitleBack();
        setNavigationIcon(R.mipmap.btn_back);
        setTitleText("找回密码");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        btnNext.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){
            case R.id.btnNext:
                Intent intent  = new Intent(FindPwdActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
