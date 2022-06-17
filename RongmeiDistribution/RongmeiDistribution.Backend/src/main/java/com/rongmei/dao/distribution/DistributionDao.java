package com.rongmei.dao.distribution;

import com.rongmei.entity.distribution.Distribution;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistributionDao extends JpaRepository<Distribution, Integer> {

  List<Distribution> findAllByFromUsername(String username);

  Distribution findFirstByToUsername(String username);
}
