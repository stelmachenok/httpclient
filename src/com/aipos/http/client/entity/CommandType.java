package com.aipos.http.client.entity;

/**
 * @author maksim.stelmachonak
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
