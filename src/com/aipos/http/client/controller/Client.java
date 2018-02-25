package com.aipos.http.client.controller;

import com.aipos.http.client.entity.HttpRequest;
import com.aipos.http.client.entity.HttpResponse;
import com.aipos.http.client.exception.EmptyHostException;
import com.aipos.http.client.parser.ResponseParser;
import com.aipos.http.client.parser.impl.ResponseParserImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author maksim.stelmachonak
 */
public class Client {
    private final String charsetName = "windows-1251";
    private final int port = 80;
    private PrintWriter out;
    private BufferedReader in;
    private ResponseParser responseParser = new ResponseParserImpl();

    public HttpResponse executeRequest(HttpRequest request) throws IOException, EmptyHostException {
        if (request.getHeader("Host") == null){
            throw new EmptyHostException("Host must not be empty!");
        }
        InetAddress addres = InetAddress.getByName(request.getHeader("Host"));

        Socket socket = new Socket(addres, port);
        boolean autoflush = true;
        out = new PrintWriter(socket.getOutputStream(), autoflush);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), charsetName));

        System.out.println(request);
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
}
