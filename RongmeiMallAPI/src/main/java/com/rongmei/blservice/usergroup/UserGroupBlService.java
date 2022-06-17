package com.rongmei.blservice.usergroup;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.usergroup.UserGroupUpdateParameters;
import com.rongmei.response.usergroup.UserGroupDetailResponse;
import com.rongmei.response.usergroup.UserGroupGetResponse;

public interface UserGroupBlService {

  UserGroupGetResponse getUserGroups(String firstType, String secondType);

  UserGroupDetailResponse getUserGroup(int userGroupId) throws ThingIdDoesNotExistException;

  UserGroupDetailResponse updateUserGroup(UserGroupUpdateParameters parameters)
      throws ThingIdDoesNotExistException;
}
