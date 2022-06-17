package com.rongmei.bl.lottery;

import com.rongmei.blservice.lottery.LotteryBlService;
import com.rongmei.dao.lottery.LotteryRecordDao;
import com.rongmei.entity.lottery.LotteryRecord;
import com.rongmei.parameters.lottery.LotterySubmitParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.lottery.LotteryGetResponse;
import com.rongmei.response.lottery.LotteryItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryBlServiceImpl implements LotteryBlService {

  private final LotteryRecordDao lotteryRecordDao;

  @Autowired
  public LotteryBlServiceImpl(LotteryRecordDao lotteryRecordDao) {
    this.lotteryRecordDao = lotteryRecordDao;
  }

  @Override
  public SuccessResponse submitLotteryRecord(LotterySubmitParameters parameters, String username) {
    LotteryRecord lotteryRecord = new LotteryRecord(username, parameters.getPrizeType(),
        parameters.getPrizeName(), parameters.getName(), parameters.getPhone(),
        parameters.getEmail(), System.currentTimeMillis(), System.currentTimeMillis());
    lotteryRecordDao.save(lotteryRecord);
    return new SuccessResponse("submit success");
  }

  @Override
  public LotteryGetResponse getLotteryRecord(int limit, int page) {
    List<LotteryRecord> lotteryRecordList = lotteryRecordDao
        .findAllByLimitAndOffset(limit, (page - 1) * limit);
    List<LotteryItem> lotteryItems = new ArrayList<>();
    for (LotteryRecord lotteryRecord : lotteryRecordList) {
      lotteryItems.add(new LotteryItem(lotteryRecord.getId(), lotteryRecord.getUsername(),
          lotteryRecord.getName(), lotteryRecord.getPhone(), lotteryRecord.getEmail(),
          lotteryRecord.getPrizeType(), lotteryRecord.getPrizeName(), lotteryRecord.getCreateTime(),
          lotteryRecord.getUpdateTime()));
    }
    return new LotteryGetResponse(lotteryItems);
  }
}
