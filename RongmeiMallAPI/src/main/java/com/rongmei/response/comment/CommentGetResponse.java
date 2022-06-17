package com.rongmei.response.comment;

import com.rongmei.response.Response;
import java.util.List;

public class CommentGetResponse extends Response {

  private List<CommentItem> comments;
  private long total;

  public CommentGetResponse() {
  }

  public CommentGetResponse(List<CommentItem> comments, long total) {
    this.comments = comments;
    this.total = total;
  }

  public List<CommentItem> getComments() {
    return comments;
  }

  public void setComments(List<CommentItem> comments) {
    this.comments = comments;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }
}
