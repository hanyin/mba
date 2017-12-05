package com.hy.mvp.ui.bean;

import java.util.List;

/**
 * 作者：hanyin on 2017/8/15 16:25
 * 邮箱：hanyinit@163.com
 */

public class YearSalary<T> {

    /**
     * status : 1
     * respData : {"ysalaryList":[{"id":1,"salary_range":"10万以下","sn":1,"isDel":0},{"id":2,"salary_range":"10万-20万","sn":2,"isDel":0},{"id":3,"salary_range":"20万-50万","sn":3,"isDel":0},{"id":4,"salary_range":"50万-100万","sn":4,"isDel":0},{"id":5,"salary_range":"100万以上","sn":5,"isDel":0}]}
     * err : null
     */

    private int status;
    private T respData;
    private String err;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getRespData() {
        return respData;
    }

    public void setRespData(T respData) {
        this.respData = respData;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public static class RespDataBean  {
        private List<YsalaryListBean> ysalaryList;

        public List<YsalaryListBean> getYsalaryList() {
            return ysalaryList;
        }

        public void setYsalaryList(List<YsalaryListBean> ysalaryList) {
            this.ysalaryList = ysalaryList;
        }

        public static class YsalaryListBean {
            /**
             * id : 1
             * salary_range : 10万以下
             * sn : 1
             * isDel : 0
             */

            private int id;
            private String salary_range;
            private int sn;
            private int isDel;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSalary_range() {
                return salary_range;
            }

            public void setSalary_range(String salary_range) {
                this.salary_range = salary_range;
            }

            public int getSn() {
                return sn;
            }

            public void setSn(int sn) {
                this.sn = sn;
            }

            public int getIsDel() {
                return isDel;
            }

            public void setIsDel(int isDel) {
                this.isDel = isDel;
            }
        }
    }
}
