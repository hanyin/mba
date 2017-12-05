package com.hy.mvp.greendao.manager;

/**
 * 作者：hanyin on 2017/7/11 11:32
 * 邮箱：hanyinit@163.com
 */


import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.gen.DaoMaster;
import com.hy.mvp.greendao.gen.DaoSession;

/**
 *  greenDao管理类
 */
public class DaoManager {
    private static DaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), "mba_db", null);
        DaoMaster mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

            public static DaoManager getInstance() {
        if (mInstance == null) {
            mInstance = new DaoManager();
        }
        return mInstance;
    }

}
