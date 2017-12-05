package com.hy.mvp.greendao.manager;

import com.hy.mvp.greendao.bean.Region;
import com.hy.mvp.greendao.gen.AddrBookDao;
import com.hy.mvp.greendao.gen.RegionDao;
import com.hy.mvp.ui.bean.AddrBook;

import java.util.List;

/**
 * Created by 韩银 on 2017/10/31 15:23
 * hanyinit@163.com
 */

public class AddrBookManager {
    private static final String TAG = AddrBookManager.class.getSimpleName();
    private  static AddrBookDao addrBookDao;
    private static AddrBookManager addrBookManager;
    public static AddrBookManager getInstance(){
        addrBookDao = GreenDaoManager.getInstance().getSession().getAddrBookDao();
        if(addrBookManager==null){
            addrBookManager = new AddrBookManager();
        }
        return addrBookManager;
    }
    public void insertAddrBook(AddrBook addrBook){
        addrBookDao.insert(addrBook);
    }

    public List<AddrBook> queryAddrBooks(){
      return     addrBookDao.queryBuilder().list();
    }

    public void deleteAddrBook(AddrBook addrBook){

        addrBookDao.delete(addrBook);
    }
    public List<AddrBook> queryByPinyin(String keyword){
        List<AddrBook> regions =  addrBookDao.queryBuilder().whereOr(AddrBookDao.Properties.Pingyin.like("%"+keyword+"%"),AddrBookDao.Properties.Contacts_name.like("%"+keyword+"%")).build().list();
        if (regions != null) {
            return regions;
        } else {
            return null;
        }
    }
    public void clearAddeBooks(){
        addrBookDao.deleteAll();
    }
}
