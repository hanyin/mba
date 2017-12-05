package com.hy.mvp.ui.dialog;

import android.content.Context;

import com.hy.mvp.view.ArrayItem;

import java.util.List;


/**
 * Created by 韩银 on 2017/9/5.
 */

public class ChooseItemDialogHelper {

    /**
     * 仿IOS弹窗选择有title
     * @param context
     * @param arrayItems
     * @param title
     * @param listener
     * @return
     */
    public ActionSheetDialog createArrayItemSheetDialog(Context context, List<ArrayItem> arrayItems, String title, ActionSheetDialog.ActionSheetListener listener) {
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(context, arrayItems, listener)
                .builder()
                .setTitle(title)
                .setCancelable(true)
                .setCanceledOnTouchOutside(true);
        return actionSheetDialog;
    }
    /**
     * 仿IOS弹窗选择没有title
     * @param context
     * @param arrayItems
     * @param listener
     * @return
     */
    public ActionSheetDialog createArrayItemSheetDialog(Context context, List<ArrayItem> arrayItems, ActionSheetDialog.ActionSheetListener listener) {
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(context, arrayItems, listener)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true);
        return actionSheetDialog;
    }
}
