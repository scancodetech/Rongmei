package com.rongmei.bl.account;

import com.rongmei.blservice.account.UserBlService;
import com.rongmei.blservice.account.UserRelationBlService;
import com.rongmei.dao.account.UserRelationDao;
import com.rongmei.entity.account.UserRelation;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.parameters.user.UserRelationUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.user.UserInfoGetResponse;
import com.rongmei.response.user.UserRelationGetResponse;
import com.rongmei.response.user.UserRelationResponse;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRelationBlServiceImpl implements UserRelationBlService {

  private final UserRelationDao userRelationDao;
  private final UserBlService userBlService;

  @Autowired
  public UserRelationBlServiceImpl(UserRelationDao userRelationDao, UserBlService userBlService) {
    this.userRelationDao = userRelationDao;
    this.userBlService = userBlService;
  }

  @Override
  public UserRelationGetResponse getUserRelation(String username) {
    List<UserRelation> followsUserRelations = userRelationDao.findAllByFromUsername(username);
    List<UserRelation> fansUserRelations = userRelationDao.findAllByToUsername(username);
    List<UserInfoGetResponse> follows = new ArrayList<>();
    List<UserInfoGetResponse> fans = new ArrayList<>();
    for (UserRelation userRelation : followsUserRelations) {
      UserInfoGetResponse userInfoItem;
      try {
        userInfoItem = userBlService.getUserInfo(userRelation.getToUsername());
      } catch (UsernameDoesNotFoundException e) {
        e.printStackTrace();
        continue;
      }
      follows.add(userInfoItem);
    }
    for (UserRelation userRelation : fansUserRelations) {
      UserInfoGetResponse userInfoItem;
      try {
        userInfoItem = userBlService.getUserInfo(userRelation.getFromUsername());
      } catch (UsernameDoesNotFoundException e) {
        e.printStackTrace();
        continue;
      }
      fans.add(userInfoItem);
    }
    return new UserRelationGetResponse(fans, follows);
  }

  @Override
  public SuccessResponse updateUserRelation(UserRelationUpdateParameters parameters) {
    if (parameters.getFromUsername().length() == 0) {
      parameters.setFromUsername(UserInfoUtil.getUsername());
    }
    UserRelation userRelation = userRelationDao
        .findFirstByFromUsernameAndToUsernameAndRelationType(parameters.getFromUsername(),
            parameters.getToUsername(), parameters.getRelationType());
    if (userRelation == null) {
      userRelation = new UserRelation(parameters.getFromUsername(), parameters.getToUsername(),
          parameters.getRelationType(), System.currentTimeMillis());
      userRelationDao.save(userRelation);
      return new SuccessResponse("add success");
    } else {
      userRelationDao.deleteById(userRelation.getId());
      return new SuccessResponse("delete success");
    }
  }

  @Override
  public UserRelationResponse getUserRelationLike(String fromUsername, String toUsername,
      int type) {
    UserRelation userRelation = userRelationDao
        .findFirstByFromUsernameAndToUsernameAndRelationType(fromUsername, toUsername, type);
    return new UserRelationResponse(userRelation != null);
  }
}
