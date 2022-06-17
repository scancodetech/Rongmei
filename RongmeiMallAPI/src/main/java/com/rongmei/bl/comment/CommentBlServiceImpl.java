package com.rongmei.bl.comment;

import static com.rongmei.util.UserInfoUtil.getUserInfoByCache;

import com.rongmei.blservice.comment.CommentBlService;
import com.rongmei.dao.comment.UserCommentDao;
import com.rongmei.dao.comment.UserCommentLikeDao;
import com.rongmei.entity.comment.UserComment;
import com.rongmei.entity.comment.UserCommentLike;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.comment.CommentSendParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.comment.CommentGetResponse;
import com.rongmei.response.comment.CommentItem;
import com.rongmei.response.user.UserInfo;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CommentBlServiceImpl implements CommentBlService {

  private final UserCommentDao userCommentDao;
  private final UserCommentLikeDao userCommentLikeDao;

  public CommentBlServiceImpl(UserCommentDao userCommentDao,
      UserCommentLikeDao userCommentLikeDao) {
    this.userCommentDao = userCommentDao;
    this.userCommentLikeDao = userCommentLikeDao;
  }

  @Override
  public SuccessResponse sendComment(CommentSendParameters parameters)
      throws ThingIdDoesNotExistException {
    UserComment userComment;
    if (parameters.getToCommentId() != 0) {
      Optional<UserComment> optionalComment = userCommentDao.findById(parameters.getToCommentId());
      if (optionalComment.isPresent()) {
        UserComment toUserComment = optionalComment.get();
        long topCommentId;
        if (toUserComment.getTopCommentId() != 0) {
          topCommentId = toUserComment.getTopCommentId();
        } else {
          topCommentId = parameters.getToCommentId();
        }
        userComment = new UserComment(UserInfoUtil.getUsername(), parameters.getContent(),
            parameters.getToUsername(), topCommentId, parameters.getToCommentId(),
            parameters.getRelationId(),
            System.currentTimeMillis(),
            System.currentTimeMillis(), true);
      } else {
        throw new ThingIdDoesNotExistException();
      }
    } else {
      userComment = new UserComment(UserInfoUtil.getUsername(), parameters.getContent(),
          parameters.getToUsername(), 0, 0, parameters.getRelationId(),
          System.currentTimeMillis(),
          System.currentTimeMillis(), true);
    }
    userCommentDao.save(userComment);
    return new SuccessResponse("send success");
  }

  @Override
  public CommentGetResponse getComments(int relationId, long toCommentId, int page, int limit) {
    List<UserComment> commentList = userCommentDao
        .findUserCommentsByRelationIdAndToCommentIdAndPageLimit(relationId, toCommentId, limit,
            (page - 1) * limit);
    long total = userCommentDao
        .countUserCommentsByRelationIdAndTopCommentId(relationId, toCommentId);
    return new CommentGetResponse(packCommentItems(commentList, toCommentId == 0), total);
  }

  private List<CommentItem> packCommentItems(List<UserComment> userComments, boolean isTopComment) {
    List<CommentItem> commentItems = new ArrayList<>();
    for (UserComment userComment : userComments) {
      UserInfo fromUserInfo = getUserInfoByCache(userComment.getUsername());
      UserInfo toUserInfo = getUserInfoByCache(userComment.getToUsername());
      long replyNum = 0;
      if (isTopComment) {
        replyNum = userCommentDao
            .countUserCommentsByRelationIdAndTopCommentId(userComment.getRelationId(),
                userComment.getId());
      }
      commentItems.add(new CommentItem(userComment.getId(), userComment.getUsername(),
          fromUserInfo.getNickname(),
          fromUserInfo.getAvatarUrl(), userComment.getContent(), userComment.getToUsername(),
          toUserInfo.getNickname(), toUserInfo.getAvatarUrl(), replyNum,
          userComment.getToCommentId(),
          userComment.getRelationId(), userComment.getCreateTime(), userComment.getUpdateTime(),
          userCommentLikeDao
              .existsUserCommentLikeByCommentIdAndUsername(userComment.getToCommentId(),
                  UserInfoUtil.getUsername()),
          userCommentLikeDao.countUserCommentLikesByCommentId(userComment.getId())));
    }
    return commentItems;
  }

  @Override
  public SuccessResponse likeComments(long commentId) {
    if (userCommentLikeDao
        .existsUserCommentLikeByCommentIdAndUsername(commentId, UserInfoUtil.getUsername())) {
      userCommentLikeDao.deleteById(commentId);
      return new SuccessResponse("unlike success");
    } else {
      UserCommentLike userCommentLike = new UserCommentLike(UserInfoUtil.getUsername(), commentId,
          System.currentTimeMillis(), System.currentTimeMillis());
      userCommentLikeDao.save(userCommentLike);
      return new SuccessResponse("like success");
    }
  }
}