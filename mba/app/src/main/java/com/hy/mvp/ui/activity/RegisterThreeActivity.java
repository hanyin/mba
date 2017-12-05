package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.bean.Leaguer;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.ui.bean.VerifyAccount;
import com.hy.mvp.utils.CommonUtil;
import com.hy.mvp.utils.ToastUtils;
import com.hy.mvp.view.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 作者：hanyin on 2017/8/3 15:02
 * 邮箱：hanyinit@163.com
 */

public class RegisterThreeActivity extends BaseActivity {

    @BindView(R.id.btnRegisterSubmit)
     Button btnRegisterSubmit;
    @BindView(R.id.btnRegisterPre)
     Button btnRegisterPre;
     @BindView(R.id.etAccount)
     EditText etAccount;
     @BindView(R.id.etLoginPwd)
     EditText etLoginPwd;
     @BindView(R.id.etLoginPwdAgain)
     EditText etLoginPwdAgain;
    Leaguer leaguer;
    @BindView(R.id.loading)
    LoadingView loadingView;
    @BindView(R.id.verifyAccount)
    Button verifyAccount;
    Boolean isVerifySuccess = false;
    @Override
    public void initView() {
        setMainView(R.layout.activity_register_three);
        MyApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        setTitleText(R.string.new_member_register);
        showTitleBack();
        setNavigationIcon(R.mipmap.btn_back);
    }

    @Override
    public void initData() {
        if(getIntent().getSerializableExtra("leaguer")!=null){
            leaguer = (Leaguer) getIntent().getSerializableExtra("leaguer");
        }

    }

    @Override
    public void initListener() {
        btnRegisterSubmit.setOnClickListener(this);
        btnRegisterPre.setOnClickListener(this);
        verifyAccount.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btnRegisterPre:
                super.onBackPressed();
                break;
            case R.id.btnRegisterSubmit:
               String account =  etAccount.getText().toString();
              String loginPwd =   etLoginPwd.getText().toString();
               String loginPwsAgain =  etLoginPwdAgain.getText().toString();
                if(leaguer!=null){
                    if(!TextUtils.isEmpty(account)){
                        leaguer.setLogin_name(account);
                    }else{
                        ToastUtils.showToast("账号不能为空");
                        return;
                    }
                    if(!TextUtils.isEmpty(loginPwd)){
                        if(!TextUtils.isEmpty(loginPwsAgain)){
                            if(loginPwd.equalsIgnoreCase(loginPwsAgain)){
                            if(loginPwd.length()>=6&&loginPwd.length()<20){
                                leaguer.setLogin_pwd(loginPwd);
                            }else{
                                ToastUtils.showToast("密码不符合规则,请按下面提示设置密码");
                                return;
                            }
                            }else{
                                ToastUtils.showToast("密码不一致,请重新设置密码");
                               return;
                            }
                        }else{
                            ToastUtils.showToast("登录确认密码不能为空");
                            return;
                        }
                    }else{
                        ToastUtils.showToast("登录密码不能为空");
                        return;
                    }
                }
                //RegisterInfoManager.getInstance().insertRegisterInfo(mRegisterInfo);
                registerLeaguer(leaguer);
                break;
            case R.id.verifyAccount:
                String accountName =  etAccount.getText().toString();
                verifyAccount(accountName);
                break;
        }
    }

    private void verifyAccount(String accountName) {
        loadingView.showLoading();
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        apiService.verifyAccount(accountName)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseResult<VerifyAccount>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            loadingView.showLoading();
                            CommonUtil.showToast("网络异常,请检查网络");
                        }

                        @Override
                        public void onNext(ResponseResult<VerifyAccount> responseResult) {
                            loadingView.hideLoading();
                            if(responseResult.getStatus()>0){
                                String isExist = responseResult.getRespData().getIsExisted();
                                if("false".equalsIgnoreCase(isExist)){
                                    isVerifySuccess = true;
                                    CommonUtil.showToast("账号验证成功");
                                }else if("true".equalsIgnoreCase(isExist)){
                                    isVerifySuccess = false;
                                    CommonUtil.showToast("账号已经注册");
                                }
                            }else{
                                CommonUtil.showToast(responseResult.getErr());
                            }
                        }
                    });
    }

    private void registerLeaguer(Leaguer leaguer) {
        if(!isVerifySuccess){
            CommonUtil.showToast("您的账号已经注册或者未验证");
            return;
        }
        loadingView.showLoading();
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        Gson gson = new Gson();
        String registerInfoStr = gson.toJson(leaguer);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),registerInfoStr);
        Log.i("data",registerInfoStr);
        apiService.registerLeaguer(body)
                  .subscribeOn(Schedulers.io())
                  .unsubscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Subscriber<ResponseResult>() {
                      @Override
                      public void onCompleted() {

                      }

                      @Override
                      public void onError(Throwable e) {
                          loadingView.hideLoading();
                          ToastUtils.showToast("网络异常,请检查网络");
                      }

                      @Override
                      public void onNext(ResponseResult responseResult) {
                          loadingView.hideLoading();
                          if(responseResult.getStatus()>0){
                              Intent intent = new Intent(RegisterThreeActivity.this,SplashActivity.class);
                              startActivity(intent);
                          }else{
                              ToastUtils.showToast(responseResult.getErr());
                          }
                      }
                  });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
