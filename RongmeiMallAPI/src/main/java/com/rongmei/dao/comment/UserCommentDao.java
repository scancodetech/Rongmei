package com.rongmei.dao.comment;

import com.rongmei.entity.comment.UserComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserCommentDao extends JpaRepository<UserComment, Long> {

  @Query(nativeQuery = true, value = "select uc.* from user_comment as uc where uc.relation_id = ?1 and uc.to_comment_id = ?2 and uc.is_active = 1 order by uc.create_time desc limit ?3 offset ?4")
  List<UserComment> findUserCommentsByRelationIdAndToCommentIdAndPageLimit(int relationId,
      long toCommentId, int limit,
      int offset);

  long countUserCommentsByRelationIdAndTopCommentId(int relationId, long commentId);
}
