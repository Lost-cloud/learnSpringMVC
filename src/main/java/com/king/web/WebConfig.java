package com.king.web;

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
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Locale;

@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = WebScanMarker.class)
public class WebConfig  implements  WebMvcConfigurer {

    //不能同时构造器注入FormattingConversionService且调用方法addFormatters

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;

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
        registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**");
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

    //资源文件加载
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource=new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("utf-8");
        messageSource.setCacheSeconds(3);
        messageSource.setBasename("classpath:title");
        return messageSource;
    }

    //地区处理，默认为简体中文
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr=new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return slr;
    }
}
