package com.nebula.Route;

import java.util.HashMap;

import com.nebula.Http.Constants.HttpMethod;
import com.nebula.Http.HttpRequest.Request;
import com.nebula.Route.Exception.RequestHandlerNotFoundException;
import com.nebula.Route.Exception.RouteNotFoundException;

public class RouteHandler {

    private Router router = Router.getRouter();

    private RequestHandler getRequestHandler(HttpMethod method, String route) {
        //{"/users" , Function}
        HashMap<String, RequestHandler> routesForMethod = router.getMethodRoutes(method);
        if(!routesForMethod.containsKey(route)) {
            throw new RouteNotFoundException("No such route is registered Route : " + route);
        }
        RequestHandler requestHandler = routesForMethod.get(route);
        return requestHandler;
    }

    public void forwardRequest(Request request, HttpMethod method, String route)
            throws RequestHandlerNotFoundException {
        RequestHandler requestHandler = getRequestHandler(method, route);
        if (requestHandler == null) {
            throw new RequestHandlerNotFoundException(
                    "Request Handler Method not found for ROUTE : " + route + " Method : " + method);
        }
        requestHandler.handleRequest(request);
    }

}
