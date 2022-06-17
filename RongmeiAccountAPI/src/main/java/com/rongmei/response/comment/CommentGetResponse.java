package com.rongmei.response.comment;

import com.rongmei.response.Response;
import java.util.List;

public class CommentGetResponse extends Response {

  private List<CommentItem> comments;

  public CommentGetResponse() {
  }

  public CommentGetResponse(List<CommentItem> comments) {
    this.comments = comments;
  }

  public List<CommentItem> getComments() {
    return comments;
  }

  public void setComments(List<CommentItem> comments) {
    this.comments = comments;
  }
}
