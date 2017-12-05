package com.hy.mvp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

/**
 * Created by 韩银 on 2017/10/13 13:53
 * hanyinit@163.com
 */

public class CheckPermission {
    Context mContext;
    View rootView;

    public CheckPermission(Context context, View rootView) {
        this.mContext = context;
        this.rootView = rootView;
    }

    private void check(String permission) {
        if (ActivityCompat.checkSelfPermission(mContext, /*Manifest.permission.READ_PHONE_STATE*/permission)
                != PackageManager.PERMISSION_GRANTED) {
            requesetContanctsPermissions(permission);
        }
    }

    private void requesetContanctsPermissions(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,/*Manifest.permission.READ_PHONE_STATE*/permission)) {
            Snackbar.make(rootView, "此应用必须获取以上权限", Snackbar.LENGTH_INDEFINITE).setAction("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ActivityCompat.requestPermissions(mContext,/*请求权的权限 String[] permissions*/,/*请求码 int requestCode*/);
                }
            }).show();
        } else {
            //无需向用户界面提示 , 直接请求权限
//                    ActivityCompat.requestPermissions(mContext,/*请求权的权限 String[] permissions*/,/*请求码 int requestCode*/);
        }
    }
}
