package com.rongmei.springcontroller.comment;

import com.rongmei.blservice.comment.CommentBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.comment.CommentAddParameters;
import com.rongmei.parameters.comment.CommentGetParameters;
import com.rongmei.parameters.exam.ExerciseUpdateParameters;
import com.rongmei.response.Response;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "comment")
public class CommentController {

  private final CommentBlService commentBlService;

  @Autowired
  public CommentController(CommentBlService commentBlService) {
    this.commentBlService = commentBlService;
  }

  @ApiOperation(value = "新增评论", notes = "新增评论")
  @RequestMapping(method = RequestMethod.POST, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> addComment(@RequestBody CommentAddParameters parameters) {
    return new ResponseEntity<>(commentBlService.addComment(parameters, UserInfoUtil.getUsername()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "删除评论", notes = "删除评论")
  @RequestMapping(method = RequestMethod.DELETE, path = "{id}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> deleteComment(@PathVariable("id") int id) {
    try {
      return new ResponseEntity<>(commentBlService.deleteComment(id), HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "获取评论", notes = "获取评论")
  @RequestMapping(method = RequestMethod.GET, path = "", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getComment(@RequestParam("limit") int limit,
      @RequestParam("page") int page) {
    return new ResponseEntity<>(commentBlService.getComment(page, limit), HttpStatus.OK);
  }
}
