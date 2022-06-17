package com.rongmei.springcontroller;

import com.rongmei.blservice.access.AccessBlService;
import com.rongmei.blservice.stats.StatsBlService;
import com.rongmei.exception.UsernameDoesNotFoundException;
import com.rongmei.parameters.access.AccessUpdateParameters;
import com.rongmei.parameters.stats.StatsCreateParameters;
import com.rongmei.response.Response;
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
@RequestMapping(value = "stats")
public class StatsController {

  private final StatsBlService statsBlService;

  @Autowired
  public StatsController(StatsBlService statsBlService) {
    this.statsBlService = statsBlService;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Response> createStats(
      @RequestBody StatsCreateParameters parameters) {
    try {
      return new ResponseEntity<>(statsBlService.createStats(parameters), HttpStatus.OK);
    } catch (UsernameDoesNotFoundException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<Response> getStats() {
    return new ResponseEntity<>(statsBlService.getStats(), HttpStatus.OK);
  }

  @RequestMapping(value = "data", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<Response> getData(@RequestParam("txHash") String txHash) {
    return new ResponseEntity<>(statsBlService.getData(txHash), HttpStatus.OK);
  }

  @RequestMapping(value = "home", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<Response> getHomeStats() {
    return new ResponseEntity<>(statsBlService.getHomeStats(), HttpStatus.OK);
  }
}
