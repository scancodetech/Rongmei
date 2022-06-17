package com.rongmei.entity.draft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "draft")
public class Draft {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "relation_id")
  private long relationId;
  @Column(name = "draft_type")
  private int draftType;// 0:commodity 1:sale 2:blindbox 3:proposal 4:groupshopping
  @Column(name = "msg")
  private String msg;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;

  public Draft() {
  }

  public Draft(long relationId, int draftType, String msg, long createTime, long updateTime) {
    this.relationId = relationId;
    this.draftType = draftType;
    this.msg = msg;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getRelationId() {
    return relationId;
  }

  public void setRelationId(long relationId) {
    this.relationId = relationId;
  }

  public int getDraftType() {
    return draftType;
  }

  public void setDraftType(int draftType) {
    this.draftType = draftType;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
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
