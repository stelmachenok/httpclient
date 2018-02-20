package com.aipos.http.client.entity;

/**
 * Created by y50-70 on 19.02.2018.
 */
public class HttpRequest {
    private CommandType command;
    private String uri;
    private String header;
    private String body;

    public HttpRequest() {
    }

  public HttpRequest(CommandType command, String uri, String header) {
    this.command = command;
    this.uri = uri;
    this.header = header;
  }

  public HttpRequest(CommandType command, String uri, String header, String body) {
        this.command = command;
        this.uri = uri;
        this.header = header;
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return command.getName() + " " + uri + " HTTP/1.1\n" + header + "\n\n" +
            ((body == null || body.equals("")) ? "" : (body + "\n\n"));
    }
}
