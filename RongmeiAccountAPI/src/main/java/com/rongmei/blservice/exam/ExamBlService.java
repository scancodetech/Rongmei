package com.rongmei.blservice.exam;

import com.rongmei.exception.ThingIdDoesNotExistException;
import com.rongmei.parameters.exam.ExamSubmitParameters;
import com.rongmei.parameters.exam.ExerciseSubmitParameters;
import com.rongmei.parameters.exam.ExerciseUpdateParameters;
import com.rongmei.parameters.exam.ThinkTankExerciseSubmitParameters;
import com.rongmei.response.SuccessResponse;
import com.rongmei.response.exam.ExamResponse;
import com.rongmei.response.exam.ExerciseVerificationResponse;
import com.rongmei.response.exam.ExerciseWithAnswerGetResponse;
import com.rongmei.response.exam.ExerciseWithoutAnswerGetResponse;
import com.rongmei.response.exam.ThinkTankExerciseGetResponse;
import com.rongmei.response.exam.UserExamResponse;

public interface ExamBlService {

  SuccessResponse updateExercise(ExerciseUpdateParameters parameters)
      throws ThingIdDoesNotExistException;

  SuccessResponse deleteExercise(long id) throws ThingIdDoesNotExistException;

  ExerciseWithAnswerGetResponse getAllExercise();

  ExerciseWithoutAnswerGetResponse getGenerateExercise(int count)
      throws ThingIdDoesNotExistException;

  ExamResponse submitUserExam(ExamSubmitParameters parameters);

  UserExamResponse getUserExam(String username) throws ThingIdDoesNotExistException;

  ExerciseVerificationResponse verificationExercise(ExerciseSubmitParameters parameters);

  SuccessResponse submitThinkTankExercise(ThinkTankExerciseSubmitParameters parameters);

  SuccessResponse approveThinkTankExercise(long exerciseId, boolean isApprove)
      throws ThingIdDoesNotExistException;

  ThinkTankExerciseGetResponse getAllThinkTankExercises();

  ThinkTankExerciseGetResponse getMineThinkTankExercises();
}
