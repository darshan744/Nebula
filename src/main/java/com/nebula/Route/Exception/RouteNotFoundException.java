package com.nebula.Route.Exception;

public class RouteNotFoundException extends RuntimeException{
    private String errorCode;
    public RouteNotFoundException(String message) {super(message);errorCode = "NO_ROUTE_FOUND";}
    public String getErrorCode() {
        return errorCode;
    }
    
}
