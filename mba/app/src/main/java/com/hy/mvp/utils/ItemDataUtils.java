package com.hy.mvp.utils;

import com.hy.mvp.view.ArrayItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 韩银 on 2017/9/19 15:22
 * hanyinit@163.com
 */

public class ItemDataUtils {

    public static List<ArrayItem> isCancel(){
        List<ArrayItem>  arrayItems = new ArrayList<>();
        arrayItems.add(new ArrayItem(0,"删除"));
        return arrayItems;
    }
}
