package com.rongmei.bl.auction;

import com.rongmei.blservice.auction.TokenBlService;
import com.rongmei.dao.auction.TokenDao;
import com.rongmei.entity.auction.Token;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.auction.TokenUpdateParamaters;
import com.rongmei.response.auction.TokenResponse;
import com.rongmei.util.RandomUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenBlServiceImpl implements TokenBlService {

  private final TokenDao tokenDao;

  @Autowired
  public TokenBlServiceImpl(TokenDao tokenDao) {
    this.tokenDao = tokenDao;
  }

  @Override
  public TokenResponse updateToken(TokenUpdateParamaters parameters) {
    if (parameters.getTokenId() == 0) {
      long tokenId = RandomUtil.generateNum(3);
      Token token = new Token(tokenId, parameters.getValue());
      tokenDao.save(token);
      return new TokenResponse(tokenId, parameters.getValue());
    } else {
      return new TokenResponse(parameters.getTokenId(), parameters.getValue());
    }
  }

  @Override
  public TokenResponse getToken(long tokenId) throws ThingIdDoesNotExistException {
    Optional<Token> optionalToken = tokenDao.findById(tokenId);
    if (optionalToken.isPresent()) {
      Token token = optionalToken.get();
      return new TokenResponse(token.getTokenId(), token.getValue());
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }
}
