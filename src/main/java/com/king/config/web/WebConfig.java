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
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
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

    @Autowired
    private FormattingConversionService conversionService;

//    //实现WebMvcConfigure去设置转换器、拦截器等
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new StringToSexEnumConverter());
//        registry.addConverter(new StringToEmployeeConverter());
//        registry.addConverter(new StringToEmployeeCollectionConverter(conversionService));
//    }

    @Bean
    public Converter myConverter() {
        StringToEmployeeConverter employeeConverter=new StringToEmployeeConverter();
        conversionService.addConverter(employeeConverter);
        StringToEmployeeCollectionConverter employeeCollectionConverter=new StringToEmployeeCollectionConverter(conversionService);
        conversionService.addConverter(employeeCollectionConverter);
        return employeeConverter;
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
