package com.rongmei.response.user;

import com.rongmei.response.Response;

public class UserRelationResponse extends Response {

  private boolean isLike;

  public UserRelationResponse() {
  }

  public UserRelationResponse(boolean isLike) {
    this.isLike = isLike;
  }

  public boolean isLike() {
    return isLike;
  }

  public void setLike(boolean like) {
    isLike = like;
  }
}
