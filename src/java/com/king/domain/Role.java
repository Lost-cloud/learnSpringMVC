package com.king.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value="role")
public class Role {

    @Value("1")
    private Long id;

    @Value("King")
    private String name;

    @Value("I am the king of north")
    private String note;

    public Role(Long id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;
    }

    public Role(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return id+" "+name+" : "+note;
    }
}
