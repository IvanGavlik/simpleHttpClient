package com.ivangavlik.http.basic;


import com.ivangavlik.http.microkernel.reflection.ExtensionPoint;
import com.ivangavlik.http.microkernel.reflection.HandleAction;
import com.ivangavlik.http.microkernel.reflection.Plugin;
import com.ivangavlik.http.microkernel.reflection.Start;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Plugin(id= "com.ivangavlik.http.basic.HttpRequestPlugin")
public class HttpRequestPlugin {
    private HttpClient client;
    @ExtensionPoint
    List<HttpSendRequest> sendRequestExtensions;

    @Start
    public void sendRequest() {
        client = HttpClient.newHttpClient();
    }

    @HandleAction()
    public void handleSend(Object payload) {
        System.out.println("hadling action");
        sendRequestExtensions.forEach(el -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost/"))
                        .header("headerName", payload.toString())
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }


    /*


     */


}
