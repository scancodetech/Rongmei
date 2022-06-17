package com.rongmei.response.relation;

import com.rongmei.response.Response;
import java.util.List;

public class FavoritesGetResponse extends Response {

  private List<FavoritesItem> favoritesItems;

  public FavoritesGetResponse() {
  }

  public FavoritesGetResponse(
      List<FavoritesItem> favoritesItems) {
    this.favoritesItems = favoritesItems;
  }

  public List<FavoritesItem> getFavoritesItems() {
    return favoritesItems;
  }

  public void setFavoritesItems(List<FavoritesItem> favoritesItems) {
    this.favoritesItems = favoritesItems;
  }
}
