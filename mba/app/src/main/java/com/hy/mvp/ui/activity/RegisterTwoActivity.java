package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.bean.Region;
import com.hy.mvp.greendao.bean.RegisterInfo;
import com.hy.mvp.greendao.manager.RegionManager;
import com.hy.mvp.greendao.manager.RegisterInfoManager;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.http.https.HttpMethods;
import com.hy.mvp.http.subscribers.ProgressSubscriber;
import com.hy.mvp.http.subscribers.SubscriberOnNextListener;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.base.Presenter;
import com.hy.mvp.ui.bean.CityListData;
import com.hy.mvp.ui.bean.Leaguer;
import com.hy.mvp.ui.bean.RegionDataList;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.ui.bean.YearSalary;
import com.hy.mvp.utils.ToastUtils;
import com.hy.mvp.view.LoadingView;
import com.maning.library.zxing.CaptureActivity;
import com.maning.library.zxing.ZXingConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 作者：hanyin on 2017/8/3 15:02
 * 邮箱：hanyinit@163.com
 */

public class RegisterTwoActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.btnRegisterNext)
    Button btnRegisterNext;
    @BindView(R.id.btnRegisterPre)
    Button btnRegisterPre;
    @BindView(R.id.rlScan)
    RelativeLayout rlScan;
    @BindView(R.id.cbPublic)
    CheckBox cbPublic;
    @BindView(R.id.rlyearSalary)
    LinearLayout rlyearSalary;
    @BindView(R.id.tvYearSalary)
    TextView tvYearSalary;
    @BindView(R.id.tvReferrer)
    TextView tvReferrer;
    @BindView(R.id.tvReferrerSchool)
    TextView tvReferrerSchool;
    @BindView(R.id.etCorp)
    EditText etCorp;
    @BindView(R.id.etDept)
    EditText etDept;
    @BindView(R.id.etPost)
    EditText etPost;
    @BindView(R.id.loading)
    LoadingView loadingView;
    int ispublic = 0;
    Leaguer leaguer;
    private SubscriberOnNextListener getRegionOnNext;
    @Override
    public void initView() {
        setMainView(R.layout.activity_register_two);
        MyApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        setTitleText(R.string.new_member_register);
        showTitleBack();
        setNavigationIcon(R.mipmap.btn_back);
        getRegionOnNext = new SubscriberOnNextListener<YearSalary.RespDataBean>() {
            @Override
            public void onNext(YearSalary.RespDataBean regionDataList) {
                Intent intentSalary = new Intent(RegisterTwoActivity.this,BottomYearSalaryActivity.class);
                String[] salary  = new String[regionDataList.getYsalaryList().size()];
                for (int i = 0; i < regionDataList.getYsalaryList().size(); i++) {
                    salary[i] = regionDataList.getYsalaryList().get(i).getSalary_range();
                }
                intentSalary.putExtra("ysalaryList",salary);
                startActivityForResult(intentSalary, 2);
            }
        };
    }

    @Override
    public void initData() {
        if(getIntent().getSerializableExtra("leaguer")!=null){
            leaguer  = (Leaguer) getIntent().getSerializableExtra("leaguer");
        }
        if(cbPublic.isChecked()){
            ispublic =0;
        }else{
            ispublic =1;
        }
    }

    @Override
    public void initListener() {
        btnRegisterNext.setOnClickListener(this);
        btnRegisterPre.setOnClickListener(this);
        rlScan.setOnClickListener(this);
        rlyearSalary.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btnRegisterNext:
                String corp = etCorp.getText().toString();
                String dept = etDept.getText().toString();
                String post = etPost.getText().toString();
                String yearSalary = tvYearSalary.getText().toString();
                String referrer = tvReferrer.getText().toString();
                String referrerSchool =  tvReferrerSchool.getText().toString();
                leaguer.setIspublic(ispublic);
            if(leaguer!=null){
               /* mRegisterInfo.setCorp(corp);
                mRegisterInfo.setDept(dept);
                mRegisterInfo.setPost(post);
                mRegisterInfo.setYsalary_id(yearSalary);
                mRegisterInfo.setReferee_id(referrer);*/
                leaguer.setIspublic(ispublic);
                }
                Intent intent = new Intent(RegisterTwoActivity.this,RegisterThreeActivity.class);
                intent.putExtra("leaguer",leaguer);
                startActivity(intent);
                break;
            case R.id.btnRegisterPre:
                super.onBackPressed();
                break;
            case R.id.rlScan:
                Intent intentScan = new Intent(RegisterTwoActivity.this,CaptureActivity.class);
                startActivityForResult(intentScan, 1);
                break;
            case R.id.rlyearSalary:
                getYearSalary();
                //HttpMethods.getInstance().getYearSalary(new ProgressSubscriber(getRegionOnNext, RegisterTwoActivity.this));
                break;
        }
    }

    private void getYearSalary() {
        loadingView.showLoading();
        ApiService mApiService = ApiRetrofit.getInstance().getApiService();
        mApiService.getYearSalary()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<YearSalary<YearSalary.RespDataBean>>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        loadingView.hideLoading();
                        ToastUtils.showToast("网络异常,请检查网络");
                    }
                    @Override
                    public void onNext(YearSalary<YearSalary.RespDataBean> responseResult) {
                        loadingView.hideLoading();
                        Intent intentSalary = new Intent(RegisterTwoActivity.this,BottomYearSalaryActivity.class);
                        String[] salary  = new String[responseResult.getRespData().getYsalaryList().size()];
                        for (int i = 0; i < responseResult.getRespData().getYsalaryList().size(); i++) {
                            salary[i] = responseResult.getRespData().getYsalaryList().get(i).getSalary_range();
                        }
                        intentSalary.putExtra("ysalaryList",salary);
                        startActivityForResult(intentSalary, 2);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (resultCode){
            case ZXingConstants.ScanRequltCode:
                /**
                 * 拿到解析完成的字符串
                 */
                String result = data.getStringExtra(ZXingConstants.ScanResult);
                Toast.makeText(RegisterTwoActivity.this,"二维码返回"+result,Toast.LENGTH_LONG).show();
                break;
            case 2:
                String salary =  data.getStringExtra("salary");
                tvYearSalary.setText(salary);
                break;
        }
    }
}
