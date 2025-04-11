package com.github.darshan744.TestApplication;

import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Route.RequestHandler;

public class GetUser implements RequestHandler {

    @Override
    public void handleRequest(Request request, Response builder) {
        System.out.println(request.getQueryParam("id"));
        System.out.println(request.getQueryParam("name"));
    }

}
