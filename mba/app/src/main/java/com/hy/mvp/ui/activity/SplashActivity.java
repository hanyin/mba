package com.hy.mvp.ui.activity;

import android.content.Intent;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.statusbar.Eyes;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.base.Presenter;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.utils.RxHelper;
import com.hy.mvp.view.SimpleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：hanyin on 2017/8/3 13:18
 * 邮箱：hanyinit@163.com
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.sb_skip)
    SimpleButton mSbSkip;

    private boolean mIsSkip = false;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }


    @Override
    public void initView() {
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //getWelcomePics();
        RxHelper.countdown(3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        doSkip();
                    }

                    @Override
                    public void onError(Throwable e) {
                        doSkip();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mSbSkip.setText("跳过 " + integer);
                    }
                });
    }

    private void getWelcomePics() {
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        apiService.getParamVersions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {

                    }
                });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    private void doSkip() {
        if (!mIsSkip) {
            mIsSkip = true;
            finish();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
        }
    }

    @Override
    public void onBackPressed() {
        // 不响应后退键
        return;
    }

    @OnClick(R.id.sb_skip)
    public void onClick() {
        doSkip();
    }
}
