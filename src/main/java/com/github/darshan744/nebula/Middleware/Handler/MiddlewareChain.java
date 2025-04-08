package com.github.darshan744.nebula.Middleware.Handler;

import java.util.ArrayList;
import java.util.List;

import com.github.darshan744.nebula.Http.HttpRequest.Request;
import com.github.darshan744.nebula.Http.HttpResponse.HttpResponseBuilder;
import com.github.darshan744.nebula.Middleware.Middleware;

public class MiddlewareChain {
    
    List<Middleware> middlewares = new ArrayList<>();
    int currentMiddleware = 0;
    public MiddlewareChain(List<Middleware> middlewares) {
        this.middlewares = middlewares;
    }

    public void next(Request req , HttpResponseBuilder builder , MiddlewareChain chain) {
        if(currentMiddleware < middlewares.size()) {
            Middleware middleware = middlewares.get(currentMiddleware++);
            middleware.middlewareHandler(req, builder, this);
        }
    }

}
