package com.hy.mvp.greendao.manager;

import com.hy.mvp.greendao.gen.InvitationDao;
import com.hy.mvp.ui.bean.Invitation;

import java.util.List;

/**
 * Created by 韩银 on 2017/11/2 16:15
 * hanyinit@163.com
 */

public class InvitationManager {
    private static final String tag  = InvitationManager.class.getSimpleName();
    private static InvitationDao invitationDao;
    public static InvitationManager invitationManager;
    public static InvitationManager getInstance(){
        invitationDao = GreenDaoManager.getInstance().getSession().getInvitationDao();
        if(invitationManager ==null ){
            invitationManager = new InvitationManager();
        }
        return  invitationManager;
    }

    public void insertInvitation(Invitation invitation){
        invitationDao.insert(invitation);
    }
    public List<Invitation> queryInvitations(){
        return invitationDao.queryBuilder().list();
    }
    public void deleteInvitation(Invitation invitation){
        invitationDao.delete(invitation);
    }
}
