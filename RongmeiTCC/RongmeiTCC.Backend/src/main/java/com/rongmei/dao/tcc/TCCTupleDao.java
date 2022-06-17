package com.rongmei.dao.tcc;

import com.rongmei.entity.tcc.TCCTuple;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TCCTupleDao extends JpaRepository<TCCTuple, Integer> {

  TCCTuple findFirstByKey(String key);
}
