package com.king.config;

import org.springframework.context.annotation.*;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.util.regex.Pattern;

@Configuration
@ImportResource("classpath:applicationContext.xml")
@ComponentScan(basePackages = "com.king",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM,value =RootConfig.WebPackage.class)
        })
public class RootConfig {
    public static class WebPackage extends RegexPatternTypeFilter {
        public WebPackage() {
            super(Pattern.compile("com\\.king\\.web"));
        }
    }

}


