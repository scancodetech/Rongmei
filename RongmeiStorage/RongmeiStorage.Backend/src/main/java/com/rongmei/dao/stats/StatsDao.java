package com.rongmei.dao.stats;

import com.rongmei.entity.stats.Stats;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatsDao extends JpaRepository<Stats, Integer> {

  List<Stats> findAllByAccessId(int accessId);
}
