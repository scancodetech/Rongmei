package com.rongmei.dao.account;

import com.rongmei.entity.account.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoDao extends JpaRepository<UserInfo, Integer> {
    UserInfo findUserInfoByUserId(int userId);
}
