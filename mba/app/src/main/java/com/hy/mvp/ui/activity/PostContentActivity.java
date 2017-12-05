package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.solver.Cache;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.http.ApiRetrofit;
import com.hy.mvp.http.ApiService;
import com.hy.mvp.ui.adapter.DragRecyclerViewAdapter;
import com.hy.mvp.ui.adapter.MyItemTouchCallback;
import com.hy.mvp.ui.adapter.OnClickListener;
import com.hy.mvp.ui.adapter.OnItemClickListener;
import com.hy.mvp.ui.adapter.OnRecyclerItemClickListener;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.bean.BPTypeData;
import com.hy.mvp.ui.bean.BlogPost;
import com.hy.mvp.ui.bean.LinkFile;
import com.hy.mvp.ui.bean.ResponseResult;
import com.hy.mvp.ui.dialog.ConfirmDialog;
import com.hy.mvp.utils.Bimp;
import com.hy.mvp.utils.CommonUtil;
import com.hy.mvp.utils.FileUtils;
import com.hy.mvp.utils.ToastUtils;
import com.hy.mvp.view.GridSpacingItemDecoration;
import com.hy.mvp.view.LoadingView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
 *  2017/9/28 11:50
 *  hanyin
 *  hanyinit@163.com
 */
public class PostContentActivity extends BaseActivity implements MyItemTouchCallback.OnDragListener,OnItemClickListener,OnClickListener {
    @BindView(R.id.tvContentType)
    TextView tvContentType;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.LoadingView)
    LoadingView loadingView;
    @BindView(R.id.etContent)
    EditText etContent;
    DragRecyclerViewAdapter dragRecyclerViewAdapter;
    private ItemTouchHelper itemTouchHelper;
    List<String> tempImage = new ArrayList<>();
    ArrayList<BPTypeData.BpTypeListBean> bpType;
    int index;
    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_post_content);
        showTitleBack();
        setTitleText("发帖");
        setTitleRight("发送");
        setNavigationIcon(R.mipmap.btn_back);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        dragRecyclerViewAdapter = new DragRecyclerViewAdapter(PostContentActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(PostContentActivity.this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        //RecycleView 增加边距
        int spanCount = 3;
        int spacing = 20;
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        recyclerView.setAdapter(dragRecyclerViewAdapter);
        ConfirmDialog.newInstance("1").setOnClickListener(this);
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        if(Bimp.drr.size()==0){
            sendBlog();
        }else{
            updataImages();
        }
    }
    private void updataImages() {
        loadingView.showLoading();
        File file = new File(this.getCacheDir()+ "/blog/");
        File[] files =  file.listFiles();
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        final int[] success = {0};
        for (int i = 0; i < files.length; i++) {
            Map<String , RequestBody> bodyMap = new HashMap<>();
            File fileBlog = files[i];
            bodyMap.put("file\";filename=\""+fileBlog.getName(),RequestBody.create(MediaType.parse("image/*"),fileBlog));
            RequestBody uploadType = RequestBody.create(MediaType.parse("text/plain"), "1");
            RequestBody category = RequestBody.create(MediaType.parse("text/plain"), "blogpost_file/");
            RequestBody fileItem = RequestBody.create(MediaType.parse("text/plain"), "1");
            RequestBody fileName  = RequestBody.create(MediaType.parse("text/plain"), fileBlog.getName());
            RequestBody sortIndex  = RequestBody.create(MediaType.parse("text/plain"), i+"");
            apiService.uploadImageToServer(bodyMap,uploadType,category,fileItem,sortIndex,fileName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LinkFile<LinkFile.FileBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingView.hideLoading();
                        CommonUtil.showToast("网络异常,请检查网络");
                    }

                    @Override
                    public void onNext(LinkFile<LinkFile.FileBean> fileBeanLinkFile) {
                        success[0]++;
                        tempImage.add(fileBeanLinkFile.getFile().getFileId());
                        if(success[0] ==files.length){
                             sendBlog();
                        }
                    }
                });
        }
    }

    private void sendBlog() {
        StringBuilder selectedFileIds = new StringBuilder();
        for (int i = 0; i < tempImage.size(); i++) {
            selectedFileIds.append(tempImage.get(i));
            if(i!=tempImage.size()-1){
                selectedFileIds.append(",");
            }
        }

        String userId = AppConfigHelper.getConfig(AppConfigDef.userId);
        String strContent = etContent.getText().toString();
        BlogPost blogPost = new BlogPost();
        blogPost.setPublisherId(userId);
        blogPost.setContent(strContent);
        if(bpType!=null){
            blogPost.setTypeId(bpType.get(index).getId());
        }
        blogPost.setSelectedFileIds(selectedFileIds.toString());
        Gson gson = new Gson();
        String blogPostStr = gson.toJson(blogPost);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),blogPostStr);
        Log.i("data",blogPostStr);
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        apiService.sendBlog(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseResult<BlogPost>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingView.hideLoading();
                        CommonUtil.showToast("网络异常,请检查网络");
                    }

                    @Override
                    public void onNext(ResponseResult<BlogPost> blogPostResponseResult) {
                        loadingView.hideLoading();
                        Bimp.bmp.removeAll(Bimp.bmp);
                        Bimp.drr.removeAll(Bimp.drr);
                        Bimp.temp.removeAll(Bimp.temp);
                        Bimp.max = 0;
                        deleteFiles();
                        Intent intent =  new Intent(PostContentActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

    }

    //监听左上角返回键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          if(item.getItemId() == android.R.id.home){
              ConfirmDialog.newInstance("1")
                      .setMargin(40)
                      .setOutCancel(false)
                      .show(getSupportFragmentManager());
              return true;
           }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        ConfirmDialog.newInstance("1")
                .setMargin(40)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    @Override
    public void initListener() {
        super.initListener();
        tvContentType.setOnClickListener(this);
        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(dragRecyclerViewAdapter).setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition()!=Bimp.bmp.size()) {
                    itemTouchHelper.startDrag(vh);
                }
            }
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                if (position == Bimp.bmp.size()) {
                    Intent intent = new Intent(PostContentActivity.this,BottomDialogPictureActivity.class);
                    startActivityForResult(intent,2);
                } else {
                    Intent intent = new Intent(PostContentActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", position);
                    startActivityForResult(intent,3);
                }

            }
        });
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
                         Intent intent = new Intent(PostContentActivity.this,BottomCommonViewActivity.class);
                         intent.putStringArrayListExtra("commonLists",content);
                         startActivityForResult(intent,1);
                     }
                 });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) return;
        if(requestCode == 1){
                String contentType =  data.getStringExtra("commonData");
                index = data.getIntExtra("index",0);
                tvContentType.setText(contentType);
            }else if(requestCode == 2){
                  //拍照
                  if(resultCode == 1){
                      Bundle extras = data.getExtras();
                      Bitmap bitmap = (Bitmap) extras.get("data");
                      /*File file = new File(Environment.getExternalStorageDirectory()
                              + "/myimage/", String.valueOf(System.currentTimeMillis())
                              + ".jpg");
                      String path = file.getPath();*/
                      updata(bitmap);
                  } else if(resultCode == 3){//选择照片
                      updata();
                  }
            }else if(requestCode == 3){
             handler.sendEmptyMessage(1);
        }
    }
    public void saveBitmapFile(Bitmap bmp) {
        File file = new File(this.getCacheDir()+ "/blog/");
        if(!file.exists()){
           file.mkdir();
        }
        File url = new File(this.getCacheDir()+ "/blog/","blog_"+System.currentTimeMillis()+".jpg");
        String path = url.getPath();
        if (Bimp.drr.size() < 9 ) {
            Bimp.drr.add(path);
        }
        Uri  mSaveUri = Uri.fromFile(url);
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    bmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException e) {
                    e.getStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onFinishDrag() {

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    dragRecyclerViewAdapter.notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public void updata() {
       // Bimp.bmp.removeAll(Bimp.bmp);
        for (int i =Bimp.temp.size()-1; i >= 0  ; i--) {
            String path = Bimp.temp.get(i);
            Bitmap bm = null;
            try {
                bm = Bimp.revitionImageSize(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Bimp.drr.add(path);
            Bimp.bmp.add(bm);
            String newStr = path.substring(
                    path.lastIndexOf("/") + 1,
                    path.lastIndexOf("."));
            saveBitmapFile(bm);
            //FileUtils.saveBitmap(bm, "" + newStr);
        }
        Bimp.max = Bimp.bmp.size();
        dragRecyclerViewAdapter.notifyDataSetChanged();
    }
    public void updata(Bitmap bmp) {
        if (Bimp.max == Bimp.drr.size()) {
            dragRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            try {
                /*File file = new File(this.getCacheDir()+ "/blog/");
                if(!file.exists()){
                    file.mkdir();
                }
                if (Bimp.drr.size() < 9 ) {
                    Bimp.drr.add(file.getPath());
                }*/
                Bimp.bmp.add(bmp);
                saveBitmapFile(bmp);
                //FileUtils.saveBitmap(bmp, "" + newStr);
                Bimp.max += 1;
                dragRecyclerViewAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
     }
    @Override
    public void onItemClick(View view, int position) {

    }
    public  void deleteFiles() {
        File fileBlog = new File(this.getCacheDir()+ "/blog/");
        if (fileBlog == null || !fileBlog.exists() || !fileBlog.isDirectory())
            return;
        for (File file : fileBlog.listFiles()) {
            if (file.isFile()){
                file.delete(); // 删除所有文件
            }
        }
    }
    @Override
    public void onBackClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                Bimp.bmp.removeAll(Bimp.bmp);
                Bimp.drr.removeAll(Bimp.drr);
                Bimp.temp.removeAll(Bimp.temp);
                Bimp.max = 0;
                deleteFiles();
                finish();
                break;
            case R.id.ok:
                Bimp.bmp.removeAll(Bimp.bmp);
                Bimp.drr.removeAll(Bimp.drr);
                Bimp.temp.removeAll(Bimp.temp);
                Bimp.max = 0;
                finish();
                break;
        }
    }
}
