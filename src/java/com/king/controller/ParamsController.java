package com.king.controller;

import com.king.domain.Employee;
import com.king.domain.EmployeeParams;
import com.king.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Controller
@RequestMapping("/params")
public class ParamsController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public ParamsController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ModelAndView showModelAndView(@PathVariable long id) {
        ModelAndView view = new ModelAndView();
        view.setViewName("showEmployee");
        view.addObject("employee", employeeRepository.getEmployee(id));
        return view;
    }

    @GetMapping(value = "/json/{id}")
    public ModelAndView showEmployee(@PathVariable long id) {
        ModelAndView view = new ModelAndView();
        view.addObject(employeeRepository.getEmployee(id));
        view.setView(new MappingJackson2JsonView());
        return view;
    }

    @RequestMapping(value = "/json/findEmployee")
    public String findEmployee(){
        return "employeeForm";
    }


    private ModelAndView view;

    @RequestMapping(value = "/json/listEmployee",method = RequestMethod.POST)
    public ModelAndView showEmployees(@RequestBody EmployeeParams employeeName) {
        List<Employee> employeeList=employeeRepository.findEmployees(employeeName.getEmployeeName());
        view= new ModelAndView();
        view.addObject("employeeList",employeeList);
//        view.setViewName("employeeList");
        view.setView(new MappingJackson2JsonView());
        return view;
    }

    @RequestMapping(value = "/json/showEmployees",method = RequestMethod.GET)
    public ModelAndView showEmployees(){
        view.setView(new MappingJackson2JsonView());
        return view;
    }

}