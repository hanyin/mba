package com.hy.mvp.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * 作者：hanyin on 2017/8/14 10:56
 * 邮箱：hanyinit@163.com
 */
@Entity
public class RegisterInfo implements Serializable{
    @Id
    private Long id;
    private String realname;//姓名
    private String sex;//性别
    private String nickname;//昵称
    private String address1_id;//地址1
    private String address2_id;
    private String address3_id;
    private String telnum;//电话
    private String school;//学校
    private String grade;//年级
    private String classname;//班级
    private String studentid;//学号
    private String headicon;//头像
    private String ispublic;//"0"公开,"1" 不公开
    private String corp;//公司
    private String dept;//部门
    private String post;//职务
    private String ysalary_id;// 年薪
    private String referee_id;//推荐人
    private String qrcode_pic;//二维码图片
    private String login_name;//账号
    private String login_pwd;//登录密码
    private String pay_pwd;//支付密码
    private String registertime;//注册时间
    private String bindtel;//绑定手机号
    public String getBindtel() {
        return this.bindtel;
    }
    public void setBindtel(String bindtel) {
        this.bindtel = bindtel;
    }
    public String getRegistertime() {
        return this.registertime;
    }
    public void setRegistertime(String registertime) {
        this.registertime = registertime;
    }
    public String getPay_pwd() {
        return this.pay_pwd;
    }
    public void setPay_pwd(String pay_pwd) {
        this.pay_pwd = pay_pwd;
    }
    public String getLogin_pwd() {
        return this.login_pwd;
    }
    public void setLogin_pwd(String login_pwd) {
        this.login_pwd = login_pwd;
    }
    public String getLogin_name() {
        return this.login_name;
    }
    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }
    public String getQrcode_pic() {
        return this.qrcode_pic;
    }
    public void setQrcode_pic(String qrcode_pic) {
        this.qrcode_pic = qrcode_pic;
    }
    public String getReferee_id() {
        return this.referee_id;
    }
    public void setReferee_id(String referee_id) {
        this.referee_id = referee_id;
    }
    public String getYsalary_id() {
        return this.ysalary_id;
    }
    public void setYsalary_id(String ysalary_id) {
        this.ysalary_id = ysalary_id;
    }
    public String getPost() {
        return this.post;
    }
    public void setPost(String post) {
        this.post = post;
    }
    public String getDept() {
        return this.dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }
    public String getCorp() {
        return this.corp;
    }
    public void setCorp(String corp) {
        this.corp = corp;
    }
    public String getIspublic() {
        return this.ispublic;
    }
    public void setIspublic(String ispublic) {
        this.ispublic = ispublic;
    }
    public String getHeadicon() {
        return this.headicon;
    }
    public void setHeadicon(String headicon) {
        this.headicon = headicon;
    }
    public String getStudentid() {
        return this.studentid;
    }
    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }
    public String getClassname() {
        return this.classname;
    }
    public void setClassname(String classname) {
        this.classname = classname;
    }
    public String getGrade() {
        return this.grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getSchool() {
        return this.school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public String getTelnum() {
        return this.telnum;
    }
    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }
    public String getAddress3_id() {
        return this.address3_id;
    }
    public void setAddress3_id(String address3_id) {
        this.address3_id = address3_id;
    }
    public String getAddress2_id() {
        return this.address2_id;
    }
    public void setAddress2_id(String address2_id) {
        this.address2_id = address2_id;
    }
    public String getAddress1_id() {
        return this.address1_id;
    }
    public void setAddress1_id(String address1_id) {
        this.address1_id = address1_id;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getRealname() {
        return this.realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1981578331)
    public RegisterInfo(Long id, String realname, String sex, String nickname,
            String address1_id, String address2_id, String address3_id,
            String telnum, String school, String grade, String classname,
            String studentid, String headicon, String ispublic, String corp,
            String dept, String post, String ysalary_id, String referee_id,
            String qrcode_pic, String login_name, String login_pwd, String pay_pwd,
            String registertime, String bindtel) {
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
        this.headicon = headicon;
        this.ispublic = ispublic;
        this.corp = corp;
        this.dept = dept;
        this.post = post;
        this.ysalary_id = ysalary_id;
        this.referee_id = referee_id;
        this.qrcode_pic = qrcode_pic;
        this.login_name = login_name;
        this.login_pwd = login_pwd;
        this.pay_pwd = pay_pwd;
        this.registertime = registertime;
        this.bindtel = bindtel;
    }
    @Generated(hash = 1470244328)
    public RegisterInfo() {
    }
}
