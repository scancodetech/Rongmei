package com.rongmei.bl.boxorder;

import com.rongmei.blservice.boxorder.BoxOrderBlService;
import com.rongmei.dao.boxorder.BoxOrderDao;
import com.rongmei.entity.boxorder.BoxOrder;
import com.rongmei.exception.ParametersErrorException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.boxorder.BoxOrderCommentParameters;
import com.rongmei.parameters.boxorder.BoxOrderRequestSubmitParameters;
import com.rongmei.parameters.boxorder.BoxOrderResultCoverUrlSubmitParameters;
import com.rongmei.parameters.boxorder.BoxOrderResultUrlSubmitParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.boxorder.BoxOrderDetailResponse;
import com.rongmei.response.boxorder.BoxOrderGetResponse;
import com.rongmei.response.boxorder.BoxOrderItem;
import com.rongmei.response.boxorder.BoxOrderShareGetResponse;
import com.rongmei.response.boxorder.BoxOrderShareItem;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoxOrderBlServiceImpl implements BoxOrderBlService {

  private final BoxOrderDao boxOrderDao;

  @Autowired
  public BoxOrderBlServiceImpl(BoxOrderDao boxOrderDao) {
    this.boxOrderDao = boxOrderDao;
  }

  /**
   * @param status 0:抢单 1:制作中 2:反馈 3:已完成
   * @param page
   * @param limit
   * @return
   */
  @Override
  public BoxOrderGetResponse queryBoxOrder(int status, int page, int limit) {
    List<BoxOrder> boxOrderList;
    long total;
    if (status == 0) {
      boxOrderList = boxOrderDao
          .findBoxOrderByStatusAndPageLimit(status, limit, (page - 1) * limit);
      total = boxOrderDao.countBoxOrderByStatus(status);
    } else {
      boxOrderList = boxOrderDao
          .findBoxOrderByStatusAndAcceptUsernameAndPageLimit(status, UserInfoUtil.getUsername(),
              limit,
              (page - 1) * limit);
      total = boxOrderDao
          .countBoxOrderByStatusAndAcceptUsername(status, UserInfoUtil.getUsername());
    }
    return new BoxOrderGetResponse(packBoxOrderItems(boxOrderList), total);
  }

  private List<BoxOrderItem> packBoxOrderItems(List<BoxOrder> boxOrderList) {
    List<BoxOrderItem> boxOrderItems = new ArrayList<>();
    for (BoxOrder boxOrder : boxOrderList) {
      boxOrderItems.add(


          new BoxOrderItem(boxOrder.getId(), boxOrder.getOrderType(), boxOrder.getCooperateType(),
              boxOrder.getCustomType(), boxOrder.getCustomStyle(), boxOrder.getWorldOutlook(),
              boxOrder.getCustomTheme(), boxOrder.getElementRequirements(), boxOrder.getStatus(),
              boxOrder.getExampleUrl(), boxOrder.getPrice(), boxOrder.getUsername(),
              boxOrder.getCreateTime(), boxOrder.getUpdateTime(), boxOrder.getAcceptTime(),
              boxOrder.getResultCoverUrl(), boxOrder.getResultUrl(), boxOrder.getCommentStatus(),
              boxOrder.getComment(), boxOrder.getFinishTime()));
    }
    return boxOrderItems;
  }

  @Override
  public SuccessResponse submitBoxOrderRequest(BoxOrderRequestSubmitParameters parameters) {
    BoxOrder boxOrder = new BoxOrder(parameters.getOrderType(), parameters.getCooperateType(),
        parameters.getCustomType(),
        parameters.getCustomStyle(),
        parameters.getCustomTheme(), parameters.getWorldOutlook(),
        parameters.getElementRequirements(), 0, 0,
        parameters.getExampleUrl(), parameters.getPrice(), UserInfoUtil.getUsername(),
        new ArrayList<>(), System.currentTimeMillis() + 1000 * 60 * 60 * 24,
        System.currentTimeMillis(), System.currentTimeMillis(), true, "", 0,
        "", "", 0, "", 0);
    boxOrderDao.save(boxOrder);
    return new SuccessResponse("submit success");
  }

  @Override
  public SuccessResponse grabBoxOrder(long orderId, String username)
      throws ThingIdDoesNotExistException {
    Optional<BoxOrder> optionalBoxOrder = boxOrderDao.findById((int) orderId);
    if (optionalBoxOrder.isPresent()) {
      BoxOrder boxOrder = optionalBoxOrder.get();
      boxOrder.setAcceptUsername(username);
      boxOrder.setAcceptTime(System.currentTimeMillis());
      boxOrder.setStatus(1);
      boxOrderDao.save(boxOrder);
      return new SuccessResponse("grad success");
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public SuccessResponse submitBoxOrderResultCover(
      BoxOrderResultCoverUrlSubmitParameters parameters) throws ThingIdDoesNotExistException {
    Optional<BoxOrder> optionalBoxOrder = boxOrderDao.findById((int) parameters.getId());
    if (optionalBoxOrder.isPresent()) {
      BoxOrder boxOrder = optionalBoxOrder.get();
      boxOrder.setResultCoverUrl(parameters.getCoverUrl());
      boxOrder.setStatus(2);
      boxOrderDao.save(boxOrder);
      return new SuccessResponse("submit success");
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public SuccessResponse submitBoxOrderResult(BoxOrderResultUrlSubmitParameters parameters)
      throws ThingIdDoesNotExistException {
    Optional<BoxOrder> optionalBoxOrder = boxOrderDao.findById((int) parameters.getId());
    if (optionalBoxOrder.isPresent()) {
      BoxOrder boxOrder = optionalBoxOrder.get();
      boxOrder.setResultUrl(parameters.getResultUrl());
      boxOrder.setFinishTime(System.currentTimeMillis());
      boxOrder.setStatus(3);
      boxOrderDao.save(boxOrder);
      return new SuccessResponse("comment success");
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public SuccessResponse commentBoxOrder(BoxOrderCommentParameters parameters)
      throws ThingIdDoesNotExistException {
    Optional<BoxOrder> optionalBoxOrder = boxOrderDao.findById((int) parameters.getId());
    if (optionalBoxOrder.isPresent()) {
      BoxOrder boxOrder = optionalBoxOrder.get();
      boxOrder.setCommentStatus(parameters.getCommentStatus());
      boxOrder.setComment(parameters.getComment());
      boxOrderDao.save(boxOrder);
      return new SuccessResponse("comment success");
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public BoxOrderGetResponse queryUserBoxOrderRequest(int status, String username, int page,
      int limit) {
    List<BoxOrder> boxOrderList;
    long total;
    if (status == 0) {
      boxOrderList = boxOrderDao
          .findBoxByPayStatusOrderByUsernameAndPageLimit(username, limit, (page - 1) * limit, 0);
      total = boxOrderDao.countBoxByPayStatusOrderByUsername(username, 0);
    } else if (status == 1) {
      boxOrderList = boxOrderDao
          .findBoxByPayStatusOrderByUsernameAndPageLimit(username, limit, (page - 1) * limit, 1);
      total = boxOrderDao.countBoxByPayStatusOrderByUsername(username, 1);
    } else if (status == 2) {
      boxOrderList = boxOrderDao
          .findBoxByPayStatusAndStatusOrderByUsernameAndPageLimit(username, limit,
              (page - 1) * limit, 2, 0);
      total = boxOrderDao.countBoxByPayStatusAndStatusOrderByUsername(username, 2, 0);
    } else if (status == 3) {
      boxOrderList = boxOrderDao
          .findBoxByPayStatusAndStatusOrderByUsernameAndPageLimit(username, limit,
              (page - 1) * limit, 2, 2);
      total = boxOrderDao.countBoxByPayStatusAndStatusOrderByUsername(username, 2, 2);
    } else if (status == 4) {
      boxOrderList = boxOrderDao
          .findBoxByPayStatusAndStatusOrderByUsernameAndPageLimit(username, limit,
              (page - 1) * limit, 2, 3);
      total = boxOrderDao.countBoxByPayStatusAndStatusOrderByUsername(username, 2, 3);
    } else {
      boxOrderList = boxOrderDao
          .findBoxOrderByUsernameAndPageLimit(username, limit, (page - 1) * limit);
      total = boxOrderDao.countBoxOrderByUsernameAndPageLimit(username);
    }
    return new BoxOrderGetResponse(packBoxOrderItems(boxOrderList), total);
  }

  @Override
  public SuccessResponse shareBoxOrderWithUser(int orderId, String username)
      throws ThingIdDoesNotExistException, ParametersErrorException {
    Optional<BoxOrder> optionalBoxOrder = boxOrderDao.findById(orderId);
    if (optionalBoxOrder.isPresent()) {
      BoxOrder boxOrder = optionalBoxOrder.get();
      List<String> shareUsernames = boxOrder.getShareUsernames();
      if (!boxOrder.getUsername().equals(username) && !shareUsernames.contains(username)) {
        shareUsernames.add(username);
        boxOrder.setShareUsernames(shareUsernames);
        boxOrderDao.save(boxOrder);
        return new SuccessResponse("share success");
      } else {
        throw new ParametersErrorException();
      }
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public BoxOrderShareGetResponse getSharingBoxOrderWithoutUser(String username) {

    List<BoxOrder> boxOrders = boxOrderDao.findAllByUsernameIsNotAndShareEndTimeAfter(username, System.currentTimeMillis());
    return new BoxOrderShareGetResponse(packBoxOrderShareItems(boxOrders));
  }

  @Override
  public BoxOrderDetailResponse getBoxOrder(int orderId) throws ThingIdDoesNotExistException {
    Optional<BoxOrder> optionalBoxOrder = boxOrderDao.findById(orderId);
    if (optionalBoxOrder.isPresent()) {
      BoxOrder boxOrder = optionalBoxOrder.get();
      return new BoxOrderDetailResponse(boxOrder.getId(), boxOrder.getOrderType(),
          boxOrder.getCooperateType(),
          boxOrder.getCustomType(), boxOrder.getCustomStyle(), boxOrder.getWorldOutlook(),
          boxOrder.getCustomTheme(),
          boxOrder.getElementRequirements(), boxOrder.getPayStatus(), boxOrder.getStatus(),
          boxOrder.getExampleUrl(), boxOrder.getPrice(), boxOrder.getUsername(),
          boxOrder.getShareUsernames(), boxOrder.getShareEndTime(), boxOrder.getCreateTime(),
          boxOrder.getUpdateTime(), boxOrder.getAcceptUsername(), boxOrder.getAcceptTime(),
          boxOrder.getResultCoverUrl(), boxOrder.getResultUrl(), boxOrder.getCommentStatus(),
          boxOrder.getComment(), boxOrder.getFinishTime());
    }
    throw new ThingIdDoesNotExistException();
  }

  private List<BoxOrderShareItem> packBoxOrderShareItems(List<BoxOrder> boxOrders) {
    List<BoxOrderShareItem> boxOrderShareItems = new ArrayList<>();
    for (BoxOrder boxOrder : boxOrders) {

      if (boxOrder.getShareUsernames().size() == 0) {
        List<String> shareUsernames = new ArrayList<>();
        shareUsernames.add(boxOrder.getUsername());
        boxOrderShareItems.add(new BoxOrderShareItem(boxOrder.getId(), boxOrder.getExampleUrl(),
            shareUsernames, boxOrder.getPrice(), boxOrder.getCooperateType(),
            boxOrder.getShareEndTime()));
      }
    }
    return boxOrderShareItems;
  }
}
