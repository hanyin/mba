package com.hy.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hy.mvp.R;
import com.hy.mvp.ui.activity.TribeActivity;
import com.hy.mvp.ui.base.BaseFragment;
import com.hy.mvp.ui.base.BasePresenter;

import butterknife.BindView;

/**
 * 作者：hanyin on 2017/8/4 12:59
 * 邮箱：hanyinit@163.com
 */
//部落
public class TribeFragment extends BaseFragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.btnTribe)
    Button btnTribe;
    public TribeFragment() {
    }



    public static TribeFragment newInstance(String param1, String param2) {
        TribeFragment fragment = new TribeFragment();
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
    public void initListener() {
        super.initListener();
        btnTribe.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_tribe;
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnTribe:
                startActivity(new Intent(getActivity(),TribeActivity.class));
                break;
        }
    }
}
