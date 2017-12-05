package com.hy.mvp.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 作者：hanyin on 2017/7/11 11:24
 * 邮箱：hanyinit@163.com
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    private String username;
    private String password;
    @Generated(hash = 1681958521)
    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


}
