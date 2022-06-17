package com.rongmei.bl.event;

import com.rongmei.blservice.event.EventOrderService;
import com.rongmei.dao.event.EventDao;
import com.rongmei.dao.event.EventOrderDao;
import com.rongmei.dao.finance.FinanceDao;
import com.rongmei.entity.event.Event;
import com.rongmei.entity.eventorder.EventOrder;
import com.rongmei.entity.finance.Finance;
import com.rongmei.exception.SystemException;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.util.BalanceUtil;
import com.rongmei.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EventOrderServiceImpl implements EventOrderService {

    private EventOrderDao eventOrderDao;
    private FinanceDao financeDao;
    private EventDao eventDao;

    @Autowired
    public EventOrderServiceImpl (EventOrderDao eventOrderDao,FinanceDao financeDao,EventDao eventDao){
        this.eventOrderDao = eventOrderDao;
        this.financeDao = financeDao;
        this.eventDao = eventDao;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response save(EventOrder eventOrder) throws SystemException {

        Optional<Event> event = eventDao.findById(eventOrder.getEventId());
        if(!event.isPresent()){
            return new WrongResponse(10002,"event_id does not exist ");
        }
        //UserInfoUtil.getUsername()
        eventOrder.setUsername(UserInfoUtil.getUsername());
        eventOrder.setCreateTime(System.currentTimeMillis());
        eventOrderDao.save(eventOrder);
        //插入记录表
        if("收入".equals(eventOrder.getType())){
            Finance finance = new Finance();
            finance.setUsername(eventOrder.getUsername());
            finance.setCreateTime(System.currentTimeMillis());
            finance.setType("活动");
            finance.setTransactionType(eventOrder.getType());
            finance.setIncomeCash(new BigDecimal(String.valueOf(eventOrder.getPrice().doubleValue())));
            finance.setExpenditureCash(BigDecimal.ZERO);
            finance.setTransactionShare(BigDecimal.ZERO);
            finance.setGuguShare(BigDecimal.ZERO);
            finance.setPayType("电子");
            finance.setDescs("活动-支出");
            finance.setCopyrightCash(BigDecimal.ZERO);
            finance.setMargin(BigDecimal.ZERO);
            finance.setIsMargin(1);
            financeDao.save(finance);
        }else {
            Finance finance = new Finance();
            finance.setUsername(eventOrder.getUsername());
            finance.setCreateTime(System.currentTimeMillis());
            finance.setType("活动");
            finance.setTransactionType(eventOrder.getType());
            finance.setIncomeCash(BigDecimal.ZERO);
            finance.setPayType("");
            finance.setDescs("活动-收入");
            finance.setExpenditureCash(new BigDecimal(String.valueOf(eventOrder.getPrice().doubleValue())));
            finance.setTransactionShare(BigDecimal.ZERO);
            finance.setGuguShare(BigDecimal.ZERO);
            finance.setCopyrightCash(BigDecimal.ZERO);
            finance.setMargin(BigDecimal.ZERO);
            finance.setIsMargin(1);
            financeDao.save(finance);
            try{
                BalanceUtil.transferByBigDecimal(eventOrder.getPrice(),eventOrder.getUsername(),"收入");

            }catch (Exception e){
                return new WrongResponse(10003,"付款失败");
            }
        }


        return new SuccessResponse("add success");
    }
}
