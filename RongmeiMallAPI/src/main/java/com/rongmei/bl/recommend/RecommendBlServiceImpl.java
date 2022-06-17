package com.rongmei.bl.recommend;

import static com.rongmei.util.UserInfoUtil.getUserInfoByCache;

import com.rongmei.blservice.recommend.RecommendBlService;
import com.rongmei.dao.auction.SaleDao;
import com.rongmei.dao.auction.ThingDao;
import com.rongmei.dao.child.SimpleChildDao;
import com.rongmei.dao.commodity.CommodityDao;
import com.rongmei.entity.auction.Sale;
import com.rongmei.entity.auction.Thing;
import com.rongmei.entity.child.SimpleChild;
import com.rongmei.entity.commodity.Commodity;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.response.recommend.RecommendGetResponse;
import com.rongmei.response.recommend.RecommendItem;
import com.rongmei.response.user.UserInfo;
import com.rongmei.util.LRUCache;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RecommendBlServiceImpl implements RecommendBlService {

  private final SimpleChildDao childDao;
  private final SaleDao saleDao;
  private final ThingDao thingDao;
  private final CommodityDao commodityDao;

  private final LRUCache<Integer, Thing> thingCache = new LRUCache<>(100);

  public RecommendBlServiceImpl(SimpleChildDao childDao, SaleDao saleDao,
      ThingDao thingDao, CommodityDao commodityDao) {
    this.childDao = childDao;
    this.saleDao = saleDao;
    this.thingDao = thingDao;
    this.commodityDao = commodityDao;
  }

  @Override
  public RecommendGetResponse getRecommendList(int page, int limit, long startFromId,
      int startFromType, String username) {
    int childLimit = limit / 3;
    int commodityLimit = limit / 3;
    int saleLimit = limit - childLimit - commodityLimit;
    List<SimpleChild> childList = childDao.findAllByPage(childLimit, (page - 1) * childLimit);
    List<Commodity> commodityList = commodityDao
        .findByLimitAndOffset(commodityLimit, (page - 1) * commodityLimit);
    List<Sale> saleList = saleDao.findAllOrderByCreateTimeDesc(saleLimit, (page - 1) * saleLimit);
    List<RecommendItem> recommendItems = new ArrayList<>();
    recommendItems.addAll(packRecommendItemsByChild(childList));
    recommendItems.addAll(packRecommendItemsByCommodity(commodityList));
    recommendItems.addAll(packRecommendItemsBySale(saleList));
    recommendItems.sort((o1, o2) -> (int) (o2.getCreateTime() - o1.getCreateTime()));
    if (startFromId != 0) {
      switch (startFromType) {
        case 0:
          Optional<Commodity> optionalCommodity = commodityDao.findById((int) startFromId);
          if (optionalCommodity.isPresent()) {
            Commodity commodity = optionalCommodity.get();
            recommendItems.add(0, getRecommendItemByCommodity(commodity));
          }
          break;
        case 1:
          Optional<Sale> optionalSale = saleDao.findById((int) startFromId);
          if (optionalSale.isPresent()) {
            Sale sale = optionalSale.get();
            try {
              recommendItems.add(0, getRecommendItemBySale(sale));
            } catch (ThingIdDoesNotExistException e) {
              e.printStackTrace();
            }
          }
          break;
        case 5:
          Optional<SimpleChild> optionalChild = childDao.findById((int) startFromId);
          if (optionalChild.isPresent()) {
            SimpleChild child = optionalChild.get();
            recommendItems.add(0, getRecommendItemByChild(child));
          }
          break;
      }
    }
    return new RecommendGetResponse(recommendItems,
        childList.size() + commodityList.size() + saleList.size());
  }

  private List<RecommendItem> packRecommendItemsByChild(List<SimpleChild> childList) {
    List<RecommendItem> recommendItems = new ArrayList<>();
    for (SimpleChild child : childList) {
      recommendItems.add(getRecommendItemByChild(child));
    }
    return recommendItems;
  }

  private RecommendItem getRecommendItemByChild(SimpleChild child) {
    UserInfo userInfo = getUserInfoByCache(child.getAuthor());
    return new RecommendItem(child.getId(), 5, userInfo.getNickname(), child.getContent(),
        child.getTopics(), child.getCoverUrl(), userInfo.getAvatarUrl(), child.getAuthor(),
        child.getCreateTime(),
        child.getUpdateTime());
  }

  private List<RecommendItem> packRecommendItemsByCommodity(List<Commodity> commodityList) {
    List<RecommendItem> recommendItems = new ArrayList<>();
    for (Commodity commodity : commodityList) {
      recommendItems.add(getRecommendItemByCommodity(commodity));
    }
    return recommendItems;
  }

  private RecommendItem getRecommendItemByCommodity(Commodity commodity) {
    UserInfo userInfo = getUserInfoByCache(commodity.getAuthor());
    return new RecommendItem(commodity.getId(), 0, commodity.getTitle(),
        commodity.getDescription(),
        new ArrayList<>(), commodity.getCoverUrl(), userInfo.getAvatarUrl(),
        commodity.getAuthor(),
        commodity.getCreateTime(),
        commodity.getUpdateTime());
  }

  private List<RecommendItem> packRecommendItemsBySale(List<Sale> saleList) {
    List<RecommendItem> recommendItems = new ArrayList<>();
    for (Sale sale : saleList) {
      try {
        recommendItems.add(getRecommendItemBySale(sale));
      } catch (ThingIdDoesNotExistException e) {
        e.printStackTrace();
      }
    }
    return recommendItems;
  }

  private RecommendItem getRecommendItemBySale(Sale sale) throws ThingIdDoesNotExistException {
    Thing thing = getThing(sale.getThingId());
    UserInfo userInfo = getUserInfoByCache(thing.getOwner());
    return new RecommendItem(sale.getId(), 1, thing.getName(),
        thing.getDescription(),
        new ArrayList<>(), thing.getUrl(), userInfo.getAvatarUrl(),
        thing.getOwner(),
        sale.getStartTime(),
        sale.getEndTime());
  }

  private Thing getThing(Integer thingId) throws ThingIdDoesNotExistException {
    Thing thing;
    if (thingCache.containsKey(thingId)) {
      thing = thingCache.get(thingId);
    } else {
      Optional<Thing> optionalThing = thingDao.findById(thingId);
      if (optionalThing.isPresent()) {
        thing = optionalThing.get();
        thingCache.put(thingId, thing);
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
    return thing;
  }
}