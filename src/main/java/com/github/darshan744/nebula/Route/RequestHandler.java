package com.nebula.Route;

import com.nebula.Http.HttpRequest.Request;
import com.nebula.Http.HttpResponse.HttpResponseBuilder;

@FunctionalInterface
public interface RequestHandler {
    void handleRequest(Request request , HttpResponseBuilder builder);
}
