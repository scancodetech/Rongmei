package com.rongmei.dao.boxegg;

import com.rongmei.entity.boxegg.BoxEgg;
import com.rongmei.entity.boxorder.BoxOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoxEggDao extends JpaRepository<BoxEgg, Integer> {

  List<BoxEgg> findAllByStatus(int status);
}
