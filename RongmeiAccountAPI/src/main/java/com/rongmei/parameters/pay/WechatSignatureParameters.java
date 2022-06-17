package com.rongmei.parameters.pay;

public class WechatSignatureParameters {

  private String nonceStr;
  private long timestamp;
  private String url;

  public WechatSignatureParameters() {
  }

  public WechatSignatureParameters(String nonceStr, long timestamp, String url) {
    this.nonceStr = nonceStr;
    this.timestamp = timestamp;
    this.url = url;
  }

  public String getNonceStr() {
    return nonceStr;
  }

  public void setNonceStr(String nonceStr) {
    this.nonceStr = nonceStr;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
