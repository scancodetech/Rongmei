package com.rongmei.dao.account;

import com.rongmei.entity.account.User;
import com.rongmei.publicdatas.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

  User findFirstByPhone(String username);

  User findFirstByPhoneAndPassword(String phone, String password);

  List<User> findAllByRole(Role role);

  List<User> findAllByPhoneIn(List<String> phones);
}
