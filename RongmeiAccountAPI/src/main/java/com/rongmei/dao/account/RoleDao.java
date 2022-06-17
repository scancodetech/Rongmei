package com.rongmei.dao.account;

import com.rongmei.entity.account.RoleE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<RoleE, Integer> {
    RoleE findByName(String name);


}
