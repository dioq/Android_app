/**
 * Copyright 2020 bejson.com
 */
package com.my.retrofit_1;

/**
 * Auto-generated: 2020-10-15 4:53:33
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class User {

    private String name;
    private String password;
    private String argot;
    private long Num;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setArgot(String argot) {
        this.argot = argot;
    }

    public String getArgot() {
        return argot;
    }

    public void setNum(long Num) {
        this.Num = Num;
    }

    public long getNum() {
        return Num;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", argot='" + argot + '\'' +
                ", Num=" + Num +
                '}';
    }
}