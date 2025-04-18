package io.github.darshan744.nebula.Middleware;

import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;

@FunctionalInterface
public interface ExceptionHandler {
    void handleException(Exception e , Request req , Response res);
}
