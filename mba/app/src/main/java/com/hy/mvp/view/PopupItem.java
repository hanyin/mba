package com.hy.mvp.view;


public class PopupItem {

    public int icon;
    public String content;

    public PopupItem() {
    }

    public PopupItem(int icon, String content) {
        this.icon = icon;
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
