package com.rongmei.dao.groupshopping;

import com.rongmei.entity.groupshopping.GroupShopping;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupShoppingDao extends JpaRepository<GroupShopping, Integer> {

  @Query(nativeQuery = true, value = "select g.* from group_shopping as g where g.is_active = 1 and g.draft_status = 2 order by g.end_time desc limit ?1 offset ?2")
  List<GroupShopping> findAllByLimitAndOffsetOrderByEndTimeDesc(int limit, int offset);

  @Query(nativeQuery = true, value = "select count(g.id) from group_shopping as g where g.is_active = 1 and g.draft_status = 2")
  long countAllWithActiveAndDraftStatus();

  @Query(nativeQuery = true, value = "select g.* from group_shopping as g where g.is_active = 1 and g.draft_status = 2 and g.end_time < ?1 order by g.end_time desc limit ?2 offset ?3")
  List<GroupShopping> findAllByLimitAndOffsetAndEndTimeBeforeOrderByEndTimeDesc(long endTime,
      int limit, int offset);

  @Query(nativeQuery = true, value = "select count(g.id) from group_shopping as g where g.is_active = 1 and g.draft_status = 2 and g.end_time < ?1")
  long countAllByEndTimeBeforeOrderByEndTimeDesc(long endTime);

  @Query(nativeQuery = true, value = "select g.* from group_shopping as g where g.is_active = 1 and g.end_time > ?1 and g.username = ?2 order by g.end_time desc limit ?3 offset ?4")
  List<GroupShopping> findAllByLimitAndOffsetAndEndTimeAfterAndUsernameOrderByEndTimeDesc(
      long endTime, String username, int limit, int offset);

  @Query(nativeQuery = true, value = "select count(g.id) from group_shopping as g where g.is_active = 1 and g.end_time > ?1 and g.username = ?2")
  long countAllByEndTimeAfterAndUsernameOrderByEndTimeDesc(long endTime, String username);

  @Query(nativeQuery = true, value = "select g.* from group_shopping as g where g.is_active = 1 and g.end_time < ?1 and g.username = ?2 order by g.end_time desc limit ?3 offset ?4")
  List<GroupShopping> findAllByLimitAndOffsetAndEndTimeBeforeAndUsernameOrderByEndTimeDesc(
      long endTime, String username, int limit, int offset);

  @Query(nativeQuery = true, value = "select count(g.id) from group_shopping as g where g.is_active = 1 and g.end_time < ?1 and g.username = ?2")
  long countAllByEndTimeBeforeAndUsernameOrderByEndTimeDesc(long endTime, String username);

  @Query(nativeQuery = true, value = "select g.* from group_shopping as g where g.is_active = 1 and g.draft_status = ?3 order by g.end_time desc limit ?1 offset ?2")
  List<GroupShopping> findAllByLimitAndOffsetAndDraftStatusOrderByEndTimeDesc(int limit, int offset,
      int draftStatus);

  @Query(nativeQuery = true, value = "select count(g.id) from group_shopping as g where g.is_active = 1 and g.draft_status = ?1")
  long countAllByDraftStatusOrderByEndTimeDesc(int draftStatus);

  List<GroupShopping> findAllByAuthorAndIsActive(String author, boolean isActive);

  long countAllByAuthorAndIsActive(String author, boolean isActive);

  List<GroupShopping> findAllByAuthorAndDraftStatusAndIsActive(String author, int draftStatus,
      boolean isActive);

  long countAllByAuthorAndDraftStatusAndIsActive(String author, int draftStatus, boolean isActive);

  List<GroupShopping> findAllByDraftStatusInAndIsActive(List<Integer> draftStatus,
      boolean isActive);

  long countAllByDraftStatusInAndIsActive(List<Integer> draftStatus, boolean isActive);

  @Query(nativeQuery = true, value = "select g.* from group_shopping as g join group_shopping_participate as gp on g.id = gp.relation_id where g.is_active = 1 and g.draft_status = 2 and gp.username = ?1")
  List<GroupShopping> findAllByUserGroupShoppingParticipate(String username);
}
