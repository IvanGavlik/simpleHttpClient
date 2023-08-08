package com.ivangavlik.http.poc5.plugin;

import com.ivangavlik.http.poc5.reflection.Dependency;
import com.ivangavlik.http.poc5.reflection.Plugin;
import com.ivangavlik.http.poc5.reflection.ExtensionPoint;
import com.ivangavlik.http.poc5.reflection.Start;

import javax.swing.*;
import java.util.List;

@Plugin(id= "com.ivangavlik.http.poc5.plugin.HttpSend")
public class HttpSend extends JFrame {
    @ExtensionPoint()
    List<HttpSendInf> inf;

    // what if I have more than one constructor
    public HttpSend(@Dependency(name = "testIt") String testIt) {
        System.out.println("HttpSend constructor have param " + testIt);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Start
    public void test() {
        System.out.println("HttpSend start ");

        inf.forEach(el -> getContentPane().add(el.getJButton()));

        pack();
        setVisible(true);
    }
}

