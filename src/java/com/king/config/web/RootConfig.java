package com.king.config.web;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = "com.king.config",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value= EnableWebMvc.class)
        })
public class RootConfig {
}


