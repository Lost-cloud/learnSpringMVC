package com.king.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.king.enums.SexEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee {

    private long id;
    private String realName;
    private String note;
    private SexEnum sex;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date birthday;

    private String mobile;
    private String email;
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

}
