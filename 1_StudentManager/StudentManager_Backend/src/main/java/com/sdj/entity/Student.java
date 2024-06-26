package com.sdj.entity;

public class Student {
  private String studentId;

    private String name;
    private String gender;
    private int age;

    public Student() {
    }
    public Student(String studentId, String name, String gender, int age) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
