package com.aipos.http.client.controller;

import com.aipos.http.client.entity.HttpRequest;
import com.aipos.http.client.entity.HttpResponse;
import com.aipos.http.client.parser.impl.ResponseParserImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by y50-70 on 06.02.2018.
 */
public class Client {

    private final int basicBodyLength = 43;
    private final String url = "loveread.ec";
    private final String charsetName = "windows-1251";
    private final int port = 80;
    private PrintWriter out;
    private BufferedReader in;
    private ResponseParserImpl responseParser = new ResponseParserImpl();

    public HttpResponse executeRequest(HttpRequest request) throws IOException {
        InetAddress addres = InetAddress.getByName(url);

        Socket socket = new Socket(addres, port);
        boolean autoflush = true;
        out = new PrintWriter(socket.getOutputStream(), autoflush);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), charsetName));

        out.println(request.toString());

        HttpResponse response = responseParser.stringToResponse(getResponse());

        socket.close();
        return response;
    }

    private String getResponse() {
        StringBuilder sb = new StringBuilder();

        int i = 0;

        while (i != -1) {
            try {
                i = in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append((char) (i));
        }

        return sb.toString();
    }

    public String requestGet(String header, String uri) {
        return "GET " + uri + " HTTP/1.1\n" +
                header +
                "\n";
    }

    public String requestHead(String header, String uri) {
        return "HEAD " + uri + " HTTP/1.1\n" +
                header +
                "\n\n";
    }

    public String requestPost(String header, String uri, String body) {
        int contentLength = body.length();
        return "POST " + uri + " HTTP/1.1\n" +
                header +
                "\nContent-Length: " + contentLength +
                "\n\n" +
                body +
                "\n\n";
    }
}
