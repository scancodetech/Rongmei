package com.rongmei.bl.blindboxnft;

import com.rongmei.blservice.blindboxnft.BlindBoxSaleBlService;
import com.rongmei.dao.blindboxnft.BlindBoxNFTDao;
import com.rongmei.dao.blindboxnft.BlindBoxNFTOrderDao;
import com.rongmei.dao.blindboxnft.BlindBoxSaleDao;

import com.rongmei.dao.finance.FinanceDao;

import com.rongmei.dao.relation.BlindBoxLikeDao;
import com.rongmei.entity.blindboxnft.BlindBoxNFT;
import com.rongmei.entity.blindboxnft.BlindBoxNFTOrder;
import com.rongmei.entity.blindboxnft.BlindBoxSale;

import com.rongmei.entity.finance.Finance;
import com.rongmei.exception.SystemException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.blindboxnft.BlindBoxOrderCancelParameter;
import com.rongmei.parameters.blindboxnft.BlindBoxOrderFinishParameter;
import com.rongmei.parameters.blindboxnft.BlindBoxSaleUpdateParameters;
import com.rongmei.parameters.blindboxnft.BlindBoxThemeQueueParameter;

import com.rongmei.parameters.constant.MoneyProportion;

import com.rongmei.response.SuccessResponse;
import com.rongmei.response.blindboxnft.BlindBoxNFTSaleGetResponse;
import com.rongmei.response.blindboxnft.BlindBoxSaleDetailResponse;
import com.rongmei.response.blindboxnft.BlindBoxSaleItem;
import com.rongmei.response.blindboxnft.BlindBoxSaleStatisticsResponse;

import com.rongmei.util.*;

import com.rongmei.util.FormatDateTime;
import com.rongmei.util.UserInfoUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


@Service
public class BlindBoxSaleBlServiceImpl implements BlindBoxSaleBlService {

  private final BlindBoxNFTDao blindBoxNFTDao;
  private final BlindBoxSaleDao blindBoxSaleDao;
  private final BlindBoxNFTOrderDao blindBoxNFTOrderDao;
  private final BlindBoxLikeDao blindBoxLikeDao;
  private final FinanceDao financeDao;

  @Autowired
  public BlindBoxSaleBlServiceImpl(BlindBoxNFTDao blindBoxNFTDao,
      BlindBoxSaleDao blindBoxSaleDao,
      BlindBoxNFTOrderDao blindBoxNFTOrderDao,
      BlindBoxLikeDao blindBoxLikeDao,
      FinanceDao financeDao) {
    this.blindBoxNFTDao = blindBoxNFTDao;
    this.blindBoxSaleDao = blindBoxSaleDao;
    this.blindBoxLikeDao = blindBoxLikeDao;
    this.blindBoxNFTOrderDao = blindBoxNFTOrderDao;
    this.financeDao = financeDao;
  }

  @Override
  public SuccessResponse updateSale(BlindBoxSaleUpdateParameters parameters, String username)
      throws ThingIdDoesNotExistException {
    if (parameters.getId() == 0) {
      Optional<BlindBoxNFT> optionalBlindBoxNFT = blindBoxNFTDao
          .findById(parameters.getBlindBoxNftId());
      if (optionalBlindBoxNFT.isPresent()) {
        BlindBoxNFT blindBoxNFT = optionalBlindBoxNFT.get();
        BlindBoxSale sale = new BlindBoxSale(parameters.getPrice(), username,
            parameters.getBlindBoxNftId(),
            blindBoxNFT.getTheme(), blindBoxNFT.getClassification(), System.currentTimeMillis(),
            System.currentTimeMillis(), true, 0);
        blindBoxSaleDao.save(sale);
        return new SuccessResponse("add success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    } else {
      Optional<BlindBoxSale> optionalBlindBoxSale = blindBoxSaleDao.findById(parameters.getId());
      if (!optionalBlindBoxSale.isPresent()) {
        throw new ThingIdDoesNotExistException();
      }
      BlindBoxSale blindBoxSale = optionalBlindBoxSale.get();
      Optional<BlindBoxNFT> optionalBlindBoxNFT = blindBoxNFTDao
          .findById(parameters.getBlindBoxNftId());
      if (optionalBlindBoxNFT.isPresent()) {
        BlindBoxNFT blindBoxNFT = optionalBlindBoxNFT.get();
        blindBoxSale.setPrice(parameters.getPrice());
        blindBoxSale.setBlindBoxNftId(parameters.getBlindBoxNftId());
        blindBoxSale.setTheme(blindBoxNFT.getTheme());
        blindBoxSale.setClassification(blindBoxNFT.getClassification());
        blindBoxSale.setUpdateTime(System.currentTimeMillis());
        if (blindBoxSale.getDraftStatus() == 1) {
          blindBoxSale.setDraftStatus(0);
        }
        blindBoxSaleDao.save(blindBoxSale);
        return new SuccessResponse("update success");
      } else {
        throw new ThingIdDoesNotExistException();
      }
    }
  }

  @Override
  public SuccessResponse deleteSale(int saleId) throws ThingIdDoesNotExistException {
    Optional<BlindBoxSale> optionalBlindBoxSale = blindBoxSaleDao.findById(saleId);
    if (!optionalBlindBoxSale.isPresent()) {
      throw new ThingIdDoesNotExistException();
    }
    BlindBoxSale blindBoxSale = optionalBlindBoxSale.get();
    blindBoxSale.setActive(false);
    blindBoxSaleDao.save(blindBoxSale);
    return new SuccessResponse("delete success");
  }

  @Override
  public BlindBoxSaleDetailResponse getBlindBoxSaleDetail(long id)
      throws ThingIdDoesNotExistException {
    Optional<BlindBoxSale> optionalBlindBoxSale = blindBoxSaleDao.findById((int) id);
    if (!optionalBlindBoxSale.isPresent()) {
      throw new ThingIdDoesNotExistException();
    }
    BlindBoxSale blindBoxSale = optionalBlindBoxSale.get();
    Optional<BlindBoxNFT> optionalBlindBoxNFT = blindBoxNFTDao
        .findById(blindBoxSale.getBlindBoxNftId());
    if (!optionalBlindBoxNFT.isPresent()) {
      throw new ThingIdDoesNotExistException();
    }
    BlindBoxNFT blindBoxNFT = optionalBlindBoxNFT.get();
    int likeNum = (int) blindBoxLikeDao.countAllByBlindBoxId(blindBoxNFT.getId());
    return new BlindBoxSaleDetailResponse(blindBoxSale.getId(), blindBoxSale.getBlindBoxNftId(),
        blindBoxNFT.getName(),
        blindBoxNFT.getUrl(), blindBoxNFT.getPrice(), blindBoxNFT.getDescription(),
        blindBoxNFT.getAuthor(), blindBoxNFT.getOwner(), blindBoxNFT.getTokenId(),
        blindBoxNFT.getTheme(), blindBoxNFT.getClassification(), blindBoxNFT.getChainTxId(),
        blindBoxNFT.getConfirmationLetterUrl(), blindBoxNFT.getCreateTime(),
        blindBoxNFT.getUpdateTime(), blindBoxNFT.getIsActive(), likeNum);
  }

  @Override
  public BlindBoxNFTSaleGetResponse getRecommendBlindBoxSaleList(String username, int page,
      int limit) {
    List<BlindBoxSale> blindBoxSaleList = blindBoxSaleDao
        .findAllByLimitAndOffset(limit, page * limit);
    return new BlindBoxNFTSaleGetResponse(packBlindBoxSaleItems(blindBoxSaleList));
  }

  @Override
  public SuccessResponse orderQueueBlindBoxTheme(BlindBoxThemeQueueParameter parameter,
      String username) {
    BlindBoxNFTOrder blindBoxNFTOrder = new BlindBoxNFTOrder(
        FormatDateTime.currentRandomTimeString(), 0, parameter.getTheme(), "", "queue",
        UserInfoUtil.getUsername(), 0, 0, System.currentTimeMillis(), System.currentTimeMillis(),
        false);
    blindBoxNFTOrderDao.save(blindBoxNFTOrder);
    return new SuccessResponse("queue success");
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SuccessResponse orderFinishBlindBox(BlindBoxOrderFinishParameter parameter,
      String username) throws ThingIdDoesNotExistException {
    BlindBoxNFTOrder blindBoxNFTOrder = blindBoxNFTOrderDao
        .findFirstByCustomerAndThemeAndIsActive(username, parameter.getTheme(), true);
    if (blindBoxNFTOrder == null) {
      throw new ThingIdDoesNotExistException();
    }
    Optional<BlindBoxSale> optionalBlindBoxSale = blindBoxSaleDao
        .findById(parameter.getBlindBoxSaleId());
    if (optionalBlindBoxSale.isPresent()) {
      BlindBoxSale blindBoxSale = optionalBlindBoxSale.get();
      Optional<BlindBoxNFT> optionalBlindBoxNFT = blindBoxNFTDao
          .findById(blindBoxSale.getBlindBoxNftId());
      if (!optionalBlindBoxNFT.isPresent()) {
        throw new ThingIdDoesNotExistException();
      }
      BlindBoxNFT blindBoxNFT = optionalBlindBoxNFT.get();
      blindBoxNFTOrder.setBlindBoxNftId(blindBoxSale.getBlindBoxNftId());
      blindBoxNFTOrder.setAvatarUrl(blindBoxNFT.getUrl());
      blindBoxNFTOrder.setPrice(blindBoxSale.getPrice());
      blindBoxNFTOrder.setStatus("finished");

      blindBoxNFTOrderDao.save(blindBoxNFTOrder);
      //在此下单
      Finance finance = new Finance();
      finance.setUsername(UserInfoUtil.getUsername());
      finance.setCreateTime(System.currentTimeMillis());
      finance.setType("盒蛋");
      finance.setTransactionType("收入");
      BigDecimal transactionCash = BigDecimal.valueOf(blindBoxSale.getPrice());
      finance.setTransactionCash(transactionCash);
      BigDecimal serviceCash = transactionCash.multiply(MoneyProportion.PLATFORMBOXSERVICE);
      finance.setServiceCash(serviceCash);
      finance.setIncomeCash(transactionCash);
      finance.setExpenditureCash(BigDecimal.ZERO);
      finance.setPayType("电子");
      finance.setDescs("盒蛋-支出");
      finance.setTransactionShare(BigDecimal.ZERO);
      finance.setGuguShare(BigDecimal.ZERO);
      finance.setCopyrightCash(BigDecimal.ZERO);
      finance.setMargin(BigDecimal.ZERO);
      finance.setIsMargin(1);
      finance.setOrderNumber(RandomUtil.generateNum2String(10));
      financeDao.save(finance);
      return new SuccessResponse("order success");
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public BlindBoxNFTSaleGetResponse getBlindBoxSaleList(String theme, String classification) {
    List<BlindBoxSale> blindBoxSales = blindBoxSaleDao
        .findAllByThemeAndClassificationAndIsActive(theme, classification, true);
    return new BlindBoxNFTSaleGetResponse(packBlindBoxSaleItems(blindBoxSales));
  }

  @Override
  public SuccessResponse orderCancelBlindBox(BlindBoxOrderCancelParameter parameter,
      String username) throws ThingIdDoesNotExistException {
    Optional<BlindBoxNFTOrder> optionalBlindBoxNFTOrder = blindBoxNFTOrderDao
        .findById(parameter.getOrderId());
    if (!optionalBlindBoxNFTOrder.isPresent()) {
      throw new ThingIdDoesNotExistException();
    }
    BlindBoxNFTOrder blindBoxNFTOrder = optionalBlindBoxNFTOrder.get();
    blindBoxNFTOrder.setStatus("failed");
    blindBoxNFTOrderDao.save(blindBoxNFTOrder);
    return new SuccessResponse("cancel success");
  }

  @Override
  public BlindBoxNFTSaleGetResponse getMineBlindBoxSaleList(int draftStatus) {
    List<BlindBoxSale> blindBoxSales;
    if (draftStatus == -1) {
      blindBoxSales = blindBoxSaleDao
          .findAllByUsernameAndIsActive(UserInfoUtil.getUsername(), true);
    } else {
      blindBoxSales = blindBoxSaleDao
          .findAllByUsernameAndIsActiveAndDraftStatus(UserInfoUtil.getUsername(), true,
              draftStatus);
    }
    return new BlindBoxNFTSaleGetResponse(packBlindBoxSaleItems(blindBoxSales));
  }

  @Override
  public BlindBoxSaleStatisticsResponse getUserBlindBoxSaleStatistics(String username,
      long startTime, long endTime) {
    int count = (int) blindBoxNFTOrderDao.countAllByBlindBoxNftOwner(username, startTime, endTime);
    return new BlindBoxSaleStatisticsResponse(count);
  }

  private List<BlindBoxSaleItem> packBlindBoxSaleItems(List<BlindBoxSale> blindBoxSales) {
    List<BlindBoxSaleItem> blindBoxSaleItemList = new ArrayList<>();
    for (BlindBoxSale blindBoxSale : blindBoxSales) {
      Optional<BlindBoxNFT> optionalBlindBoxNFT = blindBoxNFTDao
          .findById(blindBoxSale.getBlindBoxNftId());
      if (optionalBlindBoxNFT.isPresent()) {
        BlindBoxNFT blindBoxNFT = optionalBlindBoxNFT.get();
        blindBoxSaleItemList.add(
            new BlindBoxSaleItem(blindBoxSale.getId(), blindBoxSale.getBlindBoxNftId(),
                blindBoxNFT.getName(), blindBoxNFT.getUrl(), blindBoxNFT.getPrice(),
                blindBoxNFT.getDescription(), blindBoxNFT.getTheme(),
                blindBoxNFT.getClassification(), blindBoxNFT.getCreateTime(),
                blindBoxNFT.getUpdateTime(), blindBoxNFT.getIsActive()));
      }
    }
    return blindBoxSaleItemList;
  }
}
