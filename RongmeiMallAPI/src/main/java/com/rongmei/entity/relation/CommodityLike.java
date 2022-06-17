package com.rongmei.entity.relation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commodity_like")
public class CommodityLike {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "username")
  private String username;
  @Column(name = "commodity_id")
  private int commodityId;
  @Column(name = "favorites_id")
  private int favoritesId;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public CommodityLike() {
  }

  public CommodityLike(String username, int commodityId, int favoritesId, long createTime,
      long updateTime) {
    this.username = username;
    this.commodityId = commodityId;
    this.favoritesId = favoritesId;
    this.createTime = createTime;
    this.updateTime = updateTime;
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

  public int getCommodityId() {
    return commodityId;
  }

  public void setCommodityId(int commodityId) {
    this.commodityId = commodityId;
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

  public int getFavoritesId() {
    return favoritesId;
  }

  public void setFavoritesId(int favoritesId) {
    this.favoritesId = favoritesId;
  }
}
