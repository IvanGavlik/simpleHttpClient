package com.ivangavlik.http;

import com.ivangavlik.http.microkernel.Microkernel;

/**
 * Kernel
 * - basic functionality - send and recive HTTP msg, extract header params and body
 * - can run on its onw and it is configurable
 *
 * Internal server
 * - handle specific msg like POST, PUT, DELETE, OPTIONS
 *
 * External server
 * - SSL/TLS support
 * - authentication support
 * - custom headers
 * - timeouts
 * - proxy support
 * - redirecting
 * - cookie
 * - caching
 * - gzip support ++
 * - video support (specific html protocol) ++
 * ++ for plugin candidate
 *
 * Plugins
 * - also user is abel to create own plugins (implement interface or import jar to be decided)
 * - for stat might use interfaces
 * - todo handle communication and plugin execition order
 *
 * Adapter
 * - something that client uses
 *
 * Based on how HttpClient api works Kernel will call
 *  client.send(request, paramsOther)
 *  where request ( HttpClient.Builder) is going to be build using internal and external services
 *  (order might not be important)
 *  for request should I used wrapper class
 *
 *
 *  One more idea OSGi
 *  - https://hackernoon.com/what-is-osgi-an-intro-to-the-open-service-gateway-initiative?ref=hackernoon.com
 *
 */
public class App {
    public static void main(String[] args) {
        /*
        example
        final String[] dependencyClasses = {"com.ivangavlik.http.microkernel.plugin.Dependency"};
        final String[] pluginClasses = {"com.ivangavlik.http.microkernel.plugin.HttpSend",
                "com.ivangavlik.http.microkernel.plugin.HttpSendExtended"};
         */

        final String[] dependencyClasses = {};
        final String[] pluginClasses = {"com.ivangavlik.http.basic.HttpRequestPlugin", "com.ivangavlik.http.basic.HttpGetRequestSwingPlugin"};
        new Microkernel(dependencyClasses, pluginClasses);
        System.out.println("App end");
    }

    /*   public static void main(String[] args) {

        Collection collection;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost/"))
                .build();

        HttpResponse<String> response =
                null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.body());
    }*/
}


