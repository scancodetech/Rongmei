package com.rongmei.bl.order;

import com.rongmei.blservice.order.OrderBlService;
import com.rongmei.dao.auction.ThingDao;
import com.rongmei.dao.commodity.CommodityDao;
import com.rongmei.dao.finance.FinanceDao;
import com.rongmei.dao.order.OrderDao;
import com.rongmei.entity.commodity.Commodity;
import com.rongmei.entity.finance.Finance;
import com.rongmei.entity.order.Order;
import com.rongmei.exception.SystemException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.constant.MoneyProportion;
import com.rongmei.parameters.order.OrderUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.order.OrderDetailResponse;
import com.rongmei.response.order.OrderGetResponse;
import com.rongmei.response.order.OrderItem;
import com.rongmei.util.BalanceUtil;
import com.rongmei.util.FormatDateTime;
import com.rongmei.util.RandomUtil;
import com.rongmei.util.UserInfoUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderBlServiceImpl implements OrderBlService {

  private final OrderDao orderDao;
  private final CommodityDao commodityDao;
  private final FinanceDao financeDao;
  private final ThingDao thingDao;

  @Autowired
  public OrderBlServiceImpl(OrderDao orderDao, CommodityDao commodityDao,FinanceDao financeDao,ThingDao thingDao) {
    this.orderDao = orderDao;
    this.commodityDao = commodityDao;
    this.financeDao = financeDao;
    this.thingDao = thingDao;
  }

  @Override
  public OrderGetResponse getOrders(int userGroupId) {
    List<Integer> commodityIds = commodityDao.findAllByCreatorUserGroupId(userGroupId);
    List<Order> orders = orderDao.findAllByUserGroupIdOrRelationIdIn(userGroupId, commodityIds);
    return new OrderGetResponse(packOrderItemList(orders));
  }

  @Override
  public OrderGetResponse getMineOrders(String status, String orderType) {
    List<Order> orders;
    if (status.equals("全部")) {
      orders = orderDao.findAllByCustomerAndOrderType(UserInfoUtil.getUsername(), orderType);
    } else {
      orders = orderDao
          .findAllByCustomerAndStatusAndOrderType(UserInfoUtil.getUsername(), status, orderType);
    }
    return new OrderGetResponse(packOrderItemList(orders));
  }

  @Override
  public OrderDetailResponse getOrder(int orderId) throws ThingIdDoesNotExistException {
    Optional<Order> optionalOrder = orderDao.findById(orderId);
    if (optionalOrder.isPresent()) {
      Order order = optionalOrder.get();
      return new OrderDetailResponse(packOrderItem(order));
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public Response updateOrder(OrderUpdateParameters parameters)
      throws ThingIdDoesNotExistException {
    Order order;
    if (parameters.getId() == 0) {
      if (parameters.getOrderType() == null || parameters.getOrderType().length() == 0) {
        parameters.setOrderType("group");
      }
      Optional<Commodity> commodity = commodityDao.findById(parameters.getRelationId());
      if (!commodity.isPresent()) {
        return new WrongResponse(10002, "commodity id does not exists");
      }
      Commodity commodity1 = commodity.get();
      String author = commodity1.getAuthor();
      order = new Order(FormatDateTime.currentRandomTimeString(), parameters.getUserGroupId(),
          parameters.getLargePrice(), parameters.getAvatarUrl(),
          parameters.getUserGroupTitle(), parameters.getPageUrl(), parameters.getStatus(),
          parameters.getTotalNum(), parameters.getCompleteNum(), System.currentTimeMillis(),
            author, parameters.getOrderType(), parameters.getRelationId(),0);
      //UserInfoUtil.getUsername() 当购买人
      //Optional<Order> optionalOrder = orderDao.findById(parameters.getId());
        order.setCompleteNum(parameters.getCompleteNum());
        order.setStatus(parameters.getStatus());
        //在此插入交易记录
          Finance finance = new Finance();
          finance.setUsername(UserInfoUtil.getUsername());
          finance.setCreateTime(System.currentTimeMillis());
          finance.setType("素材");
          finance.setTransactionType("收入");
          BigDecimal transactionCash = BigDecimal.valueOf(order.getLargePrice());
          finance.setTransactionCash(transactionCash);
          BigDecimal serviceCash = transactionCash.multiply(MoneyProportion.PLATFORMBOXSERVICE);
          finance.setServiceCash(serviceCash);
          finance.setPayType("");
          finance.setIncomeCash(transactionCash);
          finance.setExpenditureCash(BigDecimal.ZERO);
          //看素材是否独家 37 46

          if ((commodity1.isExclusive())) {
            finance.setDescs("素材46");
            finance.setTransactionShare(transactionCash.multiply(BigDecimal.valueOf(0.85)).multiply(MoneyProportion.PLATFORMMATERIALEXCLUSIVE));
          } else {
            finance.setDescs("素材37");
            finance.setTransactionShare(transactionCash.multiply(BigDecimal.valueOf(0.85)).multiply(MoneyProportion.PLATFORMMATERIALUNEXCLUSIVE));
          }
          finance.setGuguShare(BigDecimal.ZERO);
          finance.setCopyrightCash(BigDecimal.ZERO);
          finance.setMargin(BigDecimal.ZERO);
          finance.setIsMargin(1);
          finance.setOrderNumber(RandomUtil.generateNum2String(10));
          financeDao.save(finance);

          //支出
          Finance finance1 = new Finance();
          finance1.setUsername(author);
          finance1.setCreateTime(System.currentTimeMillis());
          finance1.setType("素材");
          finance1.setTransactionType("支出");
          finance1.setTransactionCash(transactionCash);
          finance1.setServiceCash(serviceCash);
          finance1.setIncomeCash(transactionCash);
          finance1.setPayType("");
          finance1.setDescs("素材-创作者收益");
          //看素材是否独家 37 46
          if ((commodity1.isExclusive())) {
            finance1.setTransactionShare(transactionCash.multiply(BigDecimal.valueOf(0.85)).multiply(MoneyProportion.CRRATORMATERIALEXCLUSIVE));
            finance1.setExpenditureCash(transactionCash.multiply(BigDecimal.valueOf(0.85)).multiply(MoneyProportion.CRRATORMATERIALEXCLUSIVE));
          } else {
            finance1.setTransactionShare(transactionCash.multiply(BigDecimal.valueOf(0.85)).multiply(MoneyProportion.CRRATORMMATERIALUNEXCLUSIVE));
            finance1.setExpenditureCash(transactionCash.multiply(BigDecimal.valueOf(0.85)).multiply(MoneyProportion.CRRATORMMATERIALUNEXCLUSIVE));
          }
          finance1.setGuguShare(BigDecimal.ZERO);
          finance1.setCopyrightCash(BigDecimal.ZERO);
          finance1.setMargin(BigDecimal.ZERO);
          finance1.setIsMargin(1);
          financeDao.save(finance1);
          try {
            BalanceUtil.transferByBigDecimal(finance1.getIncomeCash(), UserInfoUtil.getUsername(), "支出");
            BalanceUtil.transferByBigDecimal(finance1.getExpenditureCash(), order.getCustomer(), "收入");
          } catch (SystemException e) {
            e.printStackTrace();
            return new WrongResponse(10003,"付款失败");
          }
    } else {
      throw new ThingIdDoesNotExistException();
    }
    orderDao.save(order);
    return new OrderDetailResponse(packOrderItem(order));
  }

  @Override
  public OrderDetailResponse isOrderExist(String status, String orderType, int relationId)
      throws ThingIdDoesNotExistException {
    Order order = orderDao
        .findFirstByCustomerAndStatusAndOrderTypeAndRelationId(UserInfoUtil.getUsername(), status,
            orderType, relationId);
    if (order == null) {
      throw new ThingIdDoesNotExistException();
    } else {
      return new OrderDetailResponse(packOrderItem(order));
    }
  }

  private List<OrderItem> packOrderItemList(List<Order> orders) {
    List<OrderItem> orderItems = new ArrayList<>();
    for (Order order : orders) {
      orderItems.add(packOrderItem(order));
    }
    return orderItems;
  }

  private OrderItem packOrderItem(Order order) {
    return new OrderItem(order.getId(), order.getOrderId(), order.getUserGroupId(),
        order.getLargePrice(), order.getAvatarUrl(),
        order.getUserGroupTitle(), order.getPageUrl(), order.getStatus(), order.getTotalNum(),
        order.getCompleteNum(), order.getCreateTime(), order.getOrderType(), order.getRelationId());
  }
}
