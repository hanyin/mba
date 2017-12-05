package com.hy.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.bean.AppConfig;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.serivce.MsfService;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.base.Presenter;
import com.hy.mvp.ui.bean.AddrBooksData;
import com.hy.mvp.ui.bean.Leaguer;
import com.hy.mvp.ui.bean.LeaguerData;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.utils.CommonUtil;
import com.hy.mvp.utils.ToastUtils;
import com.hy.mvp.view.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionGen;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：hanyin on 2017/8/3 15:02
 * 邮箱：hanyinit@163.com
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.tvFindPwd)
    TextView tvFindPwd;
    @BindView(R.id.tvNewUserRegister)
    TextView tvNewUserRegister;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.loading)
    LoadingView loadingView;
    @Override
    public void initView() {
        setMainView(R.layout.activity_login);
        MyApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        String  userName = AppConfigHelper.getConfig(AppConfigDef.username,"");
        String  password = AppConfigHelper.getConfig(AppConfigDef.password,"");
        if(!TextUtils.isEmpty(userName)){
            etUsername.setText(userName);
        }
        if(!TextUtils.isEmpty(password)){
            etPwd.setText(password);
        }
        PermissionGen.with(this)
                .addRequestCode(100)
                .permissions(
                        //电话通讯录
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.READ_PHONE_STATE,
                        //位置
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        //相机、麦克风
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.CAMERA,
                        //存储空间
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_SETTINGS
                )
                .request();
    }
    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        tvFindPwd.setOnClickListener(this);
        tvNewUserRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tvFindPwd://找回密码
                Intent findPwdIntent = new Intent(LoginActivity.this,FindPwdActivity.class);
                startActivity(findPwdIntent);
                break;
            case R.id.tvNewUserRegister://注册新会员
                Intent registerIntent = new Intent(LoginActivity.this,RegisterOneActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.btnLogin://登录
                String userName = etUsername.getText().toString();
                String passWord = etPwd.getText().toString();
                AppConfigHelper.setConfig(AppConfigDef.username,userName);
                AppConfigHelper.setConfig(AppConfigDef.password,passWord);
                if(TextUtils.isEmpty(userName)){
                    ToastUtils.showToast("登录账号不能为空");
                    return;
                }
                if(TextUtils.isEmpty(passWord)){
                    ToastUtils.showToast("登录密码不能为空");
                    return;
                }
                Intent intent=new Intent(this,MsfService.class);
                startService(intent);
                Login(userName,passWord);
                break;
        }
    }

    private void Login(String userName,String passWord) {
        loadingView.showLoading();
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        apiService.login(userName,passWord)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Subscriber<ResponseResult<LeaguerData>>() {
                     @Override
                     public void onCompleted() {

                     }

                     @Override
                     public void onError(Throwable e) {
                         loadingView.hideLoading();
                         ToastUtils.showToast("网络异常,请检查网络");
                     }

                     @Override
                     public void onNext(ResponseResult<LeaguerData> responseResult) {
                         loadingView.hideLoading();
                         if(responseResult.getStatus()>0){
                             Leaguer leaguer =  responseResult.getRespData().getLeaguer();
                             AppConfigHelper.setConfig(AppConfigDef.userId,leaguer.getId());
                             AppConfigHelper.setConfig(AppConfigDef.password,"123456");
                             AppConfigHelper.setConfig(AppConfigDef.protrait_url,leaguer.getProtrait_url());
                             AppConfigHelper.setConfig(AppConfigDef.qr_url,leaguer.getQr_url());
                             getAddrbook();
                         }else{
                             ToastUtils.showToast(responseResult.getErr());
                         }
                     }
                 });
           }

    private void getAddrbook() {
        loadingView.showLoading();
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        String userId = AppConfigHelper.getConfig(AppConfigDef.userId);
        apiService.getAddrbook(userId)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Subscriber<ResponseResult<AddrBooksData>>() {
                      @Override
                      public void onCompleted() {

                      }

                      @Override
                      public void onError(Throwable e) {
                          loadingView.hideLoading();
                          ToastUtils.showToast("网络异常,请检查网络");
                      }

                      @Override
                      public void onNext(ResponseResult<AddrBooksData> responseResult) {
                          loadingView.hideLoading();
                          if(responseResult.getStatus()>0){
                              Intent mainIntent = new Intent(LoginActivity.this,SplashActivity.class);
                              startActivity(mainIntent);
                              finish();
                          }else{
                              ToastUtils.showToast(responseResult.getErr());
                          }
                      }
                  });
    }
}
