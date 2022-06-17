package com.rongmei.response.user;

import com.rongmei.response.Response;
import java.util.List;

public class UserGroupApplicationGetResponse extends Response {

  private List<UserGroupApplicationItem> userGroupApplicationItemList;

  public UserGroupApplicationGetResponse() {
  }

  public UserGroupApplicationGetResponse(
      List<UserGroupApplicationItem> userGroupApplicationItemList) {
    this.userGroupApplicationItemList = userGroupApplicationItemList;
  }

  public List<UserGroupApplicationItem> getUserGroupApplicationItemList() {
    return userGroupApplicationItemList;
  }

  public void setUserGroupApplicationItemList(
      List<UserGroupApplicationItem> userGroupApplicationItemList) {
    this.userGroupApplicationItemList = userGroupApplicationItemList;
  }
}
