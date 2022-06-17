package com.rongmei.dao.certification;

import com.rongmei.entity.certification.UserCertification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCertificationDao extends JpaRepository<UserCertification, Integer> {

  UserCertification findFirstByUsernameAndCertificationType(String username,
      String certificationType);
}
