package com.hy.mvp.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hy.mvp.R;
import com.hy.mvp.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;


public class PopupMenu {

    private OnPopupItemClickListener onPopupItemClickListener;

    private PopupWindow popupMenu;
    //窗口在x轴偏移量
    private int xOff = 0;
    //窗口在y轴的偏移量
    private int yOff = 0;

    private PopupAdapter popupAdapter;

    private View rootView;

    private Context context;

    private boolean isAutoRightTopPop = true;

    private int width = 160;//默认宽度160dp, 可set

    private float textSize;

    public PopupMenu(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
        textSize = context.getResources().getDimension(R.dimen.txt_sizeH);
        initPopupMenu(context);
    }

    private void initPopupMenu(Context context) {
        popupMenu = new PopupWindow(context);
        popupMenu.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupMenu.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupMenu.setTouchable(true);
        popupMenu.setFocusable(true);
        popupMenu.setBackgroundDrawable(new BitmapDrawable());
        // 设置允许在外点击消失
        popupMenu.setOutsideTouchable(true);
        View popupMenuView = LayoutInflater.from(context).inflate(R.layout.popup_menu, null);
        popupMenu.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupMenu.dismiss();
                    return true;
                }
                return false;
            }
        });

        ListView lvPopupMenu = (ListView) popupMenuView.findViewById(R.id.lv_popup_menu);

        popupAdapter = new PopupAdapter(context);
        popupAdapter.setTextSize(textSize);
        lvPopupMenu.setAdapter(popupAdapter);
        lvPopupMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onPopupItemClickListener != null) {
                    onPopupItemClickListener.onItemClicked(position, popupAdapter.getItem(position));
                }
            }
        });
        //设置显示的视图
        popupMenu.setContentView(popupMenuView);
    }

    public void setDataChanged(List<PopupItem> popupItems) {
        popupAdapter.setDataChanged(popupItems);
    }

    public void setOnPopupItemClickListener(OnPopupItemClickListener onPopupItemClickListener) {
        this.onPopupItemClickListener = onPopupItemClickListener;
    }

    /**
     * @param xOff x轴（左右）偏移
     * @param yOff y轴（上下）偏移
     */
    public void setOff(int xOff, int yOff) {
        isAutoRightTopPop = false;
        this.xOff = xOff;
        this.yOff = yOff;
    }

    public void setPopupMenuWidth(int width){
        this.width = width;

        popupMenu.setWidth(CommonUtil.dip2Px(xOff));
    }

    public void show() {
        popupMenu.setWidth(CommonUtil.dip2Px(width));
        if (isAutoRightTopPop) {
            this.xOff = 0 - width - 5;
            this.yOff = 0;
            //设置窗口显示位置, 后面两个0 是表示偏移量，可以自由设置
            //去除右上角按钮作为源控件时的横向偏移误差 @Song
            popupMenu.showAsDropDown(rootView, CommonUtil.dip2Px(xOff) + rootView.getWidth(), CommonUtil.dip2Px(yOff));
        } else {
            //设置窗口显示位置, 后面两个0 是表示偏移量，可以自由设置
            popupMenu.showAsDropDown(rootView, CommonUtil.dip2Px(xOff), CommonUtil.dip2Px(yOff));
        }
        //更新窗口状态
        popupMenu.update();
    }


    public void hide() {
        popupMenu.dismiss();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        popupAdapter.setTextSize(this.textSize);
        popupAdapter.notifyDataSetChanged();
    }

    public float getTextSize() {
        return textSize;
    }

    private class PopupAdapter extends BaseAdapter {

        private Context context;
        private List<PopupItem> mpopupItems = new ArrayList<>();

        private float textSize;

        public PopupAdapter(Context context) {
            this.context = context;
            textSize = context.getResources().getDimension(R.dimen.txt_sizeH);
        }

        public void setTextSize(float textSize) {
            this.textSize = textSize;
        }

        @Override
        public int getCount() {
            return mpopupItems.size();
        }

        @Override
        public PopupItem getItem(int position) {
            return mpopupItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tvPopupItem;
            ImageView ivPopupItem;
            View vDivider;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_popup_menu, parent, false);
            }
            vDivider = convertView.findViewById(R.id.vDivider);
            tvPopupItem = (TextView) convertView.findViewById(R.id.tvPopupItem);
            ivPopupItem = (ImageView) convertView.findViewById(R.id.ivPopupItem);
            ivPopupItem.setImageResource(mpopupItems.get(position).getIcon());
            tvPopupItem.setText(mpopupItems.get(position).getContent());
            tvPopupItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            if (position == mpopupItems.size() - 1) {
                vDivider.setVisibility(View.GONE);
            } else {
                vDivider.setVisibility(View.VISIBLE);
            }

            return convertView;
        }

        public void setDataChanged(List<PopupItem> popupItems) {
            if (popupItems == null) {
                this.mpopupItems.clear();
            } else {
                this.mpopupItems = popupItems;
            }
            this.notifyDataSetChanged();
        }
    }

    public interface OnPopupItemClickListener {

        void onItemClicked(int position, PopupItem item);
    }

}
