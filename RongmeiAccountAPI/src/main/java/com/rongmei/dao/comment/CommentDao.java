package com.rongmei.dao.comment;

import com.rongmei.entity.comment.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentDao extends JpaRepository<Comment, Integer> {

  @Query(nativeQuery = true, value = "select * from comment where is_active = 1 order by create_time desc limit ?2 offset ?1")
  List<Comment> findAllByOffsetAndLimit(int offset, int limit);
}
