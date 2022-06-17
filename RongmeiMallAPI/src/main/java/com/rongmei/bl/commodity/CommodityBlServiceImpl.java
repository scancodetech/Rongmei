package com.rongmei.bl.commodity;

import com.google.common.collect.Lists;
import com.rongmei.blservice.commodity.CommodityBlService;
import com.rongmei.dao.commodity.CommodityDao;
import com.rongmei.dao.order.OrderDao;
import com.rongmei.dao.relation.CommodityLikeDao;
import com.rongmei.dao.usergroup.UserGroupInfoDao;
import com.rongmei.entity.commodity.Commodity;
import com.rongmei.entity.order.Order;
import com.rongmei.entity.relation.CommodityLike;
import com.rongmei.entity.usergroup.UserGroupInfo;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.commodity.CommodityQueryParameters;
import com.rongmei.parameters.commodity.CommodityUpdateParameters;
import com.rongmei.parameters.commodity.CommodityUpdateParamters2;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.commodity.CommodityAuthorDetailResponse;
import com.rongmei.response.commodity.CommodityDetailResponse;
import com.rongmei.response.commodity.CommodityGetResponse;
import com.rongmei.response.commodity.CommodityItem;
import com.rongmei.response.commodity.CommoditySaleStatisticsResponse;
import com.rongmei.response.user.UserItem;
import com.rongmei.response.usergroup.UserGroup;
import com.rongmei.util.UserInfoUtil;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommodityBlServiceImpl implements CommodityBlService {

  private final CommodityDao commodityDao;
  private final UserGroupInfoDao userGroupInfoDao;
  private final OrderDao orderDao;
  private final CommodityLikeDao commodityLikeDao;

  @Autowired
  public CommodityBlServiceImpl(CommodityDao commodityDao,
      UserGroupInfoDao userGroupInfoDao, OrderDao orderDao,
      CommodityLikeDao commodityLikeDao) {
    this.commodityDao = commodityDao;
    this.userGroupInfoDao = userGroupInfoDao;
    this.orderDao = orderDao;
    this.commodityLikeDao = commodityLikeDao;
  }

  @Override
  public CommodityDetailResponse updateCommodity(CommodityUpdateParameters parameters)
      throws ThingIdDoesNotExistException {
    Commodity commodity;
    if (parameters.getId() != 0) {
      Optional<Commodity> optionalCommodity = commodityDao.findById(parameters.getId());
      if (optionalCommodity.isPresent()) {
        commodity = optionalCommodity.get();
        commodity.setLargePrice(parameters.getLargePrice());
        commodity.setContentUrl(parameters.getContentUrl());
        commodity.setCoverUrl(parameters.getCoverUrl());
        commodity.setDescription(parameters.getDescription());
        commodity.setExtra(parameters.getExtra());
        commodity.setSigningInfo(parameters.getSigningInfo());
        commodity.setTags(parameters.getTags());
        commodity.setTitle(parameters.getTitle());
        commodity.setDownloadUrl(parameters.getDownloadUrl());
        commodity.setUpdateTime(System.currentTimeMillis());
        commodity.setExclusive(parameters.isExclusive());
        commodity.setAuthor(parameters.getAuthor());
        if (commodity.getDraftStatus() == 1) {
          commodity.setDraftStatus(0);
        }
      } else {
        throw new ThingIdDoesNotExistException();
      }
    } else {
      commodity = new Commodity(parameters.getTitle(), parameters.getLargePrice(),
          parameters.getCoverUrl(),
          parameters.getTags(), parameters.getContentUrl(), parameters.getDescription(),
          parameters.getSigningInfo(), parameters.getExtra(), parameters.getCreatorUserGroupId(),
          parameters.getDownloadUrl(),
          System.currentTimeMillis(),
          System.currentTimeMillis(), parameters.isExclusive(), parameters.getAuthor(), 0);
    }
    commodityDao.save(commodity);
    return packCommodity(commodity);
  }


  private Commodity createCommodity(CommodityUpdateParameters parameters){
    return new Commodity(parameters.getTitle(),  parameters.getLargePrice(),
              parameters.getCoverUrl(),
              parameters.getTags(), parameters.getContentUrl(), parameters.getDescription(),
              parameters.getSigningInfo(), parameters.getExtra(), parameters.getCreatorUserGroupId(),
              parameters.getDownloadUrl(),
              System.currentTimeMillis(),
              System.currentTimeMillis(), parameters.isExclusive(), parameters.getAuthor(), 0);
  }

  @Override
  public SuccessResponse deleteCommodity(int commodityId) {
    commodityDao.deleteById(commodityId);
    return new SuccessResponse("delete success");
  }

  @Override
  public CommodityGetResponse getCommodities(CommodityQueryParameters parameters) {
    List<Commodity> commodities = parameters.getTags().size() > 0 ? commodityDao
        .findCommoditiesByTitleLikeAndTagsInOrderByCreateTimeDesc("%" + parameters.getKey() + "%",
            parameters.getTags(), 2, parameters.getLimit(), parameters.getOffset())
        : commodityDao
            .findCommoditiesByTitleLikeOrderByCreateTimeDesc("%" + parameters.getKey() + "%", 2,
                parameters.getLimit(), parameters.getOffset());
    List<CommodityItem> commodityItems = new ArrayList<>();
    for (int i = parameters.getOffset(); i < parameters.getLimit() + parameters.getOffset(); i++) {
      if (i < commodities.size()) {
        Commodity commodity = commodities.get(i);
        commodityItems.add(
            new CommodityItem(commodity.getId(), commodity.getTitle(), commodity.getCoverUrl(),
                commodity.getLargePrice(),
                commodity.getTags(), commodity.getCreateTime(), commodity.getUpdateTime(),
                commodity.isExclusive(), commodity.getAuthor(), commodity.getDescription()));
      } else {
        break;
      }
    }
    return new CommodityGetResponse(commodityItems, commodities.size());
  }

  @Override
  public CommodityDetailResponse getCommodity(int commodityId) throws ThingIdDoesNotExistException {
    Optional<Commodity> optionalCommodity = commodityDao.findById(commodityId);
    if (optionalCommodity.isPresent()) {
      Commodity commodity = optionalCommodity.get();
      return packCommodity(commodity);
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public CommodityGetResponse getUserGroupCommodities(int userGroupId) {
    List<Commodity> commodities = commodityDao.findCommoditiesByCreatorUserGroupId(userGroupId);
    return new CommodityGetResponse(packCommodities(commodities), commodities.size());
  }

  @Override
  public CommodityGetResponse getRecommendCommodities(int count) {
    List<Commodity> commodities = commodityDao.findByLimit(count);
    return new CommodityGetResponse(packCommodities(commodities), commodities.size());
  }

  @Override
  public CommodityAuthorDetailResponse getCommodityAuthor(int commodityId)
      throws ThingIdDoesNotExistException {
    Optional<Commodity> optionalCommodity = commodityDao.findById(commodityId);
    if (optionalCommodity.isPresent()) {
      Commodity commodity = optionalCommodity.get();
      UserGroup userGroup = UserInfoUtil.getUserGroup(commodity.getCreatorUserGroupId());
      Set<String> usernames = new HashSet<>();
      for (UserItem userGroupInfo : userGroup.getUserItems()) {
        usernames.add(userGroupInfo.getPhone());
      }
      UserGroupInfo userGroupInfo = userGroupInfoDao
          .findFirstByUserGroupId(commodity.getCreatorUserGroupId());
      return new CommodityAuthorDetailResponse(userGroupInfo, new ArrayList<>(usernames));
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public CommodityGetResponse getTopicCommodities(String key, int offset, int limit) {
    if (!key.contains("#")) {
      key = "#" + key + "#";
    }
    key = "%" + key + "%";
    List<Commodity> commodities = commodityDao
        .findByKeyWithLimitAndOffset(key, limit, offset);
    int count = commodityDao.countByKeyWithLimitAndOffset(key);
    return new CommodityGetResponse(packCommodities(commodities), count);
  }

  @Override
  public CommodityGetResponse getFavoritesCommodities(int favoritesId, int status,
      String username) {
    Set<Integer> commodityIds = new HashSet<>();
    switch (status) {
      case 0:
        List<Order> orders;
        if (favoritesId == 0) {
//          orders = orderDao.findAllByFavoritesDefault(username);
//          orders.addAll(orderDao.findAllByFavoritesIdAndCustomer(0, username));
          orders = orderDao.findAllByCustomer(username);
        } else {
          orders = orderDao.findAllByFavoritesIdAndCustomer(favoritesId, username);
        }
        for (Order order : orders) {
          commodityIds.add(order.getRelationId());
        }
        break;
      case 1:
        List<CommodityLike> commodityLikes;
        if (favoritesId == 0) {
//          commodityLikes = commodityLikeDao.findAllByFavoritesDefault(username);
//          commodityLikes.addAll(commodityLikeDao.findAllByFavoritesIdAndUsername(0, username));
          commodityLikes = commodityLikeDao.findAllByUsername(username);
        } else {
          commodityLikes = commodityLikeDao.findAllByFavoritesIdAndUsername(favoritesId, username);
        }
        for (CommodityLike commodityLike : commodityLikes) {
          commodityIds.add(commodityLike.getCommodityId());
        }
        break;
    }
    List<Commodity> commodities = commodityDao.findAllByIdIn(commodityIds);
    return new CommodityGetResponse(packCommodities(commodities), commodities.size());
  }

  @Override
  public SuccessResponse moveFavoritesCommodity(int favoritesId, int commodityId, int status) {
    switch (status) {
      case 0:
        Order order = orderDao
            .findFirstByCustomerAndRelationId(UserInfoUtil.getUsername(), commodityId);
        order.setFavoritesId(favoritesId);
        orderDao.save(order);
        break;
      case 1:
        CommodityLike commodityLike = commodityLikeDao
            .findCommodityLikeByCommodityIdAndUsername(commodityId, UserInfoUtil.getUsername());
        if (commodityLike == null) {
          commodityLike = new CommodityLike(UserInfoUtil.getUsername(), commodityId, favoritesId,
              System.currentTimeMillis(), System.currentTimeMillis());
        } else {
          commodityLike.setFavoritesId(favoritesId);
          commodityLike.setUpdateTime(System.currentTimeMillis());
        }
        commodityLikeDao.save(commodityLike);
        break;
    }
    return new SuccessResponse("move success");
  }

  @Override
  public CommodityGetResponse getAuthorCommodities(int status, String author) {
    List<Commodity> commodities;
    if (status == -1) {
      commodities = commodityDao.findAllByAuthor(author);
    } else {
      commodities = commodityDao.findAllByAuthorAndDraftStatus(author, status);
    }
    return new CommodityGetResponse(packCommodities(commodities), commodities.size());
  }

  @Override
  public CommoditySaleStatisticsResponse getUserStatistics(String username, long startTime,
      long endTime) {
    List<Commodity> commodities = commodityDao
        .findAllByAuthorAndCreateTimeIsBetween(username, startTime, endTime);
    List<Integer> commodityIds = new ArrayList<>();
    for (Commodity commodity : commodities) {
      commodityIds.add(commodity.getId());
    }
    int count = (int) orderDao.countAllByRelationIdIn(commodityIds);
    return new CommoditySaleStatisticsResponse(count);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Response updateCommodityByGroup(List<CommodityUpdateParameters> parameters) {
    ArrayList<Commodity> commodities = Lists.newArrayList();
    System.out.println(parameters);
    for (CommodityUpdateParameters commodityUpdateParameter : parameters) {
      commodities.add(createCommodity(commodityUpdateParameter));
    }
    commodityDao.saveAll(commodities);
    return new SuccessResponse("save success");
  }

  private CommodityDetailResponse packCommodity(Commodity commodity) {
    int likeNum = (int) commodityLikeDao.countAllByCommodityId(commodity.getId());
    return new CommodityDetailResponse(commodity.getId(), commodity.getTitle(),
        commodity.getLargePrice(),
        commodity.getCoverUrl(), commodity.getTags(), commodity.getContentUrl(),
        commodity.getDescription(), commodity.getSigningInfo(), commodity.getExtra(),
        commodity.getCreatorUserGroupId(), commodity.getDownloadUrl(), commodity.isExclusive(),
        commodity.getAuthor(), likeNum);
  }

  private List<CommodityItem> packCommodities(List<Commodity> commodities) {
    List<CommodityItem> commodityItems = new ArrayList<>();
    for (Commodity commodity : commodities) {
      commodityItems.add(
          new CommodityItem(commodity.getId(), commodity.getTitle(), commodity.getCoverUrl(),
              commodity.getLargePrice(),
              commodity.getTags(), commodity.getCreateTime(), commodity.getUpdateTime(),
              commodity.isExclusive(), commodity.getAuthor(), commodity.getDescription()));
    }
    return commodityItems;
  }
}
