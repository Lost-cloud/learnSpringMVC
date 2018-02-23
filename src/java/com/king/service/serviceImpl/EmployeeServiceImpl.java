package com.king.service.serviceImpl;

import com.king.domain.Employee;
import com.king.repository.EmployeeRepository;
import com.king.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
    public Employee getEmployee(long id) {
        return empRepository.getEmployee(id);
    }

    /**
     *
     * @param id 待删除Employee的id
     * @return 返回删除的数目
     */
    public int deleteEmployee(long id) {
        return empRepository.deleteEmployee(id);
    }

    /**
     *
     * @param deleteIDs 待删除Employee的id集合
     * @return int 返回删除的总数
     */
    @Override
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
    public int updateEmployee(Employee employee) {
        return empRepository.updateEmployee(employee);
    }

    /**
     *
     * @param employee 插入的Employee对象
     * @return int 返回插入的数目 1
     */
    @Override
    public int insertEmployee(Employee employee) {
        return empRepository.insertEmployee(employee);
    }

    /**
     *
     * @param empList 插入的Employee对象集合
     * @return 返回插入的总数
     */
    @Override
    public int insertEmployees(List<Employee> empList) {
        int count=0;
        for (Employee employee : empList) {
            insertEmployee(employee);
            count++;
        }
        return count;
    }

    /**
     *
     * @param name 查找的名称
     * @return Employee对象的集合
     */
    @Override
    public List<Employee> findEmployees(String name) {
        return empRepository.findEmployees(name);
    }

}
