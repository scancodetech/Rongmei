package com.rongmei.bl.usergroup;

import com.rongmei.blservice.usergroup.UserGroupBlService;
import com.rongmei.dao.usergroup.UserGroupInfoDao;
import com.rongmei.entity.usergroup.UserGroupInfo;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.usergroup.UserGroupUpdateParameters;
import com.rongmei.response.usergroup.UserGroup;
import com.rongmei.response.usergroup.UserGroupDetailResponse;
import com.rongmei.response.usergroup.UserGroupGetResponse;
import com.rongmei.response.usergroup.UserGroupInfoItem;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupBlServiceImpl implements UserGroupBlService {

  private final UserGroupInfoDao userGroupInfoDao;

  @Autowired
  public UserGroupBlServiceImpl(UserGroupInfoDao userGroupInfoDao) {
    this.userGroupInfoDao = userGroupInfoDao;
  }

  @Override
  public UserGroupGetResponse getUserGroups(String firstType, String secondType) {
    List<UserGroupInfo> userGroupInfoList = userGroupInfoDao
        .findAllByFirstTypeAndSecondType(firstType, secondType);
    List<UserGroupInfoItem> userGroupInfoItems = new ArrayList<>();
    for (UserGroupInfo userGroupInfo : userGroupInfoList) {
      UserGroupInfoItem userGroupInfoItem = new UserGroupInfoItem(userGroupInfo.getUserGroupId(),
          UserInfoUtil.getUserGroup(userGroupInfo.getUserGroupId()).getTitle(),
          userGroupInfo.getAvatarUrl(), userGroupInfo.getLargePrice());
      userGroupInfoItems.add(userGroupInfoItem);
    }
    return new UserGroupGetResponse(userGroupInfoItems);
  }

  @Override
  public UserGroupDetailResponse getUserGroup(int userGroupId) throws ThingIdDoesNotExistException {
    UserGroupInfo userGroupInfo = userGroupInfoDao.findFirstByUserGroupId(userGroupId);
    UserGroup userGroup = UserInfoUtil.getUserGroup(userGroupId);
    if (userGroup == null) {
      throw new ThingIdDoesNotExistException();
    }
    if (userGroupInfo == null) {
      userGroupInfo = new UserGroupInfo(userGroupId, 0, "", 0, 0, new ArrayList<>(), "", "", "");
    }
    return new UserGroupDetailResponse(userGroupInfo.getId(), userGroup.getTitle(),
        userGroup.getLargeCoins(),
        userGroupInfo.getLargePrice(), userGroupId, userGroupInfo.getAvatarUrl(),
        userGroupInfo.getMinBuyNum(), userGroupInfo.getMaxBuyNum(), userGroupInfo.getTags(),
        userGroupInfo.getDescription(), userGroupInfo.getFirstType(),
        userGroupInfo.getSecondType());
  }

  @Override
  public UserGroupDetailResponse updateUserGroup(UserGroupUpdateParameters parameters)
      throws ThingIdDoesNotExistException {
    UserGroupInfo userGroupInfo;
    if (parameters.getId() == 0) {
      userGroupInfo = userGroupInfoDao.findFirstByUserGroupId(parameters.getUserGroupId());
      if (userGroupInfo == null) {
        userGroupInfo = new UserGroupInfo(parameters.getUserGroupId(), parameters.getLargePrice(),
            parameters.getAvatarUrl(), parameters.getMinBuyNum(), parameters.getMaxBuyNum(),
            parameters.getTags(), parameters.getDescription(), parameters.getFirstType(),
            parameters.getSecondType());
      } else {
        userGroupInfo.setLargePrice(parameters.getLargePrice());
        userGroupInfo.setAvatarUrl(parameters.getAvatarUrl());
        userGroupInfo.setMinBuyNum(parameters.getMinBuyNum());
        userGroupInfo.setMaxBuyNum(parameters.getMaxBuyNum());
        userGroupInfo.setTags(parameters.getTags());
        userGroupInfo.setDescription(parameters.getDescription());
        userGroupInfo.setFirstType(parameters.getFirstType());
        userGroupInfo.setSecondType(parameters.getSecondType());
      }
    } else {
      Optional<UserGroupInfo> optionalUserGroupInfo = userGroupInfoDao.findById(parameters.getId());
      if (optionalUserGroupInfo.isPresent()) {
        userGroupInfo = optionalUserGroupInfo.get();
        userGroupInfo.setLargePrice(parameters.getLargePrice());
        userGroupInfo.setAvatarUrl(parameters.getAvatarUrl());
        userGroupInfo.setMinBuyNum(parameters.getMinBuyNum());
        userGroupInfo.setMaxBuyNum(parameters.getMaxBuyNum());
        userGroupInfo.setTags(parameters.getTags());
        userGroupInfo.setDescription(parameters.getDescription());
        userGroupInfo.setFirstType(parameters.getFirstType());
        userGroupInfo.setSecondType(parameters.getSecondType());
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    userGroupInfoDao.save(userGroupInfo);
    UserGroup userGroup = UserInfoUtil.getUserGroup(parameters.getUserGroupId());
    return new UserGroupDetailResponse(userGroupInfo.getId(), userGroup.getTitle(),
        userGroup.getLargeCoins(),
        userGroupInfo.getLargePrice(), userGroupInfo.getId(), userGroupInfo.getAvatarUrl(),
        userGroupInfo.getMinBuyNum(), userGroupInfo.getMaxBuyNum(), userGroupInfo.getTags(),
        userGroupInfo.getDescription(), userGroupInfo.getFirstType(),
        userGroupInfo.getSecondType());
  }
}
