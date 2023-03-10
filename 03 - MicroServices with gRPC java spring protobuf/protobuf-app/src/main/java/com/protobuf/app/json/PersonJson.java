package com.protobuf.app.json;


public class PersonJson {
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

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonJson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
