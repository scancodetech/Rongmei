package com.rongmei.parameters.draft;

public class DraftReviewParameters {

  private long relationId;
  private int draftType;// 0:commodity 1:sale 2:blindbox
  private boolean isApprove;
  private String msg;

  public DraftReviewParameters() {
  }

  public DraftReviewParameters(long relationId, int draftType, boolean isApprove,
      String msg) {
    this.relationId = relationId;
    this.draftType = draftType;
    this.isApprove = isApprove;
    this.msg = msg;
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

  public boolean isApprove() {
    return isApprove;
  }

  public void setApprove(boolean approve) {
    isApprove = approve;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
