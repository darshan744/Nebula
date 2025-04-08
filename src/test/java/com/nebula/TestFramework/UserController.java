package com.nebula.TestFramework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nebula.Http.Constants.ContentType;
import com.nebula.Http.Constants.HttpStatus;
import com.nebula.Http.HttpRequest.Request;
import com.nebula.Http.HttpResponse.HttpResponseBuilder;
import com.nebula.Http.HttpResponse.Response;
import com.nebula.Route.RequestHandler;

public class UserController implements RequestHandler {

    @Override
    public Response handleRequest(Request request){
        UserModel user = new UserModel("Darshan", "DARSHAN744", "darshank.ec22@Bitsathy.ac.in");
        HttpResponseBuilder builder = new HttpResponseBuilder()
                                            .addContentType(ContentType.JSON)
                                            .setStatusCode(HttpStatus.OK)
                                            .addBody(user);
        Response res = builder.build();
        return res;
    }
    
}
