package com.king.web.controller;

import com.king.domain.Employee;
import com.king.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/Employee")
public class RegisterController {

    private final EmployeeService employeeService;

    @Autowired
    public RegisterController(EmployeeService employeeService) {
        this.employeeService = employeeService;

    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        LogManager.getLogger().info("进入InitBinder "+binder.getConversionService());
    }

    @GetMapping(value = "/register")
    public String showRegistrationForm() {
        return "registerForm";
    }

    @PostMapping(value = "/register")
    public String processRegistration(Employee employee) {
        //表单传递的日期为String类型,格式为yyyy-MM-dd ，需要转换为Date类型,spring可以自己转换
        //Sex不能转换直接为Bean属性的表单参数，需要自己转换
//        employee.setBirthday(DateUtil.transferToDate(emp_birthday));
//        employee.setSex(SexEnum.getSexById(Integer.parseInt(employee_sex)));
        employeeService.insertEmployee(employee);
        return "redirect:/params/json/"+employee.getId();
    }

    @GetMapping("/SpringForm/register")
    public String testSpringJspTag(Model model) {
        model.addAttribute("employee",employeeService.getEmployee(1));
        return "testSpringJspTag";
    }

    @PostMapping("/SpringForm/register")
    public String testSpringJspTagOut(ModelAndView mv,Employee employee) {
        mv.addObject(employee);
        return "showEmployee";
    }

}
