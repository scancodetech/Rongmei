package com.rongmei.dao.pcuser;

import com.rongmei.entity.PcUser.PcUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PcUserDao  extends JpaRepository<PcUser, Integer> {
}
