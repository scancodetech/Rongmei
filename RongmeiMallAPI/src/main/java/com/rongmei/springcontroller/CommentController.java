package com.rongmei.springcontroller;

import com.rongmei.blservice.comment.CommentBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.comment.CommentSendParameters;
import com.rongmei.parameters.commodity.CommodityUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.WrongResponse;
import com.rongmei.response.comment.CommentGetResponse;
import com.rongmei.response.commodity.CommodityDetailResponse;
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
@RequestMapping(value = "comment")
public class CommentController {

  private final CommentBlService commentBlService;

  @Autowired
  public CommentController(CommentBlService commentBlService) {
    this.commentBlService = commentBlService;
  }

  @ApiOperation(value = "发送评论", notes = "发送评论")
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> sendComment(
      @RequestBody CommentSendParameters parameters) {
    try {
      return new ResponseEntity<>(commentBlService.sendComment(parameters), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取评论", notes = "获取评论")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = CommentGetResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> getComments(@RequestParam("relationId") int relationId,
      @RequestParam("toCommentId") long toCommentId,
      @RequestParam("page") int page, @RequestParam("limit") int limit) {
    return new ResponseEntity<>(commentBlService.getComments(relationId,toCommentId, page, limit),
        HttpStatus.OK);
  }

  @ApiOperation(value = "喜欢评论", notes = "喜欢某评论")
  @RequestMapping(value = "like", method = RequestMethod.POST)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
  @ResponseBody
  public ResponseEntity<Response> likeComments(@RequestParam("commentId") long commentId) {
    return new ResponseEntity<>(commentBlService.likeComments(commentId), HttpStatus.OK);
  }
}
