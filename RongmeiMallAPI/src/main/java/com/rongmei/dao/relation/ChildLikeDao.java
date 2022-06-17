package com.rongmei.dao.relation;

import com.rongmei.entity.relation.ChildLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildLikeDao extends JpaRepository<ChildLike, Integer> {

  ChildLike findChildLikeByChildIdAndUsername(int childId, String username);

  long countAllByChildId(int childId);
}