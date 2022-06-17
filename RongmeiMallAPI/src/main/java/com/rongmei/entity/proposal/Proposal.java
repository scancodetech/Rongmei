package com.rongmei.entity.proposal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "proposal")
public class Proposal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "username")
  private String username;
  @Column(name = "content")
  private String content;
  @Column(name = "relation_id")
  private int relationId;
  @Column(name = "proposal_type")
  private String proposalType;
  @Column(name = "pass_standard")
  private int passStandard;
  @Column(name = "promulgate_standard")
  private int promulgateStandard;
  @Column(name = "status")
  private String status; //审核中,投票中,拒绝,通过,已颁布,已创造
  @Column(name = "start_time")
  private long startTime;
  @Column(name = "end_time")
  private long endTime;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "update_time")
  private long updateTime;
  @Column(name = "is_active", columnDefinition = "tinyint default 1")
  private boolean isActive;
  @Column(name = "visibility")
  private int visibility; //0:公开 1:私密 2:车队人员可见
  @Column(name = "painter_name")
  private String painterName;
  @Column(name = "painter_sample_url")
  private String painterSampleUrl;
  @Column(name = "work_period")
  private int workPeriod;
  @Column(name = "completion_degree")
  private int completionDegree;
  @Column(name = "price")
  private int price;
  @Column(name = "vote_end_time")
  private long voteEndTime;
  @Column(name = "draft_status", columnDefinition = "int default 0")
  private int draftStatus;//0:审核中 1:未通过 2:通过

  public Proposal() {
  }

  public Proposal(String username, String content, int relationId, String proposalType,
      int passStandard, int promulgateStandard, String status, long startTime, long endTime,
      long createTime, long updateTime, boolean isActive, int visibility,
      String painterName, String painterSampleUrl, int workPeriod, int completionDegree, int price,
      long voteEndTime, int draftStatus) {
    this.username = username;
    this.content = content;
    this.relationId = relationId;
    this.proposalType = proposalType;
    this.passStandard = passStandard;
    this.promulgateStandard = promulgateStandard;
    this.status = status;
    this.startTime = startTime;
    this.endTime = endTime;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isActive = isActive;
    this.visibility = visibility;
    this.painterName = painterName;
    this.painterSampleUrl = painterSampleUrl;
    this.workPeriod = workPeriod;
    this.completionDegree = completionDegree;
    this.price = price;
    this.voteEndTime = voteEndTime;
    this.draftStatus = draftStatus;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getRelationId() {
    return relationId;
  }

  public void setRelationId(int relationId) {
    this.relationId = relationId;
  }

  public String getProposalType() {
    return proposalType;
  }

  public void setProposalType(String proposalType) {
    this.proposalType = proposalType;
  }

  public int getPassStandard() {
    return passStandard;
  }

  public void setPassStandard(int passStandard) {
    this.passStandard = passStandard;
  }

  public int getPromulgateStandard() {
    return promulgateStandard;
  }

  public void setPromulgateStandard(int promulgateStandard) {
    this.promulgateStandard = promulgateStandard;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
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

  public int getVisibility() {
    return visibility;
  }

  public void setVisibility(int visibility) {
    this.visibility = visibility;
  }

  public String getPainterName() {
    return painterName;
  }

  public void setPainterName(String painterName) {
    this.painterName = painterName;
  }

  public String getPainterSampleUrl() {
    return painterSampleUrl;
  }

  public void setPainterSampleUrl(String painterSampleUrl) {
    this.painterSampleUrl = painterSampleUrl;
  }

  public int getWorkPeriod() {
    return workPeriod;
  }

  public void setWorkPeriod(int workPeriod) {
    this.workPeriod = workPeriod;
  }

  public int getCompletionDegree() {
    return completionDegree;
  }

  public void setCompletionDegree(int completionDegree) {
    this.completionDegree = completionDegree;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public long getVoteEndTime() {
    return voteEndTime;
  }

  public void setVoteEndTime(long voteEndTime) {
    this.voteEndTime = voteEndTime;
  }

  public int getDraftStatus() {
    return draftStatus;
  }

  public void setDraftStatus(int draftStatus) {
    this.draftStatus = draftStatus;
  }
}
