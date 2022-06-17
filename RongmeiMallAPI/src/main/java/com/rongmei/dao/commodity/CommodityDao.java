package com.rongmei.dao.commodity;

import com.rongmei.entity.commodity.Commodity;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommodityDao extends JpaRepository<Commodity, Integer> {

  @Query(nativeQuery = true, value = "select c.* from commodity as c join commodity_tags as ct on c.id = ct.commodity_id where c.title like ?1 and ct.tags in (?2) and c.draft_status = ?3 order by c.create_time desc limit ?4 offset ?5")
  List<Commodity> findCommoditiesByTitleLikeAndTagsInOrderByCreateTimeDesc(String key,
      List<String> tags, int draftStatus, int limit, int offset);

  @Query(nativeQuery = true, value = "select c.* from commodity as c where c.title like ?1 and c.draft_status = ?2 order by c.create_time desc limit ?3 offset ?4")
  List<Commodity> findCommoditiesByTitleLikeOrderByCreateTimeDesc(String key, int draftStatus,
      int limit, int offset);

  List<Commodity> findCommoditiesByCreatorUserGroupId(int userGroupId);

  @Query("select c.id from Commodity c where c.creatorUserGroupId = ?1")
  List<Integer> findAllByCreatorUserGroupId(int userGroupId);

  @Query(nativeQuery = true, value = "select c.* from commodity as c order by c.create_time desc limit ?1")
  List<Commodity> findByLimit(int count);

  @Query(nativeQuery = true, value = "select c.* from commodity as c where t.title like ?1 or t.description like ?1 order by s.create_time desc limit ?2 offset ?3")
  List<Commodity> findByKeyWithLimitAndOffset(String key, int limit, int offset);

  @Query(nativeQuery = true, value = "select c.* from commodity as c order by c.create_time desc limit ?1 offset ?2")
  List<Commodity> findByLimitAndOffset(int limit, int offset);

  @Query(nativeQuery = true, value = "select count(c.*) from commodity as c where t.title like ?1 or t.description like ?1")
  int countByKeyWithLimitAndOffset(String key);

  List<Commodity> findAllByIdIn(Set<Integer> ids);

  List<Commodity> findAllByDraftStatusOrderByUpdateTimeDesc(int draftStatus);

  List<Commodity> findAllByAuthor(String username);

  List<Commodity> findAllByAuthorAndCreateTimeIsBetween(String username, long startTime,
      long endTime);

  List<Commodity> findAllByAuthorAndDraftStatus(String username, int draftStatus);
}

