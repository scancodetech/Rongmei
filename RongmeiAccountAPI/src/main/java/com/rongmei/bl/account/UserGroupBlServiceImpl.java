package com.rongmei.bl.account;

import com.rongmei.blservice.account.UserGroupBlService;
import com.rongmei.dao.account.UserDao;
import com.rongmei.dao.account.UserGroupApplicationDao;
import com.rongmei.dao.account.UserGroupDao;
import com.rongmei.entity.account.User;
import com.rongmei.entity.account.UserGroup;
import com.rongmei.entity.account.UserGroupApplication;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.user.UserGroupApplicationApproveParameters;
import com.rongmei.parameters.user.UserGroupApplyParameters;
import com.rongmei.parameters.user.UserGroupUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.user.UserGroupApplicationGetResponse;
import com.rongmei.response.user.UserGroupApplicationItem;
import com.rongmei.response.user.UserGroupApplicationResponse;
import com.rongmei.response.user.UserGroupDetailResponse;
import com.rongmei.response.user.UserGroupGetResponse;
import com.rongmei.response.user.UserGroupItem;
import com.rongmei.response.user.UserItem;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupBlServiceImpl implements UserGroupBlService {

  private final UserGroupDao userGroupDao;
  private final UserDao userDao;
  private final UserGroupApplicationDao userGroupApplicationDao;

  @Autowired
  public UserGroupBlServiceImpl(UserGroupDao userGroupDao, UserDao userDao,
      UserGroupApplicationDao userGroupApplicationDao) {
    this.userGroupDao = userGroupDao;
    this.userDao = userDao;
    this.userGroupApplicationDao = userGroupApplicationDao;
  }

  @Override
  public UserGroupGetResponse getUserGroups() {
    List<UserGroup> userGroups = userGroupDao.findAll();
    List<UserGroupItem> userGroupItems = new ArrayList<>();
    for (UserGroup userGroup : userGroups) {
      userGroupItems.add(packUserGroupItem(userGroup));
    }
    return new UserGroupGetResponse(userGroupItems);
  }

  @Override
  public SuccessResponse deleteUserGroup(int userGroupId) {
    userGroupDao.deleteById(userGroupId);
    return new SuccessResponse("delete success");
  }

  @Override
  public UserGroupDetailResponse updateUserGroup(UserGroupUpdateParameters parameters)
      throws ThingIdDoesNotExistException {
    UserGroup userGroup;
    if (parameters.getId() == 0) {
      userGroup = new UserGroup(parameters.getTitle(), parameters.getUsernames(), 0,
          System.currentTimeMillis(), System.currentTimeMillis());
    } else {
      Optional<UserGroup> optionalUserGroup = userGroupDao.findById(parameters.getId());
      if (optionalUserGroup.isPresent()) {
        userGroup = optionalUserGroup.get();
        userGroup.setTitle(parameters.getTitle());
        userGroup.setUsernames(parameters.getUsernames());
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    userGroupDao.save(userGroup);
    return new UserGroupDetailResponse(packUserGroupItem(userGroup));
  }

  @Override
  public UserGroupDetailResponse getUserGroup(int userGroupId) throws ThingIdDoesNotExistException {
    Optional<UserGroup> optionalUserGroup = userGroupDao.findById(userGroupId);
    if (optionalUserGroup.isPresent()) {
      UserGroup userGroup = optionalUserGroup.get();
      return new UserGroupDetailResponse(packUserGroupItem(userGroup));
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public UserGroupDetailResponse updateUserGroupPrice(int userGroupId, long price)
      throws ThingIdDoesNotExistException {
    UserGroup userGroup;
    if (userGroupId == 0) {
      throw new ThingIdDoesNotExistException();
    } else {
      Optional<UserGroup> optionalUserGroup = userGroupDao.findById(userGroupId);
      if (optionalUserGroup.isPresent()) {
        userGroup = optionalUserGroup.get();
        userGroup.setLargeCoins(userGroup.getLargeCoins() + price);
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    userGroupDao.save(userGroup);
    return new UserGroupDetailResponse(packUserGroupItem(userGroup));
  }

  @Override
  public UserGroupGetResponse getUserGroupByUsername(String username) {
    List<UserGroup> userGroups = userGroupDao.findAll();
    List<UserGroupItem> userGroupItems = new ArrayList<>();
    for (UserGroup userGroup : userGroups) {
      if (userGroup.getUsernames().contains(username)) {
        userGroupItems.add(packUserGroupItem(userGroup));
      }
    }
    if (userGroupItems.size() == 0) {
      Optional<UserGroup> optionalUserGroup = userGroups.stream().findFirst();
      if (optionalUserGroup.isPresent()) {
        UserGroup userGroup = optionalUserGroup.get();
        List<String> usernames = userGroup.getUsernames();
        usernames.add(username);
        userGroup.setUsernames(usernames);
        userGroupDao.save(userGroup);
        userGroupItems.add(packUserGroupItem(userGroup));
      }
    }
    return new UserGroupGetResponse(userGroupItems);
  }

  @Override
  public SuccessResponse applyUserGroup(
      UserGroupApplyParameters parameters) throws ThingIdDoesNotExistException {
    String username = UserInfoUtil.getUsername();
    UserGroupApplication userGroupApplication;
    if (parameters.getId() == 0) {
      userGroupApplication = new UserGroupApplication(parameters.getTitle(),
          parameters.getSignature(), parameters.getAvatarUrl(), parameters.getOrgType(),
          parameters.getOrgName(), parameters.getOrgCertification(),
          parameters.getBusinessLicenseUrl(), parameters.getOfficialLetterUrl(),
          parameters.getOtherFileUrl(), parameters.getOperatorName(),
          parameters.getOperatorIdentityCard(), parameters.getOperatorPhone(),
          username, System.currentTimeMillis(), System.currentTimeMillis(),
          false, "");
      userGroupApplicationDao.save(userGroupApplication);
    } else {
      Optional<UserGroupApplication> optionalUserGroupApplication = userGroupApplicationDao
          .findById(parameters.getId());
      if (optionalUserGroupApplication.isPresent()) {
        userGroupApplication = optionalUserGroupApplication.get();
        if (!userGroupApplication.isApprove()) {
          userGroupApplication.setTitle(parameters.getTitle());
          userGroupApplication.setSignature(parameters.getSignature());
          userGroupApplication.setAvatarUrl(parameters.getAvatarUrl());
          userGroupApplication.setOrgType(parameters.getOrgType());
          userGroupApplication.setOrgName(parameters.getOrgName());
          userGroupApplication.setOrgCertification(parameters.getOrgCertification());
          userGroupApplication.setBusinessLicenseUrl(parameters.getBusinessLicenseUrl());
          userGroupApplication.setOfficialLetterUrl(parameters.getOfficialLetterUrl());
          userGroupApplication.setOtherFileUrl(parameters.getOtherFileUrl());
          userGroupApplication.setOperatorName(parameters.getOperatorName());
          userGroupApplication.setOperatorIdentityCard(parameters.getOperatorIdentityCard());
          userGroupApplication.setOperatorPhone(parameters.getOperatorPhone());
          userGroupApplication.setUpdateTime(System.currentTimeMillis());
          userGroupApplicationDao.save(userGroupApplication);
        }
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    return new SuccessResponse("apply success");
  }

  @Override
  public UserGroupApplicationResponse getUserGroupApplication()
      throws ThingIdDoesNotExistException {
    String username = UserInfoUtil.getUsername();
    UserGroupApplication userGroupApplication = userGroupApplicationDao
        .findFirstByUsername(username);
    if (userGroupApplication != null) {
      return new UserGroupApplicationResponse(userGroupApplication.getId(),
          userGroupApplication.getTitle(), userGroupApplication.getSignature(),
          userGroupApplication.getAvatarUrl(), userGroupApplication.getOrgType(),
          userGroupApplication.getOrgName(), userGroupApplication.getOrgCertification(),
          userGroupApplication.getBusinessLicenseUrl(), userGroupApplication.getOfficialLetterUrl(),
          userGroupApplication.getOtherFileUrl(), userGroupApplication.getOperatorName(),
          userGroupApplication.getOperatorIdentityCard(), userGroupApplication.getOperatorPhone(),
          userGroupApplication.getUsername(), userGroupApplication.getCreateTime(),
          userGroupApplication.getUpdateTime(),
          userGroupApplication.isApprove(), userGroupApplication.getApproveMsg());
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public SuccessResponse approveUserGroupApplication(
      UserGroupApplicationApproveParameters parameters) throws ThingIdDoesNotExistException {
    Optional<UserGroupApplication> optionalUserGroupApplication = userGroupApplicationDao
        .findById(parameters.getId());
    if (optionalUserGroupApplication.isPresent()) {
      UserGroupApplication userGroupApplication = optionalUserGroupApplication.get();
      userGroupApplication.setApprove(parameters.isApprove());
      userGroupApplication.setApproveMsg(parameters.getApproveMsg());
      userGroupApplicationDao.save(userGroupApplication);
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public UserGroupApplicationGetResponse getAllApproveUserGroupApplications() {
    List<UserGroupApplication> userGroupApplications = userGroupApplicationDao.findAll();
    return new UserGroupApplicationGetResponse(
        packUserGroupApplicationItems(userGroupApplications));
  }

  private List<UserGroupApplicationItem> packUserGroupApplicationItems(
      List<UserGroupApplication> userGroupApplications) {
    List<UserGroupApplicationItem> userGroupApplicationItems = new ArrayList<>();
    for (UserGroupApplication userGroupApplication : userGroupApplications) {
      userGroupApplicationItems.add(new UserGroupApplicationItem(userGroupApplication.getId(),
          userGroupApplication.getTitle(), userGroupApplication.getSignature(),
          userGroupApplication.getAvatarUrl(), userGroupApplication.getOrgType(),
          userGroupApplication.getOrgName(), userGroupApplication.getOrgCertification(),
          userGroupApplication.getBusinessLicenseUrl(), userGroupApplication.getOfficialLetterUrl(),
          userGroupApplication.getOtherFileUrl(), userGroupApplication.getOperatorName(),
          userGroupApplication.getOperatorIdentityCard(), userGroupApplication.getOperatorPhone(),
          userGroupApplication.getUsername(), userGroupApplication.getCreateTime(),
          userGroupApplication.getUpdateTime(), userGroupApplication.isApprove(),
          userGroupApplication.getApproveMsg()));
    }
    return userGroupApplicationItems;
  }


  private List<UserItem> getUserItems(List<String> usernames) {
    List<User> users = userDao.findAllByPhoneIn(usernames);
    List<UserItem> userItems = new ArrayList<>();
    for (User user : users) {
      userItems.add(packUserItem(user));
    }
    return userItems;
  }

  private UserItem packUserItem(User user) {
    return new UserItem(user.getId(), user.getPhone(), user.getPassword(), user.getRole());
  }

  private UserGroupItem packUserGroupItem(UserGroup userGroup) {
    return new UserGroupItem(userGroup.getId(), userGroup.getTitle(),
        getUserItems(userGroup.getUsernames()), userGroup.getLargeCoins(),
        userGroup.getCreateTime(),
        userGroup.getUpdateTime());
  }
}
