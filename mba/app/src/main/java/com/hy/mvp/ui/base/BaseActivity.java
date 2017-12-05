package com.hy.mvp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.hy.mvp.app.MyApplication;
import com.hy.mvp.ui.activity.AnimActivity;
import com.hy.mvp.ui.activity.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


/**
 * 作者：hanyin on 2017/8/3 11:29
 * 邮箱：hanyinit@163.com
 */

public abstract class BaseActivity<T extends BasePresenter>  extends BaseViewActivity {

    protected T mPresenter;
    private static long mPreTime;
    private static Activity mCurrentActivity;// 对所有activity进行管理
    public static List<Activity> mActivities = new LinkedList<Activity>();
    protected  Bundle savedInstanceState;
    private static long lastClickTime = System.currentTimeMillis();
    //private long advertisingTime = 5 * 1000;//定时跳转广告时间
    //CountDownTimer countDownTimer;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.savedInstanceState = savedInstanceState;
        //初始化的时候将其添加到集合中
        synchronized (mActivities) {
            mActivities.add(this);
        }
        mPresenter = createPresenter();
        initView();
        initData();
        initListener();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            if(isFastDoubleClick()){
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static boolean isFastDoubleClick(){
        long time = System.currentTimeMillis();
        long timeClick = time - lastClickTime;
        lastClickTime = time;
        if(timeClick<=400){
            return true;
        }else{
            return false;
        }
    }

    public boolean enableSlideClose() {
        return true;
    }

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();


    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
        //显示是启动定时
        //startAD();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
        //当activity不在前台是停止定时
        /*if (countDownTimer != null){
            countDownTimer.cancel();
        }*/
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        //销毁的时候从集合中移除
        synchronized (mActivities) {
            mActivities.remove(this);
        }

        if (mPresenter != null) {
            mPresenter.detachView();
        }
        //销毁时停止定时
        /*if (countDownTimer != null){
            countDownTimer.cancel();
        }*/
    }

    /**
     * 退出应用的方法
     */
    public static void exitApp() {

        ListIterator<Activity> iterator = mActivities.listIterator();

        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    /**
     * 统一退出控制
     *//*
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity){
            //如果是主页面
            if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2秒
                CommonUtils.showToast("再按一次，退出应用");
                mPreTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();// finish()
    }*/

    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }
    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }


   /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //有按下动作时取消定时
                if (countDownTimer != null){
                    countDownTimer.cancel();
                }
                break;
            case MotionEvent.ACTION_UP:
                //抬起时启动定时
                startAD();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    *//**
     * 跳轉廣告
     *//*
    public void startAD() {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(advertisingTime, 1000l) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    //定时完成后的操作
                    //跳转到广告页面
                    startActivity(new Intent(BaseActivity.this,AnimActivity.class));
                }
            };
            countDownTimer.start();
        } else {
            countDownTimer.start();
        }
    }*/


}
