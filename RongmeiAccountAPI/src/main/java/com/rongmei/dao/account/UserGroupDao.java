package com.rongmei.dao.account;

import com.rongmei.entity.account.UserGroup;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupDao extends JpaRepository<UserGroup, Integer> {

  List<UserGroup> findUserGroupsByUsernamesContains(String username);
}
