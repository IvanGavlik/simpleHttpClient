package com.ivangavlik.http.microkernel.plugin;

import com.ivangavlik.http.microkernel.reflection.Dependency;
import com.ivangavlik.http.microkernel.reflection.Plugin;
import com.ivangavlik.http.microkernel.reflection.Start;
import com.ivangavlik.http.microkernel.User;

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
