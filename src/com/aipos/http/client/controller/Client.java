package com.aipos.http.client.controller;

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

    public String executeGet(String bookId, String page) throws IOException {
        InetAddress addres = InetAddress.getByName(url);

        Socket socket = new Socket(addres, port);
        boolean autoflush = true;
        out = new PrintWriter(socket.getOutputStream(), autoflush);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), charsetName));

        out.println(requestGet(bookId, page));

        String response = getResponse();

        socket.close();
        return response;
    }
    
    public String executeHead(String bookId, String page) throws IOException {
        InetAddress addres = InetAddress.getByName(url);

        Socket socket = new Socket(addres, port);
        boolean autoflush = true;
        out = new PrintWriter(socket.getOutputStream(), autoflush);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), charsetName));

        out.println(requestHead(bookId, page));

        String response = getResponse();

        socket.close();
        return response;
    }

    public String executePost(String login, String pass) throws IOException {
      InetAddress addres = InetAddress.getByName(url);

      Socket socket = new Socket(addres, port);
      boolean autoflush = true;
      out = new PrintWriter(socket.getOutputStream(), autoflush);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream(), charsetName));

      out.println(requestPost(login, pass));

      String response = getResponse();

      socket.close();
      return response;

    }

    private String getResponse(){
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

    public String requestGet(String bookId, String page){
      return "GET /read_book.php?id=" + bookId + "&p=" + page + " HTTP/1.1\n" +
          "Host: " + url + "\n" +
          "Connection: close\n" +
          "\n";
    }

    public String requestHead(String bookId, String page){
      return "HEAD /read_book.php?id=" + bookId + "&p=" + page + " HTTP/1.1\n" +
          "Host: " + url + "\n" +
          "Connection: close\n" +
          "\n";
    }

    public String requestPost(String login, String pass){
      int contentLength = basicBodyLength + login.length() + pass.length();
      return "POST /login.php HTTP/1.1\n" +
          "Host: loveread.ec\n" +
          "Connection: Close\n" +
          "Content-Length: " + contentLength + "\n" +
          "Content-Type: application/x-www-form-urlencoded\n" +
          "\n" +
          "login=" + login + "&password=" + pass + "&submit_enter=submit_enter\n";
    }
}
