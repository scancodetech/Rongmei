package com.rongmei.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wechat.pay")
public class WxProperties {

  /**
   * 设置微信公众号或者小程序等的appid
   */
  private String appId;

  /**
   * 微信支付商户号
   */
  private String mchId;

  /**
   * 微信支付商户密钥
   */
  private String mchKey;

  /**
   * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
   */
  private String keyPath;

  /**
   * 微信回调接口地址
   */
  private String notifyUrl;

  public WxProperties() {
  }

  public WxProperties(String appId, String mchId, String mchKey, String keyPath,
      String notifyUrl) {
    this.appId = appId;
    this.mchId = mchId;
    this.mchKey = mchKey;
    this.keyPath = keyPath;
    this.notifyUrl = notifyUrl;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getMchId() {
    return mchId;
  }

  public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  public String getMchKey() {
    return mchKey;
  }

  public void setMchKey(String mchKey) {
    this.mchKey = mchKey;
  }

  public String getKeyPath() {
    return keyPath;
  }

  public void setKeyPath(String keyPath) {
    this.keyPath = keyPath;
  }

  public String getNotifyUrl() {
    return notifyUrl;
  }

  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }
}
