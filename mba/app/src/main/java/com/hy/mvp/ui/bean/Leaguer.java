package com.hy.mvp.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 韩银 on 2017/9/27 10:43
 * hanyinit@163.com
 */
@Entity
public class Leaguer implements Serializable{
    @Id
    private long _id;
    private String id;			//会员id
    private String realname; 	//会员姓名
    private int sex = 1;			//性别 1:男；2：女
    private String nickname;	//昵称
    private Long address1_id;	//常驻地址1
    private Long address2_id;	//常驻地址2
    private Long address3_id;	//常驻地址3
    private String telnum;		//电话号码
    private String school;		//学校
    private String grade;		//年级
    private String classname;	//班级
    private String studentid;	//学号
    private int ispublic;		//是否公开私人信息
    private String corp;		//公司
    private String dept;		//部门
    private String post;		//职位
    private Long ysalary_id;		//年薪等级
    private String referee_id;		//推荐人ID
    private int status = 1;			//会员状态  0：临时会员；1：正式会员
    private int isDel = 0;			//是否删除
    private Long regtime;
    private String login_name;
    private String login_pwd;

    //关联信息
    private String referee_realname; //用于存放推荐人的姓名
    private String portraitIds;

    private String protrait_url;//头像url
    private String qr_url;//二维码url

    private String mainAddress;

    @Generated(hash = 1376156367)
    public Leaguer(long _id, String id, String realname, int sex, String nickname,
            Long address1_id, Long address2_id, Long address3_id, String telnum,
            String school, String grade, String classname, String studentid,
            int ispublic, String corp, String dept, String post, Long ysalary_id,
            String referee_id, int status, int isDel, Long regtime,
            String login_name, String login_pwd, String referee_realname,
            String portraitIds, String protrait_url, String qr_url,
            String mainAddress) {
        this._id = _id;
        this.id = id;
        this.realname = realname;
        this.sex = sex;
        this.nickname = nickname;
        this.address1_id = address1_id;
        this.address2_id = address2_id;
        this.address3_id = address3_id;
        this.telnum = telnum;
        this.school = school;
        this.grade = grade;
        this.classname = classname;
        this.studentid = studentid;
        this.ispublic = ispublic;
        this.corp = corp;
        this.dept = dept;
        this.post = post;
        this.ysalary_id = ysalary_id;
        this.referee_id = referee_id;
        this.status = status;
        this.isDel = isDel;
        this.regtime = regtime;
        this.login_name = login_name;
        this.login_pwd = login_pwd;
        this.referee_realname = referee_realname;
        this.portraitIds = portraitIds;
        this.protrait_url = protrait_url;
        this.qr_url = qr_url;
        this.mainAddress = mainAddress;
    }

    @Generated(hash = 1843263866)
    public Leaguer() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getAddress1_id() {
        return address1_id;
    }

    public void setAddress1_id(Long address1_id) {
        this.address1_id = address1_id;
    }

    public Long getAddress2_id() {
        return address2_id;
    }

    public void setAddress2_id(Long address2_id) {
        this.address2_id = address2_id;
    }

    public Long getAddress3_id() {
        return address3_id;
    }

    public void setAddress3_id(Long address3_id) {
        this.address3_id = address3_id;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public int getIspublic() {
        return ispublic;
    }

    public void setIspublic(int ispublic) {
        this.ispublic = ispublic;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Long getYsalary_id() {
        return ysalary_id;
    }

    public void setYsalary_id(Long ysalary_id) {
        this.ysalary_id = ysalary_id;
    }

    public String getReferee_id() {
        return referee_id;
    }

    public void setReferee_id(String referee_id) {
        this.referee_id = referee_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public Long getRegtime() {
        return regtime;
    }

    public void setRegtime(Long regtime) {
        this.regtime = regtime;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getLogin_pwd() {
        return login_pwd;
    }

    public void setLogin_pwd(String login_pwd) {
        this.login_pwd = login_pwd;
    }

    public String getReferee_realname() {
        return referee_realname;
    }

    public void setReferee_realname(String referee_realname) {
        this.referee_realname = referee_realname;
    }

    public String getPortraitIds() {
        return portraitIds;
    }

    public void setPortraitIds(String portraitIds) {
        this.portraitIds = portraitIds;
    }

    public String getProtrait_url() {
        return protrait_url;
    }

    public void setProtrait_url(String protrait_url) {
        this.protrait_url = protrait_url;
    }

    public String getQr_url() {
        return qr_url;
    }

    public void setQr_url(String qr_url) {
        this.qr_url = qr_url;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }
}
