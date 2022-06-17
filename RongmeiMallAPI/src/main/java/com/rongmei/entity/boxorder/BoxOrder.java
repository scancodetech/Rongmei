package com.rongmei.entity.boxorder;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "box_order")
public class BoxOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "order_type", columnDefinition = "INT DEFAULT 0")
  private int orderType;//0:单人 1:拼单

  @Column(name = "cooperate_type", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String cooperateType;//拼单主题
  @Column(name = "custom_type")
  private String customType;
  @Column(name = "custom_style")
  private String customStyle;

  @Column(name = "world_outlook", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String worldOutlook;
  @Column(name = "custom_theme")
  private String customTheme;
  @Column(name = "element_requirements")
  private String elementRequirements;
  @Column(name = "pay_status", columnDefinition = "INT DEFAULT 2")
  private int payStatus;//0:待支付 1:待分享 2:已支付
  @Column(name = "status")
  private int status;//0:抢单 1:制作中 2:反馈 3:已完成
  @Column(name = "example_url")
  private String exampleUrl;
  @Column(name = "price")
  private int price;
  @Column(name = "username")
  private String username;
  @Column(name = "share_usernames")
  @ElementCollection(targetClass = String.class)
  private List<String> shareUsernames;
  @Column(name = "share_end_time")
  private long shareEndTime;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "is_active")
  private boolean isActive;

  @Column(name = "accept_username")
  private String acceptUsername;
  @Column(name = "accept_time")
  private long acceptTime;
  @Column(name = "result_cover_url")
  private String resultCoverUrl;
  @Column(name = "result_url")
  private String resultUrl;
  @Column(name = "comment_status")

  private int commentStatus;//0:暂无评价 1:需要修改 2:不需要修改
  @Column(name = "comment")
  private String comment;

  @Column(name = "finish_time")
  private long finishTime;

  public BoxOrder() {
  }


  public BoxOrder(int orderType, String cooperateType, String customType,
      String customStyle, String worldOutlook, String customTheme,
      String elementRequirements, int payStatus, int status, String exampleUrl, int price,
      String username, List<String> shareUsernames, long shareEndTime, long createTime,
      long updateTime, boolean isActive, String acceptUsername, long acceptTime,
      String resultCoverUrl, String resultUrl, int commentStatus, String comment, long finishTime) {
    this.orderType = orderType;
    this.cooperateType = cooperateType;
    this.customType = customType;
    this.customStyle = customStyle;
    this.worldOutlook = worldOutlook;
    this.customTheme = customTheme;
    this.elementRequirements = elementRequirements;
    this.payStatus = payStatus;
    this.status = status;
    this.exampleUrl = exampleUrl;
    this.price = price;
    this.username = username;
    this.shareUsernames = shareUsernames;
    this.shareEndTime = shareEndTime;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isActive = isActive;
    this.acceptUsername = acceptUsername;
    this.acceptTime = acceptTime;
    this.resultCoverUrl = resultCoverUrl;
    this.resultUrl = resultUrl;
    this.commentStatus = commentStatus;
    this.comment = comment;
    this.finishTime = finishTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCooperateType() {
    return cooperateType;
  }

  public void setCooperateType(String cooperateType) {
    this.cooperateType = cooperateType;
  }

  public String getWorldOutlook() {
    return worldOutlook;
  }

  public void setWorldOutlook(String worldOutlook) {
    this.worldOutlook = worldOutlook;
  }

  public String getCustomType() {
    return customType;
  }

  public void setCustomType(String customType) {
    this.customType = customType;
  }

  public String getCustomStyle() {
    return customStyle;
  }

  public void setCustomStyle(String customStyle) {
    this.customStyle = customStyle;
  }

  public String getCustomTheme() {
    return customTheme;
  }

  public void setCustomTheme(String customTheme) {
    this.customTheme = customTheme;
  }

  public String getElementRequirements() {
    return elementRequirements;
  }

  public void setElementRequirements(String elementRequirements) {
    this.elementRequirements = elementRequirements;
  }

  public int getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(int payStatus) {
    this.payStatus = payStatus;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getExampleUrl() {
    return exampleUrl;
  }

  public void setExampleUrl(String exampleUrl) {
    this.exampleUrl = exampleUrl;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public String getAcceptUsername() {
    return acceptUsername;
  }

  public void setAcceptUsername(String acceptUsername) {
    this.acceptUsername = acceptUsername;
  }

  public long getAcceptTime() {
    return acceptTime;
  }

  public void setAcceptTime(long acceptTime) {
    this.acceptTime = acceptTime;
  }

  public String getResultCoverUrl() {
    return resultCoverUrl;
  }

  public void setResultCoverUrl(String resultCoverUrl) {
    this.resultCoverUrl = resultCoverUrl;
  }

  public String getResultUrl() {
    return resultUrl;
  }

  public void setResultUrl(String resultUrl) {
    this.resultUrl = resultUrl;
  }

  public int getCommentStatus() {
    return commentStatus;
  }

  public void setCommentStatus(int commentStatus) {
    this.commentStatus = commentStatus;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public long getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(long finishTime) {
    this.finishTime = finishTime;
  }

  public int getOrderType() {
    return orderType;
  }

  public void setOrderType(int orderType) {
    this.orderType = orderType;
  }

  public List<String> getShareUsernames() {
    return shareUsernames;
  }

  public void setShareUsernames(List<String> shareUsernames) {
    this.shareUsernames = shareUsernames;
  }

  public long getShareEndTime() {
    return shareEndTime;
  }

  public void setShareEndTime(long shareEndTime) {
    this.shareEndTime = shareEndTime;
  }
}
