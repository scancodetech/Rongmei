package com.rongmei.bl.groupshopping;

import com.rongmei.blservice.groupshopping.GroupShoppingBlService;
import com.rongmei.dao.groupshopping.GroupShoppingDao;
import com.rongmei.dao.groupshopping.GroupShoppingParticipateDao;
import com.rongmei.entity.groupshopping.GroupShopping;
import com.rongmei.entity.groupshopping.GroupShoppingParticipate;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.groupshopping.GroupShoppingUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.groupshopping.GroupShoppingDetailResponse;
import com.rongmei.response.groupshopping.GroupShoppingGetResponse;
import com.rongmei.response.groupshopping.GroupShoppingItem;
import com.rongmei.response.user.UserInfo;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupShoppingBlServiceImpl implements GroupShoppingBlService {

  private final GroupShoppingDao groupShoppingDao;
  private final GroupShoppingParticipateDao groupShoppingParticipateDao;

  @Autowired
  public GroupShoppingBlServiceImpl(GroupShoppingDao groupShoppingDao,
      GroupShoppingParticipateDao groupShoppingParticipateDao) {
    this.groupShoppingDao = groupShoppingDao;
    this.groupShoppingParticipateDao = groupShoppingParticipateDao;
  }

  @Override
  public GroupShoppingGetResponse getGroupShoppings(int page, int limit, int draftStatus) {
    List<GroupShopping> groupShoppings = groupShoppingDao
        .findAllByLimitAndOffsetAndDraftStatusOrderByEndTimeDesc(limit, (page - 1) * limit,
            draftStatus);
    long count = groupShoppingDao.countAllByDraftStatusOrderByEndTimeDesc(draftStatus);
    return new GroupShoppingGetResponse(packGroupShoppingItems(groupShoppings), count);
  }

  public static List<GroupShoppingItem> packGroupShoppingItems(List<GroupShopping> groupShoppings) {
    List<GroupShoppingItem> groupShoppingItemList = new ArrayList<>();
    for (GroupShopping groupShopping : groupShoppings) {
      UserInfo userInfo = UserInfoUtil.getUserInfo(groupShopping.getAuthor());
      groupShoppingItemList.add(
          new GroupShoppingItem(groupShopping.getId(), groupShopping.getTitle(),
              groupShopping.getPrice(), groupShopping.getCoverUrl(), userInfo.getAvatarUrl(),
              groupShopping.getTags(),
              groupShopping.getInformation(), groupShopping.getDescription(),
              groupShopping.getComment(), groupShopping.getAuthor(), groupShopping.getStartTime(),
              groupShopping.getEndTime(),
              groupShopping.getCreateTime(), groupShopping.getUpdateTime()));
    }
    return groupShoppingItemList;
  }

  @Override
  public GroupShoppingGetResponse getUserGroupShoppings(int page, int limit, String username,
      int status) {
    List<GroupShopping> groupShoppings;
    long count;
    if (status == 0) {
      groupShoppings = groupShoppingDao
          .findAllByLimitAndOffsetAndEndTimeAfterAndUsernameOrderByEndTimeDesc(
              System.currentTimeMillis(), username, limit, (page - 1) * limit);
      count = groupShoppingDao
          .countAllByEndTimeAfterAndUsernameOrderByEndTimeDesc(System.currentTimeMillis(),
              username);
    } else {
      groupShoppings = groupShoppingDao
          .findAllByLimitAndOffsetAndEndTimeBeforeAndUsernameOrderByEndTimeDesc(
              System.currentTimeMillis(), username, limit, (page - 1) * limit);
      count = groupShoppingDao.countAllByEndTimeBeforeAndUsernameOrderByEndTimeDesc(
          System.currentTimeMillis(), username);
    }
    return new GroupShoppingGetResponse(packGroupShoppingItems(groupShoppings), count);
  }

  @Override
  public GroupShoppingDetailResponse getGroupShopping(int id) throws ThingIdDoesNotExistException {
    Optional<GroupShopping> optionalGroupShopping = groupShoppingDao.findById(id);
    if (optionalGroupShopping.isPresent()) {
      GroupShopping groupShopping = optionalGroupShopping.get();
      long count = groupShoppingParticipateDao.countAllByRelationId(id);
      return new GroupShoppingDetailResponse(groupShopping.getId(), groupShopping.getTitle(),
          groupShopping.getPrice(), groupShopping.getCoverUrl(), groupShopping.getTags(),
          groupShopping.getInformation(), groupShopping.getDescription(),
          groupShopping.getComment(), groupShopping.getStartTime(), groupShopping.getEndTime(),
          groupShopping.getCreateTime(), groupShopping.getUpdateTime(), count,
          groupShopping.getAuthor());
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public SuccessResponse deleteGroupShopping(int id) throws ThingIdDoesNotExistException {
    Optional<GroupShopping> optionalGroupShopping = groupShoppingDao.findById(id);
    if (optionalGroupShopping.isPresent()) {
      GroupShopping groupShopping = optionalGroupShopping.get();
      groupShopping.setActive(false);
      return new SuccessResponse("delete success");
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public SuccessResponse participateGroupShopping(int id, String username) {
    GroupShoppingParticipate groupShoppingParticipate = new GroupShoppingParticipate(id, username,
        System.currentTimeMillis(), System.currentTimeMillis());
    groupShoppingParticipateDao.save(groupShoppingParticipate);
    return new SuccessResponse("participate success");
  }

  @Override
  public GroupShoppingGetResponse getRecommendGroupShoppings(int page, int limit) {
    List<GroupShopping> groupShoppings = groupShoppingDao
        .findAllByLimitAndOffsetOrderByEndTimeDesc(limit, (page - 1) * limit);
    long count = groupShoppingDao.countAllWithActiveAndDraftStatus();
    return new GroupShoppingGetResponse(packGroupShoppingItems(groupShoppings), count);
  }

  @Override
  public SuccessResponse updateGroupShopping(GroupShoppingUpdateParameters parameters) {
    if (parameters.getId() == 0) {
      GroupShopping groupShopping = new GroupShopping(parameters.getTitle(), parameters.getPrice(),
          parameters.getCoverUrl(), parameters.getTags(), parameters.getContentUrl(),
          parameters.getInformation(), parameters.getDescription(), parameters.getComment(),
          parameters.getStartTime(), parameters.getEndTime(), System.currentTimeMillis(),
          System.currentTimeMillis(), true, 0, UserInfoUtil.getUsername());
      groupShoppingDao.save(groupShopping);
      return new SuccessResponse("add success");
    } else {
      Optional<GroupShopping> optionalGroupShopping = groupShoppingDao.findById(parameters.getId());
      if (optionalGroupShopping.isPresent()) {
        GroupShopping groupShopping = optionalGroupShopping.get();
        groupShopping.setTitle(parameters.getTitle());
        groupShopping.setPrice(parameters.getPrice());
        groupShopping.setCoverUrl(parameters.getCoverUrl());
        groupShopping.setTags(parameters.getTags());
        groupShopping.setContentUrl(parameters.getContentUrl());
        groupShopping.setInformation(parameters.getInformation());
        groupShopping.setDescription(parameters.getComment());
        groupShopping.setStartTime(parameters.getStartTime());
        groupShopping.setEndTime(parameters.getEndTime());
        groupShopping.setUpdateTime(System.currentTimeMillis());
        groupShoppingDao.save(groupShopping);
      }
      return new SuccessResponse("update success");
    }
  }

  @Override
  public GroupShoppingGetResponse getAuthorGroupShoppings(String username, int status) {
    List<GroupShopping> groupShoppings;
    long count;
    if (status == -1) {
      groupShoppings = groupShoppingDao
          .findAllByAuthorAndIsActive(username, true);
      count = groupShoppingDao.countAllByAuthorAndIsActive(username, true);
    } else {
      groupShoppings = groupShoppingDao
          .findAllByAuthorAndDraftStatusAndIsActive(username, status, true);
      count = groupShoppingDao.countAllByAuthorAndDraftStatusAndIsActive(username, status, true);
    }
    return new GroupShoppingGetResponse(packGroupShoppingItems(groupShoppings), count);
  }

  @Override
  public GroupShoppingGetResponse getUserParticipateGroupShoppings(String username) {
    List<GroupShopping> groupShoppings = groupShoppingDao
        .findAllByUserGroupShoppingParticipate(username);
    List<GroupShopping> groupShoppingList = groupShoppingDao
        .findAllByAuthorAndDraftStatusAndIsActive(username, 2, true);
    groupShoppings.addAll(groupShoppingList);
    return new GroupShoppingGetResponse(packGroupShoppingItems(groupShoppings),
        groupShoppings.size());
  }
}
