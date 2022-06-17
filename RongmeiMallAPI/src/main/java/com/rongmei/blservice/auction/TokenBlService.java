package com.rongmei.blservice.auction;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.auction.TokenUpdateParamaters;
import com.rongmei.response.auction.TokenResponse;

public interface TokenBlService {

  TokenResponse updateToken(TokenUpdateParamaters parameters);

  TokenResponse getToken(long tokenId) throws ThingIdDoesNotExistException;
}
