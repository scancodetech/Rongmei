package com.rongmei.bl.auction;

import com.rongmei.blservice.auction.SaleBlService;
import com.rongmei.dao.auction.AuctionDao;
import com.rongmei.dao.auction.SaleDao;
import com.rongmei.dao.auction.ThingDao;
import com.rongmei.dao.commodity.CommodityDao;
import com.rongmei.dao.finance.FinanceDao;
import com.rongmei.dao.order.OrderDao;
import com.rongmei.dao.pcuser.PcUserDao;
import com.rongmei.dao.relation.ThingLikeDao;
import com.rongmei.entity.auction.Auction;
import com.rongmei.entity.auction.Sale;
import com.rongmei.entity.auction.Thing;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.auction.SaleQueryParameters;
import com.rongmei.parameters.auction.SaleQueryParmeters;
import com.rongmei.parameters.auction.SaleUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.auction.AuctionTagGetResponse;
import com.rongmei.response.auction.SaleDetailResponse;
import com.rongmei.response.auction.SaleGetResponse;
import com.rongmei.response.auction.SaleItem;
import com.rongmei.response.auction.TagHotItem;
import com.rongmei.threads.AuctionThread;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.rongmei.threads.AuctionOtherThread;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class SaleBlServiceImpl implements SaleBlService {

  private final SaleDao saleDao;
  private final AuctionDao auctionDao;
  private final ThingDao thingDao;
  private final ThingLikeDao thingLikeDao;
  private final OrderDao orderDao;
  private final CommodityDao commodityDao;
  private final FinanceDao financeDao;
  private final PcUserDao pcUserDao;

  @Autowired
  public SaleBlServiceImpl(CommodityDao commodityDao, SaleDao saleDao, AuctionDao auctionDao, ThingDao thingDao,OrderDao orderDao,
      ThingLikeDao thingLikeDao,FinanceDao financeDao,PcUserDao pcUserDao) {
    this.commodityDao = commodityDao;
    this.orderDao = orderDao;
    this.saleDao = saleDao;
    this.auctionDao = auctionDao;
    this.thingDao = thingDao;
    this.thingLikeDao = thingLikeDao;
    this.financeDao = financeDao;
    this.pcUserDao = pcUserDao;
    ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    AuctionThread auctionThread = new AuctionThread(saleDao, auctionDao, thingDao,financeDao,pcUserDao);
    AuctionOtherThread auctionOtherThread = new AuctionOtherThread(saleDao, auctionDao, thingDao,financeDao,pcUserDao);
    timer.scheduleAtFixedRate(auctionThread, 1000, 1000 * 60 * 5, TimeUnit.MILLISECONDS);
    timer.scheduleAtFixedRate(auctionOtherThread,1000,1000 * 60 * 5,TimeUnit.MILLISECONDS);
  }

  @Override
  public SaleGetResponse getSales(SaleQueryParameters parameters) {
    List<Sale> sales;
    int count;
    if (parameters.getTags().size() > 0) {
      if (parameters.isOutdated() && parameters.isOwnedByAuthor()) {
        sales = saleDao
            .findAllByNameLikeAndEndTimeBeforeAndOwneredByAuthorAndTagsInOrderByCreateTimeDesc(
                "%" + parameters.getKey() + "%",
                parameters.getLimit(), parameters.getOffset(), System.currentTimeMillis(),
                parameters.getTags());
        count = saleDao
            .countAllByNameLikeAndEndTimeBeforeAndOwneredByAuthorAndTagsIn(
                "%" + parameters.getKey() + "%",
                System.currentTimeMillis(), parameters.getTags());
      } else if (parameters.isOutdated()) {
        sales = saleDao
            .findAllByNameLikeAndEndTimeBeforeAndTagsInOrderByCreateTimeDesc(
                "%" + parameters.getKey() + "%",
                parameters.getLimit(), parameters.getOffset(), System.currentTimeMillis(),
                parameters.getTags());
        count = saleDao
            .countAllByNameLikeAndEndTimeBeforeAndTagsIn("%" + parameters.getKey() + "%",
                System.currentTimeMillis(), parameters.getTags());
      } else if (parameters.isOwnedByAuthor()) {
        sales = saleDao
            .findAllByNameLikeAndOwneredByAuthorAndTagsInOrderByCreateTimeDesc(
                "%" + parameters.getKey() + "%",
                parameters.getLimit(), parameters.getOffset(), parameters.getTags());
        count = saleDao
            .countAllByNameLikeAndOwneredByAuthorAndTagsIn("%" + parameters.getKey() + "%",
                parameters.getTags());
      } else {
        sales = saleDao
            .findAllByNameLikeAndTagsInOrderByCreateTimeDesc("%" + parameters.getKey() + "%",
                parameters.getLimit(), parameters.getOffset(), parameters.getTags());
        count = saleDao
            .countAllByNameLikeAndTagsIn("%" + parameters.getKey() + "%", parameters.getTags());
      }
    } else {
      if (parameters.isOutdated() && parameters.isOwnedByAuthor()) {
        sales = saleDao
            .findAllByNameLikeAndEndTimeBeforeAndOwneredByAuthorOrderByCreateTimeDesc(
                "%" + parameters.getKey() + "%",
                parameters.getLimit(), parameters.getOffset(), System.currentTimeMillis());
        count = saleDao
            .countAllByNameLikeAndEndTimeBeforeAndOwneredByAuthor("%" + parameters.getKey() + "%",
                System.currentTimeMillis());
      } else if (parameters.isOutdated()) {
        sales = saleDao
            .findAllByNameLikeAndEndTimeBeforeOrderByCreateTimeDesc(
                "%" + parameters.getKey() + "%",
                parameters.getLimit(), parameters.getOffset(), System.currentTimeMillis());
        count = saleDao
            .countAllByNameLikeAndEndTimeBefore("%" + parameters.getKey() + "%",
                System.currentTimeMillis());
      } else if (parameters.isOwnedByAuthor()) {
        sales = saleDao
            .findAllByNameLikeAndOwneredByAuthorOrderByCreateTimeDesc(
                "%" + parameters.getKey() + "%",
                parameters.getLimit(), parameters.getOffset());
        count = saleDao
            .countAllByNameLikeAndOwneredByAuthor("%" + parameters.getKey() + "%");
      } else {
        sales = saleDao
            .findAllByNameLikeOrderByCreateTimeDesc("%" + parameters.getKey() + "%",
                parameters.getLimit(), parameters.getOffset());
        count = saleDao
            .countAllByNameLike("%" + parameters.getKey() + "%");
      }
    }
    return new SaleGetResponse(packSaleItemList(sales), count);
  }

  @Override
  public SaleDetailResponse getSale(int saleId, String username)
      throws ThingIdDoesNotExistException {
    Optional<Sale> optionalSale = saleDao.findById(saleId);
    if (!optionalSale.isPresent()) {
      throw new ThingIdDoesNotExistException();
    }
    Sale sale = optionalSale.get();
    Auction currentPriceAuction = auctionDao.findFirstBySaleIdOrderByPriceDesc(saleId);
    double currentPrice =
        currentPriceAuction == null ? sale.getStartPrice() : currentPriceAuction.getPrice();
    Auction userPriceAuction = auctionDao
        .findFirstBySaleIdAndUsernameOrderByPriceDesc(saleId, username);
    double userPrice = userPriceAuction == null ? 0 : userPriceAuction.getPrice();
    Thing thing = new Thing();
    int likeNum = 0;
    Optional<Thing> optionalThing = thingDao.findById(sale.getThingId());
    if (optionalThing.isPresent()) {
      thing = optionalThing.get();
      likeNum = (int) thingLikeDao.countAllByThingId(thing.getId());
    }
    return new SaleDetailResponse(sale.getId(), sale.getStartPrice(), sale.getStatus(),
        sale.getIntervalPrice(), thing, sale.getCreateTime(), sale.getStartTime(),
        sale.getEndTime(), currentPrice, userPrice, UserInfoUtil.getUserInfo(thing.getAuthor()),
        UserInfoUtil.getUserInfo(thing.getOwner()), sale.getTags(), sale.isNeedEarnestMoney(),
        sale.isEnableIntercept(), sale.isNeedCopyrightTax(), sale.getInterceptPrice(),
        sale.getRights(), likeNum);
  }

  @Override
  public SuccessResponse updateSale(SaleUpdateParameters parameters, String username)
      throws ThingIdDoesNotExistException {
    if (parameters.getId() == 0) {
      Sale sale = new Sale(parameters.getStartPrice(), "拍卖中", parameters.getIntervalPrice(),
          parameters.getThingId(), System.currentTimeMillis(),
          parameters.getStartTime(), parameters.getEndTime(), parameters.getTags(),
          parameters.isNeedEarnestMoney(), true, parameters.isEnableIntercept(),
          parameters.isNeedCopyrightTax(), parameters.getInterceptPrice(), parameters.getRights(),
          0,1);
      saleDao.save(sale);
      return new SuccessResponse("add success");
    } else {
      Optional<Sale> optionalSale = saleDao.findById(parameters.getId());
      if (!optionalSale.isPresent()) {
        throw new ThingIdDoesNotExistException();
      }
      Sale sale = optionalSale.get();
      sale.setStartPrice(parameters.getStartPrice());
      sale.setStartTime(parameters.getStartTime());
      sale.setEndTime(parameters.getEndTime());
      sale.setIntervalPrice(parameters.getIntervalPrice());
      sale.setEnableIntercept(parameters.isEnableIntercept());
      sale.setInterceptPrice(parameters.getInterceptPrice());
      sale.setNeedCopyrightTax(parameters.isNeedCopyrightTax());
      sale.setNeedEarnestMoney(parameters.isNeedEarnestMoney());
      sale.setRights(parameters.getRights());
      sale.setStatus(parameters.getStatus());
      if (sale.getDraftStatus() == 1) {
        sale.setDraftStatus(0);
      }
      saleDao.save(sale);
      return new SuccessResponse("update success");
    }
  }

  @Override
  public SuccessResponse deleteSale(int saleId) throws ThingIdDoesNotExistException {
    Optional<Sale> optionalSale = saleDao.findById(saleId);
    if (!optionalSale.isPresent()) {
      throw new ThingIdDoesNotExistException();
    }
    Sale sale = optionalSale.get();
    sale.setActive(false);
    saleDao.save(sale);
    return new SuccessResponse("delete success");
  }

  @Override
  public SaleGetResponse getMineSales(int draftStatus) {
    List<Sale> sales;
    if (draftStatus == -1) {
      sales = saleDao.findAllByOwnerOrderByCreateTimeDesc(UserInfoUtil.getUsername());
    } else {
      sales = saleDao.findAllByOwnerAndDraftStatusOrderByCreateTimeDesc(UserInfoUtil.getUsername(),
          draftStatus);
    }
    return new SaleGetResponse(packSaleItemList(sales), sales.size());
  }

  @Override
  public SaleGetResponse getSalesParticipate(String username) {
    long currTime = System.currentTimeMillis();
    List<Integer> saleIds = saleDao.findIdsByEndTimeAfterAndStartTimeBefore(currTime, currTime);
    List<Auction> auctions = auctionDao
        .findAllByUsernameAndSaleIdInOrderByCreateTimeDesc(username, saleIds);
    Set<Integer> mineSaleIds = new HashSet<>();
    for (Auction auction : auctions) {
      mineSaleIds.add(auction.getSaleId());
    }
    List<Sale> sales = saleDao.findAllByIdInAndIsActive(mineSaleIds, true);
    return new SaleGetResponse(packSaleItemList(sales), sales.size());
  }

  @Override
  public SaleGetResponse getCurrentSale(int limit, int offset) {
    List<Sale> sales = saleDao.findAllOrderByCreateTimeDesc(limit, offset);
    List<SaleItem> saleItems = packSaleItemList(sales);
    return new SaleGetResponse(saleItems, saleItems.size());
  }

  private List<SaleItem> packSaleItemList(List<Sale> sales) {
    List<SaleItem> saleItems = new ArrayList<>();
    for (Sale sale : sales) {
      Thing thing = new Thing();
      Optional<Thing> optionalThing = thingDao.findById(sale.getThingId());
      if (optionalThing.isPresent()) {
        thing = optionalThing.get();
      }
      double currentPrice = getCurrentSalePrice(sale.getId());
      saleItems.add(new SaleItem(sale.getId(), sale.getStartPrice(), sale.getStatus(),
          sale.getIntervalPrice(), currentPrice == 0 ? sale.getStartPrice() : currentPrice, thing,
          sale.getCreateTime(), sale.getStartTime(),
          sale.getEndTime(), sale.getTags(), sale.isNeedEarnestMoney()));
    }
    return saleItems;
  }

  @Override
  public double getCurrentSalePrice(int saleId) {
    Auction lastAuction = auctionDao.findFirstBySaleIdOrderByPriceDesc(saleId);
    if (lastAuction == null) {
      return 0;
    }
    return lastAuction.getPrice();
  }

  @Override
  public SaleGetResponse getTopicSales(String key, int offset, int limit) {
    if (!key.contains("#")) {
      key = "#" + key + "#";
    }
    key = "%" + key + "%";
    List<Sale> sales = saleDao.findAllByNameLikeOrderByCreateTimeDesc(key, limit, offset);
    int count = saleDao.countAllByNameLike(key);
    return new SaleGetResponse(packSaleItemList(sales), count);
  }

  @Override
  public AuctionTagGetResponse getTopTags(long startTime, long endTime) {
    List<Sale> sales = saleDao
        .findAllBySalesAuctionByStartTimeAndEndTimeBetween(startTime, endTime);
    Map<String, Integer> tagCountMap = new HashMap<>();
    for (Sale sale : sales) {
      for (String tag : sale.getTags()) {
        if (tagCountMap.containsKey(tag)) {
          tagCountMap.put(tag, tagCountMap.get(tag) + 1);
        } else {
          tagCountMap.put(tag, 1);
        }
      }
    }
    //这里将map.entrySet()转换成list
    List<Entry<String, Integer>> list = new ArrayList<>(tagCountMap.entrySet());
    //然后通过比较器来实现排序
    //升序排序
    list.sort(Comparator.comparing(Entry::getValue));
    List<TagHotItem> tagHotItems = new ArrayList<>();
    for (Entry<String, Integer> mapping : list) {
      tagHotItems.add(new TagHotItem(mapping.getKey(), mapping.getValue()));
    }
    return new AuctionTagGetResponse(tagHotItems);
  }

  @Override
  public Object getFinanceList(SaleQueryParmeters saleQueryParmeters) throws Exception {

    return null;
  }


  public double keepDecimals(BigDecimal bigDecimal){
    return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
  }


}