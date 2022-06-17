package com.rongmei.entity.stats;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "use_stats")
public class UseStats {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "access_key")
  private String accessKey;
  @Column(name = "use_type")
  private String useType;
  @Column(name = "create_time")
  private long createTime;

  public UseStats() {
  }

  public UseStats(String accessKey, String useType, long createTime) {
    this.accessKey = accessKey;
    this.useType = useType;
    this.createTime = createTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getUseType() {
    return useType;
  }

  public void setUseType(String useType) {
    this.useType = useType;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }
}
