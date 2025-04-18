package io.github.darshan744.nebula.Route.Exception;
/**
 * Thrown when the recieved request route is not registered
 */
public class RouteNotFoundException extends RuntimeException{
    private String errorCode;
    public RouteNotFoundException(String message) {super(message);errorCode = "NO_ROUTE_FOUND";}
    public String getErrorCode() {
        return errorCode;
    }
}
