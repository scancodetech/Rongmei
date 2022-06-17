package com.rongmei.dao.exam;

import com.rongmei.entity.exam.Exercise;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExerciseDao extends JpaRepository<Exercise, Long> {

  @Query("select e.id from Exercise e")
  List<Long> getAllIds();

  List<Exercise> getExercisesByIdIn(List<Long> ids);
}
