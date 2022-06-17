package com.rongmei.response.draft;

import com.rongmei.response.Response;

public class DraftProposalDetailResponse extends Response {

  private int id;
  private String content;
  private int relationId;
  private String proposalType;
  private int passStandard;
  private int promulgateStandard;
  private String status;
  private long startTime;
  private long endTime;
  private long createTime;
  private long updateTime;
  private int visibility;
  private String painterName;
  private String painterSampleUrl;
  private int workPeriod;
  private int completionDegree;
  private long voteEndTime;
  private int price;
  private int agreeNum;
  private int disagreeNum;
  private int draftStatus;
  private String msg;

  public DraftProposalDetailResponse() {
  }

  public DraftProposalDetailResponse(int id, String content, int relationId,
      String proposalType, int passStandard, int promulgateStandard, String status, long startTime,
      long endTime, long createTime, long updateTime, int visibility, String painterName,
      String painterSampleUrl, int workPeriod, int completionDegree, long voteEndTime, int price,
      int agreeNum, int disagreeNum, int draftStatus, String msg) {
    this.id = id;
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
    this.visibility = visibility;
    this.painterName = painterName;
    this.painterSampleUrl = painterSampleUrl;
    this.workPeriod = workPeriod;
    this.completionDegree = completionDegree;
    this.voteEndTime = voteEndTime;
    this.price = price;
    this.agreeNum = agreeNum;
    this.disagreeNum = disagreeNum;
    this.draftStatus = draftStatus;
    this.msg = msg;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public long getVoteEndTime() {
    return voteEndTime;
  }

  public void setVoteEndTime(long voteEndTime) {
    this.voteEndTime = voteEndTime;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getAgreeNum() {
    return agreeNum;
  }

  public void setAgreeNum(int agreeNum) {
    this.agreeNum = agreeNum;
  }

  public int getDisagreeNum() {
    return disagreeNum;
  }

  public void setDisagreeNum(int disagreeNum) {
    this.disagreeNum = disagreeNum;
  }

  public int getDraftStatus() {
    return draftStatus;
  }

  public void setDraftStatus(int draftStatus) {
    this.draftStatus = draftStatus;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
