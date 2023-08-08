package com.ivangavlik.http.poc5.plugin;

import com.ivangavlik.http.poc5.User;
import com.ivangavlik.http.poc5.reflection.DependencyFactory;
import com.ivangavlik.http.poc5.reflection.DependencyInstance;

@DependencyFactory
public class Dependency {

    @DependencyInstance()
    public String testIt() {
        return "test";
    }

    @DependencyInstance()
    public User user() {
        return new User("test user");
    }
}
