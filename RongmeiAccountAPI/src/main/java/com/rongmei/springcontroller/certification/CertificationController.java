package com.rongmei.springcontroller.certification;

import com.rongmei.blservice.certification.CertificationBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.certification.UserCertificationApproveParameters;
import com.rongmei.parameters.certification.UserCertificationUpdateParameters;
import com.rongmei.parameters.certification.UserMasterpieceApproveParameters;
import com.rongmei.parameters.certification.UserMasterpieceUpdateParameters;
import com.rongmei.response.Response;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "certification")
public class CertificationController {

  private final CertificationBlService certificationBlService;

  @Autowired
  public CertificationController(
      CertificationBlService certificationBlService) {
    this.certificationBlService = certificationBlService;
  }

  @ApiOperation(value = "提交创作者资料", notes = "提交创作者资料")
  @RequestMapping(method = RequestMethod.POST, path = "user", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> updateUserCertification(
      @RequestBody UserCertificationUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(certificationBlService.updateUserCertification(parameters),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
  @PreAuthorize("hasAuthority('AUTHOR')")
  @ApiOperation(value = "获取所有第一步申请", notes = "获取所有第一步提交创作者资料申请")
  @RequestMapping(method = RequestMethod.GET, path = "user", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getAllUserCertification() {
    return new ResponseEntity<>(certificationBlService.getAllUserCertification(), HttpStatus.OK);
  }

  @ApiOperation(value = "审批创作者资料", notes = "审批创作者资料")
  @RequestMapping(method = RequestMethod.POST, path = "user/approval", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> approveUserCertification(@RequestBody
      UserCertificationApproveParameters parameters) {
    try {
      return new ResponseEntity<>(certificationBlService.approveUserCertification(parameters),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "提交创作者代表作", notes = "提交创作者代表作")
  @RequestMapping(method = RequestMethod.POST, path = "masterpiece", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> updateMasterpieceCertification(
      @RequestBody UserMasterpieceUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(certificationBlService.updateMasterpieceCertification(parameters),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
  @PreAuthorize("hasAuthority('MASTERPIECE')")
  @ApiOperation(value = "获取所有第二步申请", notes = "获取所有第二步提交创作者代表作")
  @RequestMapping(method = RequestMethod.GET, path = "masterpiece", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getAllUserMasterpiece() {
    return new ResponseEntity<>(certificationBlService.getAllUserMasterpiece(), HttpStatus.OK);
  }

  @ApiOperation(value = "审批创作者代表作", notes = "审批创作者代表作")
  @RequestMapping(method = RequestMethod.POST, path = "masterpiece/approval", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> approveMasterpieceCertification(@RequestBody
      UserMasterpieceApproveParameters parameters) {
    try {
      return new ResponseEntity<>(
          certificationBlService.approveMasterpieceCertification(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取认证信息", notes = "获取当前创作者认证信息")
  @RequestMapping(method = RequestMethod.GET, path = "mine", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getMineCertification(
      @RequestParam("certificationType") String certificationType) {
    return new ResponseEntity<>(certificationBlService.getMineCertification(certificationType),
        HttpStatus.OK);
  }

}
