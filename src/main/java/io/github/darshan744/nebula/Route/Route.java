package io.github.darshan744.nebula.Route;

import io.github.darshan744.nebula.Http.Constants.HttpMethod;

/**
 * The route object for each route for managing routing and handlers
 */
public class Route {

    private HttpMethod method;

    private String pathPattern;

    private RequestHandler handler;

    /**
     * get the HttpMethod
     */
    public HttpMethod getMethod() {
        return method;
    }
    /**
     * Set method 
     */
    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    /**
     * returns URL pattern
     */
    public String getPathPattern() {
        return pathPattern;
    }
    /**
     * Set url Pattern
     */
    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }
    
}
