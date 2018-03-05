package com.king.service.serviceImpl;

import com.king.domain.Employee;
import com.king.repository.EmployeeRepository;
import com.king.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository empRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository empRepository) {
        this.empRepository = empRepository;
    }

    /**
     *
     * @param id 要获取的Employee的id
     * @return 返回对应id的Employee
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
                propagation = Propagation.REQUIRED)
    @Cacheable(value = "employee",key = "'redis_employee_'+#id")
    public Employee getEmployee(long id) {
        return empRepository.getEmployee(id);
    }

    /**
     *
     * @param id 待删除Employee的id
     * @return 返回删除的数目
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED)
    @CacheEvict(value = "employee",key = "'redis_employee_'+#id")
    public int deleteEmployee(long id) {
        return empRepository.deleteEmployee(id);
    }

    /**
     *
     * @param deleteIDs 待删除Employee的id集合
     * @return 返回删除的总数
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED)
    public int deleteEmployees(List<Long> deleteIDs) {
        int count=0;
        for (Long deleteID : deleteIDs) {
            deleteEmployee(deleteID);
            count++;
        }
        return count;
    }

    /**
     *
     * @param employee 要升级的Employee对象
     * @return int 升级的数目 1
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED)
    @CachePut(value = "employee",key = "'redis_employee_'+#employee.id")
    public Employee updateEmployee(Employee employee) {
        empRepository.updateEmployee(employee);
        return employee;
    }

    /**
     *
     * @param employee 插入的Employee对象
     * @return int 返回插入的插入对象id
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED)
    @CachePut(value = "employee",key = "'redis_employee_'+#result.id")
    public Employee insertEmployee(Employee employee) {
        empRepository.insertEmployee(employee);
        return employee;
    }

    /**
     *
     * @param empList 插入的Employee对象集合
     * @return 返回插入的总数
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED)
    public List<Employee> insertEmployees(List<Employee> empList) {
        for (Employee employee : empList) {
            insertEmployee(employee);
        }
        return empList;
    }

    /**
     *
     * @param name 查找的名称
     * @return Employee对象的集合
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED)
    public List<Employee> findEmployees(String name) {
        return empRepository.findEmployees(name);
    }

}
