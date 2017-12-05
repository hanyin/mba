package com.hy.mvp.ui.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hy.mvp.R;
import com.hy.mvp.app.MyApplication;
import com.hy.mvp.utils.Bimp;

/**
 * Created by 韩银 on 2017/10/9 08:56
 * hanyinit@163.com
 */

public class BottomDialogPictureActivity extends Activity implements View.OnClickListener{

    private TextView btn_take_photo, btn_pick_photo, btn_cancel;
    private LinearLayout layout;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_dialog_pic);
        MyApplication.getInstance().addActivity(this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        intent = getIntent();
        btn_take_photo = (TextView) this.findViewById(R.id.btn_take_photo); //拍照
        btn_pick_photo = (TextView) this.findViewById(R.id.btn_pick_photo);  //从相册选择
        btn_cancel = (TextView) this.findViewById(R.id.btn_cancel);   //取消

        layout = (LinearLayout) findViewById(R.id.pop_layout);

        // 添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // 添加按钮监听
        btn_cancel.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);
    }

    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {  //点击外面退出这activity
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   // 选择完, 拍照,或者选择图片后调用的方法
        if (resultCode != RESULT_OK) {
            return;
        }
        //选择完或者拍完照后会在这里处理，然后我们继续使用setResult返回Intent以便可以传递数据和调用
        if(requestCode==1){
            if (data.getExtras() != null){
                intent.putExtras(data.getExtras());   //拍照得到的图片
                setResult(1, intent);
            }
        }else if(requestCode == 3){
            setResult(3,data);
        }
        finish();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_photo:     //拍照
                try {
                    //拍照我们用Action为MediaStore.ACTION_IMAGE_CAPTURE，
                    //有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_pick_photo:    // 选择图片
                try {
                        Bimp.temp.removeAll(Bimp.temp);
                        Intent intent = new Intent(BottomDialogPictureActivity.this,
                                ChooseMenuActivity.class);
                        startActivityForResult(intent,3);
                } catch (Exception e) {
                      e.getStackTrace();
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
            default:
                break;
        }
    }
}