package com.hy.mvp.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hy.mvp.greendao.manager.GreenDaoManager;
import com.hy.mvp.utils.ToastUtils;

import org.jivesoftware.smack.XMPPConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：hanyin on 2017/8/3 11:13
 * 邮箱：hanyinit@163.com
 */

public class MyApplication extends BaseApp {
    public static XMPPConnection xmppConnection;
    private static MyApplication application;
    static Context mContext;
    List<Activity > activityList = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        initView();

    }

    private void initView() {
        application = this;
        mContext = getApplicationContext();
        initConfig();
        MultiDex.install(this);
    }

    private void initConfig() {
        GreenDaoManager.getInstance();
        ToastUtils.init(mContext);
        //RetrofitService.init();
    }
    /* if (BuildConfig.DEBUG) {
            LeakCanary.install(application);
            Logger.init("LogTAG");
        }
        CrashHandler.getInstance().init(mContext);
        RetrofitService.init();
        ToastUtils.init(mContext);*/

    public static MyApplication getInstance(){
        return  application;
    }
    public static Context getContext(){
        return  mContext;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    // 添加Activity到容器中
    public void deleteActivity(Activity activity) {
        activityList.add(activity);
    }
    public void  exit() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
    }
}
