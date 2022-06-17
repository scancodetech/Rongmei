package com.rongmei.springcontroller;

import com.rongmei.blservice.access.AccessBlService;
import com.rongmei.parameters.access.AccessUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "access")
public class AccessController {

  private final AccessBlService accessBlService;

  @Autowired
  public AccessController(AccessBlService accessBlService) {
    this.accessBlService = accessBlService;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Response> updateAccess(
      @RequestBody AccessUpdateParameters parameters) {
    return new ResponseEntity<>(
        accessBlService.updateAccess(parameters),
        HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<Response> getAccess() {
    return new ResponseEntity<>(accessBlService.getAccess(), HttpStatus.OK);
  }
}
