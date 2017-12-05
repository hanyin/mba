package com.hy.mvp.greendao.manager;

import android.text.TextUtils;

import com.hy.mvp.greendao.bean.AppConfig;
import com.hy.mvp.greendao.gen.AppConfigDao;

/**
 * 作者：hanyin on 2017/7/11 11:32
 * 邮箱：hanyinit@163.com
 */

public class AppConfigHelper {

	public static void setConfig(String configName, String value) {
		AppConfigDao appConfigDao = GreenDaoManager.getInstance().getSession().getAppConfigDao();
		AppConfig appConfig = appConfigDao.queryBuilder().where(AppConfigDao.Properties.Config_name.eq(configName)).build().unique();
		AppConfig app = new AppConfig();
		app.setConfig_name(configName);
		app.setConfig_value(value);
		if(appConfig!=null){
			app.setId(appConfig.getId());
			appConfigDao.update(app);
		}else{
			appConfigDao.insert(app);
		}
	}
	public static void setConfig(String configName, boolean value) {
		AppConfigDao appConfigDao = GreenDaoManager.getInstance().getSession().getAppConfigDao();
		AppConfig appConfig = appConfigDao.queryBuilder().where(AppConfigDao.Properties.Config_name.eq(configName)).build().unique();
		AppConfig app = new AppConfig();
		app.setConfig_name(configName);
		app.setConfig_value(String.valueOf(value));
		if(appConfig!=null){
			app.setId(appConfig.getId());
			appConfigDao.update(app);
		}else{
			appConfigDao.insert(app);
		}
	}
	public static String getConfig(String configName, String defaultValue) {
		AppConfigDao appConfigDao = GreenDaoManager.getInstance().getSession().getAppConfigDao();
		AppConfig appConfig = appConfigDao.queryBuilder().where(AppConfigDao.Properties.Config_name.eq(configName)).build().unique();
		String value = "";
		if(appConfig!=null){
			value = appConfig.getConfig_value();
		}
		return TextUtils.isEmpty(value) ? defaultValue : value;
	}
	public static boolean getConfig(String configName, boolean defaultValue) {
		AppConfigDao appConfigDao = GreenDaoManager.getInstance().getSession().getAppConfigDao();
		AppConfig appConfig =  appConfigDao.queryBuilder().where(AppConfigDao.Properties.Config_name.eq(configName)).build().unique();
		String value = "";
		if(appConfig!=null){
			value = appConfig.getConfig_value();
		}
		return TextUtils.isEmpty(value) ? defaultValue : true;
	}
	public static String getConfig(String configName) {
		AppConfigDao appConfigDao = GreenDaoManager.getInstance().getSession().getAppConfigDao();
		AppConfig appConfig =  appConfigDao.queryBuilder().where(AppConfigDao.Properties.Config_name.eq(configName)).build().unique();
		String value = "";
		if(appConfig!=null){
			value = appConfig.getConfig_value();
		}
		return TextUtils.isEmpty(value) ? "" : value;
	}

}
