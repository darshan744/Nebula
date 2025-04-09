package com.github.darshan744.TestApplication;

import io.github.darshan744.nebula.Http.Constants.HttpStatus;
import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Route.RequestHandler;

public class UserController implements RequestHandler {

    @Override
    public void handleRequest(Request request, Response builder) {
        request.getBody(UserModel.class);
        builder.addBody(new Object(){
            public String message = "Inserted Successfully";
        });
        builder.setStatusCode(HttpStatus.OK);
    }
    
}
