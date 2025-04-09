package io.github.darshan744.nebula.Route;

import java.util.HashMap;

import io.github.darshan744.nebula.Http.Constants.HttpMethod;
public class Router {

    private static Router router = null;
    /*
     * Routing table for managing incoming routes
     * This stores With HTTP Method as a Key and their routes and Handler as value
     * EX : {GET : "/users" , ()->new HttpResponse()}
     */
    private HashMap<HttpMethod, HashMap<String , RequestHandler>> routes = new HashMap<>();
    
    public void registerRoute(HttpMethod method , String route , RequestHandler requestHandler) {
       HashMap<String , RequestHandler> map = routes.getOrDefault(requestHandler, new HashMap<>());
       map.put(route , requestHandler);
       routes.put(method, map);
    }

    public HashMap<String , RequestHandler> getMethodRoutes(HttpMethod method) {
        return routes.get(method);
    }

    private Router() {}

    public static synchronized Router getRouter() {
        if(router == null) router = new Router();
        return router;
    }
 // TODO Parsing Request param and path variable
 
 

}
