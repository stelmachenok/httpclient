package com.aipos.http.client.entity;

/**
 * Created by y50-70 on 19.02.2018.
 */
public enum CommandType {
    GET("GET"),HEAD("HEAD"),POST("POST");

    private String name;

    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
