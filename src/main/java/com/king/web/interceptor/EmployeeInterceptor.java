package com.king.web.interceptor;

import com.king.enums.SexEnum;
import com.king.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class EmployeeInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name=parameterNames.nextElement();
            LogManager.getLogger().info("进入EmployeeInterceptor "+name+" :"+request.getParameter(name));
        }
        return true;
    }

}
