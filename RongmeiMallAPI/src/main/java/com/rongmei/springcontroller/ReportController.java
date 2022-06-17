package com.rongmei.springcontroller;

import com.rongmei.blservice.report.ReportBlService;
import com.rongmei.parameters.report.ReportSubmitParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.report.ReportGetResponse;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "report")
public class ReportController {

  private final ReportBlService reportBlService;

  @Autowired
  public ReportController(ReportBlService reportBlService) {
    this.reportBlService = reportBlService;
  }

  @PreAuthorize("hasAnyAuthority('REPORT','ROLE_USER')")
  @ApiOperation(value = "提交举报", notes = "提交举报")
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> submitReport(
      @RequestBody ReportSubmitParameters parameters) {
    return new ResponseEntity<>(
        reportBlService.submitReport(parameters, UserInfoUtil.getUsername()), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('REPORT')")
  @ApiOperation(value = "获取举报", notes = "获取分页举报")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ReportGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getReport(@RequestParam("page") int page,
      @RequestParam("limit") int limit) {
    return new ResponseEntity<>(reportBlService.getReport(page, limit), HttpStatus.OK);
  }
}
