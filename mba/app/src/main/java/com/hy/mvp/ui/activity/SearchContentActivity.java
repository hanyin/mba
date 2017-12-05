package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.bean.BPTypeData;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.utils.CommonUtil;
import com.hy.mvp.view.LoadingView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchContentActivity extends BaseActivity {

    @BindView(R.id.tvContentType)
    TextView tvContentType;
    @BindView(R.id.LoadingView)
    LoadingView loadingView;
    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.etname)
    EditText etname;
    ArrayList<BPTypeData.BpTypeListBean> bpType;
    int index ;
    @Override
    public void initView() {
        super.initView();
        setMainView(R.layout.activity_search_content);
        MyApplication.getInstance().addActivity(this);
        setTitleRight("搜索");
        setNavigationIcon(R.mipmap.btn_back);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        String content = etContent.getText().toString();
        String name = etname.getText().toString();
        Intent intent= new Intent(SearchContentActivity.this,MainActivity.class);
        //query_content,query_realname,query_nickname,query_typeId
        if(bpType!=null){
            intent.putExtra("typeId",String.valueOf(bpType.get(index).getId()));
        }
        intent.putExtra("content",content);
        intent.putExtra("name",name);
        startActivity(intent);
    }

    @Override
    public void initListener() {
        super.initListener();
        tvContentType.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tvContentType:
                getBPTypeList();
                break;
        }
    }
    private void getBPTypeList() {
        loadingView.showLoading();
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        apiService.getBPTypeList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseResult<BPTypeData>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        loadingView.hideLoading();
                        CommonUtil.showToast("网络异常,请检查网络");
                    }
                    @Override
                    public void onNext(ResponseResult<BPTypeData> responseResult) {
                        loadingView.hideLoading();
                        ArrayList<String> content = new ArrayList<>();
                        bpType = (ArrayList<BPTypeData.BpTypeListBean>) responseResult.getRespData().getBpTypeList();
                        for (int i = 0; i < bpType.size() ; i++) {
                            content.add(bpType.get(i).getType_name());
                        }
                        Intent intent = new Intent(SearchContentActivity.this,BottomCommonViewActivity.class);
                        intent.putStringArrayListExtra("commonLists",content);
                        startActivityForResult(intent,1);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data ==null)return;
        if(requestCode == 1){
            String contentType =  data.getStringExtra("commonData");
            index = data.getIntExtra("index",0);
            tvContentType.setText(contentType);
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

}
