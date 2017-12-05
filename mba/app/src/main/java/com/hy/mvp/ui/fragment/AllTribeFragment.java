package com.hy.mvp.ui.fragment;

import android.os.Bundle;

import com.hy.mvp.R;
import com.hy.mvp.ui.base.BaseFragment;
import com.hy.mvp.ui.base.BasePresenter;

/**
 *  2017/10/25 13:08
 *  hanyin
 *  hanyinit@163.com
 */
public class AllTribeFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    public AllTribeFragment() {
    }

    public static AllTribeFragment newInstance(String param1, String param2) {
        AllTribeFragment fragment = new AllTribeFragment();
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
    protected int provideContentViewId() {
        return R.layout.fragment_all_tribe;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }


}
