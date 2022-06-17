package com.rongmei.response.distribution;

import com.rongmei.response.Response;
import java.util.List;

public class UserDistributionDetailResponse extends Response {

  private int id;
  private String username;
  private String code;
  private String qrcodeUrl;
  private int level;
  private long createTime;
  private long updateTime;
  private double scores;
  private List<UserDistributionDetailResponse> children;

  public UserDistributionDetailResponse() {
  }

  public UserDistributionDetailResponse(int id, String username, String code,
      String qrcodeUrl, int level, long createTime, long updateTime, double scores,
      List<UserDistributionDetailResponse> children) {
    this.id = id;
    this.username = username;
    this.code = code;
    this.qrcodeUrl = qrcodeUrl;
    this.level = level;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.scores = scores;
    this.children = children;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getQrcodeUrl() {
    return qrcodeUrl;
  }

  public void setQrcodeUrl(String qrcodeUrl) {
    this.qrcodeUrl = qrcodeUrl;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(long updateTime) {
    this.updateTime = updateTime;
  }

  public double getScores() {
    return scores;
  }

  public void setScores(double scores) {
    this.scores = scores;
  }

  public List<UserDistributionDetailResponse> getChildren() {
    return children;
  }

  public void setChildren(
      List<UserDistributionDetailResponse> children) {
    this.children = children;
  }
}
