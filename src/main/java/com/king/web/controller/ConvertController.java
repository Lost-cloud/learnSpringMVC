package com.king.web.controller;

import com.king.domain.Employee;
import com.king.service.EmployeeService;
import org.apache.commons.collections4.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/convert")
public class ConvertController {

    private final EmployeeService employeeService;

    @Autowired
    public ConvertController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/insertEmployee")
    public String insertEmployees(Model model) {
        return "testConvert";
    }

    @PostMapping("/insertEmployee")
    public String insertEmployee(@Valid ArrayList<Employee> employeeList, Errors errors) {
        ModelAndView mv=new ModelAndView();
        if (errors.hasErrors()||employeeList==null) {
            LogManager.getLogger().info("THE ERROR is :"+errors+"\nThe EmployeeList is "+employeeList);
            return "testConvert";
        }
//        employeeService.insertEmployees(employee);
        mv.addObject("employeeList",employeeList);
//        mv.setView(new MappingJackson2JsonView());
        return "testConverterOutput";
    }
}
