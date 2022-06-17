package com.rongmei.threads;

import com.rongmei.dao.auction.AuctionDao;
import com.rongmei.dao.auction.SaleDao;
import com.rongmei.dao.auction.ThingDao;
import com.rongmei.entity.auction.Auction;
import com.rongmei.entity.auction.Sale;
import com.rongmei.entity.auction.Thing;
import com.rongmei.util.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import com.google.common.collect.Maps;
import com.rongmei.dao.auction.AuctionDao;
import com.rongmei.dao.auction.SaleDao;
import com.rongmei.dao.auction.ThingDao;
import com.rongmei.dao.finance.FinanceDao;
import com.rongmei.dao.pcuser.PcUserDao;
import com.rongmei.entity.PcUser.PcUser;
import com.rongmei.entity.auction.Auction;
import com.rongmei.entity.auction.Sale;
import com.rongmei.entity.auction.Thing;
import com.rongmei.entity.commodity.Commodity;
import com.rongmei.entity.finance.Finance;
import com.rongmei.parameters.constant.MoneyProportion;
import com.rongmei.response.WrongResponse;
import com.rongmei.util.BalanceUtil;
import com.rongmei.util.UserInfoUtil;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;


@Component
public class AuctionThread implements Runnable {




  public AuctionThread (){

  }
  private SaleDao saleDao;
  private AuctionDao auctionDao;
  private ThingDao thingDao;
  private FinanceDao financeDao;
  private PcUserDao pcUserDao;

  public AuctionThread(SaleDao saleDao, AuctionDao auctionDao,
                       ThingDao thingDao, FinanceDao financeDao,PcUserDao pcUserDao) {
    this.saleDao = saleDao;
    this.auctionDao = auctionDao;
    this.thingDao = thingDao;
    this.financeDao = financeDao;
    this.pcUserDao = pcUserDao;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void run() {

      //在此插入竞品
      List<Sale> saleList = saleDao
             .findAllByEndTimeBeforeAndStatus(System.currentTimeMillis(), "拍卖中");
      for (Sale sale : saleList) {
        try {
          Auction winnerAuction = auctionDao.findFirstBySaleIdOrderByPriceDesc(sale.getId());
          if (winnerAuction != null) {
            Optional<Thing> optionalThing = thingDao.findById(sale.getThingId());
            if (optionalThing.isPresent()) {
              Thing thing = optionalThing.get();
              thing.setOwner(winnerAuction.getUsername());
              thingDao.save(thing);
              sale.setStatus("已售出");
              Map<String, BigDecimal> zhengchang = MoneyUtil.updateUserElectronic(BigDecimal.valueOf(winnerAuction.getPrice()),
                      BigDecimal.valueOf(sale.getStartPrice()),
                      sale.getNeedCopyrightTax(), "已售出");


              BigDecimal creator = zhengchang.get("creator");
              BigDecimal pc = zhengchang.get("pc");
              BigDecimal user = zhengchang.get("user");
              BigDecimal pcShare = zhengchang.get("pcShare");
              BalanceUtil.transferByBigDecimal(BigDecimal.valueOf(winnerAuction.getPrice()), winnerAuction.getUsername(), "支出");
              BalanceUtil.transferByBigDecimal(creator, thing.getAuthor(), "收入");
              //BalanceUtil.transferByBigDecimal(pc, "rongmei", "收入");
              //支出 创作者报酬
              Finance finance = new Finance();
              //thing.getAuthor()
              finance.setUsername(thing.getAuthor());
              finance.setCreateTime(System.currentTimeMillis());
              finance.setType("竞品");
              finance.setTransactionType("支出");
              BigDecimal startPrice = BigDecimal.valueOf(sale.getStartPrice());
              finance.setStartPrice(startPrice);
              BigDecimal transactionCash = BigDecimal.valueOf(winnerAuction.getPrice());
              finance.setTransactionCash(transactionCash);
              BigDecimal serviceCash = transactionCash.multiply(MoneyProportion.PLATFORMSERVICE);
              finance.setServiceCash(BigDecimal.ZERO);
              finance.setIncomeCash(BigDecimal.ZERO);
              finance.setExpenditureCash(creator);
              finance.setTransactionShare(creator);
              finance.setGuguShare(BigDecimal.ZERO);
              finance.setPayType("");
              finance.setDescs("竞品-创作者收益");
              if (sale.getNeedCopyrightTax()) {
                finance.setCopyrightCash(BigDecimal.ZERO);
              } else {
                finance.setCopyrightCash(BigDecimal.ZERO);
              }
              finance.setMargin(BigDecimal.ZERO);
              finance.setIsMargin(1);
              finance.setOrderNumber(RandomUtil.generateNum2String(10));
              finance.setCancelCash(BigDecimal.ZERO);
              financeDao.save(finance);
              //pcUserDao.save(MoneyUtil.updatePcUser(pcUserDao,creator,"支出"));

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
              finance2.setIncomeCash(BigDecimal.valueOf(winnerAuction.getPrice()));
              finance2.setExpenditureCash(BigDecimal.ZERO);
              finance2.setTransactionShare(pcShare);
              finance2.setGuguShare(BigDecimal.ZERO);
              if (sale.getNeedCopyrightTax()) {
                finance2.setCopyrightCash(transactionCash.multiply(MoneyProportion.PLATFORMCOPYTIGHT));
              } else {
                finance2.setCopyrightCash(BigDecimal.ZERO);
              }
              finance2.setMargin(startPrice.multiply(MoneyProportion.PLATFORMMARGIN));
              finance2.setIsMargin(1);
              finance2.setCancelCash(BigDecimal.ZERO);
              finance2.setPayType("电子");
              finance2.setDescs("竞品-竞拍商品");
              finance2.setOrderNumber(RandomUtil.generateNum2String(10));

              financeDao.save(finance2);
              sale.setInterceptPrice((int)winnerAuction.getPrice());
              saleDao.save(sale);
              List<Auction> auctions = auctionDao.findAllBySaleIdOrderByCreateTimeDesc(sale.getId());
              Set<String> usernameSet = new HashSet<>();
              for (Auction auction : auctions) {
                  usernameSet.add(auction.getUsername());
              }
              // 保证金
              for (String username : usernameSet) {
                  try {
                    //插入财务表
                    Finance build = new Finance();
                    build.setMargin(startPrice.multiply(BigDecimal.valueOf(0.2)));
                    build.setType("竞品");
                    build.setTransactionType("支出");
                    build.setExpenditureCash(startPrice.multiply(BigDecimal.valueOf(0.2)));
                    build.setIsMargin(0);
                    build.setPayType("");
                    build.setDescs("竞品-退还保证金");
                    financeDao.save(build);
                    UserInfoUtil.discountEarnest(startPrice.multiply(BigDecimal.valueOf(0.2)).intValue(), username);
                    //支出 退还保证金
                    //UserInfoUtil.getUsername()
                  }catch (Exception E){
                    E.printStackTrace();
                  }
                System.out.println("discount " + username + " success price: " + Math
                        .round(sale.getStartPrice() / 5));
              }
            }
          } else {
            sale.setStatus("流拍");
            saleDao.save(sale);
          }
        } catch (Exception e) {
          System.out.println("拍卖错误");
          e.printStackTrace();
          sale.setStatus("拍卖错误");
          saleDao.save(sale);
        }
      }
    }



  }

