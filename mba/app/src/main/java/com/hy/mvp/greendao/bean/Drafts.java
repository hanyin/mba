package com.hy.mvp.greendao.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 韩银 on 2017/10/16 14:43
 * hanyinit@163.com
 */
@Entity
public class Drafts {
    @Id(autoincrement = true)
    private Long id;
    private String typename;
    private String date;
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> imagepaths;

    @Generated(hash = 2121072841)
    public Drafts(Long id, String typename, String date, List<String> imagepaths) {
        this.id = id;
        this.typename = typename;
        this.date = date;
        this.imagepaths = imagepaths;
    }

    @Generated(hash = 1010315492)
    public Drafts() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getImagepaths() {
        return imagepaths;
    }

    public void setImagepaths(List<String> imagepaths) {
        this.imagepaths = imagepaths;
    }
}
