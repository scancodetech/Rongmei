package com.rongmei.response.user;

import com.rongmei.response.Response;
import java.util.List;

public class UserRelationGetResponse extends Response {

  private List<UserInfoGetResponse> fans;
  private List<UserInfoGetResponse> follows;

  public UserRelationGetResponse() {
  }

  public UserRelationGetResponse(List<UserInfoGetResponse> fans,
      List<UserInfoGetResponse> follows) {
    this.fans = fans;
    this.follows = follows;
  }

  public List<UserInfoGetResponse> getFans() {
    return fans;
  }

  public void setFans(List<UserInfoGetResponse> fans) {
    this.fans = fans;
  }

  public List<UserInfoGetResponse> getFollows() {
    return follows;
  }

  public void setFollows(List<UserInfoGetResponse> follows) {
    this.follows = follows;
  }
}
