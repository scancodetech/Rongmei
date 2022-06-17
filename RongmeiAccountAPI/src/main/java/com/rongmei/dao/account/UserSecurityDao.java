package com.rongmei.dao.account;

import com.rongmei.entity.account.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSecurityDao extends JpaRepository<UserSecurity, Integer> {
  UserSecurity findFirstByUserId(int userId);
}
