package com.rongmei.bl.auction;

import com.rongmei.blservice.auction.ThingBlService;
import com.rongmei.dao.auction.SaleDao;
import com.rongmei.dao.auction.ThingDao;
import com.rongmei.dao.auction.TokenDao;
import com.rongmei.dao.relation.ThingLikeDao;
import com.rongmei.entity.auction.Sale;
import com.rongmei.entity.auction.Thing;
import com.rongmei.entity.auction.Token;
import com.rongmei.entity.relation.ThingLike;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.parameters.auction.ThingTransferParameters;
import com.rongmei.parameters.auction.ThingUpdateParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.auction.AuctionArtistsGetResponse;
import com.rongmei.response.auction.AuctionCollectorsGetResponse;
import com.rongmei.response.auction.ThingDetailResponse;
import com.rongmei.response.auction.ThingGetResponse;
import com.rongmei.response.auction.ThingItem;
import com.rongmei.response.auction.TokenResponse;
import com.rongmei.response.user.UserInfoDetail;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThingBlServiceImpl implements ThingBlService {

  private final ThingDao thingDao;
  private final ThingLikeDao thingLikeDao;
  private final SaleDao saleDao;
  private final TokenDao tokenDao;

  @Autowired
  public ThingBlServiceImpl(ThingDao thingDao, ThingLikeDao thingLikeDao,
      SaleDao saleDao, TokenDao tokenDao) {
    this.thingDao = thingDao;
    this.thingLikeDao = thingLikeDao;
    this.saleDao = saleDao;
    this.tokenDao = tokenDao;
  }

  @Override
  public ThingGetResponse getThings() {
    List<Thing> things = thingDao.findAllByOwner(UserInfoUtil.getUsername());
    return new ThingGetResponse(packThingItems(things));
  }

  @Override
  public ThingDetailResponse getThing(int thingId, String username)
      throws ThingIdDoesNotExistException {
    Optional<Thing> optionalThing = thingDao.findById(thingId);
    if (!optionalThing.isPresent()) {
      throw new ThingIdDoesNotExistException();
    }
    Thing thing = optionalThing.get();
    return new ThingDetailResponse(thing.getId(), thing.getName(), thing.getUrl(), thing.getPrice(),
        thing.getDescription(), thing.getCreateTime(),
        thing.getAuthor(), thing.getOwner(), thing.getTokenId(), thing.getTags(),
        thing.getChainTxId(), thing.getConfirmationLetterUrl());
  }

  @Override
  public SuccessResponse deleteThing(int thingId) {
    thingDao.deleteById(thingId);
    return new SuccessResponse("delete success");
  }

  @Override
  public SuccessResponse updateThing(ThingUpdateParameters parameters, String username)
      throws ThingIdDoesNotExistException {
    if (parameters.getId() == 0) {
      Thing thing = new Thing(parameters.getName(), parameters.getUrl(), parameters.getPrice(),
          parameters.getDescription(), System.currentTimeMillis(), username, username,
          parameters.getTokenId(), parameters.getTags(), parameters.getChainTxId(),
          parameters.getConfirmationLetterUrl(), 0);
      thingDao.save(thing);
      return new SuccessResponse("add success");
    } else {
      Optional<Thing> optionalThing = thingDao.findById(parameters.getId());
      if (!optionalThing.isPresent()) {
        throw new ThingIdDoesNotExistException();
      }
      Thing thing = optionalThing.get();
      thing.setDescription(parameters.getDescription());
      thing.setName(parameters.getName());
      thing.setPrice(parameters.getPrice());
      thing.setUrl(parameters.getUrl());
      thingDao.save(thing);
      return new SuccessResponse("update success");
    }
  }

  @Override
  public ThingGetResponse getOwnerThings(String owner) {
    List<Thing> things = thingDao.findAllByOwner(owner);
    return new ThingGetResponse(packThingItems(things));
  }

  @Override
  public ThingGetResponse getAuthorThings(String author) {
    List<Thing> things = thingDao.findAllByAuthor(author);
    return new ThingGetResponse(packThingItems(things));
  }

  @Override
  public AuctionArtistsGetResponse getTopArtists(int limit, int offset) {
    List<String> artistUsernames = thingDao.findArtistsUsername(limit, offset);
    List<UserInfoDetail> artists = new ArrayList<>();
    for (String username : artistUsernames) {
      artists.add(UserInfoUtil.getUserInfoDetail(username));
    }
    return new AuctionArtistsGetResponse(artists);
  }

  @Override
  public AuctionCollectorsGetResponse getTopCollectors(int limit, int offset) {
    List<String> collectorUsernames = thingDao.findCollectorsUsername(limit, offset);
    List<UserInfoDetail> collectors = new ArrayList<>();
    for (String username : collectorUsernames) {
      collectors.add(UserInfoUtil.getUserInfoDetail(username));
    }
    return new AuctionCollectorsGetResponse(collectors);
  }

  @Override
  public ThingDetailResponse getThingByTokenId(String tokenId) throws ThingIdDoesNotExistException {
    Thing thing = thingDao.findFirstByTokenId(tokenId);
    if (thing == null) {
      throw new ThingIdDoesNotExistException();
    }
    return new ThingDetailResponse(thing.getId(), thing.getName(), thing.getUrl(), thing.getPrice(),
        thing.getDescription(), thing.getCreateTime(),
        thing.getAuthor(), thing.getOwner(), thing.getTokenId(), thing.getTags(),
        thing.getChainTxId(), thing.getConfirmationLetterUrl());
  }

  @Override
  public SuccessResponse transferThing(ThingTransferParameters parameters)
      throws ThingIdDoesNotExistException {
    Thing thing = thingDao.findFirstByTokenId(parameters.getTokenId());
    if (thing == null) {
      throw new ThingIdDoesNotExistException();
    }
    thing.setOwner(parameters.getToUsername());
    thingDao.save(thing);
    return new SuccessResponse("transfer success");
  }

  @Override
  public ThingGetResponse getFavoritesThings(int favoritesId, int status, String username) {
    Set<Integer> thingIds = new HashSet<>();
    List<Thing> things = new ArrayList<>();
    switch (status) {
      case 0:
        if (favoritesId == 0) {
//          things = thingDao.findAllByFavoritesDefault();
//          things.addAll(thingDao.findAllByFavoritesId(0));
          things = thingDao.findAllByOwner(username);
        } else {
          things = thingDao.findAllByFavoritesId(favoritesId);
        }
        break;
      case 1:
        List<ThingLike> thingLikes;
        if (favoritesId == 0) {
//          thingLikes = thingLikeDao.findAllByFavoritesDefault(username);
//          thingLikes.addAll(thingLikeDao.findAllByFavoritesIdAndUsername(0, username));
          thingLikes = thingLikeDao.findAllByUsername(username);
        } else {
          thingLikes = thingLikeDao.findAllByFavoritesIdAndUsername(favoritesId, username);
        }
        for (ThingLike thingLike : thingLikes) {
          thingIds.add(thingLike.getThingId());
        }
        break;
      case 2:
        List<Sale> sales = saleDao
            .findAllBySalesAuctionByStartTimeBeforeAndEndTimeAfterAndUsername(
                System.currentTimeMillis(),
                System.currentTimeMillis(), username);
        for (Sale sale : sales) {
          thingIds.add(sale.getThingId());
        }
        break;
    }
    if (things.size() <= 0) {
      things = thingDao.findAllByIdIn(thingIds);
    }
    return new ThingGetResponse(packThingItems(things));
  }

  @Override
  public SuccessResponse moveFavoritesThing(int favoritesId, int thingId, int status) {
    switch (status) {
      case 0:
        Optional<Thing> optionalThing = thingDao.findById(thingId);
        if (optionalThing.isPresent()) {
          Thing thing = optionalThing.get();
          thing.setFavoritesId(favoritesId);
          thingDao.save(thing);
        }
        break;
      case 1:
        ThingLike thingLike = thingLikeDao
            .findThingLikeByThingIdAndUsername(thingId, UserInfoUtil.getUsername());
        if (thingLike == null) {
          thingLike = new ThingLike(UserInfoUtil.getUsername(), thingId, favoritesId,
              System.currentTimeMillis(), System.currentTimeMillis());
        } else {
          thingLike.setFavoritesId(favoritesId);
          thingLike.setUpdateTime(System.currentTimeMillis());
          thingLikeDao.save(thingLike);
        }
        thingLikeDao.save(thingLike);
    }
    return new SuccessResponse("move success");
  }

  @Override
  public TokenResponse getToken(String tokenId)
      throws ThingIdDoesNotExistException, UsernameDoesNotFoundException {
    Thing thing = thingDao.findFirstByTokenId(tokenId);
    if (thing != null) {
      if (thing.getOwner().equals(UserInfoUtil.getUsername())) {
        Optional<Token> optionalToken = tokenDao.findById(Long.parseLong(tokenId));
        if (optionalToken.isPresent()) {
          Token token = optionalToken.get();
          return new TokenResponse(token.getTokenId(), token.getValue());
        } else {
          throw new ThingIdDoesNotExistException();
        }
      } else {
        throw new UsernameDoesNotFoundException();
      }
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  private List<ThingItem> packThingItems(List<Thing> things) {
    List<ThingItem> thingItems = new ArrayList<>();
    for (Thing thing : things) {
      thingItems.add(new ThingItem(thing.getId(), thing.getName(), thing.getUrl(), thing.getPrice(),
          thing.getDescription(), thing.getCreateTime(), thing.getAuthor(), thing.getOwner(),
          thing.getTokenId(), thing.getTags(), thing.getChainTxId(),
          thing.getConfirmationLetterUrl()));
    }
    return thingItems;
  }
}
