package com.hy.mvp.greendao.bean;

import com.google.gson.Gson;
import com.hy.mvp.ui.bean.LinkFile;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by 韩银 on 2017/10/31 15:15
 * hanyinit@163.com
 */

public class LinkFileConverter implements PropertyConverter<LinkFile, String> {

    @Override
    public LinkFile convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        return new Gson().fromJson(databaseValue, LinkFile.class);
    }

    @Override
    public String convertToDatabaseValue(LinkFile entityProperty) {
        if (entityProperty == null) {
            return null;
        }
        return new Gson().toJson(entityProperty);
    }
}


