package com.king.config.web;

import com.king.domain.Employee;
import com.king.web.WebScanMarker;
import com.king.web.converter.StringToEmployeeCollectionConverter;
import com.king.web.converter.StringToEmployeeConverter;
import com.king.web.converter.StringToSexEnumConverter;
import com.king.web.interceptor.EmployeeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = WebScanMarker.class)
public class WebConfig  implements  WebMvcConfigurer {

    //不能同时构造器注入FormattingConversionService且调用方法addFormatters

    //  WebMvcConfigure的方法设置转换器
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSexEnumConverter());
        registry.addConverter(new StringToEmployeeConverter());
    }

    //自定义Bean设置，需要注入一个FormattingConversionService
    @Bean
    public GenericConverter myConverter(FormattingConversionService mvcConversionService) {
        StringToEmployeeCollectionConverter collectionConverter=new StringToEmployeeCollectionConverter(mvcConversionService);
        mvcConversionService.addConverter(collectionConverter);
        return collectionConverter;
    }

    @Bean
    public ViewResolver initViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new EmployeeInterceptor()).addPathPatterns("/Employee/**");
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
