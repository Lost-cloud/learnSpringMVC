package com.king.web.controller;

import com.king.web.exception.EmployeeException;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Date;

@Controller
@RequestMapping("/advice")
public class MyAdviceController {

    @RequestMapping("/")
    public String home() {
        return "testControllerAdvice";
    }

    /**
     * @param date       在@initBinder的方法里注册了格式编辑
     * @param amount  在方法里使用注解转换
     * @param model      在@ModelAttribute中会先存入数据
     */
    @RequestMapping("/test")
    public String testAdvice(Date date, @NumberFormat(pattern = "##,###.00元") BigDecimal amount, Model model) {
        model.addAttribute("date", date);
        model.addAttribute("amount", amount);
        model.addAttribute("initMessage", model.asMap().get("initMessage"));
        return "testAdviceOut";
    }

    @RequestMapping("/exception")
    public void exception() {
        throw new EmployeeException();
    }
}
