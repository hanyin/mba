package com.hy.mvp.ui.bean;

/**
 * Created by 韩银 on 2017/11/1 15:20
 * hanyinit@163.com
 */

public class ContactsPortrait {
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
