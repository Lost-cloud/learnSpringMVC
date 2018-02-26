package com.king.web.converter;

import com.king.enums.SexEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Loggers;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

public class EmployeeConverter implements Converter<String,SexEnum>{
    private Logger logger= LogManager.getLogger();
    @Override
    public SexEnum convert(String s) {
        logger.info("输出"+s);
        if(s.equals("男")|s.equalsIgnoreCase("male")){
            return SexEnum.MALE;
        }else if(s.equals("女")|s.equalsIgnoreCase("female")){
            return SexEnum.FEMALE;
        }
        return null;
    }
}
