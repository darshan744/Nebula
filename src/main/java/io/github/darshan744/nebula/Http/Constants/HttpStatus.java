package io.github.darshan744.nebula.Http.Constants;

/**
 * HTTP status code and their message enums
 * This has the status codes of most commonly used status codes
 */
public enum HttpStatus {
    OK(200 , "OK"),

    SERVER_ERROR(500 , "SERVER ERROR"),

    UNAUTHORIZED(401 , "UNAUTHORIZED"),

    METHOD_NOT_ALLOWED(401 , "METHOD NOT ALLOWED"),

    NOT_FOUND(404, "NOT FOUND");
    
    private final int statusCode;
    private final String message;
    
    HttpStatus(int code, String message) {
        this.statusCode = code;
        this.message = message;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return getStatusCode() + " " + getMessage();
    }
}
