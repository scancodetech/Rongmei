package com.rongmei.bl.finance;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rongmei.blservice.finance.FinanceService;
import com.rongmei.dao.finance.FinanceDao;
import com.rongmei.entity.finance.Finance;
import com.rongmei.parameters.finance.FinanceParameters4MyAccount;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.finance.*;
import com.rongmei.util.BalanceUtil;
import com.rongmei.util.QueryHelper;
import com.rongmei.util.TimeUtil;
import com.rongmei.util.UserInfoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.*;

@Service
public class FinanceServiceImpl implements FinanceService {

    private FinanceDao financeDao;




    @Autowired
    private FinanceServiceImpl(FinanceDao financeDao){
        this.financeDao = financeDao;
    }






    @Override
    public Response findBySome(String type,
                               String transactionType,
                               Long startTime,
                               Long endTime,
                               Integer time,
                               Integer page,
                               Integer size) {


        Specification<Finance> specification= (Specification<Finance>) (root, query, cb) -> {


            //创建一个条件的集合
            List<Predicate> predicates = new ArrayList<Predicate>();
            //判断传过来的CityId是否为null,如果不为null就加到条件中
            if(!StringUtils.isEmpty(type)){
                //这里相当于数据库字段 name 前台传过来的值
                predicates.add(cb.equal(root.get("type"),type));
            }
            if(!StringUtils.isEmpty(transactionType)){
                //这里相当于数据库字段 name 前台传过来的值
                predicates.add(cb.equal(root.get("transactionType"),transactionType));
            }
            if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)){
                predicates.add(cb.between(root.get("createTime"),startTime,endTime));
            }else  if(!StringUtils.isEmpty(startTime)){
                //大于等于
                predicates.add(cb.ge(root.get("createTime"),startTime));
            }else if(!StringUtils.isEmpty(endTime)){
                predicates.add(cb.le(root.get("createTime"),endTime));
            }

            if(!StringUtils.isEmpty(time)){
                switch (time){
                    //今天
                    case 0:
                        predicates.add(cb.between(root.get("createTime"),
                                TimeUtil.getTodayStartTime(),
                                TimeUtil.getTodayEndTime()));
                        break;
                    //昨天
                    case 1:
                        predicates.add(cb.between(root.get("createTime"),
                                TimeUtil.getLastTime(TimeUtil.getTodayStartTime()),
                                TimeUtil.getLastTime(TimeUtil.getTodayEndTime())));
                        break;
                    //最近7天
                    case 2:
                        predicates.add(cb.between(root.get("createTime"),
                                TimeUtil.getSevenTime(TimeUtil.getTodayStartTime()),
                                TimeUtil.getNowTime()));

                        break;
                    //最近30天
                    case 3:
                        predicates.add(cb.between(root.get("createTime"),
                                TimeUtil.getMonthTime(TimeUtil.getTodayStartTime()),
                                TimeUtil.getNowTime()));
                        break;
                    default:break;
                }
            }



            //创建一个条件的集合，长度为上面满足条件的个数
            Predicate[] pre = new Predicate[predicates.size()];
            //根据id 倒序排列
            query.orderBy(cb.desc(root.get("createTime")));
            //这句大概意思就是将上面拼接好的条件返回去
            return query.where(predicates.toArray(pre)).getRestriction();

        };
        //这里我们按照返回来的条件进行查询，就能得到我们想要的结果
        Page<Finance> finances = financeDao.findAll(specification,
                PageRequest.of(page - 1, size));
        FinanceResponse financeResponse = new FinanceResponse();
        LinkedList<FinanceF> objects = Lists.newLinkedList();
        finances.forEach(finance -> {

            Long createTime = finance.getCreateTime();
            List<Finance> list = financeDao.findALLByCreateTimeBeforeAndTransactionTypeAndTypeAndIsMargin(
                    finance.getCreateTime(),
                    "收入",
                    finance.getType(),
                    finance.getIsMargin()
            );
            List<Finance> list1 = financeDao.findALLByCreateTimeBeforeAndTransactionTypeAndTypeAndIsMargin(
                    finance.getCreateTime(),
                    "支出",
                    finance.getType(),
                    finance.getIsMargin()
            );

            Map<String, BigDecimal> calculation = calculation(list, list1);
            BigDecimal income = calculation.get("income");
            BigDecimal expenditure = calculation.get("expenditure");
            BigDecimal balnce = BalanceUtil.KeepTwoDecimalPlaces(income.subtract(expenditure));
            String incomeCash = balnce.toString();
            if("支出".equals(finance.getTransactionType())){
                incomeCash = "-"+finance.getExpenditureCash();
            }else {
                incomeCash = ("+"+finance.getIncomeCash());
            }






            if("竞品".equals(finance.getType())){
                Finacnce2Sale finacnce2Sale = new Finacnce2Sale();
                BeanUtils.copyProperties(finance,finacnce2Sale);
                finacnce2Sale.setIncomeCash(incomeCash);
                finacnce2Sale.setBalance(balnce);
                objects.add(finacnce2Sale);
            }
            if("素材".equals(finance.getType())){
                Finacnce2Mterial finacnce2Mterial = new Finacnce2Mterial();
                BeanUtils.copyProperties(finance,finacnce2Mterial);
                finacnce2Mterial.setIncomeCash(incomeCash);
                finacnce2Mterial.setBalance(balnce);
                objects.add(finacnce2Mterial);

            }
            if("活动".equals(finance.getType())){
                Finacnce2Event finacnce2Event = new Finacnce2Event();
                BeanUtils.copyProperties(finance,finacnce2Event);
                finacnce2Event.setPrice(finance.getIncomeCash());
                finacnce2Event.setIncomeCash(incomeCash);
                finacnce2Event.setBalance(balnce);
                objects.add(finacnce2Event);
            }
            if("盒蛋".equals(finance.getType())){
                Finacnce2Box finacnce2Box = new Finacnce2Box();
                BeanUtils.copyProperties(finance,finacnce2Box);
                finacnce2Box.setIncomeCash(incomeCash);
                finacnce2Box.setBalance(balnce);
                objects.add(finacnce2Box);
            }

        });
        //循环结束
        financeResponse.setReturnList(objects);

        //计算总额
        List<Finance> incomes = financeDao.findAllByTransactionTypeAndIsMargin("收入",1);
        List<Finance> expenditures = financeDao.findAllByTransactionTypeAndIsMargin("支出",1);
        Map<String, BigDecimal> calculation = calculation(incomes, expenditures);
        financeResponse.setIncome(calculation.get("income").toString());
        financeResponse.setExpenditure(calculation.get("expenditure").toString());
        return financeResponse;
    }

    public Map<String,BigDecimal> calculation(List<Finance> incomes, List<Finance> expenditures){
        LinkedHashMap<String, BigDecimal> map = Maps.newLinkedHashMap();
        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expenditure = BigDecimal.ZERO;
        for (Finance finance : incomes) {

            income = income.add(finance.getIncomeCash());
        }
        for (Finance finance : expenditures) {
            Integer isMargin = finance.getIsMargin();
            expenditure = expenditure.add(finance.getExpenditureCash());
        }
        map.put("income",income);
        map.put("expenditure",expenditure);
        return map;
    }

    @Override
    public Response saveByserciceCash(Double startPrice,String username,Double margin) {

        Finance finance3 = new Finance();


        //支出 退还保证金
        //UserInfoUtil.getUsername()
        finance3.setUsername(username);
        finance3.setCreateTime(System.currentTimeMillis());
        finance3.setType("竞品");
        finance3.setTransactionType("收入");
        finance3.setStartPrice(BigDecimal.valueOf(startPrice));
        finance3.setTransactionCash(BigDecimal.ZERO);
        finance3.setPayType("电子");
        finance3.setDescs("竞品-保证金");
        finance3.setTransactionCash(BigDecimal.ZERO);
        finance3.setServiceCash(BigDecimal.ZERO);
        finance3.setIncomeCash(BigDecimal.ZERO);
        finance3.setExpenditureCash(BigDecimal.valueOf(startPrice).multiply(BigDecimal.valueOf(0.2)));
        finance3.setTransactionShare(BigDecimal.ZERO);
        finance3.setGuguShare(BigDecimal.ZERO);
        if(margin != null){
            finance3.setCopyrightCash(BigDecimal.valueOf(margin));
        }else{
            finance3.setCopyrightCash(BigDecimal.ZERO);
        }
        finance3.setMargin(BigDecimal.valueOf(startPrice).multiply(BigDecimal.valueOf(0.2)));
        finance3.setIsMargin(0);
        finance3.setCancelCash(BigDecimal.ZERO);
        financeDao.save(finance3);
        return new SuccessResponse("add success");
    }

    @Override
    public Response saveByserciceCash1(Double startPrice,String username) {
        Finance finance3 = new Finance();
        finance3.setUsername(username);
        finance3.setCreateTime(System.currentTimeMillis());
        finance3.setType("竞品");
        finance3.setTransactionType("支出");
        finance3.setStartPrice(BigDecimal.valueOf(startPrice).divide(BigDecimal.valueOf(0.2)));
        finance3.setTransactionCash(BigDecimal.ZERO);
        finance3.setServiceCash(BigDecimal.ZERO);
        finance3.setPayType("电子");
        finance3.setDescs("竞品-保证金");
        finance3.setIncomeCash(BigDecimal.ZERO);
        finance3.setExpenditureCash(BigDecimal.valueOf(startPrice));
        finance3.setTransactionShare(BigDecimal.ZERO);
        finance3.setGuguShare(BigDecimal.ZERO);
        finance3.setCopyrightCash(BigDecimal.ZERO);
        finance3.setMargin(BigDecimal.valueOf(startPrice));
        finance3.setIsMargin(0);
        finance3.setCancelCash(BigDecimal.ZERO);
        financeDao.save(finance3);
        return new SuccessResponse("add success");
    }

    @Override
    public Response save(Finance finance) {
        if (!financeDao.exists(Example.of(finance))) {
            financeDao.save(finance);
            return new SuccessResponse("add success");
        }
        return new WrongResponse(10002,"finance is exists");
    }

    @Override
    public Response findByUserAccount(FinanceParameters4MyAccount financeParameters4MyAccount, Pageable pageable) {
        FinanceParameters4MyAccount init = init(financeParameters4MyAccount);
        //init.setUsername("admin");
        PageRequest of = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        //financeParameters4MyAccount.setUsername(UserInfoUtil.getUsername());
        Page<Finance> all = financeDao.findAll((root, query, criteriaBuilder) -> QueryHelper.getPredicate(root, init, criteriaBuilder),of);
        List<Finance> content = all.getContent();
        ArrayList<Finacnce2UserAccount> finances = Lists.newArrayList();
        for (Finance finance : content) {
            finances.add(new Finacnce2UserAccount(finance));
        }
        return new FinacnceByMyAccountResponse(finances);
    }

    private FinanceParameters4MyAccount init(FinanceParameters4MyAccount financeParameters4MyAccount){
        financeParameters4MyAccount.setUsername(UserInfoUtil.getUsername());
        if(financeParameters4MyAccount.getCreateTime() == null){
            financeParameters4MyAccount.setCreateTime(System.currentTimeMillis());
        }
        if(financeParameters4MyAccount.getTransactionType() != null){
            if(financeParameters4MyAccount.getTransactionType().equals("收入")){
                financeParameters4MyAccount.setTransactionType("支出");
                return financeParameters4MyAccount;
            }
            if(financeParameters4MyAccount.getTransactionType().equals("支出")){
                financeParameters4MyAccount.setTransactionType("收入");
                return financeParameters4MyAccount;
            }
        }
        return financeParameters4MyAccount;
    }

    @Override
    public Response findById(long id) {
        Finance byIdAndUsername = financeDao.findByIdAndUsername(id, "admin");
        if(byIdAndUsername != null){
            return new FinanceByPersonAccount(new Finacnce2UserAccount(byIdAndUsername));
        }
        return new WrongResponse(10002,"username or id not exist");
    }


}
