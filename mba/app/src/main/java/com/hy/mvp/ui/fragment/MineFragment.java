package com.hy.mvp.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hy.mvp.R;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.ui.adapter.RecycleMoreItemAdapter;
import com.hy.mvp.ui.base.BaseFragment;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.utils.GlideUtils;
import com.hy.mvp.view.GridDividerItemDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：hanyin on 2017/8/4 12:59
 * 邮箱：hanyinit@163.com
 */
//我的
public class MineFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.imageHead)
    ImageView imageHead;
    List<String> mDatas;

    int[] dataIcon = {R.mipmap.logo,R.mipmap.logo,R.mipmap.logo
    ,R.mipmap.logo,R.mipmap.logo,R.mipmap.logo,
            R.mipmap.logo,R.mipmap.logo,R.mipmap.logo,};
    private RecycleMoreItemAdapter recycleMoreItemAdapter;
    public MineFragment() {
    }


    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);
        String qr_url = AppConfigHelper.getConfig(AppConfigDef.qr_url);
        String protrait_url = AppConfigHelper.getConfig(AppConfigDef.protrait_url);
        GlideUtils.loadRound(getActivity(),protrait_url,imageHead);
        GlideUtils.load(getActivity(),qr_url,imageview);
    }


    //当第一次可见的时候，加载数据
    @Override
    protected void loadData() {
        mDatas = Arrays.asList(this.getResources().getStringArray(R.array.menusetting));
        recycleMoreItemAdapter = new RecycleMoreItemAdapter(getActivity(), mDatas,dataIcon);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridDividerItemDecoration(1,getActivity().getResources().getColor(R.color.black)));
        //recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        recyclerView.setAdapter(recycleMoreItemAdapter);
        /*String str = "18761537857";
        Bitmap qrImage = ZXingUtils.createQRImage(str);
        if (qrImage != null) {
            imageview.setImageBitmap(qrImage);
        } else {
            ToastUtils.showToast("生成失败");
        }*/
    }


}
