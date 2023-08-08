package com.ivangavlik.http.poc5;

public class User {
    private String name;

    public User(String n) {
        this.name = n;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
