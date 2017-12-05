package com.hy.mvp.greendao.manager;

import com.hy.mvp.greendao.gen.LeaguerDao;
import com.hy.mvp.ui.bean.Leaguer;

import java.util.List;

/**
 * Created by 韩银 on 2017/9/27 11:17
 * hanyinit@163.com
 */

public class LeaguerManager {
    private final static String TAG = LeaguerManager.class.getSimpleName();
    private static LeaguerManager leaguerManager;
    public static LeaguerDao leaguerDao;

    public static LeaguerManager getInstance() {
        leaguerDao = GreenDaoManager.getInstance().getSession().getLeaguerDao();
        if (leaguerManager == null) {
            leaguerManager = new LeaguerManager();
        }
        return leaguerManager;
    }
    public void insertLeaguer(List<Leaguer> leaguers){
        leaguerDao.insertInTx(leaguers);
    }

    public List<Leaguer> queryLeaguer(){
        return leaguerDao.queryBuilder().list();
    }
    public List<Leaguer> queryByAddress(String address){
        List<Leaguer> regions =  leaguerDao.queryBuilder().where(LeaguerDao.Properties.Address1_id.like("%"+address+"%")).build().list();
        if (regions != null) {
            return regions;
        } else {
            return null;
        }
    }
}
