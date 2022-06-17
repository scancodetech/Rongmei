package com.rongmei.response.auction;

import com.rongmei.response.Response;
import java.util.List;

public class ThingGetResponse extends Response {

  private List<ThingItem> thingItems;

  public ThingGetResponse() {
  }

  public ThingGetResponse(List<ThingItem> thingItems) {
    this.thingItems = thingItems;
  }

  public List<ThingItem> getThingItems() {
    return thingItems;
  }

  public void setThingItems(List<ThingItem> thingItems) {
    this.thingItems = thingItems;
  }
}
