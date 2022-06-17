package com.rongmei.dao.exam;

import com.rongmei.entity.exam.ThinkTankExercise;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThinkTankExerciseDao extends JpaRepository<ThinkTankExercise, Long> {

  List<ThinkTankExercise> findAllByStatusIsNotOrderByUpdateTimeDesc(int status);

  long countAllByUsername(String username);

  List<ThinkTankExercise> findAllByUsername(String username);
}
