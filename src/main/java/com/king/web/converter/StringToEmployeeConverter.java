package com.king.web.converter;

import com.king.domain.Employee;
import com.king.enums.SexEnum;
import com.king.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.convert.converter.Converter;

public class StringToEmployeeConverter implements Converter<String , Employee>{
    //name-sex-birthday(yyyy/MM/dd)-mobile-email-position-note
    //jack-1-1994/10/04/-18617164782-lost0000@outlook.com-广东深圳-by converter
    @Override
    public Employee convert(String s) {
        if(s.isEmpty())return null;
        if(!s.contains("-"))return null;
        LogManager.getLogger().info("输入字符串为："+s);
        String[] attr=s.split("-");
        for (String s1 : attr) {
            LogManager.getLogger().info("输出字符串为："+s1);
        }

        Employee employee = new Employee();
        employee.setRealName(attr[0]);
        employee.setSex(SexEnum.getSexById(Integer.parseInt(attr[1])));
        employee.setBirthday(DateUtil.transferToDate(attr[2],"yyyy/MM/dd"));
        employee.setMobile(attr[3]);
        employee.setEmail(attr[4]);
        employee.setPosition(attr[5]);
        employee.setNote(attr[6]);
        return employee;
    }
}
