package com.hy.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.ui.bean.YearSalary;
import com.hy.mvp.view.wheel.adapters.ArrayWheelAdapter;
import com.hy.mvp.view.wheel.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：hanyin on 2017/8/14 16:27
 * 邮箱：hanyinit@163.com
 */

public class BottomYearSalaryActivity extends Activity implements View.OnClickListener {
    /**
     * 所有年薪
     */
    //protected String[] mYearSalaryDatas = {"30000以下","30000-50000","50000-80000","80000以上"};
    protected String[] mYearSalaryDatas ;


    private WheelView yearSalary;
    private Button mBtnConfirm;
    ArrayList<Parcelable> ysalaryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_year_salary);
        MyApplication.getInstance().addActivity(this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mYearSalaryDatas = getIntent().getStringArrayExtra("ysalaryList");
        setUpViews();
        setUpListener();
        setUpData();
    }
    private void setUpViews() {
        yearSalary = (WheelView) findViewById(R.id.yearSalary);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
    }

    private void setUpListener() {
        // 添加onclick事件
        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpData() {
        yearSalary.setViewAdapter(new ArrayWheelAdapter<String>(BottomYearSalaryActivity.this, mYearSalaryDatas));
        // 设置可见条目数量
        yearSalary.setVisibleItems(7);

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
        intent.putExtra("salary",mYearSalaryDatas[yearSalary.getCurrentItem()]);
        setResult(2,intent);
        finish();
    }
}
