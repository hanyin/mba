package com.hy.mvp.ui.bean;

import java.util.List;

/**
 * Created by 韩银 on 2017/10/10 10:09
 * hanyinit@163.com
 */

public class BPTypeData {

    private List<BpTypeListBean> bpTypeList;

    public List<BpTypeListBean> getBpTypeList() {
        return bpTypeList;
    }

    public void setBpTypeList(List<BpTypeListBean> bpTypeList) {
        this.bpTypeList = bpTypeList;
    }

    public static class BpTypeListBean {
        /**
         * id : 2
         * type_name : 新闻
         * isDel : 0
         */

        private int id;
        private String type_name;
        private int isDel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }
    }
}
