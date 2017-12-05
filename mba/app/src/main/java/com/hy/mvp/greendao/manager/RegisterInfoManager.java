package com.hy.mvp.greendao.manager;

import com.hy.mvp.greendao.bean.RegisterInfo;
import com.hy.mvp.greendao.gen.RegisterInfoDao;

/**
 * 作者：hanyin on 2017/8/14 14:50
 * 邮箱：hanyinit@163.com
 */

public class RegisterInfoManager {
    private final static String TAG = RegisterInfoManager.class.getSimpleName();
    private static RegisterInfoManager mRegisterInfoManager;
    public static RegisterInfoDao mRegisterInfoDao;

    public static RegisterInfoManager getInstance() {
        mRegisterInfoDao = GreenDaoManager.getInstance().getSession().getRegisterInfoDao();
        if (mRegisterInfoManager == null) {
            mRegisterInfoManager = new RegisterInfoManager();
        }
        return mRegisterInfoManager;
    }
    public void insertRegisterInfo(RegisterInfo registerInfo){
        mRegisterInfoDao.insert(registerInfo);
    }


}
