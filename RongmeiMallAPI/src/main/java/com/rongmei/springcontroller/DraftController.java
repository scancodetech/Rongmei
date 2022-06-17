package com.rongmei.springcontroller;

import com.rongmei.blservice.draft.DraftBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.draft.DraftReviewParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.child.ChildGetResponse;
import com.rongmei.response.draft.DraftBlindBoxSaleDetailResponse;
import com.rongmei.response.draft.DraftBlindBoxSaleGetResponse;
import com.rongmei.response.draft.DraftChildDetailResponse;
import com.rongmei.response.draft.DraftCommodityDetailResponse;
import com.rongmei.response.draft.DraftCommodityGetResponse;
import com.rongmei.response.draft.DraftGroupShoppingDetailResponse;
import com.rongmei.response.draft.DraftProposalDetailResponse;
import com.rongmei.response.draft.DraftResponse;
import com.rongmei.response.draft.DraftSaleDetailResponse;
import com.rongmei.response.draft.DraftSaleGetResponse;
import com.rongmei.response.groupshopping.GroupShoppingDetailResponse;
import com.rongmei.response.groupshopping.GroupShoppingGetResponse;
import com.rongmei.response.proposal.ProposalGetResponse;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "draft")
public class DraftController {

  private final DraftBlService draftBlService;

  @Autowired
  public DraftController(DraftBlService draftBlService) {
    this.draftBlService = draftBlService;
  }

  @ApiOperation(value = "审核草稿", notes = "审核草稿")
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> reviewDraft(
      @RequestBody DraftReviewParameters parameter) {
    try {
      return new ResponseEntity<>(draftBlService.reviewDraft(parameter, UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取驳回理由", notes = "获取驳回理由")
  @RequestMapping(value = "reason", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = DraftResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftReason(@RequestParam("relationId") int relationId,
      @RequestParam("draftType") int draftType) {
    try {
      return new ResponseEntity<>(draftBlService.getDraftReason(relationId, draftType),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @PreAuthorize("hasAuthority('MATERIAL')")
  @ApiOperation(value = "获取待审核的素材", notes = "获取待审核的素材")
  @RequestMapping(value = "commodity", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = DraftCommodityGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftCommodity() {
    return new ResponseEntity<>(draftBlService.getDraftCommodity(), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('MATERIAL')")
  @ApiOperation(value = "获取待审核的素材", notes = "获取待审核的某素材")
  @RequestMapping(value = "commodity/{relationId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = DraftCommodityDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftCommodityDetail(
      @PathVariable("relationId") int relationId) {
    try {
      return new ResponseEntity<>(draftBlService.getDraftCommodityDetail(relationId),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @PreAuthorize("hasAuthority('LOTS')")
  @ApiOperation(value = "获取待审核的拍品", notes = "获取待审核的拍品")
  @RequestMapping(value = "sale", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = DraftSaleGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftSale() {
    return new ResponseEntity<>(draftBlService.getDraftSale(), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('LOTS')")
  @ApiOperation(value = "获取待审核的拍品", notes = "获取待审核的拍品")
  @RequestMapping(value = "sale/{relationId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = DraftSaleDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftSaleDetail(@PathVariable("relationId") int relationId) {
    try {
      return new ResponseEntity<>(draftBlService.getDraftSaleDetail(relationId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @PreAuthorize("hasAuthority('BOXEGG')")
  @ApiOperation(value = "获取待审核的盒蛋", notes = "获取待审核的盒蛋")
  @RequestMapping(value = "blindboxsale", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = DraftBlindBoxSaleGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftBlindBoxSale() {
    return new ResponseEntity<>(draftBlService.getDraftBlindBoxSale(), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('BOXEGG')")
  @ApiOperation(value = "获取待审核的盒蛋", notes = "获取待审核的盒蛋")
  @RequestMapping(value = "blindboxsale/{relationId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = DraftBlindBoxSaleDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftBlindBoxSaleDetail(
      @PathVariable("relationId") int relationId) {
    try {
      return new ResponseEntity<>(draftBlService.getDraftBlindBoxSaleDetail(relationId),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @PreAuthorize("hasAuthority('PROPOSAL')")
  @ApiOperation(value = "获取待审核的提案", notes = "获取待审核的提案")
  @RequestMapping(value = "proposal", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ProposalGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftProposal() {
    return new ResponseEntity<>(draftBlService.getDraftProposal(), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('PROPOSAL')")
  @ApiOperation(value = "获取待审核的提案", notes = "获取待审核的提案")
  @RequestMapping(value = "proposal/{relationId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = DraftProposalDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftProposalDetail(
      @PathVariable("relationId") int relationId) {
    try {
      return new ResponseEntity<>(draftBlService.getDraftProposalDetail(relationId),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @PreAuthorize("hasAuthority('GIANTCAR')")
  @ApiOperation(value = "获取待审核的巨人车", notes = "获取待审核的巨人车")
  @RequestMapping(value = "groupshopping", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = GroupShoppingGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftGroupShopping() {
    return new ResponseEntity<>(draftBlService.getDraftGroupShopping(), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('GIANTCAR')")
  @ApiOperation(value = "获取待审核的巨人车", notes = "获取待审核的巨人车")
  @RequestMapping(value = "groupshopping/{relationId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = DraftGroupShoppingDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftGroupShoppingDetail(
      @PathVariable("relationId") int relationId) {
    try {
      return new ResponseEntity<>(draftBlService.getDraftGroupShoppingDetail(relationId),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @PreAuthorize("hasAuthority('PUBLISH')")
  @ApiOperation(value = "获取待审核的溜孩子", notes = "获取待审核的溜孩子")
  @RequestMapping(value = "child", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ChildGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftChild() {
    return new ResponseEntity<>(draftBlService.getDraftChild(), HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('PUBLISH')")
  @ApiOperation(value = "获取待审核的溜孩子", notes = "获取待审核的溜孩子")
  @RequestMapping(value = "child/{relationId}", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = DraftChildDetailResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getDraftChildDetail(@PathVariable("relationId") int relationId) {
    try {
      return new ResponseEntity<>(draftBlService.getDraftChildDetail(relationId), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
}
