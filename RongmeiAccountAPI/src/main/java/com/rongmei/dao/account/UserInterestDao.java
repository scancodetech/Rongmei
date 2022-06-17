package com.rongmei.dao.account;

import com.rongmei.entity.account.UserInterest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterestDao extends JpaRepository<UserInterest, Long> {

  List<UserInterest> findUserInterestsByUsername(String username);
}