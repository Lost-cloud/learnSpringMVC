package com.king.enums;

public enum SexEnum {
    MALE(1,"男"),
    FEMALE(0,"女");

    private int id;

    private String sex;

    public int getId() { return id; }

    public String getSex() { return sex; }

    SexEnum(int id, String sex) {
        this.id=id;
        this.sex=sex;
    }

    public static SexEnum getSexById(int id){
        for (SexEnum sex:SexEnum.values()){
            if(sex.id==id)return sex;
        }
        return null;
    }
}
