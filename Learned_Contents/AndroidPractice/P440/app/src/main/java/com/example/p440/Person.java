package com.example.p440;

public class Person {
    String name;
    String birthday;
    String phone;

    public Person() {
    }

    public Person(String name, String birthday, String phone) {
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
