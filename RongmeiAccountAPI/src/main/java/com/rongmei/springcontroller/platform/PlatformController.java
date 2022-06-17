package com.rongmei.springcontroller.platform;

import com.rongmei.blservice.platform.PlatformBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.platform.PlatformUpdateParameters;
import com.rongmei.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "platform")
public class PlatformController {

  private final PlatformBlService platformBlService;

  @Autowired
  public PlatformController(PlatformBlService platformBlService) {
    this.platformBlService = platformBlService;
  }

  @PreAuthorize("hasAuthority('PLATFORM')")
  @RequestMapping(method = RequestMethod.GET, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getPlatforms() {
    return new ResponseEntity<>(platformBlService.getPlatforms(), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('PLATFORM')")
  @RequestMapping(method = RequestMethod.GET, path = "{platformId}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getPlatformDetail(
      @PathVariable(value = "platformId") int platformId) {
    try {
      return new ResponseEntity<>(platformBlService.getPlatformDetail(platformId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @PreAuthorize("hasAuthority('PLATFORM')")
  @RequestMapping(method = RequestMethod.POST, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> updatePlatform(@RequestBody PlatformUpdateParameters parameters) {
    return new ResponseEntity<>(platformBlService.updatePlatform(parameters), HttpStatus.OK);
  }
}
