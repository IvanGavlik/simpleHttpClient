package com.ivangavlik.http.microkernel.plugin;

import com.ivangavlik.http.microkernel.reflection.DependencyInstance;
import com.ivangavlik.http.microkernel.User;
import com.ivangavlik.http.microkernel.reflection.DependencyFactory;

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
