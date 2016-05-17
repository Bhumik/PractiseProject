package com.bhumik.practiseproject.intent.bean;

/**
 * Created by bhumik on 13/5/16.
 */

import java.io.Serializable;

public class Person implements Serializable {
    private static final Long serialVersionUID = -7060210544600464481L;
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int Age) {
        this.age = Age;
    }

}
