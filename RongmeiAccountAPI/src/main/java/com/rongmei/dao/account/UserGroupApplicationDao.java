package com.rongmei.dao.account;

import com.rongmei.entity.account.UserGroupApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupApplicationDao extends JpaRepository<UserGroupApplication, Integer> {

  UserGroupApplication findFirstByUsername(String username);
}
