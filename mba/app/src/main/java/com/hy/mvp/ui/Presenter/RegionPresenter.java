package com.hy.mvp.ui.Presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hy.mvp.http.ApiService;
import com.hy.mvp.ui.base.Presenter;
import com.hy.mvp.ui.view.RegionView;

import rx.subscriptions.CompositeSubscription;


/**
 * 作者：hanyin on 2017/8/10 13:49
 * 邮箱：hanyinit@163.com
 */

public class RegionPresenter implements Presenter{
    private ApiService mApiService;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private RegionView mRegionView;
    public RegionPresenter (Context mContext,RegionView mRegionView){
        this.mContext = mContext;
        this.mRegionView = mRegionView;
    }
    @Override
    public void onCreate() {
       // mApiService = RetrofitService.getInstance().getApiService();
        mCompositeSubscription = new CompositeSubscription();

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void attachView(View view) {
        mRegionView = (RegionView)view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {
    }
    public void getRegionList(){
        /*mCompositeSubscription.add( mApiService.getRegionList()
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                mRegionView.showLoading();
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegionData>() {
                    @Override
                    public void onCompleted() {
                        mRegionView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        mRegionView.showNetError();
                    }

                    @Override
                    public void onNext(RegionData regionData) {
                        mRegionView.loadData(regionData);
                    }
                })
        );*/
    }
}
