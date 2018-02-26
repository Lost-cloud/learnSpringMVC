package com.king.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    private static final String PATTERN = "yyyy年MM月dd日 HH:mm:ss";
    private static final String SQL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public static String getCurrentTime(){
        return new SimpleDateFormat(SQL_PATTERN).format(new Date());
    }

    public static String getCurrentDateTime(){
      return   LocalDateTime.now().format(FORMATTER);
    }

        //在数据库中时间与控制台打印时间相差八小时，是因为数据库url每次连接时都把数据库时区设置为UTC，本地使用CST（china standard time）==UTC+8:00
    //应该把url的serverTimeZone属性serverTimeZone=Shanghai
    public static Date buildDate(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return  Date.from(LocalDateTime.of(localDate, LocalTime.MIN).atZone(ZoneId.of("Asia/Shanghai")).toInstant());
    }

    public static Date buildDateByCalendar(int year, int month, int day) {
        return new GregorianCalendar(year  ,month-1,day+1).getTime();
    }

    public static Date transferToDate(String date) {
        LocalDate localDate=LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Date.from(LocalDateTime.of(localDate, LocalTime.MIN).atZone(ZoneId.of("Asia/Shanghai")).toInstant());
    }

    public static Date transferToDate(String date,String pattern) {
        LocalDate localDate=LocalDate.parse(date,DateTimeFormatter.ofPattern(pattern));
        return Date.from(LocalDateTime.of(localDate, LocalTime.MIN).atZone(ZoneId.of("Asia/Shanghai")).toInstant());
    }

}
