package io.github.darshan744.nebula.Route;

import io.github.darshan744.nebula.Http.Constants.HttpMethod;
import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;

/**
 * The route object for each route for managing routing and handlers
 */
public class RouteDefinition {

    private HttpMethod method;

    private String path;

    private RequestHandler handler;

    public RouteDefinition() {}
    public RouteDefinition(HttpMethod method, String path, RequestHandler handler) {
        this.method = method;
        this.path = path;
        this.handler = handler;
    }
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
    public String getPath() {
        return path;
    }
    /**
     * Set url Pattern
     */
    public void setPath(String pathPattern) {
        this.path = pathPattern;
    }
    public void call(Request req , Response res){ 
        this.handler.handleRequest(req, res);
    }
    
}
