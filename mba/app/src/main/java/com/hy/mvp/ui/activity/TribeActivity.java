package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.ui.adapter.ViewPagerAdapter;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.fragment.AllTribeFragment;
import com.hy.mvp.ui.fragment.TribePartFragment;
import com.hy.mvp.view.PopupItem;
import com.hy.mvp.view.PopupMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TribeActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    ViewPagerAdapter mPagerAdapter;
    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_tribe);
        setTitleText("部落");
        showTitleBack();
        setTitleRightImage(R.mipmap.right_nva);
        ButterKnife.bind(this);
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        showMore();
    }

    @Override
    public void initData() {
        super.initData();
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new AllTribeFragment());
        fragments.add(new TribePartFragment());
        fragments.add(new TribePartFragment());
        fragments.add(new TribePartFragment());
        mPagerAdapter.setItems(fragments, new String[] {"世界", "部落", "家族","私聊"});
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    private void showMore() {
        final PopupMenu popupMenu = new PopupMenu(this, getToolBar());
        List<PopupItem> items = new ArrayList<>();
        PopupItem pop1 = new PopupItem();
        pop1.setContent("通讯录");
        pop1.setIcon(R.mipmap.nav_rcon_home_nor);
        items.add(pop1);
        PopupItem pop2 = new PopupItem();
        pop2.setContent("查找家族");
        pop2.setIcon(R.mipmap.nav_rcon_home_nor);
        items.add(pop2);
        PopupItem pop3 = new PopupItem();
        pop3.setContent("查找部落");
        pop3.setIcon(R.mipmap.nav_rcon_home_nor);
        items.add(pop3);

        popupMenu.setDataChanged(items);
        popupMenu.setOnPopupItemClickListener(new PopupMenu.OnPopupItemClickListener() {
            @Override
            public void onItemClicked(int i, PopupItem popupItem) {
                Intent intent = new Intent();
                if(i==0){
                    //通讯录
                    intent.setClass(TribeActivity.this,MyAddressBookActivity.class);
                    startActivity(intent);
                }else if(i==1){
                    //查找家族
                    intent.setClass(TribeActivity.this, AddFamilyActivity.class);
                    startActivity(intent);
                }else if(i==2){
                    //查找部落
                    intent.setClass(TribeActivity.this, TribeQueryActivity.class);
                    startActivity(intent);
                }

                popupMenu.hide();
            }
        });
        popupMenu.setPopupMenuWidth(140);
        popupMenu.show();
    }
}
