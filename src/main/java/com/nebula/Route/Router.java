package com.nebula.Route;

import java.util.HashMap;

import com.nebula.Http.Constants.*;
public class Router {
    private static Router router = null;
    /*
     * Routing table for managing incoming routes
     * This stores With HTTP Method as a Key and their routes and Handler as value
     * EX : {GET : "/users" , ()->new HttpResponse()}
     */
    private HashMap<HttpMethod , HashMap<String , RequestHandler>> routes = new HashMap<>();
    
    public void registerRoute(HttpMethod method , String route , RequestHandler requestHandler) {
       HashMap<String , RequestHandler> map = routes.getOrDefault(requestHandler, new HashMap<>());
       map.put(route , requestHandler);
       routes.put(method, map);
    }

    public HashMap<String , RequestHandler> getMethodRoutes(HttpMethod method) {
        return routes.get(method);
    }

    private Router() {}

    public static Router getRouter() {
        if(router == null) router = new Router();
        return router;
    }


}
