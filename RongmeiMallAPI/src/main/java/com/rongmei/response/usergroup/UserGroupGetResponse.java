package com.rongmei.response.usergroup;

import com.rongmei.response.Response;
import java.util.List;

public class UserGroupGetResponse extends Response {

  private List<UserGroupInfoItem> userGroupInfoItemList;

  public UserGroupGetResponse() {
  }

  public UserGroupGetResponse(
      List<UserGroupInfoItem> userGroupInfoItemList) {
    this.userGroupInfoItemList = userGroupInfoItemList;
  }

  public List<UserGroupInfoItem> getUserGroupInfoItemList() {
    return userGroupInfoItemList;
  }

  public void setUserGroupInfoItemList(
      List<UserGroupInfoItem> userGroupInfoItemList) {
    this.userGroupInfoItemList = userGroupInfoItemList;
  }
}
