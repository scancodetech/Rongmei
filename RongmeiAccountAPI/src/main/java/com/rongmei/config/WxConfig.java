package com.rongmei.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfig {

  private final WxProperties properties;

  @Autowired
  public WxConfig(WxProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public WxPayService wxService() {
    WxPayConfig payConfig = new WxPayConfig();
    payConfig.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
    payConfig.setMchId(StringUtils.trimToNull(this.properties.getMchId()));
    payConfig.setMchKey(StringUtils.trimToNull(this.properties.getMchKey()));
    payConfig.setKeyPath(StringUtils.trimToNull(this.properties.getKeyPath()));
    payConfig.setNotifyUrl(StringUtils.trimToNull(this.properties.getNotifyUrl()));
    // 可以指定是否使用沙箱环境
    payConfig.setUseSandboxEnv(false);
    WxPayService wxPayService = new WxPayServiceImpl();
    wxPayService.setConfig(payConfig);
    return wxPayService;
  }
}
