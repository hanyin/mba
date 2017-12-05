package com.hy.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.view.wheel.adapters.ListWheelAdapter;
import com.hy.mvp.view.wheel.widget.WheelView;

import java.util.ArrayList;


/**
 *  2017/9/20 11:34
 *  韩银
 *  hanyinit@163.com
 */
public class BottomCommonViewActivity extends Activity implements View.OnClickListener {
    private WheelView wheelService;
    private Button mBtnConfirm;
    ArrayList<String> commonLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_bottom_common_view);
        MyApplication.getInstance().addActivity(this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        commonLists  =  getIntent().getStringArrayListExtra("commonLists");
        setUpViews();
        setUpListener();
        setUpData();
    }
    private void setUpViews() {
        wheelService = (WheelView) findViewById(R.id.wheelService);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
    }

    private void setUpListener() {
        // 添加onclick事件
        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpData() {
        wheelService.setViewAdapter(new ListWheelAdapter<>(BottomCommonViewActivity.this, commonLists));
        // 设置可见条目数量
        wheelService.setVisibleItems(7);

    }

    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {  //点击外面退出这activity
        finish();
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                showSelectedResult();
                break;
            default:
                break;
        }
    }

    private void showSelectedResult() {
        Intent intent  = new Intent();
        intent.putExtra("index",wheelService.getCurrentItem());
        intent.putExtra("commonData",commonLists.get(wheelService.getCurrentItem()));
        setResult(RESULT_OK,intent);
        finish();
    }
}
