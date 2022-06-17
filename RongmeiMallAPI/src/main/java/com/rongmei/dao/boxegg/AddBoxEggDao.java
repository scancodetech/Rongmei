package com.rongmei.dao.boxegg;

import com.rongmei.entity.boxegg.AddBoxEgg;
import com.rongmei.entity.boxegg.BoxEgg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddBoxEggDao extends JpaRepository<AddBoxEgg, Integer> {

  List<AddBoxEgg> findAllByStatus(int status);

  AddBoxEgg  findBySeriesName(String seriesName);

}
