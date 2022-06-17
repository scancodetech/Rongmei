package com.rongmei.bl.relation;

import com.rongmei.blservice.relation.RelationBlService;
import com.rongmei.dao.relation.BlindBoxLikeDao;
import com.rongmei.dao.relation.ChildLikeDao;
import com.rongmei.dao.relation.CommodityLikeDao;
import com.rongmei.dao.relation.FavoritesDao;
import com.rongmei.dao.relation.ThingLikeDao;
import com.rongmei.entity.relation.BlindBoxLike;
import com.rongmei.entity.relation.ChildLike;
import com.rongmei.entity.relation.CommodityLike;
import com.rongmei.entity.relation.Favorites;
import com.rongmei.entity.relation.ThingLike;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.relation.FavoritesUpdateParameters;
import com.rongmei.parameters.relation.LikeRelationUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.relation.FavoritesGetResponse;
import com.rongmei.response.relation.FavoritesItem;
import com.rongmei.response.relation.LikeGetResponse;
import com.rongmei.response.relation.LikeStatisticsResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationBlServiceImpl implements RelationBlService {

  private final FavoritesDao favoritesDao;
  private final ThingLikeDao thingLikeDao;
  private final CommodityLikeDao commodityLikeDao;
  private final BlindBoxLikeDao blindBoxLikeDao;
  private final ChildLikeDao childLikeDao;

  @Autowired
  public RelationBlServiceImpl(FavoritesDao favoritesDao,
      ThingLikeDao thingLikeDao, CommodityLikeDao commodityLikeDao,
      BlindBoxLikeDao blindBoxLikeDao, ChildLikeDao childLikeDao) {
    this.favoritesDao = favoritesDao;
    this.thingLikeDao = thingLikeDao;
    this.commodityLikeDao = commodityLikeDao;
    this.blindBoxLikeDao = blindBoxLikeDao;
    this.childLikeDao = childLikeDao;
  }

  @Override
  public SuccessResponse updateFavorites(FavoritesUpdateParameters parameters, String username)
      throws ThingIdDoesNotExistException {
    if (parameters.getId() == 0) {
      Favorites favorites = new Favorites(parameters.getName(), username, parameters.getOrderId(),
          System.currentTimeMillis(), System.currentTimeMillis(), true);
      favoritesDao.save(favorites);
      return new SuccessResponse("add success");
    } else {
      Optional<Favorites> optionalFavorites = favoritesDao.findById(parameters.getId());
      if (optionalFavorites.isPresent()) {
        Favorites favorites = optionalFavorites.get();
        favorites.setName(parameters.getName());
        favorites.setOrderId(parameters.getOrderId());
        favorites.setUpdateTime(System.currentTimeMillis());
        favoritesDao.save(favorites);
        return new SuccessResponse("update success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
  }

  @Override
  public SuccessResponse deleteFavorites(long id) throws ThingIdDoesNotExistException {
    Optional<Favorites> optionalFavorites = favoritesDao.findById((int) id);
    if (optionalFavorites.isPresent()) {
      Favorites favorites = optionalFavorites.get();
      favorites.setActive(false);
      favorites.setUpdateTime(System.currentTimeMillis());
      favoritesDao.save(favorites);
      return new SuccessResponse("update success");
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public FavoritesGetResponse getFavorites(String username) {
    List<Favorites> favoritesList = favoritesDao.findAllByIsActiveAndUsername(true, username);
    return new FavoritesGetResponse(packFavoritesItems(favoritesList));
  }

  @Override
  public SuccessResponse postLike(LikeRelationUpdateParameters parameters, String username) {
    switch (parameters.getType()) {
      case 0:
        ThingLike thingLike = thingLikeDao
            .findThingLikeByThingIdAndUsername(parameters.getRelationId(), username);
        if (thingLike != null) {
          thingLikeDao.delete(thingLike);
        } else {
          thingLike = new ThingLike(username, parameters.getRelationId(), 0,
              System.currentTimeMillis(), System.currentTimeMillis());
          thingLikeDao.save(thingLike);
        }
        break;
      case 1:
        CommodityLike commodityLike = commodityLikeDao
            .findCommodityLikeByCommodityIdAndUsername(parameters.getRelationId(), username);
        if (commodityLike != null) {
          commodityLikeDao.delete(commodityLike);
        } else {
          commodityLike = new CommodityLike(username, parameters.getRelationId(), 0,
              System.currentTimeMillis(), System.currentTimeMillis());
          commodityLikeDao.save(commodityLike);
        }
        break;
      case 2:
        BlindBoxLike blindBoxLike = blindBoxLikeDao
            .findBlindBoxLikeByBlindBoxIdAndUsername(parameters.getRelationId(), username);
        if (blindBoxLike != null) {
          blindBoxLikeDao.delete(blindBoxLike);
        } else {
          blindBoxLike = new BlindBoxLike(username, parameters.getRelationId(), 0,
              System.currentTimeMillis(), System.currentTimeMillis());
          blindBoxLikeDao.save(blindBoxLike);
        }
        break;
      case 5:
        ChildLike childLike = childLikeDao
            .findChildLikeByChildIdAndUsername(parameters.getRelationId(), username);
        if (childLike != null) {
          childLikeDao.delete(childLike);
        } else {
          childLike = new ChildLike(username, parameters.getRelationId(), 0,
              System.currentTimeMillis(), System.currentTimeMillis());
          childLikeDao.save(childLike);
        }
        break;
    }
    return new SuccessResponse("update success");
  }

  @Override
  public LikeGetResponse getLike(int relationId, int type, String username) {
    boolean isLike = false;
    int likeNum = 0;
    switch (type) {
      case 0:
        ThingLike thingLike = thingLikeDao.findThingLikeByThingIdAndUsername(relationId, username);
        if (thingLike != null) {
          isLike = true;
        }
        likeNum = (int) thingLikeDao.countAllByThingId(relationId);
        break;
      case 1:
        CommodityLike commodityLike = commodityLikeDao
            .findCommodityLikeByCommodityIdAndUsername(relationId, username);
        if (commodityLike != null) {
          isLike = true;
        }
        likeNum = (int) commodityLikeDao.countAllByCommodityId(relationId);
        break;
      case 2:
        BlindBoxLike blindBoxLike = blindBoxLikeDao
            .findBlindBoxLikeByBlindBoxIdAndUsername(relationId, username);
        if (blindBoxLike != null) {
          isLike = true;
        }
        likeNum = (int) blindBoxLikeDao.countAllByBlindBoxId(relationId);
        break;
      case 5:
        ChildLike childLike = childLikeDao.findChildLikeByChildIdAndUsername(relationId, username);
        if (childLike != null) {
          isLike = true;
        }
        likeNum = (int) childLikeDao.countAllByChildId(relationId);
    }
    return new LikeGetResponse(isLike, likeNum);
  }

  @Override
  public LikeStatisticsResponse getLikeUserStatistics(int type, String username, long startTime,
      long endTime) {
    int count = 0;
    switch (type) {
      case 0:
        count = (int) thingLikeDao.countAllByThingOwner(username, startTime, endTime);
        break;
      case 1:
        count = (int) commodityLikeDao.countAllByCommodityOwner(username, startTime, endTime);
        break;
      case 2:
        count = (int) blindBoxLikeDao.countAllByBlindBoxOwner(username, startTime, endTime);
        break;
    }
    return new LikeStatisticsResponse(count);
  }

  private List<FavoritesItem> packFavoritesItems(List<Favorites> favoritesList) {
    List<FavoritesItem> favoritesItems = new ArrayList<>();
    for (Favorites favorites : favoritesList) {
      favoritesItems.add(
          new FavoritesItem(favorites.getId(), favorites.getName(), favorites.getUsername(),
              favorites.getOrderId(), favorites.getCreateTime(), favorites.getUpdateTime(),
              favorites.isActive()));
    }
    return favoritesItems;
  }
}
