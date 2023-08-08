package com.ivangavlik.http.basic;

import com.ivangavlik.http.microkernel.Microkernel;
import com.ivangavlik.http.microkernel.reflection.Plugin;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Plugin(id = "com.ivangavlik.http.basic.HttpGetRequestSwingPlugin")
public class HttpGetRequestSwingPlugin  extends JFrame implements HttpSendRequest {

    public HttpGetRequestSwingPlugin() {
        JButton sendRequest = new JButton("send it ");
        sendRequest.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Microkernel.ACTION.emit(new Object());
            }
        });
        getContentPane().add(sendRequest);
        pack();
        setVisible(true);
    }
    @Override
    public Header send() {
        return new Header("testName", "testValue");
    }
}
