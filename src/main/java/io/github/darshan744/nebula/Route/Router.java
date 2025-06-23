package io.github.darshan744.nebula.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.darshan744.nebula.Http.Constants.HttpMethod;

/**
 * Router
 * 
 * @description This is the router Class that manages and handles
 *              the endpoint registration with the method corressponding to it
 */
public class Router {

    private static Router router = null;
    /*
     * Routing table for managing incoming routes
     * This stores With HTTP Method as a Key and their routes and Handler as value
     * EX : {GET : [RouteDefinition]}
     */
    private HashMap<HttpMethod, List<RouteDefinition>> routes = new HashMap<>();

    public void registerRoute(HttpMethod method, String path, RequestHandler requestHandler) {
        List<RouteDefinition> route = routes.getOrDefault(method, new ArrayList<>());
        route.add(new RouteDefinition(method, path, requestHandler));
        routes.put(method, route);
    }

    public List<RouteDefinition> getMethodRoutes(HttpMethod method) {
        return routes.get(method);
    }

    private Router() {
    }

    /**
     * Router constructor for singleton class
     */
    public static synchronized Router getRouter() {
        if (router == null)
            router = new Router();
        return router;
    }
}
