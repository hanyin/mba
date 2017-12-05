package com.hy.mvp.ui.webview;

import android.os.Bundle;

import com.hy.mvp.ui.base.BaseFragment;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.base.Presenter;

/**
 * 作者：hanyin on 2017/8/11 15:36
 * 邮箱：hanyinit@163.com
 */

public abstract class BackHandledFragment extends BaseFragment {

    public BackHandledFragment() {
    }

   // protected BackHandledInterface mBackHandledInterface;

    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
    public abstract boolean onBackPressed();


    @Override
    public void onStart() {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
       // mBackHandledInterface.setSelectedFragment(this);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if(!(getActivity() instanceof BackHandledInterface)){
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        }else{
            this.mBackHandledInterface = (BackHandledInterface)getActivity();
        }*/
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return 0;
    }

    @Override
    protected void loadData() {

    }
}
