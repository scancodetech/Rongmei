package com.rongmei.response.select;

public class SelectItem {
    private String title;

    private String value;

    public SelectItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SelectItem(String title, String value) {
        this.title = title;
        this.value = value;
    }
}
