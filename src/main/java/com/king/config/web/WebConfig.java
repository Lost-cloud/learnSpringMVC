package com.king.config.web;

import com.king.web.WebScanMarker;
import com.king.web.converter.EmployeeConverter;
import com.king.web.interceptor.EmployeeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackageClasses = WebScanMarker.class)
@EnableWebMvc
public class WebConfig extends DelegatingWebMvcConfiguration{

    private final FormattingConversionService conversionService;

    private List<Converter> myConvertList = null;

    @Autowired
    public WebConfig(FormattingConversionService conversionService) {
        this.conversionService = conversionService;
    }

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

    //注册类型转换器
    @Bean(name="myConverters")
    public List<Converter> conversionService() {
        if (myConvertList == null) myConvertList=new ArrayList<>();
        EmployeeConverter employeeConverter=new EmployeeConverter();
        conversionService.addConverter(employeeConverter);
        return myConvertList;
    }


//    静态资源的处理
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //文件上传处理
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }


    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource=new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:title");
        messageSource.setCacheSeconds(10);
        return messageSource;
    }

}
