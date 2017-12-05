package com.hy.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hy.mvp.R;
import com.hy.mvp.greendao.bean.Region;
import com.hy.mvp.greendao.manager.AddrBookManager;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.greendao.manager.RegionManager;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.ui.activity.MyAddressBookActivity;
import com.hy.mvp.ui.activity.MyQRcodeActivity;
import com.hy.mvp.ui.activity.MySettingActivity;
import com.hy.mvp.ui.activity.QueryCityActivity;
import com.hy.mvp.ui.activity.RegisterOneActivity;
import com.hy.mvp.ui.base.BaseFragment;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.bean.AddrBook;
import com.hy.mvp.ui.bean.AddrBooksData;
import com.hy.mvp.ui.bean.CityListData;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.utils.GlideUtils;
import com.hy.mvp.utils.PinyinUtils;
import com.hy.mvp.utils.ToastUtils;
import com.hy.mvp.view.LoadingView;

import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MinesFragment extends BaseFragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    @BindView(R.id.llHeadIcon)
    LinearLayout llHeadIcon;
    @BindView(R.id.imageHead)
    ImageView  imageHead;
    @BindView(R.id.llSetting)
    LinearLayout llSetting;
    @BindView(R.id.llMyAddressBook)
    LinearLayout llMyAddressBook;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.tvUserId)
    TextView tvUserId;
    public MinesFragment() {
    }

    public static MinesFragment newInstance(String param1, String param2) {
        MinesFragment fragment = new MinesFragment();
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
    public void initView(View rootView) {
        super.initView(rootView);
    }

    @Override
    public void initListener() {
        super.initListener();
        llHeadIcon.setOnClickListener(this);
        llSetting.setOnClickListener(this);
        llMyAddressBook.setOnClickListener(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_mines;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {
        String protrait_url = AppConfigHelper.getConfig(AppConfigDef.protrait_url);
        String tvUserIdStr = AppConfigHelper.getConfig(AppConfigDef.userId);
        GlideUtils.load(getActivity(),protrait_url,imageHead);
        tvUserId.setText(tvUserIdStr);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llHeadIcon:
                Intent intent = new Intent(getActivity(), MyQRcodeActivity.class);
                startActivity(intent);
                break;
            case R.id.llSetting:
                startActivity(new Intent(getActivity(), MySettingActivity.class));
                break;
            case R.id.llMyAddressBook:
                getAddrBooks();
                break;
        }


    }

    private void getAddrBooks() {
        loadingView.showLoading();
        ApiService mApiService = ApiRetrofit.getInstance().getApiService();
        String userId = AppConfigHelper.getConfig(AppConfigDef.userId);
        mApiService.getAddrbook(userId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
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
                            AddrBookManager.getInstance().clearAddeBooks();
                           List<AddrBook> addrBooks = responseResult.getRespData().getAddrBooks();
                            for (int i = 0; i < addrBooks.size(); i++) {
                                AddrBook addrBook = addrBooks.get(i);
                                String pinyin = PinyinUtils.getPingYin(addrBook.getContacts_name());
                                addrBook.setPingyin(pinyin);
                                AddrBookManager.getInstance().insertAddrBook(addrBooks.get(i));
                            }
                            startActivity(new Intent(getActivity(),MyAddressBookActivity.class));
                        }else{
                            ToastUtils.showToast(responseResult.getErr());
                        }
                    }
                });
    }
}