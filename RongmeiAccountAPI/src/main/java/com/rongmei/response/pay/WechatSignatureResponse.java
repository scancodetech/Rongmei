package com.rongmei.response.pay;

import com.rongmei.response.Response;

public class WechatSignatureResponse extends Response {

  private String signature;

  public WechatSignatureResponse() {
  }

  public WechatSignatureResponse(String signature) {
    this.signature = signature;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }
}
