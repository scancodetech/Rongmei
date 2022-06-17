package com.rongmei.response.user;

import com.rongmei.response.Response;
import java.util.List;

public class UserGroupGetResponse extends Response {

  private List<UserGroupItem> userGroupItems;

  public UserGroupGetResponse() {
  }

  public UserGroupGetResponse(List<UserGroupItem> userGroupItems) {
    this.userGroupItems = userGroupItems;
  }

  public List<UserGroupItem> getUserGroupItems() {
    return userGroupItems;
  }

  public void setUserGroupItems(List<UserGroupItem> userGroupItems) {
    this.userGroupItems = userGroupItems;
  }
}
