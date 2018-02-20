package com.king.repository;

import com.king.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository {
    Employee getEmployee(long id) ;
    int deleteEmployee(long id);
    int updateEmployee(Employee employee);
    int insertEmployee(Employee employee);
    List<Employee> findEmployees(String realName);
}
