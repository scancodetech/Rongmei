package com.rongmei.dao.account;

import com.rongmei.entity.account.EmployeesRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeesRoleDao extends JpaRepository<EmployeesRole, Integer> {
    EmployeesRole findByEmployeesId(int employeesId);
    List<EmployeesRole> findByRoleId(int roleId);

}
