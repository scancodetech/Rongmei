package com.rongmei.dao.relation;

import com.rongmei.entity.relation.ThingLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThingLikeDao extends JpaRepository<ThingLike, Integer> {

  ThingLike findThingLikeByThingIdAndUsername(int thingId, String username);

  List<ThingLike> findAllByFavoritesIdAndUsername(int favoritesId, String username);

  List<ThingLike> findAllByUsername(String username);

  long countAllByThingId(int thingId);

  @Query(nativeQuery = true, value = "select count(*) from thing_like as tl join thing as t on tl.thing_id=t.id and t.owner = ?1 and tl.create_time between ?2 and ?3")
  long countAllByThingOwner(String username, long startTime, long endTime);
}
