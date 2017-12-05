package com.hy.mvp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 作者：hanyin on 2017/8/3 13:24
 * 邮箱：hanyinit@163.com
 */

public class NetWorkUtils {
    public static boolean isNetworkAvailable(Context context) {
        if(context !=null){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if(info !=null){
                return info.isAvailable();
            }
        }
        return false;
    }
}
