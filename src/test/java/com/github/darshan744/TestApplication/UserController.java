package com.github.darshan744.TestApplication;

import io.github.darshan744.nebula.Http.Constants.HttpStatus;
import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Route.RequestHandler;

public class UserController implements RequestHandler {

    @Override
    public void handleRequest(Request request, Response response) {
        request.getBody(UserModel.class);
        System.out.println(request.getParam("id"));
        response.setStatusCode(HttpStatus.OK);
    }
    
}
