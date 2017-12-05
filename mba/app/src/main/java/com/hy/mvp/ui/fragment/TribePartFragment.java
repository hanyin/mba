package com.hy.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hy.mvp.R;
import com.hy.mvp.ui.adapter.NewFriendsAdapter;
import com.hy.mvp.ui.base.BaseFragment;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.bean.Friend;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *  2017/10/25 13:12
 *  hanyin
 *  hanyinit@163.com
 */
public class TribePartFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    NewFriendsAdapter newFriendsAdapter;
    List<Friend> datas ;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public TribePartFragment() {
    }

    public static TribePartFragment newInstance(String param1, String param2) {
        TribePartFragment fragment = new TribePartFragment();
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
    public void initData() {
        super.initData();
        datas = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            Friend friend = new Friend();
            datas.add(friend);
        }
        newFriendsAdapter = new NewFriendsAdapter(getActivity(),datas);
        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity());
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),1));
        recyclerView.setAdapter(newFriendsAdapter);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_tribe_part;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }


}
