package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.ui.adapter.NewFriendsAdapter;
import com.hy.mvp.ui.adapter.OnItemClickListener;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.bean.Friend;
import com.hy.mvp.ui.bean.FriendData;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.utils.CommonUtil;
import com.hy.mvp.view.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddNewFriends extends BaseActivity implements OnItemClickListener{


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    NewFriendsAdapter newFriendsAdapter;
    List<Friend> friends ;
    @BindView(R.id.searchFriend)
    EditText searchFriend;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_add_new_friends);
        showTitleBack();
        setTitleText("添加朋友");
        setTitleRight("确定");
        ButterKnife.bind(this);
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        String searchFriendStr = searchFriend.getText().toString();
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        if(!TextUtils.isEmpty(searchFriendStr)){
            loadingView.showLoading();
            apiService.searchFriends(searchFriendStr,"","")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseResult<FriendData>>() {
                        @Override
                        public void onCompleted() {
                            loadingView.hideLoading();
                        }

                        @Override
                        public void onError(Throwable e) {
                            loadingView.hideLoading();
                            CommonUtil.showToast(getResources().getString(R.string.net_error));
                        }

                        @Override
                        public void onNext(ResponseResult<FriendData> responseResult) {
                            loadingView.hideLoading();
                            if(responseResult.getStatus()>0){
                                List<Friend> friendList = responseResult.getRespData().getFriends();
                                friends.addAll(friendList);
                                newFriendsAdapter.notifyDataSetChanged();
                            }else{
                                CommonUtil.showToast(responseResult.getErr());
                            }
                        }
                    });
        }else{
            CommonUtil.showToast("请输入搜索条件");
        }

    }

    @Override
    public void initData() {
        super.initData();
        friends = new ArrayList<>();
        /*for (int i = 0; i <10 ; i++) {
            friends.add("ceshi"+1);
        }*/
        newFriendsAdapter = new NewFriendsAdapter(this,friends);
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,1));
        recyclerView.setAdapter(newFriendsAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        newFriendsAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onItemClick(View view, int position) {
        Friend friend = friends.get(position);
        Intent intent = new Intent(AddNewFriends.this,VerifyApplyActivity.class);
        intent.putExtra("friend",friend);
        startActivity(intent);
    }
}
