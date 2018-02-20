package com.aipos.http.client.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by y50-70 on 19.02.2018.
 */
public class HttpRequest {
    private CommandType command;
    private String uri;
    private Map<String, String> headers = new HashMap<>();
    private String body;

    public HttpRequest() {
    }

  public HttpRequest(CommandType command, String uri, Map<String, String> headers) {
    this.command = command;
    this.uri = uri;
    this.headers = headers;
  }

  public HttpRequest(CommandType command, String uri, Map<String, String> headers, String body) {
        this.command = command;
        this.uri = uri;
        this.headers = headers;
        this.body = body;
    }

    public CommandType getCommand() {
        return command;
    }

    public void setCommand(CommandType command) {
        this.command = command;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(command.getName()).append(" ").append(uri).append(" HTTP/1.1\n");
        Set<String> set = headers.keySet();
        set.forEach((k)->sb.append(k).append(": ").append(headers.get(k)).append("\n"));
        sb.append("\n\n");
        if (body != null && !body.equals("")){
            sb.append(body).append("\n\n");
        }
        return sb.toString();
    }
}
