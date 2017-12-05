package com.hy.mvp.ui.view;

import com.hy.mvp.greendao.bean.User;
import com.hy.mvp.ui.bean.RegionData;
import com.hy.mvp.ui.bean.RegionDataList;

/**
 * 作者：hanyin on 2017/8/9 13:39
 * 邮箱：hanyinit@163.com
 */

public interface RegionView extends BaseView{
    /**
     * 显示数据
     * @param
     */
    void loadData(RegionData mRegionData);
}
