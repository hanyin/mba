package com.hy.mvp.ui.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  2017/10/23 15:41
 *  hanyin
 *  hanyinit@163.com
 */
public class ModifyPasswordActivity extends BaseActivity {
    @BindView(R.id.etOldPwd)
    EditText etOldPwd;
    @BindView(R.id.etNewPwd)
    EditText etNewPwd;
    @BindView(R.id.etAgainPwd)
    EditText etAgainPwd;
    String newPwd ;
    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_modify_password);
        setTitleText("修改登录密码");
        setTitleRight("提交");
        showTitleBack();
        ButterKnife.bind(this);
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        String oldPwd = etOldPwd.getText().toString();
        newPwd = etNewPwd.getText().toString();
        String againPwd = etAgainPwd.getText().toString();
        if(TextUtils.isEmpty(oldPwd)){
            ToastUtils.showToast("原密码不能为空");
            return;
        }
        if(TextUtils.isEmpty(newPwd)){
            ToastUtils.showToast("新密码不能为空");
            return;
        }
        if(oldPwd.equalsIgnoreCase(newPwd)){
            ToastUtils.showToast("原密码不能和新密码相同");
            return;
        }
        if(!newPwd.equalsIgnoreCase(againPwd)){
            ToastUtils.showToast("两次输入的密码不一致");
            return;
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

        }
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
