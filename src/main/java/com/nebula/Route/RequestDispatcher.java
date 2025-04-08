package com.nebula.Route;

import java.io.InputStream;
import java.util.HashMap;

import com.nebula.Http.Constants.HttpMethod;
import com.nebula.Http.HttpRequest.Request;
import com.nebula.Http.HttpRequest.Parser.HttpParser;
import com.nebula.Http.HttpResponse.Response;
import com.nebula.Route.Exception.RequestHandlerNotFoundException;
import com.nebula.Route.Exception.RouteNotFoundException;

public class RequestDispatcher {

    private Router router = Router.getRouter();

    private RequestHandler getRequestHandler(HttpMethod method, String route) {
        //{"/users" , Function}
        HashMap<String, RequestHandler> routesForMethod = router.getMethodRoutes(method);
        if(!routesForMethod.containsKey(route)) {
            throw new RouteNotFoundException("No Route is registered for `Route : " + route + "`");
        }
        RequestHandler requestHandler = routesForMethod.get(route);
        return requestHandler;
    }

    private Response forwardRequest(Request request)
            throws RequestHandlerNotFoundException {
        RequestHandler requestHandler = getRequestHandler(request.getMethod(), request.getUrl());
        if (requestHandler == null) {
            throw new RequestHandlerNotFoundException(
                    "Request Handler Method not found for ROUTE : " + request.getMethod() + " Method : " + request.getUrl());
        }
       return requestHandler.handleRequest(request);
    }

    public Response handleRequest(InputStream ioInputStream) {
         HttpParser parser = new HttpParser();
         Request req = parser.parseHttpRequest(ioInputStream);
         return forwardRequest(req);
    }

}
