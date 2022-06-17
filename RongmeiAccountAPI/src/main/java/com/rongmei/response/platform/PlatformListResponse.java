package com.rongmei.response.platform;

import com.rongmei.response.Response;
import java.util.List;

public class PlatformListResponse extends Response {

  private List<PlatformItem> platformItems;

  public PlatformListResponse() {
  }

  public PlatformListResponse(
      List<PlatformItem> platformItems) {
    this.platformItems = platformItems;
  }

  public List<PlatformItem> getPlatformItems() {
    return platformItems;
  }

  public void setPlatformItems(List<PlatformItem> platformItems) {
    this.platformItems = platformItems;
  }
}
