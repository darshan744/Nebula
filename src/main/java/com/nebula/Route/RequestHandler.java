package com.nebula.Route;

import com.nebula.Http.HttpRequest.Request;
import com.nebula.Http.HttpResponse.Response;

@FunctionalInterface
public interface RequestHandler {
    Response handleRequest(Request request);
}
