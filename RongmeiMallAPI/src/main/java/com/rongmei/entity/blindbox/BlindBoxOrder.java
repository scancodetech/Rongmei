package com.rongmei.entity.blindbox;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blind_box_order")
public class BlindBoxOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column(name = "username")
  private String username;
  @Column(name = "blind_box_id")
  private long blindBoxId;
  @Column(name = "price")
  private int price;
  @Column(name = "theme")
  private String theme;
  @Column(name = "classification")
  private String classification;
  @Column(name = "status")
  private String status;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public BlindBoxOrder() {
  }

  public BlindBoxOrder(String username, long blindBoxId, int price, String theme,
      String classification, String status, long createTime, long updateTime) {
    this.username = username;
    this.blindBoxId = blindBoxId;
    this.price = price;
    this.theme = theme;
    this.classification = classification;
    this.status = status;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public long getBlindBoxId() {
    return blindBoxId;
  }

  public void setBlindBoxId(long blindBoxId) {
    this.blindBoxId = blindBoxId;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public String getClassification() {
    return classification;
  }

  public void setClassification(String classification) {
    this.classification = classification;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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
}
