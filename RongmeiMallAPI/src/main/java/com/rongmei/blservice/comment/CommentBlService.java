package com.rongmei.blservice.comment;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.comment.CommentSendParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.comment.CommentGetResponse;

public interface CommentBlService {

  SuccessResponse sendComment(CommentSendParameters parameters) throws ThingIdDoesNotExistException;

  CommentGetResponse getComments(int relationId, long toCommentId, int page, int limit);

  SuccessResponse likeComments(long commentId);
}
