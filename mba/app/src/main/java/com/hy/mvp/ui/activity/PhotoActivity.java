package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.dialog.ActionSheetDialog;
import com.hy.mvp.ui.dialog.ChooseItemDialogHelper;
import com.hy.mvp.ui.dialog.OnItemSetListener;
import com.hy.mvp.utils.Bimp;
import com.hy.mvp.utils.ItemDataUtils;
import com.hy.mvp.view.ArrayItem;

import java.io.File;
import java.util.ArrayList;

/**
 *  2017/9/29 14:02
 *  hanyin
 *  hanyinit@163.com
 */
public class PhotoActivity extends BaseActivity {

    private ArrayList<View> listViews = null;
    private ViewPager pager;
    private MyPageAdapter adapter;
    int index;
    private ActionSheetDialog actionSheetDialog = null;
    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_photo);
        showTitleBack();
        setTitleRightImage(R.drawable.bt_shanchu);
        pager = (ViewPager) findViewById(R.id.viewpager);

    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        showPayTypeItems("", new OnItemSetListener() {
            @Override
            public void onItemSet(ArrayItem item) {
                String path  = Bimp.drr.get(index);
                Bimp.bmp.remove(index);
                Bimp.drr.remove(index);
                Bimp.max--;
                deleteImage(path);
                Intent intent  = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void deleteImage(String path) {
        if(!TextUtils.isEmpty(path)){
            File file = new File(path);
            if(file.isFile()){
                file.delete();
            }
        }
    }

    @Override
    public void initData() {
        super.initData();
        pager.setOnPageChangeListener(pageChangeListener);
        for (int i = 0; i < Bimp.bmp.size(); i++) {
            initListViews(Bimp.bmp.get(i));//
        }
        adapter = new MyPageAdapter(listViews);// 构造adapter
        pager.setAdapter(adapter);// 设置适配器
        Intent intent = getIntent();
        index = intent.getIntExtra("ID", 0);
        pager.setCurrentItem(index);
    }


    @Override
    public void initListener() {
        super.initListener();
    }
    public void showPayTypeItems(final String currentValue, final OnItemSetListener listener) {
        if (actionSheetDialog == null || !actionSheetDialog.isShowing()) {
            actionSheetDialog = new ChooseItemDialogHelper()
                    .createArrayItemSheetDialog(this, ItemDataUtils.isCancel()
                            , "要删除这张照片吗?", new ActionSheetDialog.ActionSheetListener() {
                                @Override
                                public void onCancel() {
                                }

                                @Override
                                public void onClick(ArrayItem item) {
                                    listener.onItemSet(item);
                                }
                            });
            actionSheetDialog.show();
        }
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initListViews(Bitmap bm) {
        if (listViews == null){
            listViews = new ArrayList<View>();
        }
        ImageView img = new ImageView(this);// 构造textView对象
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(bm);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        listViews.add(img);//添加view
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int arg0) {// 页面选择响应函数
            index = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。

        }

        public void onPageScrollStateChanged(int arg0) {// 滑动状态改变

        }
    };

    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;// content

        private int size;// 页数

        public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
            // 初始化viewpager的时候给的一个页面
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {// 返回数量
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
            ((ViewPager) arg0).removeView(listViews.get(arg1));
        }

        public void finishUpdate(View arg0) {
        }

        public Object instantiateItem(View arg0, int arg1) {// 返回view对象
            try {
                ((ViewPager) arg0).addView(listViews.get(arg1), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }
}
