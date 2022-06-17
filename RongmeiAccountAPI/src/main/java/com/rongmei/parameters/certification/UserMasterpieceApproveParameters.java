package com.rongmei.parameters.certification;

public class UserMasterpieceApproveParameters {

  private int id;
  private boolean isApprove;
  private String approveMsg;

  public UserMasterpieceApproveParameters() {
  }

  public UserMasterpieceApproveParameters(int id, boolean isApprove, String approveMsg) {
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
