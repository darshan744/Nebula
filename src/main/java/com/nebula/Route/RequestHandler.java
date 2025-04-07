package com.nebula.Route;

import com.nebula.Http.HttpRequest.Request;

@FunctionalInterface
public interface RequestHandler {
    void handleRequest(Request request);
}
