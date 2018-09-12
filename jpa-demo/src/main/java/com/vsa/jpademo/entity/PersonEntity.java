package com.vsa.jpademo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "person")
public class PersonEntity implements Serializable{


    @Id
    @GeneratedValue
    public long id;
    private String name;
    private int age;

    public PersonEntity(){}

    public PersonEntity(String name,int age){
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
