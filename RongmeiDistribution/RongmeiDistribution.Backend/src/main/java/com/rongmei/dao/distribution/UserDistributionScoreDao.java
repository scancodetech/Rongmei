package com.rongmei.dao.distribution;

import com.rongmei.entity.distribution.UserDistributionScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDistributionScoreDao extends JpaRepository<UserDistributionScore, Integer> {

  UserDistributionScore findFirstByUsername(String username);
}
