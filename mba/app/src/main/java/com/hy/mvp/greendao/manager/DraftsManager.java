package com.hy.mvp.greendao.manager;

import com.hy.mvp.greendao.bean.Drafts;
import com.hy.mvp.greendao.gen.DraftsDao;

import java.util.List;

/**
 * Created by 韩银 on 2017/10/16 14:59
 * hanyinit@163.com
 */

public class DraftsManager {
    private static final String TAG  = DraftsManager.class.getSimpleName();
    public  static  DraftsDao draftsDao;
    private static DraftsManager draftsManager;
    public static DraftsManager getInstance(){
        draftsDao = GreenDaoManager.getInstance().getSession().getDraftsDao();
        if(draftsManager==null){
            draftsManager = new DraftsManager();
        }
        return draftsManager;
    }

    public void insertDrafts(Drafts drafts){
        draftsDao.insert(drafts);
    }
    public List<Drafts> queryDrafts(){
       return  draftsDao.queryBuilder().list();
    }
    public void deleteDrafts(Drafts drafts){
      draftsDao.delete(drafts);
    }
}
