package com.rongmei.blservice.money;

import com.rongmei.exception.SystemException;
import com.rongmei.parameters.money.RechargeParameters;
import com.rongmei.response.SuccessResponse;

public interface MoneyBlService {

  SuccessResponse recharge(RechargeParameters parameters) throws SystemException;
}
