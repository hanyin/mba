package com.hy.mvp.ui.bean;


/**
 * 作者：hanyin on 2017/8/10 13:44
 * 邮箱：hanyinit@163.com
 */

public class RegionData<T> {
     public T respData;
     public int status;
     public String err;

     public T getRespData() {
          return respData;
     }

     public void setRespData(T respData) {
          this.respData = respData;
     }

     public int getStatus() {
          return status;
     }

     public void setStatus(int status) {
          this.status = status;
     }

     public String getErr() {
          return err;
     }

     public void setErr(String err) {
          this.err = err;
     }
}
