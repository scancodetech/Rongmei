package com.rongmei.bl.select;

import com.rongmei.entity.select.Select;
import com.rongmei.response.Response;

public class GetSelectResponse extends Response {

    private Select select;

    private String msg;

    public GetSelectResponse() {
    }

    public GetSelectResponse(Select select, String msg) {
        this.select = select;
        this.msg = msg;
    }

    public Select getSelect() {
        return select;
    }

    public void setSelect(Select select) {
        this.select = select;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
