package com.ivangavlik.http.poc5.plugin;

import com.ivangavlik.http.poc5.User;
import com.ivangavlik.http.poc5.reflection.Dependency;
import com.ivangavlik.http.poc5.reflection.Plugin;
import com.ivangavlik.http.poc5.reflection.Start;

import javax.swing.*;

@Plugin(id = "com.ivangavlik.http.poc5.plugin.HttpSendExtended")
public class HttpSendExtended implements HttpSendInf {

    public HttpSendExtended(@Dependency(name = "user") User user) {
        System.out.println("HttpSendExtended constructor param " + user);
    }
    @Start
    public void init() {
        System.out.println("HttpSendExtended start");
    }
    @Override
    public void sendMsg() {
        System.out.println("HttpSendExtended extended");
    }

    @Override
    public JButton getJButton() {
        JButton b = new JButton("Click Here");
        return b;
    }
}
