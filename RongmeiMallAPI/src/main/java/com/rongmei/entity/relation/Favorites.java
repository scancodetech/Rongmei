package com.rongmei.entity.relation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "favorites")
public class Favorites {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "name")
  private String name;
  @Column(name = "username")
  private String username;
  @Column(name = "order_id")
  private int orderId;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "is_active")
  private boolean isActive;

  public Favorites() {
  }

  public Favorites(String name, String username, int orderId, long createTime,
      long updateTime, boolean isActive) {
    this.name = name;
    this.username = username;
    this.orderId = orderId;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isActive = isActive;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
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
}
