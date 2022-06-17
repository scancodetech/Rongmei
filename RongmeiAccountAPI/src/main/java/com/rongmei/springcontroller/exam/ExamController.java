package com.rongmei.springcontroller.exam;

import com.rongmei.blservice.exam.ExamBlService;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.exam.ExamSubmitParameters;
import com.rongmei.parameters.exam.ExerciseSubmitParameters;
import com.rongmei.parameters.exam.ExerciseUpdateParameters;
import com.rongmei.parameters.exam.ThinkTankExerciseSubmitParameters;
import com.rongmei.response.Response;
import com.rongmei.util.UserInfoUtil;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "exam")
public class ExamController {

  private final ExamBlService examBlService;

  @Autowired
  public ExamController(ExamBlService examBlService) {
    this.examBlService = examBlService;
  }

  @ApiOperation(value = "更新题目", notes = "更新或新增题目")
  @RequestMapping(method = RequestMethod.POST, path = "exercise", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> updateExercise(@RequestBody ExerciseUpdateParameters parameters) {
    try {
      return new ResponseEntity<>(examBlService.updateExercise(parameters),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
  @PreAuthorize("hasAuthority('TOPIC')")
  @ApiOperation(value = "删除题目", notes = "删除题目")
  @RequestMapping(method = RequestMethod.DELETE, path = "exercise/{id}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> deleteExercise(@PathVariable("id") long id) {
    try {
      return new ResponseEntity<>(examBlService.deleteExercise(id),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

//  @PreAuthorize("hasAuthority('TOPIC')")
  @ApiOperation(value = "获取所有题目", notes = "获取所有题目")
  @RequestMapping(method = RequestMethod.GET, path = "exercise/all", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getAllExercise() {
    return new ResponseEntity<>(examBlService.getAllExercise(),
        HttpStatus.OK);
  }

  @ApiOperation(value = "获取生成的题目", notes = "获取所有题目")
  @RequestMapping(method = RequestMethod.GET, path = "exercise/generation", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getGenerateExercise(@RequestParam("count") int count) {
    try {
      return new ResponseEntity<>(examBlService.getGenerateExercise(count),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "提交题目", notes = "提交题目并给出得分，判定通过")
  @RequestMapping(method = RequestMethod.POST, path = "user", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> submitUserExam(@RequestBody ExamSubmitParameters parameters) {
    return new ResponseEntity<>(examBlService.submitUserExam(parameters),
        HttpStatus.OK);
  }

  @ApiOperation(value = "验证答案", notes = "提交题目并验证答案")
  @RequestMapping(method = RequestMethod.POST, path = "exercise/verification", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> verificationExercise(
      @RequestBody ExerciseSubmitParameters parameters) {
    return new ResponseEntity<>(examBlService.verificationExercise(parameters), HttpStatus.OK);
  }

  @ApiOperation(value = "用户答题情况", notes = "获取用户答题情况")
  @RequestMapping(method = RequestMethod.GET, path = "user", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getUserExam() {
    try {
      return new ResponseEntity<>(examBlService.getUserExam(UserInfoUtil.getUsername()),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
  @PreAuthorize("hasAuthority('TOPICCHECK')")
  @ApiOperation(value = "智囊团提交题目", notes = "智囊团提交题目")
  @RequestMapping(method = RequestMethod.POST, path = "think_tank/exercise", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> submitThinkTankExercise(
      @RequestBody ThinkTankExerciseSubmitParameters parameters) {
    return new ResponseEntity<>(examBlService.submitThinkTankExercise(parameters), HttpStatus.OK);
  }
  @PreAuthorize("hasAuthority('TOPICCHECK')")
  @ApiOperation(value = "智囊团提交题目", notes = "智囊团提交题目")
  @RequestMapping(method = RequestMethod.GET, path = "think_tank/exercise", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getAllThinkTankExercises() {
    return new ResponseEntity<>(examBlService.getAllThinkTankExercises(), HttpStatus.OK);
  }

  @ApiOperation(value = "获取智囊团题目", notes = "获取某用户智囊团题目")
  @RequestMapping(method = RequestMethod.GET, path = "think_tank/exercise/mine", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> getMineThinkTankExercises() {
    return new ResponseEntity<>(examBlService.getMineThinkTankExercises(), HttpStatus.OK);
  }
  @PreAuthorize("hasAuthority('TOPICCHECK')")
  @ApiOperation(value = "审批智囊团提交的题目", notes = "审批智囊团提交的题目")
  @RequestMapping(method = RequestMethod.GET, path = "think_tank/exercise/approval", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Response> approveThinkTankExercise(
      @RequestParam("exerciseId") long exerciseId, @RequestParam("isApprove") boolean isApprove) {
    try {
      return new ResponseEntity<>(examBlService.approveThinkTankExercise(exerciseId, isApprove),
          HttpStatus.OK);
    } catch (ThingIdDoesNotExistException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
    }
  }
}
