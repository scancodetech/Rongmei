package com.rongmei.blservice.account;

import com.rongmei.parameters.user.UserRelationUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.user.UserRelationGetResponse;
import com.rongmei.response.user.UserRelationResponse;

public interface UserRelationBlService {

  UserRelationGetResponse getUserRelation(String username);

  SuccessResponse updateUserRelation(UserRelationUpdateParameters parameters);

  UserRelationResponse getUserRelationLike(String fromUsername, String toUsername, int type);
}
