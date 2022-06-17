package com.rongmei.springcontroller;

import com.rongmei.blservice.metrics.MetricsBlService;
import com.rongmei.parameters.metrics.MetricsAddParameters;
import com.rongmei.parameters.metrics.MetricsCountParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.metrics.MetricsBatchCountResponse;
import com.rongmei.response.metrics.MetricsCountResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "metrics")
public class MetricsController {

  private final MetricsBlService metricsBlService;

  @Autowired
  public MetricsController(MetricsBlService metricsBlService) {
    this.metricsBlService = metricsBlService;
  }

  @ApiOperation(value = "新增打点", notes = "新增打点")
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> addMetrics(@RequestBody MetricsAddParameters parameters) {
    return new ResponseEntity<>(metricsBlService.addMetrics(parameters), HttpStatus.OK);
  }

  @ApiOperation(value = "新增打点，去重", notes = "新增打点，去重")
  @RequestMapping(value = "single", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> addSingleMetrics(@RequestBody MetricsAddParameters parameters) {
    return new ResponseEntity<>(metricsBlService.addSingleMetrics(parameters), HttpStatus.OK);
  }

  @ApiOperation(value = "获取聚合打点信息", notes = "获取聚合打点信息")
  @RequestMapping(value = "count", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = MetricsCountResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getCountMetrics(@RequestParam("key") String key) {
    return new ResponseEntity<>(metricsBlService.getCountMetrics(key), HttpStatus.OK);
  }

  @ApiOperation(value = "批量获取聚合打点信息", notes = "批量获取聚合打点信息")
  @RequestMapping(value = "count/batch", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = MetricsBatchCountResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getBatchCountMetrics(@RequestBody MetricsCountParameters parameters) {
    return new ResponseEntity<>(metricsBlService.getBatchCountMetrics(parameters), HttpStatus.OK);
  }
}
