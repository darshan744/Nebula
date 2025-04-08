package com.github.darshan744.nebula.Middleware;

import com.github.darshan744.nebula.Http.HttpRequest.Request;
import com.github.darshan744.nebula.Http.HttpResponse.HttpResponseBuilder;
import com.github.darshan744.nebula.Middleware.Handler.MiddlewareChain;

public interface Middleware {
    void middlewareHandler(Request req , HttpResponseBuilder resBuilder , MiddlewareChain next);
}
