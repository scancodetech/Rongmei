package com.rongmei.response.employees;

import com.rongmei.entity.account.Employees;
import com.rongmei.response.Response;

import java.util.List;

public class EmployeesListResponse extends Response {
    private List<Employees> employeesList;
    public EmployeesListResponse() {
    }

    public EmployeesListResponse(List<Employees> employeesList) {
        this.employeesList = employeesList;
    }

    public List<Employees> getRole1List() {
        return employeesList;
    }

    public void setRole1List(List<Employees> employeesList) {
        this.employeesList = employeesList;
    }
}
