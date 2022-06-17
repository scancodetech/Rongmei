package com.rongmei.dao.account;

import com.rongmei.entity.account.UserRelation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRelationDao extends JpaRepository<UserRelation, Integer> {

  List<UserRelation> findAllByFromUsername(String fromUsername);

  List<UserRelation> findAllByToUsername(String toUsername);

  UserRelation findFirstByFromUsernameAndToUsernameAndRelationType(String fromUsername,
      String toUsername, int relationType);
}
