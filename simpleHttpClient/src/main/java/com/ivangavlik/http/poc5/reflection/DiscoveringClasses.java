package com.ivangavlik.http.poc5.reflection;

import com.ivangavlik.http.poc5.plugin.HttpSend;

import java.lang.reflect.Modifier;

public class DiscoveringClasses {

    public static void main(String[] args) throws Exception {
        Class<?> test = HttpSend.class;
        args.getClass();

        // can be from configuration
        String className = "java.awt.Color";
        Class<?> c = Class.forName(className);

        System.out.println("name " + c.getName());
        System.out.println("name simple " + c.getSimpleName());
        System.out.println("show Hierarchy");
        showHierarchy(c);
        System.out.println("is public " + Modifier.isPublic(c.getModifiers()));
    }

    static void showHierarchy(Class<?> c) {
        if (c.getSuperclass() == null) {
            System.out.println(c.getName());
            return;
        }
        showHierarchy(c.getSuperclass());
        System.out.println(c.getName());
    }
}
