package com.rongmei.dao.comment;

import com.rongmei.entity.comment.UserCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommentLikeDao extends JpaRepository<UserCommentLike, Long> {

  boolean existsUserCommentLikeByCommentIdAndUsername(long commentId, String username);

  long countUserCommentLikesByCommentId(long commentId);
}
