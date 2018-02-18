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
    private final String login = "maxon1408";
    private final String pass = "maxon1408";
    private final String command = "GET";
    private final int basicBodyLength = 43;
    private final int contentLength = basicBodyLength + login.length() + pass.length();
    private final String url = "loveread.ec";
    private final String charsetName = "windows-1251";
    private final int port = 80;
    private PrintWriter out;
    private BufferedReader in;
    private String titleBegin = "<title>";
    private String titleEnd = "</title>";
    private String textBegin = "<p class=MsoNormal>";
    private String textEnd = "</p>";


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

        socket.close();
        return sb.toString();
    }

    public String parseGet(String response) {
        StringBuilder sb = new StringBuilder();

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
            int startPosiiton = matcherBegin.end();
            int endPosition = matcherEnd.start();

            while (startPosiiton > endPosition) {
                int oldEndPositon = endPosition;
                endPosition = matcherEnd.start();
                if (oldEndPositon == endPosition)
                    break;
            }
            if (startPosiiton < endPosition) {
                sb.append(response.substring(startPosiiton, endPosition));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public Client() {
    }


//    public static void main(String[] args) throws IOException {
//
//
//        switch (command) {
//            case "GET": {
//                out.println("GET /read_book.php?id=7468&p=1 HTTP/1.1\n" +
//                        "Host: loveread.ec\n" +
//                        "Connection: Close\n" +
//                        "\n");
//
//                StringBuilder sb = new StringBuilder();
//
//                int i = 0;
//
//                while (i != -1) {
//                    i = in.read();
//                    sb.append((char) (i));
//                }
//                System.out.println(sb.toString());
//                break;
//            }
//            case "HEAD": {
//                out.println("HEAD /read_book.php?id=7468&p=1 HTTP/1.1\n" +
//                        "Host: loveread.ec\n" +
//                        "Connection: Close\n" +
//                        "\n");
//                StringBuilder sb = new StringBuilder();
//
//                int i = 0;
//
//                while (i != -1) {
//                    i = in.read();
//                    sb.append((char) (i));
//                }
//                System.out.println(sb.toString());
//                break;
//            }
//            case "POST": {
//                out.println("POST /login.php HTTP/1.1\n" +
//                        "Host: loveread.ec\n" +
//                        "Connection: Close\n" +
//                        "Content-Length: " + contentLength + "\n" +
//                        "Content-Type: application/x-www-form-urlencoded\n" +
//                        "\n" +
//                        "login=" + login + "&password=" + pass + "&submit_enter=submit_enter\n"
//                );
//
//                StringBuilder sb = new StringBuilder();
//
//                int i = 0;
//
//                while (i != -1) {
//                    i = in.read();
//                    sb.append((char) (i));
//                }
//
//                String responseText = sb.toString();
//
//                System.out.println(responseText);
//
//
//                Pattern hasCookies = Pattern.compile(cockieString);
//
//                Matcher matcher = hasCookies.matcher(responseText);
//
//                if (matcher.find()) {
//                    System.out.println("Login and pass are correct!");
//                    break;
//                }
//
//
//                Pattern loginErrorPattern = Pattern.compile(loginErrorString);
//
//                matcher = loginErrorPattern.matcher(responseText);
//                if (matcher.find()) {
//                    System.out.println("Login is not correct!");
//                    break;
//                }
//
//
//                Pattern passwordErrorPattern = Pattern.compile(passwordErrorString);
//
//                matcher = passwordErrorPattern.matcher(responseText);
//                if (matcher.find()) {
//                    System.out.println("Password is not correct!");
//                    break;
//                }
//
//                System.out.println("Login or pass is empty!");
//
//                break;
//            }
//        }
//
////        GET
//
////        out.println("GET /read_book.php?id=7468&p=193 HTTP/1.1");
////
////        out.println("Host: loveread.ec");
////
////        out.println("Content-Type: text/html");
////
////        out.println("Accept-Charset: windows-1251");
////
////        out.println("Connection: Close");
////
////        out.println("Accept-Language: ru");
////
////        out.println("");
////
////        StringBuilder sb = new StringBuilder();
////
////        String str = "";
////        while (str != null) {
////            str = in.readLine();
////            sb.append(str);
////            sb.append("\n");
////        }
////
////        System.out.println(sb.toString());
//
//
//        //POST
//
////        out.println("POST http://loveread.ec/login.php HTTP/1.1");
////
////        out.println("Host: loveread.ec");
////
////        out.println("Content-Type: text/html; charset=windows-1251");
////
////        out.println("Referer: http://loveread.ec/index.php");
////
////        out.println("");
////
////        out.println("login=sdfsd&password=gergergerge&submit_enter=%C2%F5%EE%E4");
////
////        out.println("");
//
////        POST works
//
////        out.println("POST /login.php HTTP/1.1\n" +
////                "Host: loveread.ec\n" +
////                "Connection: keep-alive\n" +
////                "Content-Length: 61\n" +
////                "Cache-Control: max-age=0\n" +
////                "Origin: http://loveread.ec\n" +
////                "Upgrade-Insecure-Requests: 1\n" +
////                "Content-Type: application/x-www-form-urlencoded\n" +
////                "User-Agent: Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36\n" +
////                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
////                "Referer: http://loveread.ec/info.php?me=register_end\n" +
////                "Accept-Encoding: gzip, deflate\n" +
////                "Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7,be;q=0.6\n" +
////                "Cookie: __lx147195_load_cnt=12; __lx147195_load_tmr=1518974415331; __lx147195_load_tmr_pre=1518974437715; MarketGidStorage=%7B%220%22%3A%7B%22svspr%22%3A%22%22%2C%22svsds%22%3A12%2C%22TejndEEDj%22%3A%224Q6DZmJJ%22%7D%2C%22C596930%22%3A%7B%22page%22%3A1%2C%22time%22%3A1518958023494%7D%2C%22C596712%22%3A%7B%22page%22%3A2%2C%22time%22%3A1518974437835%7D%7D\n" +
////                "\n" +
////                "login=maxon1408&password=maxon1408&submit_enter=%C2%F5%EE%E4\n"
////
////        );
////
////
////
////
////        StringBuilder sb = new StringBuilder();
////
////        int i = 0;
////
////        while (i != -1) {
////            i = in.read();
////            sb.append((char) (i));
////        }
////
////        System.out.println(sb.toString());
////
////        out.println("GET /profile.php HTTP/1.1\n" +
////                "Host: loveread.ec\n" +
////                "Connection: keep-alive\n" +
////                "Cache-Control: max-age=0\n" +
////                "Upgrade-Insecure-Requests: 1\n" +
////                "User-Agent: Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36\n" +
////                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
////                "Referer: http://loveread.ec/read_book.php?id=7468&p=2\n" +
////                "Accept-Encoding: gzip, deflate\n" +
////                "Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7,be;q=0.6\n" +
////                "Cookie: PHPSESSID=q7q4d22offsr4cg0n55utg8871; _ym_uid=1518975918820029130; _ym_isad=2; lsModified=no; __lx147195_load_cnt=44; __lx147195_load_tmr=1518976748223; __lx147195_load_tmr_pre=1518976769826; MarketGidStorage=%7B%220%22%3A%7B%22svspr%22%3A%22%22%2C%22svsds%22%3A44%2C%22TejndEEDj%22%3A%224Q6DZmJJ%22%7D%2C%22C596930%22%3A%7B%22page%22%3A2%2C%22time%22%3A1518976702397%7D%2C%22C596712%22%3A%7B%22page%22%3A9%2C%22time%22%3A1518976769899%7D%7D\n");
////
////        sb = new StringBuilder();
////
////        i = 0;
////
////        while (i != -1) {
////            i = in.read();
////            sb.append((char) (i));
////        }
////
////        System.out.println(sb.toString());
//
//
////        out.println("GET /read_book.php?id=7468&p=256 HTTP/1.1\n" +
////                "Host: loveread.ec\n" +
////                "Content-Type: text/html; charset=windows-1251\n" +
////                "Connection: Close\n" +
////                "\n");
////
////        StringBuilder sb = new StringBuilder();
////
////        int i = 0;
////
////        while (i != -1) {
////            i = in.read();
////            sb.append((char) (i));
////        }
////        System.out.println(sb.toString());
//
//        socket.close();
//
//    }

}
