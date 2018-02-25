package com.aipos.http.client.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author maksim.stelmachonak
 */
public class HttpResponse {
    private int code;
    private String message;
    private Map<String, String> headers = new HashMap<>();
    private String htmlCode;

    public HttpResponse() {
    }

    public HttpResponse(int code, String message,  Map<String, String> headers, String htmlCode) {
        this.code = code;
        this.message = message;
        this.headers = headers;
        this.htmlCode = htmlCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addHeader(String key, String value){
        this.headers.put(key, value);
    }

    public void removeHeader(String key){
        this.headers.remove(key);
    }

    public String getHeader(String key){
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHtmlCode() {
        return htmlCode;
    }

    public void setHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ").append(code).append(" ").append(message).append("\n");
        Set<String> set = headers.keySet();
        set.forEach((k)->sb.append(k).append(": ").append(headers.get(k)).append("\n"));
        sb.append(htmlCode).append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
