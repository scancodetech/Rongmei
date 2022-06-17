package com.rongmei.blservice.account;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.user.UserGroupApplicationApproveParameters;
import com.rongmei.parameters.user.UserGroupApplyParameters;
import com.rongmei.parameters.user.UserGroupUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.user.UserGroupApplicationGetResponse;
import com.rongmei.response.user.UserGroupApplicationResponse;
import com.rongmei.response.user.UserGroupDetailResponse;
import com.rongmei.response.user.UserGroupGetResponse;

public interface UserGroupBlService {

  UserGroupGetResponse getUserGroups();

  SuccessResponse deleteUserGroup(int userGroupId);

  UserGroupDetailResponse updateUserGroup(UserGroupUpdateParameters parameters)
      throws ThingIdDoesNotExistException;

  UserGroupDetailResponse getUserGroup(int userGroupId) throws ThingIdDoesNotExistException;

  UserGroupDetailResponse updateUserGroupPrice(int userGroupId, long price)
      throws ThingIdDoesNotExistException;

  UserGroupGetResponse getUserGroupByUsername(String username);

  SuccessResponse applyUserGroup(UserGroupApplyParameters parameters)
      throws ThingIdDoesNotExistException;

  UserGroupApplicationResponse getUserGroupApplication() throws ThingIdDoesNotExistException;

  SuccessResponse approveUserGroupApplication(UserGroupApplicationApproveParameters parameters)
      throws ThingIdDoesNotExistException;

  UserGroupApplicationGetResponse getAllApproveUserGroupApplications();
}
