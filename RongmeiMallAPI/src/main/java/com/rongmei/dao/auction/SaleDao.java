package com.rongmei.dao.auction;

import com.rongmei.entity.auction.Sale;
import com.rongmei.entity.commodity.Commodity;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SaleDao extends JpaRepository<Sale, Integer>, JpaSpecificationExecutor<Sale> {

  @Query(nativeQuery = true, value = "select s.* from thing as t join sale as s on t.id = s.thing_id join sale_tags as st on s.id = st.sale_id where s.is_active = 1 and s.draft_status = 2 and st.tags in (?4) and t.name like ?1 order by s.create_time desc limit ?2 offset ?3")
  List<Sale> findAllByNameLikeAndTagsInOrderByCreateTimeDesc(String key, int limit, int offset,
      List<String> tags);

  @Query(nativeQuery = true, value = "select s.* from thing as t join sale as s on t.id = s.thing_id join sale_tags as st on s.id = st.sale_id where s.is_active = 1 and s.draft_status = 2 and st.tags in (?4) and t.name and t.author = t.owner like ?1 order by s.create_time desc limit ?2 offset ?3")
  List<Sale> findAllByNameLikeAndOwneredByAuthorAndTagsInOrderByCreateTimeDesc(String key,
      int limit,
      int offset, List<String> tags);

  @Query(nativeQuery = true, value = "select s.* from thing as t join sale as s on t.id = s.thing_id join sale_tags as st on s.id = st.sale_id where s.is_active = 1 and s.draft_status = 2 and st.tags in (?5) and t.name like ?1 and s.end_time < ?4 order by s.create_time desc limit ?2 offset ?3")
  List<Sale> findAllByNameLikeAndEndTimeBeforeAndTagsInOrderByCreateTimeDesc(String key, int limit,
      int offset, long endTime, List<String> tags);

  @Query(nativeQuery = true, value = "select s.* from thing as t join sale as s on t.id = s.thing_id join sale_tags as st on s.id = st.sale_id where s.is_active = 1 and s.draft_status = 2 and st.tags in (?5) and t.name like ?1 and s.end_time < ?4 and t.author = t.owner order by s.create_time desc limit ?2 offset ?3")
  List<Sale> findAllByNameLikeAndEndTimeBeforeAndOwneredByAuthorAndTagsInOrderByCreateTimeDesc(
      String key,
      int limit,
      int offset, long endTime, List<String> tags);

  @Query(nativeQuery = true, value = "select count(s.id) from thing as t join sale as s on t.id = s.thing_id join sale_tags as st on s.id = st.sale_id where s.is_active = 1 and s.draft_status = 2 and st.tags in (?2) and t.name like ?1")
  int countAllByNameLikeAndTagsIn(String key, List<String> tags);

  @Query(nativeQuery = true, value = "select count(s.id) from thing as t join sale as s on t.id = s.thing_id join sale_tags as st on s.id = st.sale_id where s.is_active = 1 and s.draft_status = 2 and st.tags in (?2) and t.name like ?1 and t.author = t.owner")
  int countAllByNameLikeAndOwneredByAuthorAndTagsIn(String key, List<String> tags);

  @Query(nativeQuery = true, value = "select count(s.id) from thing as t join sale as s on t.id = s.thing_id join sale_tags as st on s.id = st.sale_id where s.is_active = 1 and s.draft_status = 2 and st.tags in (?3) and t.name like ?1 and s.end_time < ?2")
  int countAllByNameLikeAndEndTimeBeforeAndTagsIn(String key, long endTime, List<String> tags);

  @Query(nativeQuery = true, value = "select count(s.id) from thing as t join sale as s on t.id = s.thing_id join sale_tags as st on s.id = st.sale_id where s.is_active = 1 and s.draft_status = 2 and st.tags in (?3) and t.name like ?1 and t.author = t.owner and s.end_time < ?2")
  int countAllByNameLikeAndEndTimeBeforeAndOwneredByAuthorAndTagsIn(String key, long endTime,
      List<String> tags);

  @Query(nativeQuery = true, value = "select s.* from thing as t join sale as s on t.id = s.thing_id where s.is_active = 1 and s.draft_status = 2 and t.name like ?1 order by s.create_time desc limit ?2 offset ?3")
  List<Sale> findAllByNameLikeOrderByCreateTimeDesc(String key, int limit, int offset);

  @Query(nativeQuery = true, value = "select s.* from thing as t join sale as s on t.id = s.thing_id where s.is_active = 1 and s.draft_status = 2 and t.name like ?1 and t.author = t.owner order by s.create_time desc limit ?2 offset ?3")
  List<Sale> findAllByNameLikeAndOwneredByAuthorOrderByCreateTimeDesc(String key, int limit,
      int offset);

  @Query(nativeQuery = true, value = "select s.* from thing as t join sale as s on t.id = s.thing_id where s.is_active = 1 and s.draft_status = 2 and t.name like ?1 and s.end_time < ?4 order by s.create_time desc limit ?2 offset ?3")
  List<Sale> findAllByNameLikeAndEndTimeBeforeOrderByCreateTimeDesc(String key, int limit,
      int offset, long endTime);

  @Query(nativeQuery = true, value = "select s.* from thing as t join sale as s on t.id = s.thing_id where s.is_active = 1 and s.draft_status = 2 and t.name like ?1 and s.end_time < ?4 and t.author = t.owner order by s.create_time desc limit ?2 offset ?3")
  List<Sale> findAllByNameLikeAndEndTimeBeforeAndOwneredByAuthorOrderByCreateTimeDesc(String key,
      int limit, int offset, long endTime);

  @Query(nativeQuery = true, value = "select count(s.id) from thing as t join sale as s on t.id = s.thing_id where s.is_active = 1 and s.draft_status = 2 and t.name like ?1")
  int countAllByNameLike(String key);

  @Query(nativeQuery = true, value = "select count(s.id) from thing as t join sale as s on t.id = s.thing_id where s.is_active = 1 and s.draft_status = 2 and t.name like ?1 and t.author = t.owner")
  int countAllByNameLikeAndOwneredByAuthor(String key);

  @Query(nativeQuery = true, value = "select count(s.id) from thing as t join sale as s on t.id = s.thing_id where s.is_active = 1 and s.draft_status = 2 and t.name like ?1 and s.end_time < ?2")
  int countAllByNameLikeAndEndTimeBefore(String key, long endTime);

  @Query(nativeQuery = true, value = "select count(s.id) from thing as t join sale as s on t.id = s.thing_id where s.is_active = 1 and s.draft_status = 2 and t.name like ?1 and t.author = t.owner and s.end_time < ?2")
  int countAllByNameLikeAndEndTimeBeforeAndOwneredByAuthor(String key, long endTime);

  @Query(nativeQuery = true, value = "select s.* from thing as t join sale as s on t.id = s.thing_id where s.is_active = 1 and t.owner = ?1 order by s.create_time desc")
  List<Sale> findAllByOwnerOrderByCreateTimeDesc(String username);

  @Query(nativeQuery = true, value = "select s.* from thing as t join sale as s on t.id = s.thing_id where s.is_active = 1 and t.owner = ?1 and s.draft_status = ?2 order by s.create_time desc")
  List<Sale> findAllByOwnerAndDraftStatusOrderByCreateTimeDesc(String username, int draftStatus);

  @Query(nativeQuery = true, value = "select s.id from sale as s where s.is_active = 1 and end_time > ?1 and start_time < ?2")
  List<Integer> findIdsByEndTimeAfterAndStartTimeBefore(long endTime, long startTime);

  List<Sale> findAllByIdInAndIsActive(Set<Integer> saleIds, boolean isActive);

  @Query(nativeQuery = true, value = "select s.* from sale as s where s.is_active = 1 order by s.create_time desc limit ?1 offset ?2")
  List<Sale> findAllOrderByCreateTimeDesc(int limit, int offset);

  @Query(nativeQuery = true, value = "select s.* from sale as s join auction as a on s.id = a.sale_id where s.is_active = 1 and a.create_time between ?1 and ?2")
  List<Sale> findAllBySalesAuctionByStartTimeAndEndTimeBetween(long startTime, long endTime);

  @Query(nativeQuery = true, value = "select s.* from sale as s join auction as a on s.id = a.sale_id where s.is_active = 1 and s.start_time < ?1 and s.end_time > ?2 and a.username = ?3")
  List<Sale> findAllBySalesAuctionByStartTimeBeforeAndEndTimeAfterAndUsername(long startTime,
      long endTime, String username);

  List<Sale> findAllByDraftStatusAndIsActiveOrderByCreateTimeDesc(int draftStatus,
      boolean isActive);

  List<Sale> findAllByEndTimeBeforeAndStatus(long currTime, String status);


  @Query(nativeQuery = true ,value = "SELECT s.* FROM sale s where s.status in (?1,?2)")
  List<Sale> findAllByStatus(String cancel ,String gugu );

  @Query(nativeQuery = true,value = "select s.* from sale s where s.start_time <= ?1")
  List<Sale> findAllByCreateTime(Long startTime);

}
