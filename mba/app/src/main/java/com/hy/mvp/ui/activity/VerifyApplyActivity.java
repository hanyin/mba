package com.hy.mvp.ui.activity;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.greendao.manager.InvitationManager;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.bean.Friend;
import com.hy.mvp.ui.bean.Invitation;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.utils.CommonUtil;
import com.hy.mvp.utils.GlideUtils;
import com.hy.mvp.view.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VerifyApplyActivity extends BaseActivity {

    Friend friend;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.imageHead)
    ImageView imageHead;
    @BindView(R.id.tvRealName)
    TextView tvRealName;
    @BindView(R.id.tvNickName)
    TextView tvNickName;
    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_verify_apply);
        showTitleBack();
        setTitleText("验证申请");
        setTitleRight("发送");
        ButterKnife.bind(this);
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        loadingView.showLoading();
        String content = etContent.getText().toString();
        String userId = AppConfigHelper.getConfig(AppConfigDef.userId);
        String id = friend.getLeaguerId();
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        apiService.inviteFriend(userId,id,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseResult<Invitation>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingView.hideLoading();
                        CommonUtil.showToast(getResources().getString(R.string.net_error));
                    }

                    @Override
                    public void onNext(ResponseResult<Invitation> responseResult) {
                        loadingView.hideLoading();
                        if(responseResult.getStatus()>0){
                            Invitation invitation= responseResult.getRespData();
                            InvitationManager.getInstance().insertInvitation(invitation);
                            finish();
                        }else{
                            CommonUtil.showToast(responseResult.getErr());
                        }
                    }
                });
    }

    @Override
    public void initData() {
        super.initData();
        friend = (Friend) getIntent().getSerializableExtra("friend");
        GlideUtils.load(VerifyApplyActivity.this,friend.getProtrait_url(),imageHead);
        tvRealName.setText(friend.getRealname());
        tvNickName.setText(friend.getNickname());
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
