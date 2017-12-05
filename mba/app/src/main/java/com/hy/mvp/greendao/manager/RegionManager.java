package com.hy.mvp.greendao.manager;


import android.database.Cursor;

import com.hy.mvp.greendao.bean.Region;
import com.hy.mvp.greendao.gen.RegionDao;

import java.util.List;

/**
 * 作者：hanyin on 2017/8/10 14:21
 * 邮箱：hanyinit@163.com
 */

public class RegionManager {
    private final static String TAG = RegionManager.class.getSimpleName();
    private static RegionManager mRegionManager;
    public static RegionDao mRegionDao;

    public static RegionManager getInstance() {
        mRegionDao = GreenDaoManager.getInstance().getSession().getRegionDao();
        if (mRegionManager == null) {
            mRegionManager = new RegionManager();
        }
        return mRegionManager;
    }
    public void insertResion(List<Region> regions){
        mRegionDao.insertInTx(regions);
    }

    public List<Region> queryRegion(){
        return mRegionDao.queryBuilder().list();
    }


    public List<Region> queryByPinyin(String city){
        List<Region> regions =  mRegionDao.queryBuilder().where(RegionDao.Properties.Pinyin.like("%"+city+"%")).build().list();
        if (regions != null) {
            return regions;
        } else {
            return null;
        }
    }
    public Region queryByAddress(String address){
        Region regions =  mRegionDao.queryBuilder().where(RegionDao.Properties.RegionName.eq("address")).unique();
        if (regions != null) {
            return regions;
        } else {
            return null;
        }
    }
}
