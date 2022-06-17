package com.rongmei.dao.proposal;

import com.rongmei.entity.proposal.Proposal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProposalDao extends JpaRepository<Proposal, Integer> {

  @Query(nativeQuery = true, value = "select p.* from proposal as p where p.is_active = 1 and p.draft_status = 2 and p.end_time > ?1 and p.status != ?2 order by p.end_time desc limit ?3 offset ?4")
  List<Proposal> findAllByLimitAndOffsetAndEndTimeAfterAndStatusNotOrderByEndTimeDesc(long endTime,
      String status, int limit, int offset);

  @Query(nativeQuery = true, value = "select p.* from proposal as p where p.is_active = 1 and p.draft_status = 2 and p.end_time < ?1 and p.status != ?2 order by p.end_time desc limit ?3 offset ?4")
  List<Proposal> findAllByLimitAndOffsetAndEndTimeBeforeAndStatusNotOrderByEndTimeDesc(long endTime,
      String status, int limit, int offset);

  @Query(nativeQuery = true, value = "select p.* from proposal as p where p.is_active = 1 and p.username = ?1 order by p.end_time desc limit ?2 offset ?3")
  List<Proposal> findAllByLimitAndOffsetAndUsernameOrderByEndTimeDesc(String username, int limit,
      int offset);

  List<Proposal> findAllByDraftStatusInAndIsActive(List<Integer> draftStatus, boolean isActive);

  long countAllByDraftStatusInAndIsActive(List<Integer> draftStatus, boolean isActive);

  List<Proposal> findAllByEndTimeBeforeAndStatus(long currTime, String status);
}
