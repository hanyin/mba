package com.hy.mvp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * 作者：hanyin on 2017/8/3 13:48
 * 邮箱：hanyinit@163.com
 */

public abstract class BaseFragment<T extends BasePresenter> extends LazyLoadFragment {

    protected T mPresenter;
    private View rootView;
    protected Activity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(provideContentViewId(),container,false);
            ButterKnife.bind(this, rootView);
            initView(rootView);
            initData();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    /**StateView的根布局，默认是整个界面，如果需要变换可以重写此方法*/
    public View getStateViewRoot() {
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }



    /**
     * 初始化一些view
     * @param rootView
     */
    public void initView(View rootView) {
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 设置listener的操作
     */
    public void initListener() {

    }


    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        loadData();
    }


    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    //加载数据
    protected abstract void loadData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        rootView = null;
    }

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
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity)getActivity()).initToolBar(toolbar, homeAsUpEnabled, title);
    }
}
