package com.rongmei.bl.blindbox;

import com.rongmei.blservice.blindbox.BlindBoxBlService;
import com.rongmei.dao.blindbox.BlindBoxDao;
import com.rongmei.dao.blindbox.BlindBoxOrderDao;
import com.rongmei.entity.blindbox.BlindBox;
import com.rongmei.entity.blindbox.BlindBoxOrder;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.blindbox.BlindBoxUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.blindbox.BlindBoxDetailResponse;
import com.rongmei.response.blindbox.BlindBoxGetResponse;
import com.rongmei.response.blindbox.BlindBoxItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlindBoxBlServiceImpl implements BlindBoxBlService {

  private final BlindBoxDao blindBoxDao;
  private final BlindBoxOrderDao blindBoxOrderDao;
  private final List<String> LOCK_STATUS_LIST = new ArrayList<>();
  private final List<String> UNLOCK_STATUS_LIST = new ArrayList<>();

  @Autowired
  public BlindBoxBlServiceImpl(BlindBoxDao blindBoxDao,
      BlindBoxOrderDao blindBoxOrderDao) {
    this.blindBoxDao = blindBoxDao;
    this.blindBoxOrderDao = blindBoxOrderDao;
    LOCK_STATUS_LIST.add("已完成");
    LOCK_STATUS_LIST.add("未完成");
    UNLOCK_STATUS_LIST.add("已取消");
    UNLOCK_STATUS_LIST.add("已退款");
  }

  @Override
  public SuccessResponse updateBlindBox(BlindBoxUpdateParameters parameters)
      throws ThingIdDoesNotExistException {
    if (parameters.getId() == 0) {
      BlindBox blindBox = new BlindBox(parameters.getCoverUrl(), parameters.getName(),
          parameters.getPrice(), parameters.getTheme(), parameters.getClassification(),
          parameters.getTotalCount(), System.currentTimeMillis(), System.currentTimeMillis());
      blindBoxDao.save(blindBox);
      return new SuccessResponse("add success");
    } else {
      Optional<BlindBox> optionalBlindBox = blindBoxDao.findById(parameters.getId());
      if (optionalBlindBox.isPresent()) {
        BlindBox blindBox = optionalBlindBox.get();
        blindBox.setCoverUrl(blindBox.getCoverUrl());
        blindBox.setName(blindBox.getName());
        blindBox.setPrice(blindBox.getPrice());
        blindBox.setTheme(blindBox.getTheme());
        blindBox.setClassification(blindBox.getClassification());
        blindBox.setTotalCount(blindBox.getTotalCount());
        blindBox.setUpdateTime(System.currentTimeMillis());
        blindBoxDao.save(blindBox);
      } else {
        throw new ThingIdDoesNotExistException();
      }
      return new SuccessResponse("update success");
    }
  }

  @Override
  public BlindBoxDetailResponse getBlindBoxDetail(long id) throws ThingIdDoesNotExistException {
    Optional<BlindBox> optionalBlindBox = blindBoxDao.findById(id);
    if (optionalBlindBox.isPresent()) {
      BlindBox blindBox = optionalBlindBox.get();
      List<BlindBoxOrder> blindBoxOrders = blindBoxOrderDao
          .findAllByBlindBoxIdAndStatusIn(blindBox.getId(), LOCK_STATUS_LIST);
      return new BlindBoxDetailResponse(blindBox.getId(), blindBox.getCoverUrl(),
          blindBox.getName(), blindBox.getPrice(), blindBox.getTheme(),
          blindBox.getClassification(), blindBox.getTotalCount(), blindBoxOrders.size(),
          blindBox.getCreateTime(), blindBox.getUpdateTime());
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public BlindBoxGetResponse getBlindBoxList(String theme, String classification) {
    List<BlindBox> blindBoxes = blindBoxDao.findAllByThemeAndClassification(theme, classification);
    return new BlindBoxGetResponse(packBlindBoxItems(blindBoxes));
  }

  @Override
  public BlindBoxGetResponse getRecommendBlindBoxList(String username) {
    return null;
  }

  private List<BlindBoxItem> packBlindBoxItems(List<BlindBox> blindBoxes) {
    List<BlindBoxItem> blindBoxItemList = new ArrayList<>();
    for (BlindBox blindBox : blindBoxes) {
      blindBoxItemList.add(
          new BlindBoxItem(blindBox.getId(), blindBox.getCoverUrl(), blindBox.getName(),
              blindBox.getPrice(), blindBox.getTheme(), blindBox.getClassification(),
              blindBox.getTotalCount(), blindBox.getCreateTime(), blindBox.getUpdateTime()));
    }
    return blindBoxItemList;
  }
}
