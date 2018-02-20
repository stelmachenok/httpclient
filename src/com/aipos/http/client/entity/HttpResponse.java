package com.aipos.http.client.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by y50-70 on 19.02.2018.
 */
public class HttpResponse {
    private int code;
    private String message;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse() {
    }

    public HttpResponse(int code, String message,  Map<String, String> headers) {
        this.code = code;
        this.message = message;
        this.headers = headers;
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

    public void setHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ").append(code).append(" ").append(message).append("\n");
        Set<String> set = headers.keySet();
        set.forEach((k)->sb.append(k).append(": ").append(headers.get(k)).append("\n"));
        sb.append("\n\n");
        return sb.toString();
    }
}
