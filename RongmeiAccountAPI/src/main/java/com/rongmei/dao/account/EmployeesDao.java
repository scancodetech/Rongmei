package com.rongmei.dao.account;

import com.rongmei.entity.account.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesDao extends JpaRepository<Employees, Integer> {

    Employees findOneByUsername(String username);

    Employees findByUsernameAndPassword(String userName, String password);


}
