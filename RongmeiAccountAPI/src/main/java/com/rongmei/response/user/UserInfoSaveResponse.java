package com.rongmei.response.user;

import com.rongmei.response.Response;

public class UserInfoSaveResponse extends Response {
    private String info;

    public UserInfoSaveResponse() {
        this.info = "success";
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
