package com.hy.mvp.greendao.manager;

import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.gen.DaoMaster;
import com.hy.mvp.greendao.gen.DaoSession;

/**
 * 作者：hanyin on 2017/8/9 21:38
 * 邮箱：hanyinit@163.com
 */

public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static volatile GreenDaoManager mInstance = null;
    private GreenDaoManager(){
        if (mInstance == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new
                    DaoMaster.DevOpenHelper(MyApplication.getInstance(), "mba",null);
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }
    public static GreenDaoManager getInstance() {
                if (mInstance == null) {
                    synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                mInstance = new GreenDaoManager();
            }
            }
        }
        return mInstance;
    }
    public DaoMaster getMaster() {
        return mDaoMaster;
    }
    public DaoSession getSession() {
        return mDaoSession;
    }
    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
