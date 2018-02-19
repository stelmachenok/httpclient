package com.aipos.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by y50-70 on 06.02.2018.
 */
public class Client {
    private final String loginErrorString = "login_error";
    private final String passwordErrorString = "password_error";
    private final String cockieString = "Set-Cookie:";
    private final int basicBodyLength = 43;
    private final String url = "loveread.ec";
    private final String charsetName = "windows-1251";
    private final int port = 80;
    private PrintWriter out;
    private BufferedReader in;
    private final String titleBegin = "<title>";
    private final String titleEnd = "</title>";
    private final String textBegin = "<p class=MsoNormal>";
    private final String textEnd = "</p>";
    private final String responseCode = "HTTP/1\\.1\\W\\d{3}";
    private final int responseCodeLength = 3;
    private final String code301 = "301";
    private final String code301Message = "Moved Permanently";
    private final String code404 = "404";
    private final String code404Message = "Not Found";
    private final String code200 = "200";
    private final String code200Message = "OK";
    private final String loginErrorMessage = "Incorrect login";
    private final String passwordErrorMessage = "Incorrect password";
    private final String emptyFieldMessage = "Login or password is empty";
    private final String loginSuccessMessage = "Login successfully";


    public String executeGet(String bookId, String page) throws IOException {
        InetAddress addres = InetAddress.getByName(url);

        Socket socket = new Socket(addres, port);
        boolean autoflush = true;
        out = new PrintWriter(socket.getOutputStream(), autoflush);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), charsetName));

        out.println("GET /read_book.php?id=" + bookId + "&p=" + page + " HTTP/1.1\n" +
                "Host: " + url + "\n" +
                "Connection: close\n" +
                "\n");

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

        out.println("HEAD /read_book.php?id=" + bookId + "&p=" + page + " HTTP/1.1\n" +
            "Host: " + url + "\n" +
            "Connection: close\n" +
            "\n");

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
      int contentLength = basicBodyLength + login.length() + pass.length();

      out.println("POST /login.php HTTP/1.1\n" +
                        "Host: loveread.ec\n" +
                        "Connection: Close\n" +
                        "Content-Length: " + contentLength + "\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "\n" +
                        "login=" + login + "&password=" + pass + "&submit_enter=submit_enter\n"
                );

      String response = getResponse();

      socket.close();
      return response;

    }

    public String parseGet(String response) {
        StringBuilder sb = new StringBuilder();

        Pattern pattern = Pattern.compile(this.responseCode);

        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
          sb.append(response.substring(matcher.end() - responseCodeLength, matcher.end()));
        }

        String responseCode = sb.toString();

        String answer = "";

        if (responseCode.equals(code301)) {
          answer = code301Message;
        }

        if (responseCode.equals(code404)) {
          answer = code404Message;
        }

        if (!answer.equals("")) {
          return answer;
        }
        sb = new StringBuilder();

        Pattern patternBegin = Pattern.compile(this.titleBegin);
        Pattern patternEnd = Pattern.compile(this.titleEnd);

        Matcher matcherBegin = patternBegin.matcher(response);
        Matcher matcherEnd = patternEnd.matcher(response);

        if (matcherBegin.find() && matcherEnd.find()) {
            sb.append(response.substring(matcherBegin.end(), matcherEnd.start()));
            sb.append("\n");
        }

        patternBegin = Pattern.compile(this.textBegin);
        patternEnd = Pattern.compile(this.textEnd);

        matcherBegin = patternBegin.matcher(response);
        matcherEnd = patternEnd.matcher(response);

        while (matcherBegin.find() && matcherEnd.find()) {
            int startPosition = matcherBegin.end();
            int endPosition = matcherEnd.start();

            while (startPosition > endPosition) {
                int oldEndPosition = endPosition;
                endPosition = matcherEnd.start();
                if (oldEndPosition == endPosition)
                    break;
            }
            if (startPosition < endPosition) {
                sb.append(response.substring(startPosition, endPosition));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public String parseHead(String response){
        StringBuilder sb = new StringBuilder();

        Pattern pattern = Pattern.compile(this.responseCode);

        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            sb.append(response.substring(matcher.end() - responseCodeLength, matcher.end()));
        }

        String responseCode = sb.toString();

        String answer = "";

        if (responseCode.equals(code301)) {
          answer = code301Message;
        }

        if (responseCode.equals(code404)) {
          answer = code404Message;
        }

        if (responseCode.equals(code200)) {
          answer = code200Message;
        }
        return answer;
    }

    public String parsePost(String response){
        StringBuilder sb = new StringBuilder();

        String answer = emptyFieldMessage;

        Pattern pattern = Pattern.compile(this.loginErrorString);
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
          answer = loginErrorMessage;
        }

        pattern = Pattern.compile(this.passwordErrorString);
        matcher = pattern.matcher(response);
        if (matcher.find()) {
          answer = passwordErrorMessage;
        }

        pattern = Pattern.compile(this.cockieString);
        matcher = pattern.matcher(response);
        if (matcher.find()) {
          answer = loginSuccessMessage;
        }
        return answer;
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

}
