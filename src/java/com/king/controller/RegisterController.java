package com.king.controller;

import com.king.domain.Employee;
import com.king.enums.SexEnum;
import com.king.repository.EmployeeRepository;
import com.king.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
@RequestMapping(value = "/Employee")
public class RegisterController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public RegisterController(@Qualifier("employeeMapper") EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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
        employeeRepository.insertEmployee(employee);
        return "redirect:/Employee/"+employee.getId();
    }

}
