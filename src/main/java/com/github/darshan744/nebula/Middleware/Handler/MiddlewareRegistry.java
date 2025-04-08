package com.github.darshan744.nebula.Middleware.Handler;

import java.util.ArrayList;
import java.util.List;

import com.github.darshan744.nebula.Middleware.Middleware;

public class MiddlewareRegistry {

    private List<Middleware> middlewares = new ArrayList<>();

    private static MiddlewareRegistry registry = null;

    private MiddlewareRegistry(){}

    public static synchronized MiddlewareRegistry getRegistry() {
        if(registry == null) {
            registry = new MiddlewareRegistry();
        }
        return registry;
    }
    public void registerMiddleware(Middleware middleware) {middlewares.add(middleware);}
    public List<Middleware> getMiddlewares(){return middlewares;}
}
