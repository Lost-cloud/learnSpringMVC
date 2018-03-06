package com.king.config.web;

import com.king.config.RootConfig;
import com.king.web.WebConfig;
import com.sun.javafx.runtime.SystemProperties;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

public class MyWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        System.setProperty("spring.profiles.active", "production");
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        //映射所有的url，假如设置为cs.do，则需要有对应的controller
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        String filePath ="e:/tmp/fileUpload";
        File file=new File(filePath);
        if(!file.exists()) file.mkdirs();
        Long singleMax = (long)(5 * Math.pow(2, 20));
        Long totalMax = (long)(10 * Math.pow(2, 20));
        registration.setMultipartConfig(new MultipartConfigElement(filePath,singleMax,totalMax,0));
    }

}
