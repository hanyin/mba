package com.hy.mvp.ui.bean;

/**
 * Created by 韩银 on 2017/9/18.
 */

public class ResponseResult<T> {
    public T  respData;
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
