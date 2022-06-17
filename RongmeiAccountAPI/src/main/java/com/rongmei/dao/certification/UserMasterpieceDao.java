package com.rongmei.dao.certification;

import com.rongmei.entity.certification.UserMasterpiece;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMasterpieceDao extends JpaRepository<UserMasterpiece, Integer> {

  UserMasterpiece findFirstByUsernameAndCertificationType(String username,
      String certificationType);
}
