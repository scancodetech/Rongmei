package com.rongmei.bl.exam;

import com.rongmei.blservice.exam.ExamBlService;
import com.rongmei.dao.exam.ExerciseDao;
import com.rongmei.dao.exam.ThinkTankExerciseDao;
import com.rongmei.dao.exam.UserExamDao;
import com.rongmei.entity.exam.Exercise;
import com.rongmei.entity.exam.ThinkTankExercise;
import com.rongmei.entity.exam.UserExam;
import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.exam.ExamSubmitParameters;
import com.rongmei.parameters.exam.ExerciseSubmitParameters;
import com.rongmei.parameters.exam.ExerciseUpdateParameters;
import com.rongmei.parameters.exam.ThinkTankExerciseSubmitParameters;
import com.rongmei.publicdatas.exam.UserExerciseItem;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.exam.ExamResponse;
import com.rongmei.response.exam.ExerciseVerificationResponse;
import com.rongmei.response.exam.ExerciseWithAnswerGetResponse;
import com.rongmei.response.exam.ExerciseWithAnswerItem;
import com.rongmei.response.exam.ExerciseWithoutAnswerGetResponse;
import com.rongmei.response.exam.ExerciseWithoutAnswerItem;
import com.rongmei.response.exam.ThinkTankExerciseGetResponse;
import com.rongmei.response.exam.ThinkTankExerciseItem;
import com.rongmei.response.exam.UserExamResponse;
import com.rongmei.util.RandomUtil;
import com.rongmei.util.UserInfoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamBlServiceImpl implements ExamBlService {

  private final ThinkTankExerciseDao thinkTankExerciseDao;
  private final ExerciseDao exerciseDao;
  private final UserExamDao userExamDao;

  @Autowired
  public ExamBlServiceImpl(ThinkTankExerciseDao thinkTankExerciseDao,
      ExerciseDao exerciseDao, UserExamDao userExamDao) {
    this.thinkTankExerciseDao = thinkTankExerciseDao;
    this.exerciseDao = exerciseDao;
    this.userExamDao = userExamDao;
  }

  @Override
  public SuccessResponse updateExercise(ExerciseUpdateParameters parameters)
      throws ThingIdDoesNotExistException {
    if (parameters.getId() == 0) {
      Exercise exercise = new Exercise(parameters.getTag(), parameters.getQuestion(),
          parameters.getSelections(), parameters.getAnswer(), true, System.currentTimeMillis(),
          System.currentTimeMillis());
      exerciseDao.save(exercise);
      return new SuccessResponse("add success");
    } else {
      Optional<Exercise> optionalExercise = exerciseDao.findById(parameters.getId());
      if (optionalExercise.isPresent()) {
        Exercise exercise = optionalExercise.get();
        exercise.setAnswer(parameters.getAnswer());
        exercise.setQuestion(parameters.getQuestion());
        exercise.setUpdateTime(System.currentTimeMillis());
        exercise.setSelections(parameters.getSelections());
        exerciseDao.save(exercise);
      } else {
        throw new ThingIdDoesNotExistException();
      }
      return new SuccessResponse("update success");
    }
  }

  @Override
  public SuccessResponse deleteExercise(long id) throws ThingIdDoesNotExistException {
    Optional<Exercise> optionalExercise = exerciseDao.findById(id);
    if (optionalExercise.isPresent()) {
      Exercise exercise = optionalExercise.get();
      exerciseDao.delete(exercise);
      return new SuccessResponse("delete success");
    } else {
      throw new ThingIdDoesNotExistException();
    }
  }

  @Override
  public ExerciseWithAnswerGetResponse getAllExercise() {
    List<Exercise> exercises = exerciseDao.findAll();
    return new ExerciseWithAnswerGetResponse(packExerciseWithAnswerItems(exercises));
  }

  @Override
  public ExerciseWithoutAnswerGetResponse getGenerateExercise(int count)
      throws ThingIdDoesNotExistException {
    List<Long> ids = exerciseDao.getAllIds();
    if (count > ids.size()) {
      throw new ThingIdDoesNotExistException();
    }
    List<Long> selectedIds = RandomUtil.generateRandomElement(ids, count);
    List<Exercise> exercises = exerciseDao.getExercisesByIdIn(selectedIds);
    return new ExerciseWithoutAnswerGetResponse(packExerciseWithoutAnswerItems(exercises));
  }

  @Override
  public ExamResponse submitUserExam(ExamSubmitParameters parameters) {
    String username = UserInfoUtil.getUsername();
    UserExam userExam = userExamDao.findFirstByUsername(username);
    if (userExam == null) {
      userExam = new UserExam(username, parameters.getUserExercises(), false,
          System.currentTimeMillis(),
          System.currentTimeMillis());
    } else {
      userExam.setUserExercises(parameters.getUserExercises());
      userExam.setUpdateTime(System.currentTimeMillis());
    }
    List<UserExerciseItem> userExerciseItems = parameters.getUserExercises();
    int totalCount = userExerciseItems.size();
    int okCount = 0;
    for (UserExerciseItem userExerciseItem : userExerciseItems) {
      Optional<Exercise> optionalExercise = exerciseDao.findById(userExerciseItem.getId());
      if (optionalExercise.isPresent()) {
        Exercise exercise = optionalExercise.get();
        if (exercise.getAnswer().equals(userExerciseItem.getUserAnswer())) {
          okCount++;
        }
      } else {
        okCount++;
      }
    }
    int score = (int) Math.ceil(okCount * 1.0 / totalCount * 100);
    if (score >= 60) {
      userExam.setPass(true);
    } else {
      userExam.setPass(false);
    }
    userExamDao.save(userExam);
    return new ExamResponse(totalCount, okCount, score);
  }

  @Override
  public UserExamResponse getUserExam(String username) throws ThingIdDoesNotExistException {
    UserExam userExam = userExamDao.findFirstByUsername(username);
    if (userExam == null) {
      throw new ThingIdDoesNotExistException();
    } else {
      return new UserExamResponse(userExam.getId(), userExam.getUsername(),
          userExam.getUserExercises(), userExam.isPass(), userExam.getCreateTime(),
          userExam.getUpdateTime());
    }
  }

  @Override
  public ExerciseVerificationResponse verificationExercise(ExerciseSubmitParameters parameters) {
    Optional<Exercise> optionalExercise = exerciseDao.findById(parameters.getId());
    if (optionalExercise.isPresent()) {
      Exercise exercise = optionalExercise.get();
      if (exercise.getAnswer().equals(parameters.getAnswer())) {
        return new ExerciseVerificationResponse(parameters.getId(), true);
      }
    }
    return new ExerciseVerificationResponse(parameters.getId(), false);
  }

  @Override
  public SuccessResponse submitThinkTankExercise(ThinkTankExerciseSubmitParameters parameters) {
    ThinkTankExercise thinkTankExercise = new ThinkTankExercise(parameters.getTag(),
        parameters.getQuestion(), parameters.getSelections(), parameters.getAnswer(), 0,
        UserInfoUtil.getUsername(), System.currentTimeMillis(), System.currentTimeMillis());
    thinkTankExerciseDao.save(thinkTankExercise);
    return new SuccessResponse("submit success");
  }

  @Override
  public SuccessResponse approveThinkTankExercise(long exerciseId, boolean isApprove)
      throws ThingIdDoesNotExistException {
    Optional<ThinkTankExercise> optionalThinkTankExercise = thinkTankExerciseDao
        .findById(exerciseId);
    if (optionalThinkTankExercise.isPresent()) {
      ThinkTankExercise thinkTankExercise = optionalThinkTankExercise.get();
      if (isApprove) {
        thinkTankExercise.setStatus(1);
      } else {
        thinkTankExercise.setStatus(2);
      }
      thinkTankExerciseDao.save(thinkTankExercise);
      Exercise exercise = new Exercise(thinkTankExercise.getTag(), thinkTankExercise.getQuestion(),
          thinkTankExercise.getSelections(), thinkTankExercise.getAnswer(), true,
          System.currentTimeMillis(), System.currentTimeMillis());
      exerciseDao.save(exercise);
    }
    throw new ThingIdDoesNotExistException();
  }

  @Override
  public ThinkTankExerciseGetResponse getAllThinkTankExercises() {
    List<ThinkTankExercise> thinkTankExercises = thinkTankExerciseDao
        .findAllByStatusIsNotOrderByUpdateTimeDesc(1);
    return new ThinkTankExerciseGetResponse(packThinkTankExerciseItems(thinkTankExercises));
  }

  @Override
  public ThinkTankExerciseGetResponse getMineThinkTankExercises() {
    List<ThinkTankExercise> thinkTankExercises = thinkTankExerciseDao
        .findAllByUsername(UserInfoUtil.getUsername());
    return new ThinkTankExerciseGetResponse(packThinkTankExerciseItems(thinkTankExercises));
  }

  private List<ThinkTankExerciseItem> packThinkTankExerciseItems(
      List<ThinkTankExercise> thinkTankExercises) {
    List<ThinkTankExerciseItem> thinkTankExerciseItems = new ArrayList<>();
    for (ThinkTankExercise thinkTankExercise : thinkTankExercises) {
      thinkTankExerciseItems.add(
          new ThinkTankExerciseItem(thinkTankExercise.getId(), thinkTankExercise.getTag(),
              thinkTankExercise.getQuestion(), thinkTankExercise.getSelections(),
              thinkTankExercise.getAnswer(), thinkTankExercise.getCreateTime(),
              thinkTankExercise.getUpdateTime(), thinkTankExercise.getStatus(),
              thinkTankExercise.getUsername()));
    }
    return thinkTankExerciseItems;
  }

  private List<ExerciseWithAnswerItem> packExerciseWithAnswerItems(List<Exercise> exercises) {
    List<ExerciseWithAnswerItem> exerciseWithAnswerItems = new ArrayList<>();
    for (Exercise exercise : exercises) {
      exerciseWithAnswerItems.add(
          new ExerciseWithAnswerItem(exercise.getId(), exercise.getTag(), exercise.getQuestion(),
              exercise.getSelections(), exercise.getAnswer(), exercise.getCreateTime(),
              exercise.getUpdateTime()));
    }
    return exerciseWithAnswerItems;
  }

  private List<ExerciseWithoutAnswerItem> packExerciseWithoutAnswerItems(List<Exercise> exercises) {
    List<ExerciseWithoutAnswerItem> exerciseWithoutAnswerItems = new ArrayList<>();
    for (Exercise exercise : exercises) {
      exerciseWithoutAnswerItems.add(
          new ExerciseWithoutAnswerItem(exercise.getId(), exercise.getTag(), exercise.getQuestion(),
              exercise.getSelections(), exercise.getCreateTime(),
              exercise.getUpdateTime()));
    }
    return exerciseWithoutAnswerItems;
  }
}
