package com.rongmei.response.commodity;

import com.rongmei.entity.usergroup.UserGroupInfo;
import com.rongmei.response.Response;
import java.util.List;

public class CommodityAuthorDetailResponse extends Response {

  private UserGroupInfo userGroupInfo;
  private List<String> usernameList;

  public CommodityAuthorDetailResponse() {
  }

  public CommodityAuthorDetailResponse(UserGroupInfo userGroupInfo,
      List<String> usernameList) {
    this.userGroupInfo = userGroupInfo;
    this.usernameList = usernameList;
  }

  public UserGroupInfo getUserGroupInfo() {
    return userGroupInfo;
  }

  public void setUserGroupInfo(UserGroupInfo userGroupInfo) {
    this.userGroupInfo = userGroupInfo;
  }

  public List<String> getUsernameList() {
    return usernameList;
  }

  public void setUsernameList(List<String> usernameList) {
    this.usernameList = usernameList;
  }
}
