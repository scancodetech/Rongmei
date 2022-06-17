package com.rongmei.entity.stats;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stats")
public class Stats {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "transactionId")
  private String transactionId;
  @Column(name = "txHash")
  private String txHash;
  @Column(name = "extra")
  private String extra;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "access_id")
  private int accessId;

  public Stats() {
  }

  public Stats(String transactionId, String txHash, String extra, long createTime, int accessId) {
    this.transactionId = transactionId;
    this.txHash = txHash;
    this.extra = extra;
    this.createTime = createTime;
    this.accessId = accessId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getTxHash() {
    return txHash;
  }

  public void setTxHash(String txHash) {
    this.txHash = txHash;
  }

  public String getExtra() {
    return extra;
  }

  public void setExtra(String extra) {
    this.extra = extra;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public int getAccessId() {
    return accessId;
  }

  public void setAccessId(int accessId) {
    this.accessId = accessId;
  }
}
