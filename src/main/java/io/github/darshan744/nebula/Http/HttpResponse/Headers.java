package io.github.darshan744.nebula.Http.HttpResponse;


/**
 * This is a Enum containing most commonly used Headers
 */
public enum Headers {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    CONNECTION("Connection"),
    AUTHORIZATION("Authorization"),
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
    SERVER("Server"),
    DATE("Date");
    private final String header;
    Headers(String header) {
        this.header = header;
    }

    public String getHeader() {return this.header;}
}
