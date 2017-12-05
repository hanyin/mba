package com.hy.mvp.ui.bean;

/**
 * 作者：hanyin on 2017/8/18 14:50
 * 邮箱：hanyinit@163.com
 */

public class LinkFile<T> {

    /**
     * file : {"fileId":"d27109e0-5b98-4675-90a4-6456528cf9ea","fileName":"null.null","sortIndex":0,"url":"http://192.168.0.103:9091/mbaport/upload/d27109e0-5b98-4675-90a4-6456528cf9ea/null.null"}
     * error : false
     */

    private T file;
    private boolean error;

    public T getFile() {
        return file;
    }

    public void setFile(T file) {
        this.file = file;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public static class FileBean {
        /**
         * fileId : d27109e0-5b98-4675-90a4-6456528cf9ea
         * fileName : null.null
         * sortIndex : 0
         * url : http://192.168.0.103:9091/mbaport/upload/d27109e0-5b98-4675-90a4-6456528cf9ea/null.null
         */

        private String fileId;
        private String fileName;
        private int sortIndex;
        private String url;

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getSortIndex() {
            return sortIndex;
        }

        public void setSortIndex(int sortIndex) {
            this.sortIndex = sortIndex;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
