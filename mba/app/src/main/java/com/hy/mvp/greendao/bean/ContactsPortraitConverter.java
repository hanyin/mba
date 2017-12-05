package com.hy.mvp.greendao.bean;

import com.google.gson.Gson;
import com.hy.mvp.ui.bean.ContactsPortrait;
import com.hy.mvp.ui.bean.LinkFile;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by 韩银 on 2017/11/1 15:21
 * hanyinit@163.com
 */

public class ContactsPortraitConverter implements PropertyConverter<ContactsPortrait, String> {

    @Override
    public ContactsPortrait convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        return new Gson().fromJson(databaseValue, ContactsPortrait.class);
    }

    @Override
    public String convertToDatabaseValue(ContactsPortrait entityProperty) {
        if (entityProperty == null) {
            return null;
        }
        return new Gson().toJson(entityProperty);
    }
}

