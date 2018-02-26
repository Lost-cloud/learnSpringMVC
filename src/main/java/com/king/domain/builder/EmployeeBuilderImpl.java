package com.king.domain.builder;

import com.king.domain.Employee;
import com.king.enums.SexEnum;
import org.springframework.stereotype.Component;

import java.util.Date;

public class EmployeeBuilderImpl implements EmployeeBuilder {

    private Employee employee;

    public  EmployeeBuilderImpl(Employee employee){
        this.employee=employee;
    }

    @Override
    public EmployeeBuilder buildRealName(String realName) {
        employee.setRealName(realName);
        return this;
    }

    @Override
    public EmployeeBuilder buildSex(SexEnum sex) {
        employee.setSex(sex);
        return this;
    }

    @Override
    public EmployeeBuilder buildBirthday(Date birthday) {
        employee.setBirthday(birthday);
        return this;
    }

    @Override
    public EmployeeBuilder buildMobile(String mobile) {
        employee.setMobile(mobile);
        return this;
    }

    @Override
    public EmployeeBuilder buildEmail(String email) {
        employee.setEmail(email);
        return this;
    }

    @Override
    public EmployeeBuilder buildPosition(String position) {
        employee.setPosition(position);
        return this;
    }

    @Override
    public EmployeeBuilder buildNote(String note) {
        employee.setNote(note);
        return this;
    }

    @Override
    public Employee getEmployee() {
        return this.employee;
    }
}
