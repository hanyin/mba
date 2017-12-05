package com.hy.mvp.view.wheel.adapters;

import android.content.Context;

import java.util.List;

/**
 * Created by 韩银 on 2017/9/18.
 */

public class ListWheelAdapter<T> extends AbstractWheelTextAdapter {
    List<T> items;

    public ListWheelAdapter(Context context, List<T> items) {
        super(context);
        this.items = items;
    }


    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    protected CharSequence getItemText(int index) {
        if(index>=0 && index<items.size()){
            T item  = items.get(index);
            if(item instanceof Character){
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }
}
