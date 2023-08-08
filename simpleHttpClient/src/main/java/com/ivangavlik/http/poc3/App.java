package com.ivangavlik.http.poc3;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Yes for plug in play architecture
 * but I must go witch sockets not http client
 */
public class App {
    public static void main(String[] args) throws Exception {
        // https://docs.netscaler.com/en-us/citrix-adc/current-release/appexpert/http-callout/http-request-response-notes-format.html
        basicHttp();
    }
    private static void basicHttp() {

        String getRequest = "GET / HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        //  String hostname = "whois.internic.net";
        //  int port = 43;

        String hostname = "localhost";
        int port = 80;
        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(getRequest);

            InputStream input = socket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }


    };

    /**
     * MK
     *  - manage resource (socket)
     *  - process communication (external services)
     *
     *  External services run in separate thread process
     *      -> SSL/TLS
     *      -> Authentication
     *      -> timeouts
     *      -> error handling
     *      -> cookies
     *      -> caching
     *      -> zip /gzip support
     *
     * Internal services
     *  - GET, POST, PUT, DELETE, OPTIONS
     *
     *  Adapters
     */
}

class RecordBuileder {
    class Record {
        Map<String, String> list = new HashMap<>();
    }
    Record record = new Record();

    RecordBuileder addHeaderParm(String key, String body) {
        record.list.put(key, body);
        return this;
    }
}
class InternalServerGet {
    public static String getRequest() {
        String getRequest = "GET / HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        return getRequest;
    }
}

class Mk {
    public static String requestService(String id) {
        String hostname = "localhost";
        int port = 80;
        StringBuilder stringBuilder = new StringBuilder();
        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(getRequest(id));

            InputStream input = socket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
        return stringBuilder.toString();
    }

    private static String getRequest(final String id) {
        if ("GET".equals(id)) {
            return InternalServerGet.getRequest();
        }
        return null;
    }
}

class ESSequrity {

}

class Adapter {
    Mk mk;

    ESSequrity sequrity;
}
