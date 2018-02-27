package com.king.web.advice;

import com.king.web.WebScanMarker;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@ControllerAdvice(basePackageClasses = {WebScanMarker.class})
public class CommonControllerAdvice {
    //处理器全局通知

    //在构造控制器参数之前执行，可定义一些类型转换规则
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
    }

    @ModelAttribute("initMessage")
    public String initMessage() {
        return "In CommonControllerAdvice";
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception exception,Model model ) {
        model.addAttribute("exception", exception);
        return "CommonException";
    }
}
