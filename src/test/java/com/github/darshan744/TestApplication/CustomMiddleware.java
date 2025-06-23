package com.github.darshan744.TestApplication;

import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Middleware.core.Middleware;
import io.github.darshan744.nebula.Middleware.core.NextFn;

public class CustomMiddleware implements Middleware {

    @Override
    public void handle(Request req, Response res, NextFn next) {
        System.out.println("HI");
        next.next();
    }

}
