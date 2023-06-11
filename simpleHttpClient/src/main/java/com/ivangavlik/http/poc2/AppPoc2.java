package com.ivangavlik.http.poc2;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class AppPoc2 {
    public static void main(String[] args) {
        final Adapter a = new Adapter("basic Config maybe");
        a.sslConection("");
    }
}

class Microkernel {
    List<InternalService> internalServiceList = new ArrayList<>();
    public Microkernel() {
        start(null);
    }

    private void start(HttpRequest.Builder builder) {
     internalServiceList.forEach(el -> el.exe());


        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response =
                null;
        try {
            response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    // its basic functionality is to send and receive http msg
    // should I do this at socket level - I think so ! because we have more control,
    // but what about http 1.2 and 1.3. where encoding is needed
}

interface InternalService {
    public void exe();
}

class ExternalService {

    Microkernel microkernel;
    public ExternalService(String cofigOrSometing, Microkernel microkernel) {
        this.microkernel = microkernel;
    }
    public void exe() {
// TODO        microkernel.
    }
}

class Adapter {

    List<String> externalServices = new ArrayList<>();
    Microkernel microkernel;

    public Adapter(String basicConfig) {
        this.microkernel = new Microkernel();
        externalServices.forEach(el -> new ExternalService(el, this.microkernel));
    }
    public void sslConection(final String configuration) {
        // find extranal serive and do the job
        // think how to handle access to service should be list or something else
        this.externalServices.stream()
                .map(el -> new ExternalService(configuration, this.microkernel))
                .findFirst().get().exe();
    }

}
