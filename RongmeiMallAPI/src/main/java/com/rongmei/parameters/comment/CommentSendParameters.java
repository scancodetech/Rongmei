package com.rongmei.parameters.comment;

public class CommentSendParameters {

  private String toUsername;
  private String content;
  private long toCommentId;
  private int relationId;

  public CommentSendParameters() {
  }

  public CommentSendParameters(String toUsername, String content, long toCommentId,
      int relationId) {
    this.toUsername = toUsername;
    this.content = content;
    this.toCommentId = toCommentId;
    this.relationId = relationId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getToUsername() {
    return toUsername;
  }

  public void setToUsername(String toUsername) {
    this.toUsername = toUsername;
  }

  public long getToCommentId() {
    return toCommentId;
  }

  public void setToCommentId(long toCommentId) {
    this.toCommentId = toCommentId;
  }

  public int getRelationId() {
    return relationId;
  }

  public void setRelationId(int relationId) {
    this.relationId = relationId;
  }
}
