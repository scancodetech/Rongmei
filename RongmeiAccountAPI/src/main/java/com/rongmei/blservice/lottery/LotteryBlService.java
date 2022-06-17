package com.rongmei.blservice.lottery;

import com.rongmei.parameters.lottery.LotterySubmitParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.lottery.LotteryGetResponse;

public interface LotteryBlService {

  SuccessResponse submitLotteryRecord(LotterySubmitParameters parameters, String username);

  LotteryGetResponse getLotteryRecord(int limit, int page);
}