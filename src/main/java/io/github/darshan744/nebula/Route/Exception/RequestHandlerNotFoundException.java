package io.github.darshan744.nebula.Route.Exception;

public class RequestHandlerNotFoundException extends RuntimeException {

    private String errorCode;
    public RequestHandlerNotFoundException(String message) {super(message);errorCode = "NO_HANDLER_FOUND";}
    public String getErrorCode() {
        return errorCode;
    }
    
}
