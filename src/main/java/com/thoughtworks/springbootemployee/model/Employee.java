package com.thoughtworks.springbootemployee.model;

public class Employee {
    private int id;
    private String gender;
    private String name;
    private int age;
    private int salary;

    public Employee(int id, String gender, String name, int age, int salary) {
        this.id = id;
        this.gender = gender;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Employee() {
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
