package com.king.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.king.enums.SexEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee implements Serializable{

    private long id;

    @NotNull
    @Size(min = 2,max = 20)
    private String realName;

    @NotNull
    private String note;

    @NotNull(message ="{sex.valid}")
    private SexEnum sex;

    //输出json格式
    @JsonFormat(pattern = "yyyy年MM月dd")
    //设置日期转换格式
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //验证
    @Past
    @NotNull
    private Date birthday;

    @NotNull
    @Size(min = 8,max = 11,message = "{mobile.valid}")
    private String mobile;

    @NotNull
    private String email;

    @NotNull
    private String position;


    public Employee(String real_name,String note){
        this.realName=real_name;
        this.note=note;
    }

    public Employee(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "the id is "+id+"\n"
                +"name : " +realName
                +"\ngender : "+sex
                + "\nbirthday : "+new SimpleDateFormat("yyyy年MM月dd日").format(birthday)
                +"\nmobile : "+ mobile
                +"\nemail : "+email
                +"\nposition : " +position
                +"\nnote : "+note+"\n";
    }

    @Override
    public int hashCode() {
        return this.hashCode()*31;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId()>=((Employee)obj).getId();
    }
}
