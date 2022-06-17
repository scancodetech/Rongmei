package com.rongmei.dao.relation;

import com.rongmei.entity.relation.BlindBoxLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlindBoxLikeDao extends JpaRepository<BlindBoxLike, Integer> {

  BlindBoxLike findBlindBoxLikeByBlindBoxIdAndUsername(int blindBoxId, String username);

  long countAllByBlindBoxId(int blindBoxNftId);

  @Query(nativeQuery = true, value = "select count(*) from blind_box_like as bl join blind_box_nft as b on bl.blind_box_id=b.id and b.owner = ?1 and bl.create_time between ?2 and ?3")
  long countAllByBlindBoxOwner(String username, long startTime, long endTime);
}
