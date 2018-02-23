package com.king.web.controller;

import com.king.domain.Employee;
import com.king.domain.EmployeeParams;
import com.king.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Controller
@RequestMapping("/params")
public class ParamsController {

    Logger log= LogManager.getLogger();
    private final EmployeeService employeeService;
    private List<Employee> employeeList;

    @Autowired
    public ParamsController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/jsp/{id}",method = RequestMethod.GET)
    public ModelAndView showEmployeeJspInfo(@PathVariable long id) {
        ModelAndView view = new ModelAndView();
        view.setViewName("showEmployee");
        view.addObject("employee", employeeService.getEmployee(id));
        return view;
    }

    @GetMapping(value = "/json/{id}")
    public ModelAndView showEmployeeJsonInfo(@PathVariable long id) {
        ModelAndView view = new ModelAndView();
        view.addObject(employeeService.getEmployee(id));
        view.setView(new MappingJackson2JsonView());
        return view;
    }

//    返回包含提交json数据的表单
    @RequestMapping(value = "/findEmployees")
    public String findEmployeesByJson(){
        return "postParams";
    }

    //提交json数据
    @RequestMapping(value = "/json/postJsonParams",method = RequestMethod.POST)
    public ModelAndView postJsonParams(@RequestBody EmployeeParams employeeParams) {
        ModelAndView mv=new ModelAndView();
        employeeList = employeeService.findEmployees(employeeParams.getEmployeeName());
        mv.addObject("employeeList", employeeList);
        mv.setView(new MappingJackson2JsonView());
        return mv;
    }

    //测试javaScript用
    @RequestMapping(value = "/json/showEmployees",method = RequestMethod.GET)
    public ModelAndView showEmployeesJsonInfo(){
        ModelAndView view = new ModelAndView();
        view.addObject("employeeList", employeeList);
        view.setViewName("employeeList");
        return view;
    }

    @PostMapping(value = "/json/deleteEmployees")
    public String jsonParamDeleteEmployees(@RequestBody List<Long> idList) {
        employeeService.deleteEmployees(idList);
        return "redirect:/params/json/findEmployee";
    }

    @RequestMapping(value = "/json/findEmployees")
    public ModelAndView formToJsonEmployees( EmployeeParams employeeParams) {
        ModelAndView mv=new ModelAndView();
        List<Employee> employeeList = employeeService.findEmployees(employeeParams.getEmployeeName());
        mv.addObject("employeeList", employeeList);
        mv.setView(new MappingJackson2JsonView());
        return mv;
    }
}
