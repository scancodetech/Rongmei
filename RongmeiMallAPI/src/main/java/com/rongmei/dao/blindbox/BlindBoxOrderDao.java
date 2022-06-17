package com.rongmei.dao.blindbox;

import com.rongmei.entity.blindbox.BlindBoxOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlindBoxOrderDao extends JpaRepository<BlindBoxOrder, Long> {

  List<BlindBoxOrder> findAllByBlindBoxIdAndStatusIn(long blindBoxId, List<String> status);
}