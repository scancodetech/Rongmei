package com.rongmei.response.event;

import com.rongmei.response.Response;
import java.util.List;

public class EventGetResponse extends Response {

  private List<EventItem> blindBoxEventItemList;

  public EventGetResponse() {
  }

  public EventGetResponse(
      List<EventItem> blindBoxEventItemList) {
    this.blindBoxEventItemList = blindBoxEventItemList;
  }

  public List<EventItem> getBlindBoxEventItemList() {
    return blindBoxEventItemList;
  }

  public void setBlindBoxEventItemList(
      List<EventItem> blindBoxEventItemList) {
    this.blindBoxEventItemList = blindBoxEventItemList;
  }
}
