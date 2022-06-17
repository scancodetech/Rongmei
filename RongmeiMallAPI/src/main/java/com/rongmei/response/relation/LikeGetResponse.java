package com.rongmei.response.relation;

import com.rongmei.response.Response;

public class LikeGetResponse extends Response {

  private boolean isLike;
  private int likeNum;

  public LikeGetResponse() {
  }

  public LikeGetResponse(boolean isLike, int likeNum) {
    this.isLike = isLike;
    this.likeNum = likeNum;
  }

  public boolean isLike() {
    return isLike;
  }

  public void setLike(boolean like) {
    isLike = like;
  }

  public int getLikeNum() {
    return likeNum;
  }

  public void setLikeNum(int likeNum) {
    this.likeNum = likeNum;
  }
}
