package com.rongmei.response.notice;

import com.rongmei.response.Response;
import java.util.List;

public class NoticeGetResponse extends Response {

  private List<NoticeItem> noticeItems;
  private long total;

  public NoticeGetResponse() {
  }

  public NoticeGetResponse(List<NoticeItem> noticeItems, long total) {
    this.noticeItems = noticeItems;
    this.total = total;
  }

  public List<NoticeItem> getNoticeItems() {
    return noticeItems;
  }

  public void setNoticeItems(List<NoticeItem> noticeItems) {
    this.noticeItems = noticeItems;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }
}
