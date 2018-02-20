package com.king.domain.builder;

import com.king.domain.Employee;
import com.king.enums.SexEnum;

import java.util.Date;

public interface EmployeeBuilder   {
    EmployeeBuilder buildRealName(String realName);
    EmployeeBuilder buildSex(SexEnum sex);
    EmployeeBuilder buildBirthday(Date birthday);
    EmployeeBuilder buildMobile(String mobile);
    EmployeeBuilder buildEmail(String email);
    EmployeeBuilder buildPosition(String position);
    EmployeeBuilder buildNote(String note);
    Employee getEmployee();
}
