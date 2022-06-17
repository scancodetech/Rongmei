package com.rongmei.dao.access;

import com.rongmei.entity.access.Access;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessDao extends JpaRepository<Access, Integer> {

  Access findFirstByAccessKeyAndAccessSecret(String accessKey, String accessSecret);
}
