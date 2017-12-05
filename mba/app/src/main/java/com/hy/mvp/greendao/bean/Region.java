package com.hy.mvp.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：hanyin on 2017/8/10 13:33
 * 邮箱：hanyinit@163.com
 */
@Entity
public class Region {
    @Id
    private Long _id;
    private Long id;
    private String regionName;
    private Long parentId;
    private String shortName;
    private  int levelType;
    private String cityCode;
    private String zipCode;
    private String mergerName;
    private float ing;
    private float lat;
    private String pinyin;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getPinyin() {
        return this.pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public float getLat() {
        return this.lat;
    }
    public void setLat(float lat) {
        this.lat = lat;
    }
    public float getIng() {
        return this.ing;
    }
    public void setIng(float ing) {
        this.ing = ing;
    }
    public String getMergerName() {
        return this.mergerName;
    }
    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }
    public String getZipCode() {
        return this.zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getCityCode() {
        return this.cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public int getLevelType() {
        return this.levelType;
    }
    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }
    public String getShortName() {
        return this.shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public Long getParentId() {
        return this.parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public String getRegionName() {
        return this.regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1981627791)
    public Region(Long _id, Long id, String regionName, Long parentId,
            String shortName, int levelType, String cityCode, String zipCode,
            String mergerName, float ing, float lat, String pinyin) {
        this._id = _id;
        this.id = id;
        this.regionName = regionName;
        this.parentId = parentId;
        this.shortName = shortName;
        this.levelType = levelType;
        this.cityCode = cityCode;
        this.zipCode = zipCode;
        this.mergerName = mergerName;
        this.ing = ing;
        this.lat = lat;
        this.pinyin = pinyin;
    }

    @Generated(hash = 600106640)
    public Region() {
    }
}
