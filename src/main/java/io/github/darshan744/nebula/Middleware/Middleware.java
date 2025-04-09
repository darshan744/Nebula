package io.github.darshan744.nebula.Middleware;

import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Middleware.Handler.MiddlewareChain;

public interface Middleware {
    void middlewareHandler(Request req , Response res , MiddlewareChain next);
}
