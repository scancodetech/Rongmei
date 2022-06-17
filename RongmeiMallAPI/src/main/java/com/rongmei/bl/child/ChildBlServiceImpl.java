package com.rongmei.bl.child;

import com.rongmei.blservice.child.ChildBlService;
import com.rongmei.dao.child.ChildDao;
import com.rongmei.entity.child.Child;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.child.ChildUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.child.ChildDetailResponse;
import com.rongmei.response.child.ChildGetResponse;
import com.rongmei.response.child.ChildItem;
import com.rongmei.response.child.TopicsGetResponse;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildBlServiceImpl implements ChildBlService {

  private final ChildDao childDao;

  @Autowired
  public ChildBlServiceImpl(ChildDao childDao) {
    this.childDao = childDao;
  }

  @Override
  public SuccessResponse updateChild(ChildUpdateParameters parameters)
      throws ThingIdDoesNotExistException {
    if (parameters.getId() == 0) {
      Child child = new Child(parameters.getGroupName(), parameters.getChildPicList(),
          parameters.getDescription(),
          UserInfoUtil.getUsername(), parameters.getSecretName(), parameters.getPrice(),
          System.currentTimeMillis(), System.currentTimeMillis(), true);
      childDao.save(child);
      return new SuccessResponse("add success");
    } else {
      Optional<Child> optionalChild = childDao.findById(parameters.getId());
      if (optionalChild.isPresent()) {
        Child child = optionalChild.get();
        child.setChildPicList(parameters.getChildPicList());
        child.setDescription(parameters.getDescription());
        child.setPrice(parameters.getPrice());
        child.setGroupName(parameters.getGroupName());
        child.setSecretName(parameters.getSecretName());
        child.setUpdateTime(System.currentTimeMillis());
        childDao.save(child);
        return new SuccessResponse("update success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
  }

  @Override
  public ChildDetailResponse getChildDetail(int id) throws ThingIdDoesNotExistException {
    Optional<Child> optionalChild = childDao.findById(id);
    if (optionalChild.isPresent()) {
      Child child = optionalChild.get();
      return new ChildDetailResponse(child.getId(), child.getGroupName(), child.getChildPicList(),
          child.getDescription(), child.getUsername(), child.getSecretName(), child.getPrice(),
          child.getCreateTime(), child.getUpdateTime());
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public ChildGetResponse getChildren(int page, int limit) {
    List<Child> childList = childDao.findAllByLimitAndOffset(limit, (page - 1) * limit);
    return new ChildGetResponse(packChildItemList(childList));
  }

  @Override
  public ChildGetResponse getUserChild(int page, int limit, String username) {
    List<Child> childList = childDao
        .findAllByUsernameAndLimitAndOffset(limit, (page - 1) * limit, username);
    return new ChildGetResponse(packChildItemList(childList));
  }

  @Override
  public SuccessResponse deleteChild(int id) throws ThingIdDoesNotExistException {
    Optional<Child> optionalChild = childDao.findById(id);
    if (optionalChild.isPresent()) {
      Child child = optionalChild.get();
      child.setActive(false);
      child.setUpdateTime(System.currentTimeMillis());
      childDao.save(child);
      return new SuccessResponse("delete success");
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  private List<ChildItem> packChildItemList(List<Child> childList) {
    List<ChildItem> childItems = new ArrayList<>();
    for (Child child : childList) {
      childItems.add(new ChildItem(child.getId(), child.getGroupName(), child.getChildPicList(),
          child.getDescription(), child.getUsername(), child.getSecretName(), child.getPrice(),
          child.getCreateTime(), child.getUpdateTime()));
    }
    return childItems;
  }
}
