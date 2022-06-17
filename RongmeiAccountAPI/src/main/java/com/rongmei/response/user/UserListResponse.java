package com.rongmei.response.user;

import com.rongmei.response.Response;
import java.util.List;

public class UserListResponse extends Response {

  private List<UserItem> userItems;

  public UserListResponse() {
  }

  public UserListResponse(List<UserItem> userItems) {
    this.userItems = userItems;
  }

  public List<UserItem> getUserItems() {
    return userItems;
  }

  public void setUserItems(List<UserItem> userItems) {
    this.userItems = userItems;
  }
}
