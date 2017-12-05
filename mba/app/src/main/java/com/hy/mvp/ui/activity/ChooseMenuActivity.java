package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.ui.adapter.ImageBucketAdapter;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.view.AlbumHelper;
import com.hy.mvp.ui.view.ImageBucket;

import java.io.Serializable;
import java.util.List;

public class ChooseMenuActivity extends BaseActivity {
    List<ImageBucket> dataList;
    GridView gridView;
    ImageBucketAdapter adapter;// 自定义的适配器
    AlbumHelper helper;
    public static final String EXTRA_IMAGE_LIST = "imagelist";
    public static Bitmap bimap;

    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_choose_menu);
        setTitleText("相册");
        showTitleBack();
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());
        dataList = helper.getImagesBucketList(false);
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.mipmap.icon_addpic_unfocused);
    }

    @Override
    public void initData() {
        super.initData();
        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new ImageBucketAdapter(ChooseMenuActivity.this, dataList);
        gridView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /**
                 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
                 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
                 */
                /**
                 * 通知适配器，绑定的数据发生了改变，应当刷新视图
                 */
                Intent intent = new Intent(ChooseMenuActivity.this,
                        ImageGridActivity.class);
                intent.putExtra(ChooseMenuActivity.EXTRA_IMAGE_LIST,
                        (Serializable) dataList.get(position).imageList);
                startActivityForResult(intent,1);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}