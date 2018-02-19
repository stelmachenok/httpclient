package com.aipos.http.client.entity;

/**
 * Created by y50-70 on 19.02.2018.
 */
public class HttpResponse {
    private int code;
    private String message;
    private String header;

    public HttpResponse() {
    }

    public HttpResponse(int code, String message, String header) {
        this.code = code;
        this.message = message;
        this.header = header;
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
