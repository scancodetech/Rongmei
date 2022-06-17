package com.rongmei.blservice.auction;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.parameters.auction.ThingTransferParameters;
import com.rongmei.parameters.auction.ThingUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.auction.AuctionArtistsGetResponse;
import com.rongmei.response.auction.AuctionCollectorsGetResponse;
import com.rongmei.response.auction.ThingDetailResponse;
import com.rongmei.response.auction.ThingGetResponse;
import com.rongmei.response.auction.TokenResponse;

public interface ThingBlService {

  ThingGetResponse getThings();

  ThingDetailResponse getThing(int thingId, String username) throws ThingIdDoesNotExistException;

  SuccessResponse deleteThing(int thingId);

  SuccessResponse updateThing(ThingUpdateParameters parameters, String username)
      throws ThingIdDoesNotExistException;

  ThingGetResponse getOwnerThings(String owner);

  ThingGetResponse getAuthorThings(String author);

  AuctionArtistsGetResponse getTopArtists(int limit, int offset);

  AuctionCollectorsGetResponse getTopCollectors(int limit, int offset);

  ThingDetailResponse getThingByTokenId(String tokenId) throws ThingIdDoesNotExistException;

  SuccessResponse transferThing(ThingTransferParameters parameters)
      throws ThingIdDoesNotExistException;

  /**
   * @param status   0:已购,1:喜欢,2:在拍
   * @param username
   */
  ThingGetResponse getFavoritesThings(int favoritesId, int status, String username);

  SuccessResponse moveFavoritesThing(int favoritesId, int thingId, int status);

  TokenResponse getToken(String tokenId)
      throws ThingIdDoesNotExistException, UsernameDoesNotFoundException;
}
