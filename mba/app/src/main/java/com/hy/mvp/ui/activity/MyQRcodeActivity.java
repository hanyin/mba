package com.hy.mvp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.greendao.manager.AppConfigDef;
import com.hy.mvp.greendao.manager.AppConfigHelper;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.utils.GlideUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  2017/10/23 14:49
 *  hanyin
 *  hanyinit@163.com
 */
public class MyQRcodeActivity extends BaseActivity {

    @BindView(R.id.imageQRcode)
    ImageView imageQRcode;
    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_my_qrcode);
        showTitleBack();
        setTitleText("我的二维码");
        ButterKnife.bind(this);
    }
    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();
            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }
    @Override
    public void initData() {
        super.initData();
        String qr_url = AppConfigHelper.getConfig(AppConfigDef.qr_url);
        /* new Thread(new Runnable() {
            @Override
            public void run() {
                imageQRcode.setImageBitmap(getBitmap(qr_url));
            }
        }).start();*/
         GlideUtils.load(this,qr_url,imageQRcode);
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
