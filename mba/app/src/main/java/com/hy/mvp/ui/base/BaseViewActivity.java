package com.hy.mvp.ui.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hy.mvp.R;
import com.hy.mvp.interfaces.OnProgressListener;
import com.hy.mvp.view.ErrorView;
import com.hy.mvp.view.ProgressLayout;

/**
 * 作者：hanyin on 2017/8/4 11:41
 * 邮箱：hanyinit@163.com
 */

public class BaseViewActivity extends AppCompatActivity implements View.OnClickListener, ErrorView.OnRetryListener, OnProgressListener {

    private Toolbar toolbar;
    private TextView tvToolbarTitle;
    private TextView tvToolbarRight;
    private ImageView ivToolbarRight;
    private RelativeLayout rlMain;
    protected ProgressLayout progresser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base_view);
        initBaseView();

    }

    public View getToolBar(){
        return toolbar;
    }
    protected void doDefaultOnCreateAction() {

    }

    protected void setNavigationIcon(int id){
        toolbar.setNavigationIcon(id);
    }
    protected void setNavigationIcon(Drawable drawable){
        toolbar.setNavigationIcon(drawable);
    }
    protected void initBaseView() {
        initToolbar();
        rlMain = (RelativeLayout) findViewById(R.id.rlMain);
        progresser = (ProgressLayout) findViewById(R.id.progress);
        progresser.setRetryListener(this);
        progresser.setOnProgressLintener(this);
    }

    protected void setMainView(int layoutId) {
        View mainView = getLayoutInflater().inflate(layoutId, null);
        setMainView(mainView);
    }

    protected void setMainView(View view) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        rlMain.addView(view, params);
        progresser.showContent();
    }

    /**
     * 初始化状态栏
     */
    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolbarTitle = (TextView) toolbar.findViewById(R.id.tvToolbarTitle);
        tvToolbarRight = (TextView) toolbar.findViewById(R.id.tvToolbarRight);
        ivToolbarRight = (ImageView) toolbar.findViewById(R.id.ivToolbarRight);
        setSupportActionBar(toolbar);
    }

    /**
     * 显示标题栏左侧返回按钮
     */
    protected void showTitleBack() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showToolbar();
    }

    /**
     * 设置标题栏文字
     */
    protected void setTitleText(int title) {
        tvToolbarTitle.setText(title);
        ivToolbarRight.setVisibility(View.GONE);
        showToolbar();
    }

    /**
     * 设置标题栏右侧图片
     */
    protected void setTitleRightImage(int resId) {
        ivToolbarRight.setImageResource(resId);
        ivToolbarRight.setOnClickListener(this);
        tvToolbarRight.setVisibility(View.GONE);
        showToolbar();
    }


    /**
     * 设置标题栏右侧文字
     */
    protected void setTitleRightText(int title) {
        tvToolbarRight.setText(title);
        tvToolbarRight.setOnClickListener(this);
        ivToolbarRight.setVisibility(View.GONE);
        showToolbar();
    }

    /**
     * 设置标题栏文字
     */
    protected void setTitleText(String title) {
        tvToolbarTitle.setText(title);
        showToolbar();
    }

    protected void showTitleRightText() {
        if (tvToolbarRight.getVisibility() != View.VISIBLE) {
            tvToolbarRight.setVisibility(View.VISIBLE);
        }
        if (ivToolbarRight.getVisibility() != View.VISIBLE) {
            ivToolbarRight.setVisibility(View.GONE);
        }
    }

    protected void hideTitleRightText() {
        if (tvToolbarRight.getVisibility() == View.VISIBLE) {
            tvToolbarRight.setVisibility(View.GONE);
        }
    }

    protected void hideTitleRightImage() {
        if (ivToolbarRight.getVisibility() == View.VISIBLE) {
            ivToolbarRight.setVisibility(View.GONE);
        }
    }

    protected void hideTitleRight() {
        hideTitleRightImage();
        hideTitleRightText();
    }

    protected void setTitleRightVisible(boolean flag) {
        tvToolbarRight.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置标题栏右侧文字并添加监听
     */
    protected void setTitleRight(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            tvToolbarRight.setText(title);
            tvToolbarRight.setOnClickListener(this);
        }
        showToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onTitleBackClikced();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tvToolbarRight || i == R.id.ivToolbarRight) {
            onTitleRightClicked();
        }
    }

    /**
     * 点击返回按钮
     */
    protected void onTitleBackClikced() {
        this.finish();
    }

    /**
     * 标题栏右侧文字被点击
     */
    protected void onTitleRightClicked() {
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void onStartProgressing() {
        hideTitleRightText();
    }

    @Override
    public void onStopProgressing() {
        showTitleRightText();
    }

    private void showToolbar() {
        if (toolbar.getVisibility() != View.VISIBLE) {
            toolbar.setVisibility(View.VISIBLE);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    protected void hideToolbar(){
        if (toolbar.getVisibility() == View.VISIBLE) {
            toolbar.setVisibility(View.GONE);
        }
    }
}
