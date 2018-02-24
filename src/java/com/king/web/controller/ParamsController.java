package com.king.web.controller;

import com.king.domain.Employee;
import com.king.domain.EmployeeParams;
import com.king.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/params")
@SessionAttributes(names = {"id","employeeList","jsonEmployeeList","employeeMap"},types ={Employee.class, Map.class})
public class ParamsController {

    Logger log= LogManager.getLogger();
    private final EmployeeService employeeService;

    @Autowired
    public ParamsController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/jsp",method = RequestMethod.GET)
    public ModelAndView showEmployeeJspInfo( long id) {
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
        mv.addObject("jsonEmployeeList", employeeService.findEmployees(employeeParams.getEmployeeName()));
//        mv.setView(new MappingJackson2JsonView());
        //测试json重定向session 参数,不能传递参数
        mv.setViewName("testSessionForJsonPost");
        return mv;
    }

    //测试javaScript用
    @RequestMapping(value = "/json/showEmployees",method = RequestMethod.GET)
    public ModelAndView showEmployeesJsonInfo(ArrayList<Employee>employeeList){
        ModelAndView view = new ModelAndView();
        view.addObject("redirectJsonEmpList", employeeList);
        view.setViewName("testSessionForJsonPost");
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
        mv.addObject("formEmployeeList", employeeService.findEmployees(employeeParams.getEmployeeName()));
        mv.setView(new MappingJackson2JsonView());
        return mv;
    }

    //测试session发送
    @RequestMapping(value = "/testSessionAttribute")
    public ModelAndView testSessionAttrs() {
        ModelAndView mv=new ModelAndView();
        //List 和 Map只能以名称存入sessionAttributes中
        Map<String, Employee> employeeMap = new HashMap<>();
        employeeMap.put("employee100", employeeService.getEmployee(100));
        mv.addObject("employeeMap", employeeMap);
        mv.addObject("employeeList", employeeService.findEmployees("king"));

        mv.addObject("id", 1);
        mv.addObject("employee",employeeService.getEmployee(1));
        //可以重定向到下一个控制器，传递session参数
        mv.setViewName("redirect:/params/json/sessionEmployees");
        return mv;
    }

    //测试session，接收
    @RequestMapping(value = "/json/sessionEmployees",method = RequestMethod.GET)
    public ModelAndView showEmployeesTestSession(ArrayList<Employee> employeeList){
        ModelAndView view = new ModelAndView();
        view.addObject("redirectEmployeeList", employeeList);
        view.setViewName("testSession");
        return view;
    }
}
