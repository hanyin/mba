package com.hy.mvp.greendao.manager;


import com.hy.mvp.greendao.bean.User;
import com.hy.mvp.greendao.gen.UserDao;

/**
 * 作者：hanyin on 2017/8/9 13:01s
 * 邮箱：hanyinit@163.com
 */

public class UserManager {
    private final static String TAG = UserManager.class.getSimpleName();
    private static UserManager userManager;
    public static UserDao userDao;

    public static UserManager getInstance() {
        userDao = GreenDaoManager.getInstance().getSession().getUserDao();
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public User getUser(){
        return userDao.queryBuilder().build().unique();
    }
    public void addUser(User user){
        userDao.insert(user);
    }
}
