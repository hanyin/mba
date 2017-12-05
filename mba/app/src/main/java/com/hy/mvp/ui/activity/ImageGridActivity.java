package com.hy.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.ui.adapter.ImageGridAdapter;
import com.hy.mvp.ui.base.BaseActivity;
import com.hy.mvp.ui.base.BasePresenter;
import com.hy.mvp.ui.view.AlbumHelper;
import com.hy.mvp.ui.view.ImageItem;
import com.hy.mvp.utils.Bimp;
import com.hy.mvp.utils.CommonUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
/**
 *  2017/10/11 13:06
 *  hanyin
 *  hanyinit@163.com
 */
public class ImageGridActivity extends BaseActivity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";

    List<ImageItem> dataList;
    GridView gridView;
    ImageGridAdapter adapter;
    AlbumHelper helper;
    Button bt;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    CommonUtil.showToast("最多选择9张图片");
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void initView() {
        super.initView();
        MyApplication.getInstance().addActivity(this);
        setMainView(R.layout.activity_image_grid);
        setTitleText("图片选择");
        showTitleBack();
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());
        dataList = (List<ImageItem>) getIntent().getSerializableExtra(
                EXTRA_IMAGE_LIST);
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
                mHandler);
        gridView.setAdapter(adapter);
        adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
            public void onListen(int count) {
                bt.setText("完成" + "(" + count + ")");
            }
        });


    }

    @Override
    public void initListener() {
        super.initListener();
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                Collection<String> c = adapter.map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext();) {
                    list.add(it.next());
                }

                /*if (Bimp.act_bool) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    Bimp.act_bool = false;
                }*/
                if(list.size()==0){
                   CommonUtil.showToast("请选图片");
                }else{
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    for (int i = 0; i < list.size(); i++) {
                        if (Bimp.drr.size() < 9) {
                            Bimp.temp.add(list.get(i));
                        }
                    }
                    finish();
                }
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                adapter.notifyDataSetChanged();
            }

        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

}
