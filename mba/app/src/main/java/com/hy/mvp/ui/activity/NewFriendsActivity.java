package com.hy.mvp.ui.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.ui.adapter.InvitationAdapter;
import com.hy.mvp.ui.adapter.NewFriendsAdapter;
import com.hy.mvp.ui.adapter.OnViewClickListener;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.bean.Friend;
import com.hy.mvp.ui.bean.Invitation;
import com.hy.mvp.ui.bean.Invitations;
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

/**
 *  2017/10/24 10:09
 *  hanyin
 *  hanyinit@163.com
 */
public class NewFriendsActivity extends BaseActivity implements OnViewClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    InvitationAdapter invitationAdapter;
    List<Invitation> invitations ;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_new_friends);
        showTitleBack();
        setTitleText("新的朋友");
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        invitations = new ArrayList<>();
        invitationAdapter = new InvitationAdapter(this,invitations);
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,1));
        recyclerView.setAdapter(invitationAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        invitationAdapter.setOnViewClickLisrtener(this);
        loadingView.showLoading();
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        String userId = AppConfigHelper.getConfig(AppConfigDef.userId);
        apiService.loadMyInvitation(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseResult<Invitations>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        loadingView.hideLoading();
                        CommonUtil.showToast(getResources().getString(R.string.net_error));
                    }

                    @Override
                    public void onNext(ResponseResult<Invitations> responseResult) {
                        loadingView.hideLoading();
                        if(responseResult.getStatus()>0){
                            List<Invitation> invitationList = responseResult.getRespData().getInvitations();
                            invitations.addAll(invitationList);
                            invitationAdapter.notifyDataSetChanged();
                        }else{
                            CommonUtil.showToast(responseResult.getErr());

                        }
                    }
                });

    }
    

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onViewClick(View view, int position) {
        String userId = AppConfigHelper.getConfig(AppConfigDef.userId);
        String  inviterId =  invitations.get(position).getInviterId();
        String id = invitations.get(position).getId();
        addMyAddrBook(userId,inviterId,id);
    }
    private void addMyAddrBook(String userId, String inviterId,String id) {
        loadingView.showLoading();
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        apiService.addMyAddrBook(userId,inviterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingView.hideLoading();
                        CommonUtil.showToast(getResources().getString(R.string.net_error));
                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {
                        loadingView.hideLoading();
                        if(responseResult.getStatus()>0){
                            modifyStatusOfInvitation(id);
                        }else{
                            CommonUtil.showToast(responseResult.getErr());
                        }
                    }
                });
    }

    private void modifyStatusOfInvitation(String id) {
        loadingView.showLoading();
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        apiService.modifyStatusOfInvitation(id,"2")
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Subscriber<ResponseResult>() {
                      @Override
                      public void onCompleted() {

                      }

                      @Override
                      public void onError(Throwable e) {
                          loadingView.hideLoading();
                          CommonUtil.showToast(getResources().getString(R.string.net_error));
                      }

                      @Override
                      public void onNext(ResponseResult responseResult) {
                          loadingView.hideLoading();
                          if(responseResult.getStatus()>0){
                              CommonUtil.showToast("成功");
                          }else{
                              CommonUtil.showToast(responseResult.getErr());
                          }

                      }
                  });
    }

}
