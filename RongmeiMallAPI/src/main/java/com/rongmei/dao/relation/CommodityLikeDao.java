package com.rongmei.dao.relation;

import com.rongmei.entity.relation.CommodityLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommodityLikeDao extends JpaRepository<CommodityLike, Integer> {

  CommodityLike findCommodityLikeByCommodityIdAndUsername(int commodityId, String username);

  @Query(nativeQuery = true, value = "select c.* from commodity_like as c join favorites as f on c.favorites_id=f.id where f.is_active = false and c.username = ?1")
  List<CommodityLike> findAllByFavoritesDefault(String username);

  List<CommodityLike> findAllByFavoritesIdAndUsername(int favoritesId, String username);

  List<CommodityLike> findAllByUsername(String username);

  long countAllByCommodityId(int commodityId);

  @Query(nativeQuery = true, value = "select count(*) from commodity_like as cl join commodity as c on cl.commodity_id=c.id and c.author = ?1 and cl.create_time between ?2 and ?3")
  long countAllByCommodityOwner(String username, long startTime, long endTime);
}
