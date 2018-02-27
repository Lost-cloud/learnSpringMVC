package com.king.web.controller;

import com.king.domain.Employee;
import com.king.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/convert")
public class ConvertController {

    private final EmployeeService employeeService;

    @Autowired
    public ConvertController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/insertEmployee")
    public String insertEmployees() {
        return "testConvert";
    }

    @PostMapping(value = "/insertEmployee")
    @Valid
    public String insertEmployee( List<Employee> employees,Errors errors) {
        //如果方法返回String 在JSP视图中集合引用名称只能为employeeList，因为使用转换器后似乎会自动添加名为”employeeList“--的对象
        //如果要另取名称，需要返回ModelAndView
        if (errors.hasErrors() || employees == null) {
            LogManager.getLogger().info("THE ERROR is :" + errors + "\nThe EmployeeList is " + employees);
            return "testConvert";
        }
        LogManager.getLogger().info(employees);
        employeeService.insertEmployees(employees);
        return "testConvertOutput";
    }
}
