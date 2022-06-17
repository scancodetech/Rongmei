package com.rongmei.springcontroller;

import com.rongmei.blservice.proposal.ProposalBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.proposal.ProposalUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.proposal.ProposalDetailResponse;
import com.rongmei.response.proposal.ProposalGetResponse;
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
@RequestMapping(value = "proposal")
public class ProposalController {

  private final ProposalBlService proposalBlService;

  @Autowired
  public ProposalController(ProposalBlService proposalBlService) {
    this.proposalBlService = proposalBlService;
  }

  @ApiOperation(value = "获取提案列表", notes = "根据分页和状态获取提案列表")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ProposalGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getProposals(@RequestParam("page") int page,
      @RequestParam("limit") int limit, @RequestParam("status") int status) {
    return new ResponseEntity<>(proposalBlService.getProposals(page, limit, status), HttpStatus.OK);
  }

  @ApiOperation(value = "获取我的提案列表", notes = "根据分页和状态获取我的提案列表")
  @RequestMapping(value = "me", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ProposalGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getMineProposals(@RequestParam("page") int page,
      @RequestParam("limit") int limit) {
    return new ResponseEntity<>(
        proposalBlService.getUserProposals(page, limit, UserInfoUtil.getUsername()), HttpStatus.OK);
  }

  @ApiOperation(value = "获取提案", notes = "获取某个提案")
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ProposalDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getProposal(@PathVariable("id") int id) {
    try {
      return new ResponseEntity<>(proposalBlService.getProposal(id), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "更新提案", notes = "更新提案")
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> updateProposals(
      @RequestBody ProposalUpdateParameters parameters) {
    return new ResponseEntity<>(proposalBlService.updateProposals(parameters), HttpStatus.OK);
  }

  @ApiOperation(value = "删除提案", notes = "删除提案")
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> deleteProposal(@PathVariable("id") int id) {
    try {
      return new ResponseEntity<>(proposalBlService.deleteProposal(id), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "提案投票", notes = "提案投票")
  @RequestMapping(value = "vote/{id}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> voteProposal(@PathVariable("id") int id,
      @RequestParam("isAgree") boolean isAgree) {
    return new ResponseEntity<>(proposalBlService.voteProposal(id, isAgree), HttpStatus.OK);
  }
}
