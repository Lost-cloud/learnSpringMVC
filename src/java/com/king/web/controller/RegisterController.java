package com.king.web.controller;

import com.king.domain.Employee;
import com.king.enums.SexEnum;
import com.king.service.EmployeeService;
import com.king.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/Employee")
public class RegisterController {

    private final EmployeeService employeeService;

    @Autowired
    public RegisterController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/register")
    public String showRegistrationForm() {
        return "registerForm";
    }

    @PostMapping(value = "/register")
    public String processRegistration(Employee employee,String emp_birthday,String employee_sex) {
        //表单传递的日期为String类型,格式为yyyy-MM-dd ，需要转换为Date类型
        //不能转换直接为Bean属性的表单参数，需要自己转换
        employee.setBirthday(DateUtil.transferFormDate(emp_birthday));
        employee.setSex(SexEnum.getSexById(Integer.parseInt(employee_sex)));
        employeeService.insertEmployee(employee);
        return "redirect:/params/json/"+employee.getId();
    }

}
