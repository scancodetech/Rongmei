package com.rongmei.response.pay;

import com.rongmei.response.Response;

public class WechatPostResponse extends Response {

  private String body;

  public WechatPostResponse(String body) {
    this.body = body;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
