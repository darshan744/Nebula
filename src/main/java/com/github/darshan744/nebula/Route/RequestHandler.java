package com.github.darshan744.nebula.Route;

import com.github.darshan744.nebula.Http.HttpRequest.Request;
import com.github.darshan744.nebula.Http.HttpResponse.HttpResponseBuilder;

@FunctionalInterface
public interface RequestHandler {
    void handleRequest(Request request , HttpResponseBuilder builder);
}
