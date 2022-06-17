package com.rongmei.parameters.commodity;

import java.util.List;

public class CommodityQueryParameters {

  private List<String> tags;
  private String key;
  private String orderKey;
  private int offset;
  private int limit;

  public CommodityQueryParameters() {
  }

  public CommodityQueryParameters(List<String> tags, String key, String orderKey, int offset,
      int limit) {
    this.tags = tags;
    this.key = key;
    this.orderKey = orderKey;
    this.offset = offset;
    this.limit = limit;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getOrderKey() {
    return orderKey;
  }

  public void setOrderKey(String orderKey) {
    this.orderKey = orderKey;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }
}
