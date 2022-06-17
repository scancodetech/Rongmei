package com.rongmei.dao.groupshopping;

import com.rongmei.entity.groupshopping.GroupShoppingParticipate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupShoppingParticipateDao extends
    JpaRepository<GroupShoppingParticipate, Integer> {

  long countAllByRelationId(int relationId);
}
