package com.rongmei.entity.blindboxnft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blind_box_sale")
public class BlindBoxSale {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "price")
  private int price;
  @Column(name = "username", columnDefinition = "VARCHAR(255) DEFAULT ''")
  private String username;
  @Column(name = "blind_box_nft_id")
  private int blindBoxNftId;
  @Column(name = "theme")
  private String theme;
  @Column(name = "classification")
  private String classification;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "is_active")
  private boolean isActive;
  @Column(name = "draft_status", columnDefinition = "tinyint default 0")
  private int draftStatus;//0:审核中 1:未通过 2:通过

  public BlindBoxSale() {
  }

  public BlindBoxSale(int price, String username, int blindBoxNftId, String theme,
      String classification, long createTime, long updateTime, boolean isActive,
      int draftStatus) {
    this.price = price;
    this.username = username;
    this.blindBoxNftId = blindBoxNftId;
    this.theme = theme;
    this.classification = classification;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isActive = isActive;
    this.draftStatus = draftStatus;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public int getBlindBoxNftId() {
    return blindBoxNftId;
  }

  public void setBlindBoxNftId(int blindBoxNftId) {
    this.blindBoxNftId = blindBoxNftId;
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

  public int getDraftStatus() {
    return draftStatus;
  }

  public void setDraftStatus(int draftStatus) {
    this.draftStatus = draftStatus;
  }
}
