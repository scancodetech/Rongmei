package com.rongmei.dao.stats;

import com.rongmei.entity.stats.UseStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UseStatsDao extends JpaRepository<UseStats, Integer> {

  int countAllByCreateTimeBetween(long startCreateTime, long endCreateTime);
}
