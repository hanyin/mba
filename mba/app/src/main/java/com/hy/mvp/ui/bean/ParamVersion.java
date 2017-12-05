package com.hy.mvp.ui.bean;

import java.util.List;

/**
 * Created by 韩银 on 2017/9/25 14:56
 * hanyinit@163.com
 */

public class ParamVersion {

    private List<PversionsBean> pversions;

    public List<PversionsBean> getPversions() {
        return pversions;
    }

    public void setPversions(List<PversionsBean> pversions) {
        this.pversions = pversions;
    }

    public static class PversionsBean {
        /**
         * param_type : param_version_type_blogposttype
         * mdf_time : 1493890123957
         */

        private String param_type;
        private long mdf_time;

        public String getParam_type() {
            return param_type;
        }

        public void setParam_type(String param_type) {
            this.param_type = param_type;
        }

        public long getMdf_time() {
            return mdf_time;
        }

        public void setMdf_time(long mdf_time) {
            this.mdf_time = mdf_time;
        }
    }
}
