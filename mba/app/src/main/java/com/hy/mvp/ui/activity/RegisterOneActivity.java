package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.bean.Region;
import com.hy.mvp.greendao.manager.RegionManager;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.http.subscribers.SubscriberOnNextListener;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.bean.CityListData;
import com.hy.mvp.ui.bean.Leaguer;
import com.hy.mvp.ui.bean.LinkFile;
import com.hy.mvp.ui.bean.RegionData;
import com.hy.mvp.ui.bean.RegionDataList;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.ui.view.RegionView;
import com.hy.mvp.utils.ToastUtils;
import com.hy.mvp.view.LoadingView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class RegisterOneActivity extends BaseActivity implements View.OnClickListener,RegionView,RadioGroup.OnCheckedChangeListener{
    @BindView(R.id.btnRegisterNext)
    Button btnRegisterNext;
    @BindView(R.id.llUploadPhoto)
    LinearLayout llUploadPhoto;
    @BindView(R.id.imagePhone)
    ImageView imagePhone;
    @BindView(R.id.rlOneAdress)
    LinearLayout rlOneAdress;
    @BindView(R.id.rlTwoAdress)
    LinearLayout rlTwoAdress;
    @BindView(R.id.rlThreeAdress)
    LinearLayout rlThreeAdress;
    @BindView(R.id.tvCityTwo)
    TextView tvCityTwo;
    @BindView(R.id.tvCityThree)
    TextView tvCityThree;
    RegionView mRegionView;
    @BindView(R.id.tvCityOne)
    TextView tvCityOne;
    @BindView(R.id.rgGroup)
    RadioGroup rgGroup;
    @BindView(R.id.rbMale)
    RadioButton rbMale;
    @BindView(R.id.rbFemale)
    RadioButton rbFemale;
    @BindView(R.id.etRealName)
    EditText etRealName;
    @BindView(R.id.etNickName)
    EditText etNickName;
    @BindView(R.id.etSchool)
    EditText etSchool;
    @BindView(R.id.etGrade)
    EditText etGrade;
    @BindView(R.id.etClassName)
    EditText etClassName;
    @BindView(R.id.etStudentId)
    EditText etStudentId;
    @BindView(R.id.loading)
    LoadingView loadingView;
    Leaguer leaguer = new Leaguer();
    int sexId = 1;
    int type = -1;
    long address_id1 = 0l;
    long address_id2 = 0l;
    long address_id3 = 0l;
    private SubscriberOnNextListener getRegionOnNext;
    private SubscriberOnNextListener getImageOnNext;
    //File filePath;
    @Override
    public void initView() {
        setMainView(R.layout.activity_register_one);
        MyApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        setTitleText(R.string.new_member_register);
        showTitleBack();
        setNavigationIcon(R.mipmap.btn_back);
        mRegionView = this;
        getRegionOnNext = new SubscriberOnNextListener<RegionDataList>() {
            @Override
            public void onNext(RegionDataList regionDataList) {
                /*for (int i = 0; i <regionDataList.getCityList().size() ; i++) {
                    RegionManager.getInstance().insertResion(regionDataList.getCityList().get(i));
                }*/
                RegionManager.getInstance().insertResion(regionDataList.getCityList());
                startActivityForResult(new Intent(RegisterOneActivity.this,QueryCityActivity.class),2);
            }
        };
        getImageOnNext = new SubscriberOnNextListener<LinkFile.FileBean>() {
            @Override
            public void onNext(LinkFile.FileBean responseBody) {
                ToastUtils.showToast("success"+responseBody.getUrl());
            }
        };
        /*File appDir = new File(Environment.getExternalStorageDirectory(), "clip");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "clipbitmap.jpg";
        filePath = new File(appDir, fileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            Bitmap bitmap  = BitmapFactory.decodeStream(fis);
            imagePhone.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        btnRegisterNext.setOnClickListener(this);
        llUploadPhoto.setOnClickListener(this);
        rlOneAdress.setOnClickListener(this);
        rlTwoAdress.setOnClickListener(this);
        rlThreeAdress.setOnClickListener(this);
        rgGroup.setOnCheckedChangeListener(this);

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case  R.id.btnRegisterNext:
                String realName =  etRealName.getText().toString();
                String nickName =  etNickName.getText().toString();
                String cityOne = tvCityOne.getText().toString();
                String cityTwo = tvCityTwo.getText().toString();
                String cityThree = tvCityThree.getText().toString();
                String school = etSchool.getText().toString();
                String grade = etGrade.getText().toString();
                String className = etClassName.getText().toString();
                String studentId = etStudentId.getText().toString();
               // RegisterInfo mRegisterInfo = new RegisterInfo();
                if(!TextUtils.isEmpty(realName)){
                    leaguer.setRealname(realName);
                }else{
                    ToastUtils.showToast("姓名不能为空");
                    return;
                }
                if(!TextUtils.isEmpty(nickName)){
                    leaguer.setNickname(nickName);
                }else{
                    ToastUtils.showToast("昵称不能为空");
                    return;
                }
                  if(!TextUtils.isEmpty(cityOne)){
                      leaguer.setAddress1_id(address_id1);
                  }else{
                      ToastUtils.showToast("常驻城市1不能为空");
                      return;
                  }

                  if(!TextUtils.isEmpty(school)){
                      leaguer.setSchool(school);
                  }else{
                      ToastUtils.showToast("学校不能为空");
                      return;
                  }
                  if(!TextUtils.isEmpty(grade)){
                      leaguer.setGrade(grade);
                  }else{
                      ToastUtils.showToast("年级不能为空");
                      return;
                  }
                if(!TextUtils.isEmpty(className)){
                    leaguer.setClassname(className);
                }else{
                    ToastUtils.showToast("班级不能为空");
                    return;
                }
                leaguer.setSex(sexId);
                leaguer.setStudentid(studentId);
                leaguer.setAddress2_id(address_id2);
                leaguer.setAddress3_id(address_id3);
                Intent intent = new Intent(RegisterOneActivity.this,RegisterTwoActivity.class);
                intent.putExtra("leaguer",leaguer);
                startActivity(intent);
                break;
            case  R.id.llUploadPhoto:
                startActivityForResult(new Intent(RegisterOneActivity.this,
                        BottomDialogPicActivity.class), 1);
                break;
            case R.id.rlOneAdress:
                type = 0;
                getCityInfo();
                break;
            case R.id.rlTwoAdress:
                type = 1;
                getCityInfo();
                break;
            case R.id.rlThreeAdress:
                type = 2;
                getCityInfo();
                break;
        }

    }

    public void getCityInfo(){
        List<Region> regions =  RegionManager.getInstance().queryRegion();
        if(regions!=null&regions.size()>0){
            startActivityForResult(new Intent(RegisterOneActivity.this,QueryCityActivity.class),2);
        }else{
            getCityList();
        }
    }

    private void getCityList() {
        loadingView.showLoading();
        ApiService mApiService = ApiRetrofit.getInstance().getApiService();
        mApiService.getCityList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseResult<CityListData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingView.hideLoading();
                        ToastUtils.showToast("网络异常,请检查网络");
                    }
                    @Override
                    public void onNext(ResponseResult<CityListData> responseResult) {
                        loadingView.hideLoading();
                        if(responseResult.getStatus()>0){
                            List<Region> regions = responseResult.getRespData().getCityList();
                            RegionManager.getInstance().insertResion(regions);
                            startActivityForResult(new Intent(RegisterOneActivity.this,QueryCityActivity.class),2);
                        }else{
                            ToastUtils.showToast(responseResult.getErr());
                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        type = -1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null) return;
        switch (requestCode) {
            case 1:
                if(resultCode==1){
                    if (data != null) {
                        data.setClass(RegisterOneActivity.this,
                                ClipHeaderActivity.class);
                        Bundle extras = data.getExtras();
                        data.putExtras(extras);
                        data.putExtra("type","1");
                        data.putExtra("side_length", 200);//裁剪图片宽高
                        startActivityForResult(data, 3);
                    }
                } else if (resultCode == 2) {
                        if (data != null) {
                            data.setClass(RegisterOneActivity.this,
                                    ClipHeaderActivity.class);
                            data.putExtra("type","2");
                            data.putExtra("side_length", 200);//裁剪图片宽高
                            startActivityForResult(data, 3);
                }
            }
                break;
            case 2:
                String city = data.getStringExtra("city");
                List<Region> regions =  RegionManager.getInstance().queryRegion();
                int index =  data.getIntExtra("index",0);
                if(type==0){
                    address_id1 = regions.get(index).getId();
                    tvCityOne.setText(city);
                }else if(type==1){
                    address_id2 = regions.get(index).getId();
                    tvCityTwo.setText(city);
                }else if(type==2){
                    address_id3 = regions.get(index).getId();
                    tvCityThree.setText(city);
                }
               break;
            case 3:
                setPicToView(data);
                uploadImageToServer(data);
                break;

            default:
                break;

        }
    }
    private void setPicToView(Intent picdata) {
        Uri uri = picdata.getData();
        if (uri == null) {
            return;
        }
        imagePhone.setImageURI(uri);

    }
    /**
     * 上传头像
     * @param
     * @param
     */
    private void uploadImageToServer(Intent data) {
            File fileCrop = new File(data.getData().getPath());
            //创建RequestBody对象
            loadingView.showLoading();
            ApiService mApiService = ApiRetrofit.getInstance().getApiService();
             /*RequestBody requestBody = RequestBody.create(MediaType.parse("image*//*"), fileCrop);
            RequestBody uploadType = RequestBody.create(MediaType.parse("text/plain"), "1");
            RequestBody fileName  = RequestBody.create(MediaType.parse("text/plain"), fileCrop.getName());
            mApiService.uploadImageToServer(requestBody,uploadType,fileName)*/
           Map<String,RequestBody> bodyMap = new HashMap<>();
           bodyMap.put("file\";filename=\""+fileCrop.getName(),RequestBody.create(MediaType.parse("image/*"),fileCrop));
           RequestBody uploadType = RequestBody.create(MediaType.parse("text/plain"), "1");
           RequestBody category = RequestBody.create(MediaType.parse("text/plain"), "leaguer_file/");
           RequestBody fileItem = RequestBody.create(MediaType.parse("text/plain"), "1");
           RequestBody fileName  = RequestBody.create(MediaType.parse("text/plain"), fileCrop.getName());
           RequestBody sortIndex  = RequestBody.create(MediaType.parse("text/plain"), "1");
           mApiService.uploadImageToServer(bodyMap,uploadType,category,fileItem,sortIndex,fileName)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LinkFile<LinkFile.FileBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingView.hideLoading();
                        ToastUtils.showToast("网络异常,请检查网络");
                    }
                    @Override
                    public void onNext(LinkFile<LinkFile.FileBean> responseResult) {
                        loadingView.hideLoading();
                        leaguer.setPortraitIds(responseResult.getFile().getFileId());
                        ToastUtils.showToast("图片上传成功");
                    }
                });
    }


    @Override
    public void showLoading() {
        //progresser.showProgress();
        ToastUtils.showToast("showLoading==");
    }

    @Override
    public void hideLoading() {
        //progresser.showContent();
        ToastUtils.showToast("hideLoading==");
    }

    @Override
    public void showNetError() {
        //progresser.showError();
        ToastUtils.showToast("showNetError==");
    }

    @Override
    public void loadData(RegionData mRegionData) {
        ToastUtils.showToast("mRegionData=="+mRegionData.toString());
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        if(rbMale.isChecked()){
            sexId = 1;
            imagePhone.setImageResource(R.mipmap.male);
        }else if(rbFemale.isChecked()){
            sexId = 2;
            imagePhone.setImageResource(R.mipmap.female);
        }
    }
}
