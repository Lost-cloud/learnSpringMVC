package com.king.config.web;

import com.king.web.WebScanMarker;
import com.king.web.converter.EmployeeConverter;
import com.king.web.interceptor.EmployeeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.HashSet;
import java.util.Set;

@Configuration
@ComponentScan(basePackageClasses = WebScanMarker.class)
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{

    @Bean
    public ViewResolver initViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new EmployeeInterceptor()).addPathPatterns("/Employee/**");
    }

//    //注册类型转换器
//    @Bean
//    public ConversionServiceFactoryBean conversionService() {
//        ConversionServiceFactoryBean factoryBean=new ConversionServiceFactoryBean();
//        Set<EmployeeConverter> converterSet = new HashSet<>();
//        converterSet.add(new EmployeeConverter());
//        factoryBean.setConverters(converterSet);
//        return factoryBean;
//    }
}
