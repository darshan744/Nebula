package com.github.darshan744.nebula.Http.Constants;

public enum ContentType {

    JSON("application/json"),
    TEXT_PLAIN("text/plain"),
    HTML("text/html");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        
        return this.contentType;
    }
}
