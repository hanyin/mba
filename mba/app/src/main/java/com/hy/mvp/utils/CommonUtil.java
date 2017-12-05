package com.hy.mvp.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.widget.Toast;

import com.hy.mvp.app.MyApplication;

/**
 * 作者：hanyin on 2017/8/3 13:24
 * 邮箱：hanyinit@163.com
 */
public class CommonUtil {

	/**
	 * Try to return the absolute file path from the given Uri  兼容了file:///开头的 和 content://开头的情况
	 *
	 * @param context
	 * @param uri
	 * @return the file path or null
	 */
	public static String getRealFilePathFromUri( final Context context, final Uri uri ) {
		if ( null == uri ) return null;
		final String scheme = uri.getScheme();
		String data = null;
		if ( scheme == null )
			data = uri.getPath();
		else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
			data = uri.getPath();
		} else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
			Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
			if ( null != cursor ) {
				if ( cursor.moveToFirst() ) {
					int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
					if ( index > -1 ) {
						data = cursor.getString( index );
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	public static Toast mToast;

	public static void showToast(String msg) {
		showToast(msg, Toast.LENGTH_SHORT);
	}

	public static void showToast(String msg, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(getContext(), "", duration);
		}
		mToast.setText(msg);
		mToast.show();
	}

	/**
	 * 用于在线程中执行弹土司操作
	 */
	public static void showToastSafely(final String msg) {
		getMainThreadHandler().post(new Runnable() {

			@Override
			public void run() {
				if (mToast == null) {
					mToast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
				}
				mToast.setText(msg);
				mToast.show();
			}
		});
	}


	/**
	 * 得到上下文
	 *
	 * @return
	 */
	public static Context getContext() {
		return MyApplication.getContext();
	}

	/**
	 * 得到resources对象
	 *
	 * @return
	 */
	public static Resources getResource() {
		return getContext().getResources();
	}

	/**
	 * 得到string.xml中的字符串
	 *
	 * @param resId
	 * @return
	 */
	public static String getString(int resId) {
		return getResource().getString(resId);
	}

	/**
	 * 得到string.xml中的字符串，带点位符
	 *
	 * @return
	 */
	public static String getString(int id, Object... formatArgs) {
		return getResource().getString(id, formatArgs);
	}

	/**
	 * 得到string.xml中和字符串数组
	 *
	 * @param resId
	 * @return
	 */
	public static String[] getStringArr(int resId) {
		return getResource().getStringArray(resId);
	}

	/**
	 * 得到colors.xml中的颜色
	 *
	 * @param colorId
	 * @return
	 */
	public static int getColor(int colorId) {
		return getResource().getColor(colorId);
	}

	/**
	 * 得到应用程序的包名
	 *
	 * @return
	 */
	public static String getPackageName() {
		return getContext().getPackageName();
	}

	/**
	 * 得到主线程Handler
	 *
	 * @return
	 */
	public static Handler getMainThreadHandler() {
		return MyApplication.getMainHandler();
	}

	/**
	 * 得到主线程id
	 *
	 * @return
	 */
	public static long getMainThreadId() {
		return MyApplication.getMainThreadId();
	}

	/**
	 * 安全的执行一个任务
	 *
	 * @param task
	 */
	public static void postTaskSafely(Runnable task) {
		int curThreadId = android.os.Process.myTid();
		// 如果当前线程是主线程
		if (curThreadId == getMainThreadId()) {
			task.run();
		} else {
			// 如果当前线程不是主线程
			getMainThreadHandler().post(task);
		}
	}

	/**
	 * 延迟执行任务
	 *
	 * @param task
	 * @param delayMillis
	 */
	public static void postTaskDelay(Runnable task, int delayMillis) {
		getMainThreadHandler().postDelayed(task, delayMillis);
	}

	/**
	 * 移除任务
	 */
	public static void removeTask(Runnable task) {
		getMainThreadHandler().removeCallbacks(task);
	}

	/**
	 * dip-->px
	 */
	public static int dip2Px(int dip) {
		// px/dip = density;
		// density = dpi/160
		// 320*480 density = 1 1px = 1dp
		// 1280*720 density = 2 2px = 1dp

		float density = getResource().getDisplayMetrics().density;
		int px = (int) (dip * density + 0.5f);
		return px;
	}

	/**
	 * px-->dip
	 */
	public static int px2dip(int px) {

		float density = getResource().getDisplayMetrics().density;
		int dip = (int) (px / density + 0.5f);
		return dip;
	}

	/**
	 * sp-->px
	 */
	public static int sp2px(int sp) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResource().getDisplayMetrics()) + 0.5f);
	}


}
