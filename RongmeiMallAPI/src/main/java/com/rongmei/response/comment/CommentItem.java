package com.rongmei.response.comment;

public class CommentItem {

  private long id;
  private String fromUsername;
  private String fromNickname;
  private String fromAvatarUrl;
  private String content;
  private String toUsername;
  private String toNickname;
  private String toAvatarUrl;
  private long replyNum;
  private long toCommentId;
  private int relationId;
  private long createTime;
  private long updateTime;
  private boolean isLike;
  private long likeNum;

  public CommentItem() {
  }

  public CommentItem(long id, String fromUsername, String fromNickname,
      String fromAvatarUrl, String content, String toUsername, String toNickname,
      String toAvatarUrl, long replyNum, long toCommentId, int relationId, long createTime,
      long updateTime, boolean isLike, long likeNum) {
    this.id = id;
    this.fromUsername = fromUsername;
    this.fromNickname = fromNickname;
    this.fromAvatarUrl = fromAvatarUrl;
    this.content = content;
    this.toUsername = toUsername;
    this.toNickname = toNickname;
    this.toAvatarUrl = toAvatarUrl;
    this.replyNum = replyNum;
    this.toCommentId = toCommentId;
    this.relationId = relationId;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.isLike = isLike;
    this.likeNum = likeNum;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFromUsername() {
    return fromUsername;
  }

  public void setFromUsername(String fromUsername) {
    this.fromUsername = fromUsername;
  }

  public String getFromNickname() {
    return fromNickname;
  }

  public void setFromNickname(String fromNickname) {
    this.fromNickname = fromNickname;
  }

  public String getFromAvatarUrl() {
    return fromAvatarUrl;
  }

  public void setFromAvatarUrl(String fromAvatarUrl) {
    this.fromAvatarUrl = fromAvatarUrl;
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

  public String getToNickname() {
    return toNickname;
  }

  public void setToNickname(String toNickname) {
    this.toNickname = toNickname;
  }

  public long getReplyNum() {
    return replyNum;
  }

  public void setReplyNum(long replyNum) {
    this.replyNum = replyNum;
  }

  public String getToAvatarUrl() {
    return toAvatarUrl;
  }

  public void setToAvatarUrl(String toAvatarUrl) {
    this.toAvatarUrl = toAvatarUrl;
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

  public boolean isLike() {
    return isLike;
  }

  public void setLike(boolean like) {
    isLike = like;
  }

  public long getLikeNum() {
    return likeNum;
  }

  public void setLikeNum(long likeNum) {
    this.likeNum = likeNum;
  }
}
