package com.hy.mvp.ui.bean;

import com.hy.mvp.greendao.bean.Region;

import java.util.List;

/**
 * Created by 韩银 on 2017/9/18.
 */

public class CityListData {
    public List<Region> cityList;

    public List<Region> getCityList() {
        return cityList;
    }

    public void setCityList(List<Region> cityList) {
        this.cityList = cityList;
    }
}
