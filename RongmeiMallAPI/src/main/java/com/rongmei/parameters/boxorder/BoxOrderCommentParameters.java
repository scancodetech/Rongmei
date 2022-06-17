package com.rongmei.parameters.boxorder;

public class BoxOrderCommentParameters {

  private long id;
  private int commentStatus;
  private String comment;

  public BoxOrderCommentParameters() {
  }

  public BoxOrderCommentParameters(long id, int commentStatus, String comment) {
    this.id = id;
    this.commentStatus = commentStatus;
    this.comment = comment;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getCommentStatus() {
    return commentStatus;
  }

  public void setCommentStatus(int commentStatus) {
    this.commentStatus = commentStatus;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
