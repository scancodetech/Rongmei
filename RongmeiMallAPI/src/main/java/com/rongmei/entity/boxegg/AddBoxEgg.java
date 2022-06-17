package com.rongmei.entity.boxegg;


import javax.persistence.*;

@Entity
@Table(name = "add_box_egg")
public class AddBoxEgg {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "username")
  private String username;//创作者用户名
  @Column(name = "specification")
  private String specification;//套盒规格  1: 3*3 ， 2: 3*4；
  @Column(name = "box_number")
  private int boxNumber;//箱子数量
  @Column(name = "cover_url")
  private String  coverUrl;//封面图路径
  @Column(name = "series_name")
  private String  seriesName;//系列名称
  @Column(name = "series_introduction")
  private String seriesIntroduction;//系列简介
  @Column(name = "hide_model_id")
  private String  hideModelId;//隐藏款盒蛋id 字符串
  @Column(name = "common_model_id")
  private String commonModelId;//普通款id 字符串
  @Column(name = "price")
  private int price;//定价
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "status")
  private int status;//审核状态 1 未通过 2 通过  0 审核中
  @Column(name = "start_time")//售卖开始时间
  private long startTime;
  @Column(name = "end_time")//售卖结束时间
  private long endTime;

  public AddBoxEgg() {
  }

  public int getBoxNumber() {
    return boxNumber;
  }

  public void setBoxNumber(int boxNumber) {
    this.boxNumber = boxNumber;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSpecification() {
    return specification;
  }

  public void setSpecification(String specification) {
    this.specification = specification;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getSeriesName() {
    return seriesName;
  }

  public void setSeriesName(String seriesName) {
    this.seriesName = seriesName;
  }

  public String getSeriesIntroduction() {
    return seriesIntroduction;
  }

  public void setSeriesIntroduction(String seriesIntroduction) {
    this.seriesIntroduction = seriesIntroduction;
  }

  public String getHideModelId() {
    return hideModelId;
  }

  public void setHideModelId(String hideModelId) {
    this.hideModelId = hideModelId;
  }

  public String getCommonModelId() {
    return commonModelId;
  }

  public void setCommonModelId(String commonModelId) {
    this.commonModelId = commonModelId;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
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

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }
}
