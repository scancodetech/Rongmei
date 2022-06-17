package com.rongmei.entity.auction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "auction")
public class Auction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "price")
  private double price;
  @Column(name = "username")
  private String username;
  @Column(name = "sale_id")
  private int saleId;
  @Column(name = "thing_id")
  private int thingId;
  @Column(name = "create_time")
  private long createTime;

  public Auction() {
  }

  public Auction(double price, String username, int saleId, int thingId, long createTime) {
    this.price = price;
    this.username = username;
    this.saleId = saleId;
    this.thingId = thingId;
    this.createTime = createTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getSaleId() {
    return saleId;
  }

  public void setSaleId(int saleId) {
    this.saleId = saleId;
  }

  public int getThingId() {
    return thingId;
  }

  public void setThingId(int thingId) {
    this.thingId = thingId;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }
}
