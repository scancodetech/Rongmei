package com.rongmei.threads;

import com.rongmei.dao.auction.AuctionDao;
import com.rongmei.dao.auction.SaleDao;
import com.rongmei.dao.auction.ThingDao;
import com.rongmei.dao.finance.FinanceDao;
import com.rongmei.dao.pcuser.PcUserDao;
import com.rongmei.entity.PcUser.PcUser;
import com.rongmei.entity.auction.Auction;
import com.rongmei.entity.auction.Sale;
import com.rongmei.entity.auction.Thing;
import com.rongmei.entity.finance.Finance;
import com.rongmei.parameters.constant.MoneyProportion;
import com.rongmei.util.BalanceUtil;
import com.rongmei.util.MoneyUtil;
import com.rongmei.util.RandomUtil;
import com.rongmei.util.UserInfoUtil;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

public class AuctionOtherThread implements Runnable {

    private SaleDao saleDao;
    private AuctionDao auctionDao;
    private ThingDao thingDao;
    private FinanceDao financeDao;
    private PcUserDao pcUserDao;

    public AuctionOtherThread(SaleDao saleDao, AuctionDao auctionDao,
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
                .findAllByStatus("已取消", "咕咕");
        for (Sale sale : saleList) {
            try {
                Auction winnerAuction = auctionDao.findFirstBySaleIdOrderByPriceDesc(sale.getId());
                if (winnerAuction != null) {
                    Optional<Thing> optionalThing = thingDao.findById(sale.getThingId());
                    if (optionalThing.isPresent()) {
                        Thing thing = optionalThing.get();

                        if("已取消".equals(sale.getStatus())){
                            Map<String, BigDecimal> zhengchang = MoneyUtil.updateUserElectronic(BigDecimal.valueOf(winnerAuction.getPrice()),
                                    BigDecimal.valueOf(sale.getStartPrice()),
                                    sale.getNeedCopyrightTax(), "已取消");
                            BigDecimal pc = zhengchang.get("pc");

                            //收入
                            Finance finance = new Finance();

                            finance.setUsername(winnerAuction.getUsername());
                            finance.setCreateTime(System.currentTimeMillis());
                            finance.setType("竞品");
                            finance.setTransactionType("收入");
                            BigDecimal startPrice = BigDecimal.valueOf(sale.getStartPrice());
                            finance.setStartPrice(startPrice);
                            finance.setTransactionCash(BigDecimal.ZERO);
                            finance.setServiceCash(BigDecimal.ZERO);
                            finance.setIncomeCash(pc);
                            finance.setExpenditureCash(BigDecimal.ZERO);
                            finance.setTransactionShare(BigDecimal.ZERO);
                            finance.setGuguShare(BigDecimal.ZERO);
                            finance.setCopyrightCash(BigDecimal.ZERO);
                            finance.setCancelCash(pc);
                            finance.setMargin(BigDecimal.ZERO);
                            finance.setPayType("电子");
                            finance.setIsMargin(1);
                            finance.setDescs("竞品-取消费");
                            finance.setOrderNumber(RandomUtil.generateNum2String(10));
                            sale.setStatus("已完成");
                            financeDao.save(finance);
                            BalanceUtil.transferByBigDecimal(pc, thing.getAuthor(), "支出");


                        }else {
                            Map<String, BigDecimal> zhengchang = MoneyUtil.updateUserElectronic(BigDecimal.valueOf(winnerAuction.getPrice()),
                                    BigDecimal.valueOf(sale.getStartPrice()),
                                    sale.getNeedCopyrightTax(), "咕咕");
                            BigDecimal creator = zhengchang.get("creator");
                            BigDecimal pc = zhengchang.get("pc");
                            BalanceUtil.transferByBigDecimal(creator, thing.getAuthor(), "收入");
                            //支出
                            Finance finance1 = new Finance();
                            finance1.setUsername(thing.getAuthor());
                            finance1.setCreateTime(System.currentTimeMillis());
                            finance1.setType("竞品");
                            finance1.setTransactionType("支出");
                            BigDecimal startPrice = BigDecimal.valueOf(sale.getStartPrice());
                            finance1.setStartPrice(startPrice);
                            finance1.setTransactionCash(BigDecimal.ZERO);
                            finance1.setServiceCash(BigDecimal.ZERO);
                            finance1.setIncomeCash(BigDecimal.ZERO);
                            finance1.setExpenditureCash(creator);
                            finance1.setTransactionShare(BigDecimal.ZERO);
                            finance1.setGuguShare(creator);
                            finance1.setCopyrightCash(BigDecimal.ZERO);
                            finance1.setCancelCash(BigDecimal.ZERO);
                            finance1.setMargin(BigDecimal.ZERO);
                            finance1.setIsMargin(1);
                            finance1.setPayType("");
                            finance1.setDescs("竞品-咕咕费");
                            finance1.setOrderNumber(RandomUtil.generateNum2String(10));
                            financeDao.save(finance1);
                            //收入
                            Finance finance2 = new Finance();
                            finance2.setUsername("rongmei");
                            finance2.setCreateTime(System.currentTimeMillis());
                            finance2.setType("竞品");
                            finance2.setTransactionType("收入");
                            finance2.setStartPrice(startPrice);
                            finance2.setTransactionCash(BigDecimal.ZERO);
                            finance2.setServiceCash(BigDecimal.ZERO);
                            finance2.setIncomeCash(startPrice.multiply(BigDecimal.valueOf(0.2)));
                            finance2.setExpenditureCash(BigDecimal.ZERO);
                            finance2.setTransactionShare(BigDecimal.ZERO);
                            finance2.setGuguShare(pc);
                            finance2.setCopyrightCash(BigDecimal.ZERO);
                            finance2.setCancelCash(BigDecimal.ZERO);
                            finance2.setMargin(BigDecimal.ZERO);
                            finance2.setIsMargin(1);
                            finance2.setPayType("电子");
                            finance2.setDescs("竞品-咕咕费");
                            finance2.setOrderNumber(RandomUtil.generateNum2String(10));
                            financeDao.save(finance2);
                            sale.setStatus("已完成");
                        }


                        saleDao.save(sale);
                        List<Auction> auctions = auctionDao.findAllBySaleIdOrderByCreateTimeDesc(sale.getId());
                        Set<String> usernameSet = new HashSet<>();
                        for (Auction auction : auctions) {
                            usernameSet.add(auction.getUsername());
                        }
                        for (String username : usernameSet) {
                            //退还保证金
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
