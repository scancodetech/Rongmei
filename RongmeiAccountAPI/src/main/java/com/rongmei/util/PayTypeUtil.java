package com.rongmei.util;

public class PayTypeUtil {

    public static Integer getPayType(String aliAccount,String wechatAccount){
        if(null != aliAccount && "0".equals(aliAccount)){
            return 0;
        }
        if(null != wechatAccount && "0".equals(wechatAccount)){
            return 1;
        }
        return -1;
    }
}
