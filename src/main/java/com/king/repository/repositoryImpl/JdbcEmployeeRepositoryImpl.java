package com.king.repository.repositoryImpl;

import com.king.domain.Employee;
import com.king.enums.SexEnum;
import com.king.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("jdbcEmployeeRepo")
public class JdbcEmployeeRepositoryImpl implements EmployeeRepository {

    private JdbcOperations jdbcOperations;

    //接口方式注入，与实际的jdbcOperations实现解耦
    @Autowired
    public JdbcEmployeeRepositoryImpl(@Nullable JdbcOperations jdbcOperations){
        this.jdbcOperations=jdbcOperations;
    }

    @Override
    public Employee getEmployee(long id) {
        String sql = "select * from t_employee where id="+id;
        return  jdbcOperations.queryForObject(sql,(ResultSet rs, int rowNum)-> getEmployee(rs));
    }

    @Override
    public int deleteEmployee(long id) {
        String  sql="delete from t_employee where id="+id;
        return jdbcOperations.update(sql);
    }

    @Override
    public int updateEmployee(Employee employee) {
        String sql="update t_employee set real_name=?,note=? where id=?";
        return jdbcOperations.update(sql,employee.getRealName(),employee.getNote(),employee.getId());
    }

    @Override
    public int insertEmployee(Employee employee) {
        String sql="insert into t_employee (real_name, sex,birthday,mobile,email,position,note) values(?, ?, ?, ?, ?, ?, ? )";
        return jdbcOperations.update(sql,
                employee.getRealName(),
                employee.getSex().getId(),
                employee.getBirthday(),
                employee.getMobile(),
                employee.getEmail(),
                employee.getPosition(),
                employee.getNote());
    }

    @Override
    public List<Employee> findEmployees(String realName) {
        String sql="select * from t_employee where real_name like concat('%',  ?, '%')";
        //返回数组
        return jdbcOperations.query(sql,new Object[]{realName},(ResultSet rs, int rowNum) -> getEmployee(rs));
    }

    private Employee getEmployee(ResultSet rs) throws SQLException {
        Employee employee=new Employee(rs.getString("real_name"),rs.getString("note"));
        employee.setId(rs.getLong("id"));
        employee.setSex(SexEnum.getSexById(rs.getInt("sex")));
        employee.setBirthday(rs.getDate("birthday"));
        employee.setMobile(rs.getString("mobile"));
        employee.setEmail(rs.getString("email"));
        employee.setPosition(rs.getString("position"));
        return employee;
    }
}
