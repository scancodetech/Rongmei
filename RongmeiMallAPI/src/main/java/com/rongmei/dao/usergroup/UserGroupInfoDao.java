package com.rongmei.dao.usergroup;

import com.rongmei.entity.usergroup.UserGroupInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupInfoDao extends JpaRepository<UserGroupInfo, Integer> {

  List<UserGroupInfo> findAllByFirstTypeAndSecondType(String firstType, String secondType);

  UserGroupInfo findFirstByUserGroupId(int userGroupId);
}
