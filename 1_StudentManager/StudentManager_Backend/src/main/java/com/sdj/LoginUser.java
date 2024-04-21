package com.sdj;

public class LoginUser {
    private String id;
    private String pwd;
    private String name;
    private String course;
// 必须要存在无参数构造函数
    public LoginUser() {

    }
// 必须存在有参数的构造函数 
    public LoginUser(String id, String pwd, String name, String course) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
