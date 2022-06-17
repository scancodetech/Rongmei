package com.rongmei.dao.exam;

import com.rongmei.entity.exam.UserExam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExamDao extends JpaRepository<UserExam, Integer> {

  UserExam findFirstByUsername(String username);
}
