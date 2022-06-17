package com.rongmei.dao.distribution;

import com.rongmei.entity.distribution.UserDistribution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDistributionDao extends JpaRepository<UserDistribution, Integer> {

  UserDistribution findFirstByUsername(String username);

  UserDistribution findFirstByCode(String code);
}
