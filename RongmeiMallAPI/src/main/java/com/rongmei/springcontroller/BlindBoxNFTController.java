package com.rongmei.springcontroller;

import com.rongmei.blservice.blindboxnft.BlindBoxNFTBlService;
import com.rongmei.blservice.blindboxnft.BlindBoxSaleBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.blindboxnft.BlindBoxNftTransferParameters;
import com.rongmei.parameters.blindboxnft.BlindBoxNftUpdateParameters;
import com.rongmei.parameters.blindboxnft.BlindBoxOrderCancelParameter;
import com.rongmei.parameters.blindboxnft.BlindBoxOrderFinishParameter;
import com.rongmei.parameters.blindboxnft.BlindBoxSaleUpdateParameters;
import com.rongmei.parameters.blindboxnft.BlindBoxThemeQueueParameter;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.blindbox.BlindBoxGetResponse;
import com.rongmei.response.blindboxnft.BlindBoxNFTGetResponse;
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
@RequestMapping(value = "blind_box_nft")
public class BlindBoxNFTController {

  private final BlindBoxNFTBlService blindBoxNFTBlService;
  private final BlindBoxSaleBlService blindBoxSaleBlService;

  @Autowired
  public BlindBoxNFTController(BlindBoxNFTBlService blindBoxNFTBlService,
      BlindBoxSaleBlService blindBoxSaleBlService) {
    this.blindBoxNFTBlService = blindBoxNFTBlService;
    this.blindBoxSaleBlService = blindBoxSaleBlService;
  }

  @ApiOperation(value = "????????????NFT", notes = "????????????NFT")
  @RequestMapping(value = "nft", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateNFT(@RequestBody BlindBoxNftUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(
          blindBoxNFTBlService.updateNFT(parameters, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "????????????NFT", notes = "????????????NFT")
  @RequestMapping(value = "nft/transfer/inner", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> transferNFT(
      @RequestBody BlindBoxNftTransferParameters parameters) {
    try {
      return new ResponseEntity<>(blindBoxNFTBlService.transferNFT(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "????????????NFT", notes = "????????????NFT")
  @RequestMapping(value = "nft/{nftId}", method = RequestMethod.DELETE)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> deleteNFT(@PathVariable(name = "nftId") int nftId) {
    try {
      return new ResponseEntity<>(blindBoxNFTBlService.deleteNFT(nftId),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "????????????NFT", notes = "????????????NFT")
  @RequestMapping(value = "nft/{nftId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getBlindBoxNFT(@PathVariable(name = "nftId") int nftId) {
    try {
      return new ResponseEntity<>(blindBoxNFTBlService.getBlindBoxNFT(nftId),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
  @RequestMapping(value = "nft/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BlindBoxGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineBlindBoxNFTList(@RequestParam("page") int page,
      @RequestParam("limit") int limit) {
    return new ResponseEntity<>(
        blindBoxNFTBlService.getMineBlindBoxNFTList(UserInfoUtil.getUsername(), page, limit),
        HttpStatus.OK);
  }

  @ApiOperation(value = "????????????", notes = "????????????????????????")
  @RequestMapping(value = "sale", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateSale(@RequestBody BlindBoxSaleUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(
          blindBoxSaleBlService.updateSale(parameters, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "????????????", notes = "???????????????")
  @RequestMapping(value = "sale/{saleId}", method = RequestMethod.DELETE)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> deleteSale(@PathVariable(name = "saleId") int saleId) {
    try {
      return new ResponseEntity<>(blindBoxSaleBlService.deleteSale(saleId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.OK);
    }
  }

  @ApiOperation(value = "??????????????????????????????", notes = "??????????????????????????????")
  @RequestMapping(value = "sale/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineBlindBoxSaleList(
      @RequestParam(value = "draftStatus", defaultValue = "-1") int draftStatus) {
    return new ResponseEntity<>(blindBoxSaleBlService.getMineBlindBoxSaleList(draftStatus),
        HttpStatus.OK);
  }

  @ApiOperation(value = "??????????????????", notes = "????????????id????????????")
  @RequestMapping(value = "sale/{saleId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getBlindBoxSaleDetail(@PathVariable("saleId") long saleId) {
    try {
      return new ResponseEntity<>(blindBoxSaleBlService.getBlindBoxSaleDetail(saleId),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "??????????????????", notes = "???????????????????????????????????????")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BlindBoxNFTGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getBlindBoxList(
      @RequestParam(value = "theme", defaultValue = "") String theme,
      @RequestParam(value = "classification", defaultValue = "") String classification) {
    return new ResponseEntity<>(blindBoxNFTBlService.getBlindBoxList(theme, classification),
        HttpStatus.OK);
  }

  @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
  @RequestMapping(value = "recommend", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BlindBoxGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getRecommendBlindBoxSaleList(@RequestParam("page") int page,
      @RequestParam("limit") int limit) {
    return new ResponseEntity<>(
        blindBoxSaleBlService.getRecommendBlindBoxSaleList(UserInfoUtil.getUsername(), page, limit),
        HttpStatus.OK);
  }

  @ApiOperation(value = "????????????????????????", notes = "?????????????????????????????????????????????")
  @RequestMapping(value = "sale", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = BlindBoxGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getBlindBoxSaleList(
      @RequestParam(value = "theme", defaultValue = "") String theme,
      @RequestParam(value = "classification", defaultValue = "") String classification) {
    return new ResponseEntity<>(blindBoxSaleBlService.getBlindBoxSaleList(theme, classification),
        HttpStatus.OK);
  }

  @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
  @RequestMapping(value = "order/queue", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> orderQueueBlindBoxTheme(
      @RequestBody BlindBoxThemeQueueParameter parameter) {
    return new ResponseEntity<>(
        blindBoxSaleBlService.orderQueueBlindBoxTheme(parameter, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "??????????????????", notes = "??????????????????")
  @RequestMapping(value = "order/cancel", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> orderCancelBlindBox(
      @RequestBody BlindBoxOrderCancelParameter parameter) {
    try {
      return new ResponseEntity<>(
          blindBoxSaleBlService.orderCancelBlindBox(parameter, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "????????????", notes = "????????????")
  @RequestMapping(value = "order/finish", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> orderFinishBlindBox(
      @RequestBody BlindBoxOrderFinishParameter parameter) {
    try {
      return new ResponseEntity<>(                          
          blindBoxSaleBlService.orderFinishBlindBox(parameter, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "????????????", notes = "????????????")
  @RequestMapping(value = "statistics/mine", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineBlindBoxSaleStatistics(
      @RequestParam("startTime") long startTime, @RequestParam("endTime") long endTime) {
    return new ResponseEntity<>(blindBoxSaleBlService
        .getUserBlindBoxSaleStatistics(UserInfoUtil.getUsername(), startTime, endTime),
        HttpStatus.OK);
  }
}
