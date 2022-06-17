package com.rongmei.util;

import com.google.common.collect.Maps;
import com.rongmei.dao.pcuser.PcUserDao;
import com.rongmei.entity.PcUser.PcUser;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MoneyUtil {
    /**
     * 只扣除用户 竞拍价+保证金
     * @param electronic
     * @param satrtPrice
     * @param needCopyrightTax
     * @param type
     * @return
     */
    public static Map<String, BigDecimal> updateUserElectronic (BigDecimal electronic, BigDecimal satrtPrice, Boolean
            needCopyrightTax, String type){

        switch (type) {
            case "已售出":
                //用户支付 只有 最终价+保证金

                if (needCopyrightTax) {
                    return calculateByMargin(electronic, satrtPrice);

                } else {
                    //给 创作者返 85

                    return calculateUnMargin(electronic, satrtPrice);
                }

            case "已取消":
                return calculateByCancel(electronic, satrtPrice);
            case "咕咕":
                return calculateByGugu(electronic, satrtPrice);
            default:
                return null;
        }


    }
    private static Map<String, BigDecimal> calculateByGugu (BigDecimal electronic, BigDecimal startPrice){
        HashMap<String, BigDecimal> rentrnMap = Maps.newHashMap();
        //咕咕
        BigDecimal gugu = startPrice.multiply(BigDecimal.valueOf(0.2));
        //平台
        BigDecimal pcguugu = gugu.multiply(BigDecimal.valueOf(0.85));
        //创作者
        BigDecimal usergugu = gugu.multiply(BigDecimal.valueOf(0.15));
        rentrnMap.put("creator", usergugu);
        rentrnMap.put("pc", pcguugu);
        return rentrnMap;

    }

    private static Map<String, BigDecimal> calculateByCancel (BigDecimal electronic, BigDecimal startPrice){
        HashMap<String, BigDecimal> rentrnMap = Maps.newHashMap();
        //取消
        BigDecimal pc = startPrice.multiply(BigDecimal.valueOf(0.03));
        rentrnMap.put("pc", pc);
        return rentrnMap;

    }


    public static Map<String, BigDecimal> calculateByMargin (BigDecimal electronic, BigDecimal startPrice){
        HashMap<String, BigDecimal> rentrnMap = Maps.newHashMap();
        //给 创作者返 75
        BigDecimal chuangzuo = electronic.multiply(BigDecimal.valueOf(0.75));

        BigDecimal pc1 = electronic.multiply(BigDecimal.valueOf(0.15)).
                add(electronic.multiply(BigDecimal.valueOf(0.1))).
                add(startPrice.multiply(BigDecimal.valueOf(0.2)));

        BigDecimal pcShare = electronic.multiply(BigDecimal.valueOf(0.15));

        BigDecimal user1 = startPrice.multiply(BigDecimal.valueOf(0.2));
        //支出
        rentrnMap.put("creator", chuangzuo);
        //收入
        rentrnMap.put("pc", pc1);
        rentrnMap.put("pcShare", pcShare);
        //支出
        rentrnMap.put("user", user1);
        return rentrnMap;
    }


    public static Map<String, BigDecimal> calculateUnMargin (BigDecimal electronic, BigDecimal startPrice){
        HashMap<String, BigDecimal> rentrnMap = Maps.newHashMap();
        BigDecimal chuangzuo = electronic.multiply(BigDecimal.valueOf(0.85));
        BigDecimal pc1 = electronic.multiply(BigDecimal.valueOf(0.15)).
                add(startPrice.multiply(BigDecimal.valueOf(0.2)));
        BigDecimal user1 = startPrice.multiply(BigDecimal.valueOf(0.2));
        rentrnMap.put("creator", chuangzuo);
        rentrnMap.put("pc", pc1);
        rentrnMap.put("user", user1);
        return rentrnMap;
    }


    public static PcUser updatePcUser(PcUserDao pcUserDao,BigDecimal moeny, String type){
        PcUser one = pcUserDao.getOne(1);

        PcUser pcUser = new PcUser();
        BigDecimal income = one.getIncome();
        BigDecimal expenditure = one.getExpenditure();
        BigDecimal balance = one.getBalance();
        if(income != null && expenditure != null){
            if("收入".equals(type)){
                income = income.add(moeny);
            }else {
                expenditure = expenditure.add(moeny);
            }
            one.setIncome(income);
            one.setExpenditure(expenditure);
            one.setBalance(income.subtract(expenditure));
        }
        return one;
    }


}
