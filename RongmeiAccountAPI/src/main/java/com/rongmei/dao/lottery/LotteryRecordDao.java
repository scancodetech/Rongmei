package com.rongmei.dao.lottery;

import com.rongmei.entity.lottery.LotteryRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LotteryRecordDao extends JpaRepository<LotteryRecord, Long> {

  @Query(nativeQuery = true, value = "select * from lottery_record order by create_time desc limit ?1 offset ?2")
  List<LotteryRecord> findAllByLimitAndOffset(int limit, int offset);
}
