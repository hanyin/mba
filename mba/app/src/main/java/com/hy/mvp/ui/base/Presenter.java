package com.hy.mvp.ui.base;

import android.content.Intent;
import android.view.View;

/**
 * 作者：hanyin on 2017/8/9 13:24
 * 邮箱：hanyinit@163.com
 */

public interface Presenter {
        void onCreate();

        void onStart();//暂时没用到

        void onStop();

        void pause();//暂时没用到

        void attachView(View view);

        void attachIncomingIntent(Intent intent);//暂时没用到

}
