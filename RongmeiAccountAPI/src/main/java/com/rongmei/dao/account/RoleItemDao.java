package com.rongmei.dao.account;

import com.rongmei.entity.account.RoleItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleItemDao extends JpaRepository<RoleItems, Integer> {
    List<RoleItems> findByRoleId(int roleId);


}
