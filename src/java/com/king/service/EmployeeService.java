package com.king.service;

import com.king.domain.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {
    Employee getEmployee(long id);
    int deleteEmployee(long id);
    int deleteEmployees(List<Long> deleteIDs);
    int updateEmployee(Employee employee);
    int insertEmployee(Employee employee);
    int insertEmployees(List<Employee> employeeList);
    List<Employee> findEmployees(String name);
}
