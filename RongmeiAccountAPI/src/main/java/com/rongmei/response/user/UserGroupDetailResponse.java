package com.rongmei.response.user;

import com.rongmei.response.Response;

public class UserGroupDetailResponse extends Response {

  private UserGroupItem userGroupItem;

  public UserGroupDetailResponse() {
  }

  public UserGroupDetailResponse(UserGroupItem userGroupItem) {
    this.userGroupItem = userGroupItem;
  }

  public UserGroupItem getUserGroupItem() {
    return userGroupItem;
  }

  public void setUserGroupItem(UserGroupItem userGroupItem) {
    this.userGroupItem = userGroupItem;
  }
}
