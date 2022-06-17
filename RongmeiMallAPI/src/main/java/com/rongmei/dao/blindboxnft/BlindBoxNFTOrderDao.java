package com.rongmei.dao.blindboxnft;

import com.rongmei.entity.blindboxnft.BlindBoxNFTOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlindBoxNFTOrderDao extends JpaRepository<BlindBoxNFTOrder, Integer> {

  BlindBoxNFTOrder findFirstByCustomerAndThemeAndIsActive(String username, String theme,
      boolean isActive);

  @Query(nativeQuery = true, value = "select count(*) from blind_box_order as bo join blind_box_nft as b on bo.blind_box_nft_id=b.id where b.owner = ?1 and bo.create_time between ?2 and ?3")
  long countAllByBlindBoxNftOwner(String username, long startTime, long endTime);
}
