package com.github.darshan744.TestApplication;

import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Middleware.Middleware;
import io.github.darshan744.nebula.Middleware.Handler.MiddlewareChain;

public class CustomMiddleware implements Middleware{

    @Override
    public void middlewareHandler(Request req, Response res, MiddlewareChain next) {
        System.out.println("HI");
        next.next(req, res, next);
    }

}
