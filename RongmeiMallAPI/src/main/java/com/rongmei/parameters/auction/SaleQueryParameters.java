package com.rongmei.parameters.auction;

import java.util.List;

public class SaleQueryParameters {

  private List<String> tags;
  private String key;
  private int offset;
  private int limit;
  private boolean isOwnedByAuthor;
  private boolean isOutdated;
  private int rankType;// 0: 最近活跃, 1: 价格（低到高）, 2: 价格（高到低）, 3: 最近发布

  public SaleQueryParameters() {
  }

  public SaleQueryParameters(List<String> tags, String key, int offset, int limit,
      boolean isOwnedByAuthor, boolean isOutdated, int rankType) {
    this.tags = tags;
    this.key = key;
    this.offset = offset;
    this.limit = limit;
    this.isOwnedByAuthor = isOwnedByAuthor;
    this.isOutdated = isOutdated;
    this.rankType = rankType;
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

  public boolean isOwnedByAuthor() {
    return isOwnedByAuthor;
  }

  public void setOwnedByAuthor(boolean ownedByAuthor) {
    isOwnedByAuthor = ownedByAuthor;
  }

  public boolean isOutdated() {
    return isOutdated;
  }

  public void setOutdated(boolean outdated) {
    isOutdated = outdated;
  }

  public int getRankType() {
    return rankType;
  }

  public void setRankType(int rankType) {
    this.rankType = rankType;
  }
}
