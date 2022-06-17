package com.rongmei.springcontroller;

import com.rongmei.blservice.boxegg.BoxEggBlService;
import com.rongmei.blservice.child.ChildBlService;
import com.rongmei.exception.AddFailedException;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.exception.ThingNameDoesNotExistException;
import com.rongmei.parameters.boxegg.AddBoxEggParameters;
import com.rongmei.parameters.boxegg.CastBoxEggParameters;
import com.rongmei.parameters.boxegg.PublishBoxEggParameters;
import com.rongmei.parameters.boxegg.UpdateCastBoxEggParameters;
import com.rongmei.parameters.child.ChildUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.child.ChildDetailResponse;
import com.rongmei.response.child.ChildGetResponse;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "box_egg")
public class BoxEggController {

  private final BoxEggBlService boxEggBlService;

  @Autowired
  public BoxEggController(BoxEggBlService boxEggBlService) {
    this.boxEggBlService = boxEggBlService;
  }

  @ApiOperation(value = "铸造盒蛋设定", notes = "铸造盒蛋设定")
  @RequestMapping(value = "castBoxEgg", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> castBoxEgg(@RequestBody CastBoxEggParameters parameters) {
    try {
      return new ResponseEntity<>(boxEggBlService.castBoxEgg(parameters), HttpStatus.OK);
    } catch (AddFailedException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "查看铸造盒蛋设定列表 nft 暂时好像没有审核状态", notes = "查看铸造盒蛋设定列表")
  @RequestMapping(value = "findBoxEggByStatus", method = RequestMethod.GET)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> findBoxEggByStatus(@RequestParam (value = "status") int status ) {
    try{
    return new ResponseEntity<>(boxEggBlService.findCastBoxEggByStatus(status), HttpStatus.OK);
  } catch (ThingIdDoesNotExistException e) {
    e.printStackTrace();
    return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
  }
  }


  @ApiOperation(value = "查找所有的系列盒蛋系列名称", notes = "查找所有的系列盒蛋系列名称")
  @RequestMapping(value = "findAllAddBoxEggName", method = RequestMethod.GET)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> findAllAddBoxEggName() {
      return new ResponseEntity<>(boxEggBlService.findAllAddBoxEggName(), HttpStatus.OK);
  }
  @ApiOperation(value = "通过名称查找单个系列盒蛋信息", notes = "通过名称查找单个系列盒蛋信息")
  @RequestMapping(value = "findAddBoxEggByName", method = RequestMethod.GET)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> findAddBoxEggByName(@RequestParam (value = "seriesName") String seriesName) {
    try {
    return new ResponseEntity<>(boxEggBlService.findAddBoxEggByName(seriesName), HttpStatus.OK);
  } catch (ThingIdDoesNotExistException e) {
    e.printStackTrace();
    return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
  }catch (ThingNameDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
  @ApiOperation(value = "盒蛋系列随机封装", notes = "盒蛋系列随机封装")
  @RequestMapping(value = "randomPackageAddBoxEgg", method = RequestMethod.GET)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> randomPackageAddBoxEgg(@RequestBody AddBoxEggParameters parameters) {
    try {
      return new ResponseEntity<>(boxEggBlService.randomPackageAddBoxEgg(parameters), HttpStatus.OK);
    } catch (AddFailedException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "前端活动调用盒蛋系列随机封装并且显示", notes = "前端活动调用盒蛋系列随机封装并且显示")
  @RequestMapping(value = "randomPackageAddBoxEgg2", method = RequestMethod.GET)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> randomPackageAddBoxEgg2(
//          @RequestBody AddBoxEggParameters parameters
          @RequestParam(value = "addBoxEggId") int addBoxEggId  //这个都还不确定是这个参数
  ) {
    try {
      return new ResponseEntity<>(boxEggBlService.randomPackageAddBoxEgg2(addBoxEggId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }


  @ApiOperation(value = "查看新增系列盒蛋列表", notes = "查看新增系列盒蛋列表")
  @RequestMapping(value = "findAddBoxEggByStatus", method = RequestMethod.GET)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> findAddBoxEggByStatus(@RequestParam (value = "status") int status ) {
    try {
    return new ResponseEntity<>(boxEggBlService.findAddBoxEggByStatus(status), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "发布盒蛋", notes = "发布盒蛋")
  @RequestMapping(value = "publishBoxEgg", method = RequestMethod.GET)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> publishBoxEgg(@RequestBody PublishBoxEggParameters parameters ) {
    try {
      return new ResponseEntity<>(boxEggBlService.publishBoxEgg(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    } catch (AddFailedException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }


//  @ApiOperation(value = "获取盒蛋系列活动抽奖列表", notes = "获取盒蛋系列活动抽奖列表")
//  @RequestMapping(value = "getAllBoxSeriesList", method = RequestMethod.GET)
//  @ApiResponses(value = {
//          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//  @ResponseBody
//  public ResponseEntity<Response> getAllBoxSeriesList(
//  ) {
//    try {
//      return new ResponseEntity<>(boxEggBlService.getAllBoxSeriesList(), HttpStatus.OK);
//    } catch (ThingIdDoesNotExistException e) {
//      e.printStackTrace();
//      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//    }
//  }
//  @ApiOperation(value = "获取记录用户排队的接口代码", notes = "记录用户排队的接口代码")
//  @RequestMapping(value = "randomPackageAddBoxEgg", method = RequestMethod.POST)
//  @ApiResponses(value = {
//          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//  @ResponseBody
//  public ResponseEntity<Response> recordLineInformation(
////          @RequestBody AddBoxEggParameters parameters
//          @RequestParam(value = "addBoxEggId") int addBoxEggId  //这个都还不确定是这个参数
//  ) {
//    try {
//      return new ResponseEntity<>(boxEggBlService.recordLineInformation(addBoxEggId), HttpStatus.OK);
//    } catch (ThingIdDoesNotExistException e) {
//      e.printStackTrace();
//      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//    }
//  }
//
//    @ApiOperation(value = "获取记录用户排队的接口代码", notes = "记录用户排队的接口代码")
//  @RequestMapping(value = "randomPackageAddBoxEgg", method = RequestMethod.GET)
//  @ApiResponses(value = {
//          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//  @ResponseBody
//  public ResponseEntity<Response> recordLineInformation(
////          @RequestBody AddBoxEggParameters parameters
//          @RequestParam(value = "addBoxEggId") int addBoxEggId  //这个都还不确定是这个参数
//  ) {
//    try {
//      return new ResponseEntity<>(boxEggBlService.recordLineInformation(addBoxEggId), HttpStatus.OK);
//    } catch (ThingIdDoesNotExistException e) {
//      e.printStackTrace();
//      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//    }
//  }
//  @ApiOperation(value = "判断自己是否排队是第一个可以抽奖的", notes = "判断自己是否排队是第一个可以抽奖的")
//  @RequestMapping(value = "randomPackageAddBoxEgg", method = RequestMethod.GET)
//  @ApiResponses(value = {
//          @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
//          @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//          @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//  @ResponseBody
//  public ResponseEntity<Response> judgeLineInformation(
////          @RequestBody AddBoxEggParameters parameters
//          @RequestParam(value = "addBoxEggId") int addBoxEggId  //这个都还不确定是这个参数
//  ) {
//    try {
//      return new ResponseEntity<>(boxEggBlService.judgeLineInformation(addBoxEggId), HttpStatus.OK);
//    } catch (ThingIdDoesNotExistException e) {
//      e.printStackTrace();
//      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
//    }
//  }
}
