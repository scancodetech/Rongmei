package com.rongmei.bl.auction;

import com.rongmei.blservice.auction.AuctionBlService;
import com.rongmei.blservice.auction.SaleBlService;
import com.rongmei.dao.auction.AuctionDao;
import com.rongmei.dao.auction.AuctionTransactionDao;
import com.rongmei.dao.auction.SaleDao;
import com.rongmei.dao.auction.ThingDao;
import com.rongmei.dao.finance.FinanceDao;
import com.rongmei.entity.auction.Auction;
import com.rongmei.entity.auction.AuctionTransaction;
import com.rongmei.entity.auction.Sale;
import com.rongmei.entity.auction.Thing;
import com.rongmei.entity.finance.Finance;
import com.rongmei.exception.ParametersErrorException;
import com.rongmei.exception.SystemException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.auction.AuctionParamaters;
import com.rongmei.parameters.auction.AuctionTransactionParameters;
import com.rongmei.parameters.constant.MoneyProportion;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.auction.AuctionHistory;
import com.rongmei.response.auction.AuctionHistoryGetResponse;
import com.rongmei.response.auction.AuctionJoinStatisticsResponse;
import com.rongmei.response.auction.AuctionStatisticsResponse;
import com.rongmei.response.auction.AuctionTransactionHistory;
import com.rongmei.response.auction.AuctionTransactionHistoryGetResponse;
import com.rongmei.response.auction.SaleDetailResponse;
import com.rongmei.util.BalanceUtil;
import com.rongmei.util.MoneyUtil;
import com.rongmei.util.RandomUtil;
import com.rongmei.util.UserInfoUtil;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuctionBlServiceImpl implements AuctionBlService {

  private final AuctionDao auctionDao;
  private final SaleBlService saleBlService;
  private final SaleDao saleDao;
  private final AuctionTransactionDao auctionTransactionDao;
  private final ThingDao thingDao;
  private final FinanceDao financeDao;

  @Autowired
  public AuctionBlServiceImpl(AuctionDao auctionDao,
      SaleBlService saleBlService, SaleDao saleDao,
      AuctionTransactionDao auctionTransactionDao, ThingDao thingDao,FinanceDao financeDao) {
    this.auctionDao = auctionDao;
    this.saleBlService = saleBlService;
    this.saleDao = saleDao;
    this.auctionTransactionDao = auctionTransactionDao;
    this.thingDao = thingDao;
    this.financeDao = financeDao;
  }

  @Override
  public SaleDetailResponse bid(AuctionParamaters parameters, String username)
      throws ThingIdDoesNotExistException, ParametersErrorException {
    Optional<Sale> optionalSale = saleDao.findById(parameters.getSaleId());
    if (optionalSale.isPresent()) {
      Sale sale = optionalSale.get();
      double currentPrice = saleBlService.getCurrentSalePrice(parameters.getSaleId());
      if (sale.getStartPrice() > parameters.getPrice() || (currentPrice > 0
          && currentPrice + sale.getIntervalPrice() > parameters.getPrice())) {
        throw new ParametersErrorException();
      } else {
        Auction auction = new Auction(parameters.getPrice(), username, parameters.getSaleId(),
            sale.getThingId(),
            System.currentTimeMillis());
        auctionDao.save(auction);
      }
      return saleBlService.getSale(parameters.getSaleId(), username);
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public AuctionHistoryGetResponse getAuctionHistory(int saleId) {
    List<Auction> auctions = auctionDao.findAllBySaleIdOrderByCreateTimeDesc(saleId);
    List<AuctionHistory> auctionHistories = new ArrayList<>();
    for (Auction auction : auctions) {
      auctionHistories.add(new AuctionHistory(auction.getId(), auction.getPrice(),
          UserInfoUtil.getUserInfoDetail(auction.getUsername()), auction.getCreateTime()));
    }
    return new AuctionHistoryGetResponse(auctionHistories);
  }

  @Override
  public AuctionTransactionHistoryGetResponse getAuctionTransactionHistory() {
    List<AuctionTransaction> auctionTransactions = auctionTransactionDao
        .findAllByUsername(UserInfoUtil.getUsername());
    List<AuctionTransactionHistory> auctionTransactionHistories = packAuctionTransactionHistory(
        auctionTransactions);
    return new AuctionTransactionHistoryGetResponse(auctionTransactionHistories);
  }

  @Override
  public SuccessResponse addAuctionTransaction(AuctionTransactionParameters parameters) {
    AuctionTransaction auctionTransaction = new AuctionTransaction(parameters.getTransactionId(),
        parameters.getTokenId(), parameters.getName(), System.currentTimeMillis(),
        parameters.getUsername(), parameters.getEth(), parameters.getNft());
    auctionTransactionDao.save(auctionTransaction);
    return new SuccessResponse("add success");
  }

  @Override
  public AuctionTransactionHistoryGetResponse getCurrentAuctionTransaction(int limit, int offset) {
    List<AuctionTransaction> auctionTransactions = auctionTransactionDao
        .findAllOrderByCreateTimeDesc(limit, offset);
    List<AuctionTransactionHistory> auctionTransactionHistories = packAuctionTransactionHistory(
        auctionTransactions);
    return new AuctionTransactionHistoryGetResponse(auctionTransactionHistories);
  }

  @Override
  public AuctionStatisticsResponse getStatistics() {
    long thingNum = thingDao.count();
    long saleNum = saleDao.count();
    long ethNum = 0;
    List<AuctionTransaction> auctionTransactions = auctionTransactionDao.findAll();
    for (AuctionTransaction auctionTransaction : auctionTransactions) {
      if (auctionTransaction.getEth() > 0) {
        ethNum += auctionTransaction.getEth();
      }
    }
    return new AuctionStatisticsResponse(thingNum, ethNum, saleNum);
  }

  @Override
  public AuctionJoinStatisticsResponse getUserStatistics(String username, long startTime,
      long endTime) {
    int count = (int) auctionDao.countAllByAuctionSaleOwner(username, startTime, endTime);
    return new AuctionJoinStatisticsResponse(count);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Response onePrice(String username, int saleId, int price) throws SystemException {
      Optional<Sale> sale1 = saleDao.findById(saleId);
      if (sale1.isPresent()) {
        Auction auction1 = new Auction();
        auction1.setPrice(Double.valueOf(price));
        auction1.setSaleId(saleId);
        auction1.setThingId(saleDao.getOne(saleId).getThingId());
        auction1.setUsername(username);
        auction1.setCreateTime(System.currentTimeMillis());
        auctionDao.save(auction1);
        Sale sale = sale1.get();
        sale.setStatus("已售出");
        //因为已经售出 所以就代表已经结束了
        sale.setEndTime(System.currentTimeMillis());
        sale.setInterceptPrice(price);
        saleDao.save(sale);

        Optional<Thing> optionalThing = thingDao.findById(sale.getThingId());
        if (optionalThing.isPresent()) {
          Thing thing = optionalThing.get();
          thing.setOwner(username);
          thingDao.save(thing);
          Map<String, BigDecimal> zhengchang = MoneyUtil.updateUserElectronic(BigDecimal.valueOf(price),
                  BigDecimal.valueOf(sale.getStartPrice()),
                  sale.getNeedCopyrightTax(), "已售出");
          BigDecimal creator = zhengchang.get("creator");
          BigDecimal pcShare = zhengchang.get("pcShare");

          //支出 创作者报酬
          Finance finance = new Finance();
          //thing.getAuthor()
          finance.setUsername(thing.getAuthor());
          finance.setCreateTime(System.currentTimeMillis());
          finance.setType("竞品");
          finance.setTransactionType("支出");
          BigDecimal startPrice = BigDecimal.valueOf(sale.getStartPrice());
          finance.setStartPrice(startPrice);
          BigDecimal transactionCash = BigDecimal.valueOf(price);
          finance.setTransactionCash(transactionCash);
          BigDecimal serviceCash = transactionCash.multiply(MoneyProportion.PLATFORMSERVICE);
          finance.setServiceCash(BigDecimal.ZERO);
          finance.setIncomeCash(BigDecimal.ZERO);
          finance.setExpenditureCash(creator);
          finance.setTransactionShare(creator);
          finance.setGuguShare(BigDecimal.ZERO);
          finance.setPayType("");
          finance.setDescs("竞品-创作者收益");
          finance.setOrderNumber(RandomUtil.generateNum2String(10));
          if (sale.getNeedCopyrightTax()) {
            finance.setCopyrightCash(BigDecimal.ZERO);
          } else {
            finance.setCopyrightCash(BigDecimal.ZERO);
          }
          finance.setMargin(BigDecimal.ZERO);
          finance.setIsMargin(1);
          finance.setCancelCash(BigDecimal.ZERO);
          financeDao.save(finance);


          //收入 竞拍收入
          //UserInfoUtil.getUsername()
          Finance finance2 = new Finance();
          finance2.setUsername(UserInfoUtil.getUsername());
          finance2.setCreateTime(System.currentTimeMillis());
          finance2.setType("竞品");
          finance2.setTransactionType("收入");
          finance2.setStartPrice(startPrice);
          finance2.setTransactionCash(transactionCash);
          finance2.setServiceCash(serviceCash);
          finance2.setIncomeCash(BigDecimal.valueOf(price));
          finance2.setExpenditureCash(BigDecimal.ZERO);
          finance2.setTransactionShare(pcShare);
          finance2.setGuguShare(BigDecimal.ZERO);
          finance2.setPayType("电子");
          finance2.setDescs("竞品-一口价竞拍");
          finance2.setOrderNumber(RandomUtil.generateNum2String(10));
          if (sale.getNeedCopyrightTax()) {
            finance2.setCopyrightCash(transactionCash.multiply(MoneyProportion.PLATFORMCOPYTIGHT));
          } else {
            finance2.setCopyrightCash(BigDecimal.ZERO);
          }
          finance2.setMargin(startPrice.multiply(MoneyProportion.PLATFORMMARGIN));
          finance2.setIsMargin(1);
          finance2.setCancelCash(BigDecimal.ZERO);

          financeDao.save(finance2);

          try {
            System.out.println("start money --------------->");
            BalanceUtil.transferByBigDecimal(BigDecimal.valueOf(price), username, "支出");
            BalanceUtil.transferByBigDecimal(creator, thing.getAuthor(), "收入");
            System.out.println("end <----------------");
          } catch (Exception e) {
            System.out.println("exception <----------------");
            e.printStackTrace();
            throw new SystemException();
          }

          List<Auction> auctions = auctionDao.findAllBySaleIdOrderByCreateTimeDesc(sale.getId());
          Set<String> usernameSet = new HashSet<>();
          for (Auction auction : auctions) {
            usernameSet.add(auction.getUsername());
          }
          // 保证金
          for (String usernames : usernameSet) {
            try {
              UserInfoUtil.discountEarnest(startPrice.multiply(BigDecimal.valueOf(0.2)).intValue(), usernames);
              //支出 退还保证金
            } catch (Exception E) {
              E.printStackTrace();
            }
          }
        }else {
          return new WrongResponse(10002,"thing id is not exits");
        }

      }
    return new SuccessResponse("transaction success");
  }

  private List<AuctionTransactionHistory> packAuctionTransactionHistory(
      List<AuctionTransaction> auctionTransactions) {
    List<AuctionTransactionHistory> auctionTransactionHistories = new ArrayList<>();
    for (AuctionTransaction auctionTransaction : auctionTransactions) {
      auctionTransactionHistories.add(
          new AuctionTransactionHistory(auctionTransaction.getTransactionId(),
              auctionTransaction.getTokenId(), auctionTransaction.getName(),
              auctionTransaction.getCreateTime(), auctionTransaction.getUsername(),
              auctionTransaction.getEth(), auctionTransaction.getNft()));
    }
    return auctionTransactionHistories;
  }
}
