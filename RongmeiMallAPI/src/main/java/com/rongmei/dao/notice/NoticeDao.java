package com.rongmei.dao.notice;

import com.rongmei.entity.notice.Notice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeDao extends JpaRepository<Notice, Integer> {

  List<Notice> findAllByToUsernameOrderByCreateTimeDesc(String username);

  @Query(nativeQuery = true,value = "select n.* from notice as n where n.to_username = ?1 order by n.create_time desc limit ?2 offset ?3")
  List<Notice> findAllByToUsernameOrderByCreateTimeDescAndLimitOffset(String username, int limit,
      int offset);

  long countAllByToUsername(String username);
}
