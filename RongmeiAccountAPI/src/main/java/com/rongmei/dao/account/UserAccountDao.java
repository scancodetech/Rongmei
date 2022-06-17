package com.rongmei.dao.account;

import com.rongmei.entity.account.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountDao extends JpaRepository<UserAccount, Integer> {

  UserAccount findFirstByUserId(int userId);
}
