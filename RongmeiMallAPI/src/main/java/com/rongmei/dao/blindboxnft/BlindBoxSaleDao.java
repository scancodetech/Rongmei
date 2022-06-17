package com.rongmei.dao.blindboxnft;

import com.rongmei.entity.blindboxnft.BlindBoxSale;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlindBoxSaleDao extends JpaRepository<BlindBoxSale, Integer> {

  @Query(nativeQuery = true, value = "select b.* from blind_box_sale as b where b.is_active = 1 order by s.update_time desc limit ?1 offset ?2")
  List<BlindBoxSale> findAllByLimitAndOffset(int limit, int offset);

  List<BlindBoxSale> findAllByThemeAndClassificationAndIsActive(String theme, String classification,
      boolean isActive);

  List<BlindBoxSale> findAllByUsernameAndIsActive(String username, boolean isActive);

  List<BlindBoxSale> findAllByUsernameAndIsActiveAndDraftStatus(String username, boolean isActive,
      int draftStatus);

  List<BlindBoxSale> findAllByDraftStatusAndIsActiveOrderByUpdateTimeDesc(int draftStatus,
      boolean isActive);
}
