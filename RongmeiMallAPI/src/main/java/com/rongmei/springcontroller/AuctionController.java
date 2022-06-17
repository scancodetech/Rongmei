package com.rongmei.springcontroller;

import com.rongmei.blservice.auction.AuctionBlService;
import com.rongmei.blservice.auction.SaleBlService;
import com.rongmei.blservice.auction.ThingBlService;
import com.rongmei.blservice.auction.TokenBlService;
import com.rongmei.exception.ParametersErrorException;
import com.rongmei.exception.SystemException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.parameters.auction.AuctionParamaters;
import com.rongmei.parameters.auction.AuctionTransactionParameters;
import com.rongmei.parameters.auction.SaleQueryParameters;
import com.rongmei.parameters.auction.SaleUpdateParameters;
import com.rongmei.parameters.auction.ThingTransferParameters;
import com.rongmei.parameters.auction.ThingUpdateParameters;
import com.rongmei.parameters.auction.TokenUpdateParamaters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.auction.AuctionHistoryGetResponse;
import com.rongmei.response.auction.AuctionJoinStatisticsResponse;
import com.rongmei.response.auction.AuctionTransactionHistoryGetResponse;
import com.rongmei.response.auction.SaleDetailResponse;
import com.rongmei.response.auction.SaleGetResponse;
import com.rongmei.response.auction.ThingDetailResponse;
import com.rongmei.response.auction.ThingGetResponse;
import com.rongmei.response.auction.TokenResponse;
import com.rongmei.response.commodity.CommodityGetResponse;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auction")
public class AuctionController {

  private final AuctionBlService auctionBlService;
  private final ThingBlService thingBlService;
  private final SaleBlService saleBlService;
  private final TokenBlService tokenBlService;

  @Autowired
  public AuctionController(AuctionBlService auctionBlService,
      ThingBlService thingBlService, SaleBlService saleBlService,
      TokenBlService tokenBlService) {
    this.auctionBlService = auctionBlService;
    this.thingBlService = thingBlService;
    this.saleBlService = saleBlService;
    this.tokenBlService = tokenBlService;
  }



  @ApiOperation(value = "获取顶级艺术家", notes = "获取顶级艺术家")
  @RequestMapping(value = "artist", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = TokenResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getTopArtists(@RequestParam("limit") int limit,
      @RequestParam("offset") int offset) {
    return new ResponseEntity<>(thingBlService.getTopArtists(limit, offset), HttpStatus.OK);
  }

  @ApiOperation(value = "获取顶级收藏家", notes = "获取顶级收藏家")
  @RequestMapping(value = "collector", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = TokenResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getTopCollectors(@RequestParam("limit") int limit,
      @RequestParam("offset") int offset) {
    return new ResponseEntity<>(thingBlService.getTopCollectors(limit, offset), HttpStatus.OK);
  }

  @ApiOperation(value = "获取tag热度", notes = "获取tag热度")
  @RequestMapping(value = "statistics/tag", method = RequestMethod.GET)
  @ApiResponses(value = {
          //fix
      @ApiResponse(code = 200, message = "Success", response = TokenResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getTopTags(@RequestParam("startTime") long startTime,
      @RequestParam("endTime") long endTime) {
    return new ResponseEntity<>(saleBlService.getTopTags(startTime, endTime), HttpStatus.OK);
  }

  @ApiOperation(value = "新增token", notes = "新增token")
  @RequestMapping(value = "token", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = TokenResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateToken(@RequestBody TokenUpdateParamaters parameters) {
    return new ResponseEntity<>(tokenBlService.updateToken(parameters), HttpStatus.OK);
  }

  @ApiOperation(value = "加载token", notes = "加载某token")
  @RequestMapping(value = "token/{tokenId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = TokenResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getToken(@PathVariable("tokenId") String tokenId) {
    try {
      return new ResponseEntity<>(thingBlService.getToken(tokenId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.FORBIDDEN);
    }
  }

  @ApiOperation(value = "加载拍卖项", notes = "加载所有现有拍卖项信息")
  @RequestMapping(value = "sale/query", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SaleGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getSales(@RequestBody SaleQueryParameters parameters) {
    return new ResponseEntity<>(saleBlService.getSales(parameters), HttpStatus.OK);
  }

  @ApiOperation(value = "加载我正参与的拍卖", notes = "加载我正参与的拍卖")
  @RequestMapping(value = "sale/participate/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SaleGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineSalesParticipate() {
    return new ResponseEntity<>(saleBlService.getSalesParticipate(UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "加载我的拍卖项", notes = "加载我的拍卖项")
  @RequestMapping(value = "sale/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SaleGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineSales(
      @RequestParam(value = "draftStatus", defaultValue = "-1") int draftStatus) {
    return new ResponseEntity<>(saleBlService.getMineSales(draftStatus), HttpStatus.OK);
  }

  @ApiOperation(value = "加载某拍卖项", notes = "获得某拍卖项的详细信息")
  @RequestMapping(value = "sale/{saleId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SaleDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getSale(@PathVariable(name = "saleId") int saleId) {
    try {
      return new ResponseEntity<>(saleBlService.getSale(saleId, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取最近拍卖项", notes = "获取最近拍卖项")
  @RequestMapping(value = "sale/current", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getCurrentSale(@RequestParam("limit") int limit,
      @RequestParam("offset") int offset) {
    return new ResponseEntity<>(saleBlService.getCurrentSale(limit, offset), HttpStatus.OK);
  }

  @ApiOperation(value = "更新某拍卖项", notes = "更新或添加某拍卖项")
  @RequestMapping(value = "sale", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateSale(@RequestBody SaleUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(saleBlService.updateSale(parameters, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "删除某拍卖项", notes = "删除某拍卖项")
  @RequestMapping(value = "sale/{saleId}", method = RequestMethod.DELETE)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> deleteSale(@PathVariable(name = "saleId") int saleId) {
    try {
      return new ResponseEntity<>(saleBlService.deleteSale(saleId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.OK);
    }
  }

  @ApiOperation(value = "获取话题拍卖项", notes = "获取话题拍卖项")
  @RequestMapping(value = "sale/topic", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getTopicSales(@RequestParam("key") String key,
      @RequestParam("offset") int offset, @RequestParam("limit") int limit) {
    return new ResponseEntity<>(saleBlService.getTopicSales(key, offset, limit), HttpStatus.OK);
  }

  @ApiOperation(value = "加载物品项", notes = "加载所有现有物品项信息")
  @RequestMapping(value = "thing", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ThingGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getThings() {
    return new ResponseEntity<>(thingBlService.getThings(), HttpStatus.OK);
  }

  @ApiOperation(value = "加载某物品项", notes = "获得某物品项的详细信息")
  @RequestMapping(value = "thing/{thingId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ThingDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getThing(@PathVariable(name = "thingId") int thingId) {
    try {
      return new ResponseEntity<>(thingBlService.getThing(thingId, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "加载某物品项", notes = "根据tokenId加载某物品项")
  @RequestMapping(value = "thing/token/{tokenId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ThingDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getThingByTokenId(
      @PathVariable(name = "tokenId") String tokenId) {
    try {
      return new ResponseEntity<>(thingBlService.getThingByTokenId(tokenId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "更新某物品项", notes = "更新或添加某物品项")
  @RequestMapping(value = "thing", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateThing(@RequestBody ThingUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(
          thingBlService.updateThing(parameters, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "转移某物品项", notes = "转移某物品项")
  @RequestMapping(value = "thing/transfer/inner", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> transferThing(@RequestBody ThingTransferParameters parameters) {
    try {
      return new ResponseEntity<>(thingBlService.transferThing(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "删除某物品项", notes = "删除某物品项")
  @RequestMapping(value = "thing/{thingId}", method = RequestMethod.DELETE)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> deleteThing(@PathVariable(name = "thingId") int thingId) {
    return new ResponseEntity<>(thingBlService.deleteThing(thingId),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取我的藏品", notes = "获取我的藏品")
  @RequestMapping(value = "thing/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ThingGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineThing() {
    return new ResponseEntity<>(thingBlService.getOwnerThings(UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取艺术家所创作的艺术品", notes = "获取艺术家所创作的艺术品")
  @RequestMapping(value = "thing/owner", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ThingGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getOwnerThing(@RequestParam("owner") String owner) {
    return new ResponseEntity<>(thingBlService.getOwnerThings(owner), HttpStatus.OK);
  }

  @ApiOperation(value = "获取艺术家所创作的艺术品", notes = "获取艺术家所创作的艺术品")
  @RequestMapping(value = "thing/author", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ThingGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getAuthorThing(@RequestParam("author") String author) {
    return new ResponseEntity<>(thingBlService.getAuthorThings(author), HttpStatus.OK);
  }

  @ApiOperation(value = "获取收藏竞品", notes = "获取收藏竞品")
  @RequestMapping(value = "favorites", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ThingGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getFavoritesThings(@RequestParam("favorites_id") int favoritesId,
      @RequestParam("status") int status) {
    return new ResponseEntity<>(
        thingBlService.getFavoritesThings(favoritesId, status, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "移动收藏竞品", notes = "移动收藏竞品")
  @RequestMapping(value = "favorites/move", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommodityGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> moveFavoritesThing(
      @RequestParam("favorites_id") int favoritesId,
      @RequestParam("thing_id") int thingId,
      @RequestParam("status") int status) {
    return new ResponseEntity<>(
        thingBlService.moveFavoritesThing(favoritesId, thingId, status),
        HttpStatus.OK);
  }

  @ApiOperation(value = "竞价", notes = "竞价")
  @RequestMapping(value = "bid", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SaleDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> bid(@RequestBody AuctionParamaters parameters) {
    try {
      return new ResponseEntity<>(auctionBlService.bid(parameters, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    } catch (ParametersErrorException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(value = "获取竞价记录", notes = "获取竞价记录")
  @RequestMapping(value = "history/{saleId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = AuctionHistoryGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getAuctionHistory(@PathVariable("saleId") int saleId) {
    return new ResponseEntity<>(auctionBlService.getAuctionHistory(saleId), HttpStatus.OK);
  }

  @ApiOperation(value = "获取交易列表", notes = "获取交易列表")
  @RequestMapping(value = "transaction/history/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = AuctionTransactionHistoryGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getAuctionTransactionHistory() {
    return new ResponseEntity<>(auctionBlService.getAuctionTransactionHistory(), HttpStatus.OK);
  }

  @ApiOperation(value = "新增交易记录", notes = "新增交易记录")
  @RequestMapping(value = "transaction", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> addAuctionTransaction(
      @RequestBody AuctionTransactionParameters parameters) {
    return new ResponseEntity<>(auctionBlService.addAuctionTransaction(parameters), HttpStatus.OK);
  }

  @ApiOperation(value = "获取最近交易记录", notes = "获取最近交易记录")
  @RequestMapping(value = "transaction/current", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getCurrentAuctionTransaction(@RequestParam("limit") int limit,
      @RequestParam("offset") int offset) {
    return new ResponseEntity<>(auctionBlService.getCurrentAuctionTransaction(limit, offset),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取首页数据统计", notes = "获取艺术品收藏、艺术家收益、拍卖场数")
  @RequestMapping(value = "statistics", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getStatistics() {
    return new ResponseEntity<>(auctionBlService.getStatistics(), HttpStatus.OK);
  }

  @ApiOperation(value = "获取参与人数数据统计", notes = "获取竞拍数据统计，参与人数")
  @RequestMapping(value = "statistics/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = AuctionJoinStatisticsResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineStatistics(@RequestParam("startTime") long startTime,
      @RequestParam("endTime") long endTime) {
    return new ResponseEntity<>(
        auctionBlService.getUserStatistics(UserInfoUtil.getUsername(), startTime, endTime),
        HttpStatus.OK);
  }


  @ApiOperation(value = "一口价", notes = "一口价")
  @RequestMapping(value = "sale/onePrice", method = RequestMethod.GET)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> onePrice(
          @RequestParam("saleId") int saleId,@RequestParam("price") int price ) {
    try {
      return new ResponseEntity<>(
              auctionBlService.onePrice(UserInfoUtil.getUsername(), saleId, price),
              HttpStatus.OK);
    } catch (SystemException e) {
      e.printStackTrace();
      return new ResponseEntity<>(
       new WrongResponse(20000,"请检查余额,或者服务器错误"),
              HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }
}
