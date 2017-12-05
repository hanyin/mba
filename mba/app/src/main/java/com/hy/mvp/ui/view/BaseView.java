package com.hy.mvp.ui.view;

/**
 * 作者：hanyin on 2017/8/10 14:09
 * 邮箱：hanyinit@163.com
 */

public interface BaseView {
    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示网络错误，modify 对网络异常在 BaseActivity 和 BaseFragment 统一处理
     */
    void showNetError();
}
