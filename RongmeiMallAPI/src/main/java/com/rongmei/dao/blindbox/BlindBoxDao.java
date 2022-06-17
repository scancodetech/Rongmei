package com.rongmei.dao.blindbox;

import com.rongmei.entity.blindbox.BlindBox;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlindBoxDao extends JpaRepository<BlindBox, Long> {

  List<BlindBox> findAllByThemeAndClassification(String theme, String classification);
}
