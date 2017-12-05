package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  2017/10/23 15:01
 *  hanyin
 *  hanyinit@163.com
 */
public class MySettingActivity extends BaseActivity {

    @BindView(R.id.llAccountManager)
    LinearLayout llAccountManager;
    @BindView(R.id.llExit)
    LinearLayout llExit;
    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_my_setting);
        setTitleText("设置");
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
        llAccountManager.setOnClickListener(this);
        llExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.llAccountManager:
                startActivity(new Intent(MySettingActivity.this,ModifyPasswordActivity.class));
                break;
            case R.id.llExit:
                AppConfigHelper.setConfig(AppConfigDef.username,"");
                AppConfigHelper.setConfig(AppConfigDef.password,"");
                MyApplication.getInstance().exit();
                Intent i=new Intent(MySettingActivity.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
