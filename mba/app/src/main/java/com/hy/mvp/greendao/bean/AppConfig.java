package com.hy.mvp.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：hanyin on 2017/8/9 21:27
 * 邮箱：hanyinit@163.com
 */
@Entity
public class AppConfig {
    @Id
    private Long id;
    private String config_name;
    private String config_value;
    @Generated(hash = 1089689598)
    public AppConfig(Long id, String config_name, String config_value) {
        this.id = id;
        this.config_name = config_name;
        this.config_value = config_value;
    }
    @Generated(hash = 136961441)
    public AppConfig() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getConfig_name() {
        return this.config_name;
    }
    public void setConfig_name(String config_name) {
        this.config_name = config_name;
    }
    public String getConfig_value() {
        return this.config_value;
    }
    public void setConfig_value(String config_value) {
        this.config_value = config_value;
    }
}
