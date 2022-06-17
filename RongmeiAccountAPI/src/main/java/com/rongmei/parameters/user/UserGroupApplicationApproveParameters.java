package com.rongmei.parameters.user;

public class UserGroupApplicationApproveParameters {

  private int id;
  private boolean isApprove;
  private String approveMsg;

  public UserGroupApplicationApproveParameters() {
  }

  public UserGroupApplicationApproveParameters(int id, boolean isApprove, String approveMsg) {
    this.id = id;
    this.isApprove = isApprove;
    this.approveMsg = approveMsg;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isApprove() {
    return isApprove;
  }

  public void setApprove(boolean approve) {
    isApprove = approve;
  }

  public String getApproveMsg() {
    return approveMsg;
  }

  public void setApproveMsg(String approveMsg) {
    this.approveMsg = approveMsg;
  }
}
