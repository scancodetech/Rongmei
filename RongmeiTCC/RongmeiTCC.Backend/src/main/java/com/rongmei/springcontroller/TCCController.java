package com.rongmei.springcontroller;

import com.rongmei.blservice.TCCBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.tcc.TCCTupleUpdateParameters;
import com.rongmei.response.Response;
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
@RequestMapping(path = "tcc")
public class TCCController {

  private final TCCBlService tccBlService;

  @Autowired
  public TCCController(TCCBlService tccBlService) {
    this.tccBlService = tccBlService;
  }

  @RequestMapping(path = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<Response> getTCCs() {
    return new ResponseEntity<>(tccBlService.getTCCs(), HttpStatus.OK);
  }

  @RequestMapping(path = "key", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<Response> getTCC(@RequestParam("key") String key) {
    try {
      return new ResponseEntity<>(tccBlService.getTCC(key), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(path = "", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Response> updateTCC(@RequestBody TCCTupleUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(tccBlService.updateTCC(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
}
