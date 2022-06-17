package com.rongmei.entity.proposal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "proposal_participate")
public class ProposalParticipate {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "relation_id")
  private int relationId;
  @Column(name = "username")
  private String username;
  @Column(name = "is_agree")
  private boolean isAgree;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public ProposalParticipate() {
  }

  public ProposalParticipate(int relationId, String username, boolean isAgree,
      long createTime, long updateTime) {
    this.relationId = relationId;
    this.username = username;
    this.isAgree = isAgree;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getRelationId() {
    return relationId;
  }

  public void setRelationId(int relationId) {
    this.relationId = relationId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public boolean isAgree() {
    return isAgree;
  }

  public void setAgree(boolean agree) {
    isAgree = agree;
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
