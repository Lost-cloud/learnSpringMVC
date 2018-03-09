package com.king.service;

import com.king.domain.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {
    Employee getEmployee(long id);
    int deleteEmployee(long id);
    int deleteEmployees(List<Long> deleteIDs);
    Employee updateEmployee(Employee employee);
    Employee insertEmployee(Employee employee);
    List<Employee> insertEmployees(List<Employee> employeeList);
    List<Employee> findEmployees(String name);
}
