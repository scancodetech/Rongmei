package com.rongmei.response.draft;

import com.rongmei.response.Response;

public class DraftResponse extends Response {

  private String msg;

  public DraftResponse() {
  }

  public DraftResponse(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
