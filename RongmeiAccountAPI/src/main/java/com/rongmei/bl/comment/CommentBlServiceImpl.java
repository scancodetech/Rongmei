package com.rongmei.bl.comment;

import com.rongmei.blservice.comment.CommentBlService;
import com.rongmei.dao.comment.CommentDao;
import com.rongmei.entity.comment.Comment;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.comment.CommentAddParameters;
import com.rongmei.parameters.comment.CommentGetParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.comment.CommentGetResponse;
import com.rongmei.response.comment.CommentItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentBlServiceImpl implements CommentBlService {

  private final CommentDao commentDao;

  @Autowired
  public CommentBlServiceImpl(CommentDao commentDao) {
    this.commentDao = commentDao;
  }

  @Override
  public SuccessResponse addComment(CommentAddParameters parameters, String username) {
    Comment comment = new Comment(username, parameters.getText(), parameters.getRandomNum(),
        System.currentTimeMillis(), true);
    commentDao.save(comment);
    return new SuccessResponse("add success");
  }

  @Override
  public SuccessResponse deleteComment(int id) throws ThingIdDoesNotExistException {
    Optional<Comment> optionalComment = commentDao.findById(id);
    if (optionalComment.isPresent()) {
      Comment comment = optionalComment.get();
      comment.setActive(false);
      commentDao.save(comment);
      return new SuccessResponse("delete success");
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public CommentGetResponse getComment(int page, int limit) {
    List<Comment> commentList = commentDao.findAllByOffsetAndLimit((page - 1) * limit, limit);
    List<CommentItem> commentItems = new ArrayList<>();
    for (Comment comment : commentList) {
      commentItems.add(new CommentItem(comment.getId(), comment.getUsername(), comment.getText(),
          comment.getRandomNum(), comment.getCreateTime()));
    }
    return new CommentGetResponse(commentItems);
  }
}
