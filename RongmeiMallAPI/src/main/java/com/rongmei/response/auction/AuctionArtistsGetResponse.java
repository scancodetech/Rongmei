package com.rongmei.response.auction;

import com.rongmei.response.Response;
import com.rongmei.response.user.UserInfoDetail;
import java.util.List;

public class AuctionArtistsGetResponse extends Response {

  private List<UserInfoDetail> auctionArtistItems;

  public AuctionArtistsGetResponse() {
  }

  public AuctionArtistsGetResponse(
      List<UserInfoDetail> auctionArtistItems) {
    this.auctionArtistItems = auctionArtistItems;
  }

  public List<UserInfoDetail> getAuctionArtistItems() {
    return auctionArtistItems;
  }

  public void setAuctionArtistItems(
      List<UserInfoDetail> auctionArtistItems) {
    this.auctionArtistItems = auctionArtistItems;
  }
}
