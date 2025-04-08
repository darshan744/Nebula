package com.github.darshan744.nebula.Middleware.core;

import java.util.HashMap;

import com.github.darshan744.nebula.Http.HttpRequest.Request;
import com.github.darshan744.nebula.Http.HttpResponse.HttpResponseBuilder;
import com.github.darshan744.nebula.Middleware.Middleware;
import com.github.darshan744.nebula.Middleware.Handler.MiddlewareChain;
import com.github.darshan744.nebula.Route.RequestHandler;
import com.github.darshan744.nebula.Route.Router;
import com.github.darshan744.nebula.Route.Exception.RouteNotFoundException;

public class RouteResolverMiddleware implements Middleware{

    private Router router = Router.getRouter();
    
    @Override
    public void middlewareHandler(Request req, HttpResponseBuilder resBuilder, MiddlewareChain next) {
        HashMap<String , RequestHandler> routes = router.getMethodRoutes(req.getMethod());
        if(!routes.containsKey(req.getUrl())){
            throw new RouteNotFoundException("No Route is registered for Route : " + req.getUrl());
        }
        next.next(req, resBuilder, next);
    }
    
}
