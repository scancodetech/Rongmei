package com.rongmei.blservice.comment;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.comment.CommentAddParameters;
import com.rongmei.parameters.comment.CommentGetParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.comment.CommentGetResponse;

public interface CommentBlService {

  SuccessResponse addComment(CommentAddParameters parameters, String username);

  SuccessResponse deleteComment(int id) throws ThingIdDoesNotExistException;

  CommentGetResponse getComment(int page, int limit);
}
