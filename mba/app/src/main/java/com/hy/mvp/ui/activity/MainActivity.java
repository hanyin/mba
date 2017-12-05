package com.hy.mvp.ui.activity;



import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.serivce.MsfService;
import com.hy.mvp.ui.adapter.FragmentAdapter;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.base.Presenter;
import com.hy.mvp.ui.fragment.BlogosphereFragment;
import com.hy.mvp.ui.fragment.DiscoverFragment;
import com.hy.mvp.ui.fragment.MineFragment;
import com.hy.mvp.ui.fragment.MinesFragment;
import com.hy.mvp.ui.fragment.TribeFragment;
import com.hy.mvp.utils.ToastUtils;
import com.hy.mvp.view.NoScrollViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 作者：hanyin on 2017/8/3 15:02
 * 邮箱：hanyinit@163.com
 */

public class MainActivity extends BaseActivity  {

    @BindView(R.id.viewPager)
     NoScrollViewPager viewPager;
    @BindView(R.id.bottomBar)
     BottomBar bottomBar;
    private List<Fragment> fragments;
    private List<String> titles;
    private long mExitTime = 0l;
    private boolean hadIntercept;
    String typeId = "";
    String content ="";
    String name ="";
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void initView() {
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(getIntent()!=null){
            if(getIntent().getStringExtra("typeId")!=null){
                typeId = getIntent().getStringExtra("typeId");
            }
            if(getIntent().getStringExtra("content")!=null){
                content = getIntent().getStringExtra("content");
            }
            if(getIntent().getStringExtra("name")!=null){
                name = getIntent().getStringExtra("name");
            }
        }
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }


    @Override
    public void initData() {
        BlogosphereFragment blogosphereFragment = BlogosphereFragment.newInstance(typeId,content,name);
        fragments.add(blogosphereFragment);
        fragments.add(new TribeFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new MinesFragment());
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void initListener() {
        final FragmentPagerAdapter fragmentPagerAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(fragmentPagerAdapter);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.navigation_blogosphere:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_tribe:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_discover:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.navigation_mine:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
    }



    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-mExitTime>2000){
            ToastUtils.showToast("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        }else{
            MsfService.getInstance().stopSelf();
            MyApplication.getInstance().exit();
        }
    }
}