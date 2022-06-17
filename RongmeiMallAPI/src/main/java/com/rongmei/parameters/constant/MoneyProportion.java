package com.rongmei.parameters.constant;

import java.math.BigDecimal;

public class MoneyProportion {


    //平台竞品保证金比例
    public static final BigDecimal PLATFORMMARGIN = new BigDecimal("0.2");

    //平台竞品咕咕比例
    public static final BigDecimal PLATFORMGUGU = new BigDecimal("0.85");

    //平台竞品取消比例
    public static final BigDecimal PLATFORMCANCEL = new BigDecimal("0.03");
    //平台服务费
    public static final BigDecimal PLATFORMSERVICE = new BigDecimal("0.15");
    //竞品版权比例
    public static final BigDecimal PLATFORMCOPYTIGHT = new BigDecimal("0.1");


    //用户咕咕费
    public static final BigDecimal USERGUGU = new BigDecimal("0.15");

    //返还用户交易额比例
    public static final BigDecimal USERTRANSACTION = new BigDecimal("0.85");


    //盲盒交易服务费比例
    public static final BigDecimal PLATFORMBOXSERVICE = new BigDecimal("0.15");


    //创作者拿的钱
    public static final double CRRATORMSERVICE = 0.85;

    //平台素材独家分成
    public static final BigDecimal PLATFORMMATERIALEXCLUSIVE = new BigDecimal("0.6");


    // 创作者素材独家分成
    public static final BigDecimal CRRATORMATERIALEXCLUSIVE = new BigDecimal("0.4");

    //平台素材非独家分成
    public static final BigDecimal PLATFORMMATERIALUNEXCLUSIVE = new BigDecimal("0.7");


    //平台素材非独家分成
    public static final BigDecimal CRRATORMMATERIALUNEXCLUSIVE = new BigDecimal("0.3");


}
