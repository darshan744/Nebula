package com.nebula.Route;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;

import com.nebula.Http.Constants.ContentType;
import com.nebula.Http.Constants.HttpMethod;
import com.nebula.Http.Constants.HttpStatus;
import com.nebula.Http.HttpRequest.Request;
import com.nebula.Http.HttpRequest.Parser.HttpParser;
import com.nebula.Http.HttpResponse.HttpResponseBuilder;
import com.nebula.Http.HttpResponse.Response;
import com.nebula.Logger.NebulaLogger;
import com.nebula.Logger.NebulaLoggerFactory;
import com.nebula.Route.Exception.RequestHandlerNotFoundException;
import com.nebula.Route.Exception.RouteNotFoundException;

public class RequestDispatcher {

    private Router router = Router.getRouter();
    private NebulaLogger logger = NebulaLoggerFactory.getLogger(RequestDispatcher.class);
    private RequestHandler getRequestHandler(HttpMethod method, String route) {
        //{"/users" , Function}
        HashMap<String, RequestHandler> routesForMethod = router.getMethodRoutes(method);
        if(!routesForMethod.containsKey(route)) {
            logger.severe("No such route exist");
            throw new RouteNotFoundException("No Route is registered for `Route : " + route + "`");
        }
        RequestHandler requestHandler = routesForMethod.get(route);
        return requestHandler;
    }

    private Response forwardRequest(Request request)
            throws RequestHandlerNotFoundException {
        RequestHandler requestHandler = getRequestHandler(request.getMethod(), request.getUrl());
        if (requestHandler == null) {
            logger.severe("No Handler exist");
            throw new RequestHandlerNotFoundException(
                    "Request Handler Method not found for ROUTE : " + request.getMethod() + " Method : " + request.getUrl());
        }
       return requestHandler.handleRequest(request);
    }

    public Response handleRequest(InputStream ioInputStream) {
         HttpParser parser = new HttpParser();
         Request req = parser.parseHttpRequest(ioInputStream);
         logger.info("Forwarding Request to Route : " + req.getUrl() + " Method : " + req.getMethod());
         Response res = null;
         try {
            res = forwardRequest(req);
            return res;
         } catch (RequestHandlerNotFoundException requestHandlerNotFoundException) {
            Object errorObject = new Object(){
                public String error = requestHandlerNotFoundException.getErrorCode();
                public String httpMethod = req.getMethod().toString();
                public String route = req.getUrl();
             };
            HttpResponseBuilder builder = new HttpResponseBuilder()
                                               .addContentType(ContentType.TEXT_PLAIN)
                                               .setStatusCode(HttpStatus.NOT_FOUND)
                                               .addBody(errorObject);
            res = builder.build();
            return res;
         }
         catch(RouteNotFoundException routeNotFoundException) {
            // The Jackson serializes only the public fields
            // So we the error must be public not default 
            
            Object errorObject = new Object(){
               public String error = routeNotFoundException.getErrorCode();
               public String httpMethod = req.getMethod().toString();
               public String route = req.getUrl();
               public String date = LocalDateTime.now().toString();
             };
            HttpResponseBuilder builder = new HttpResponseBuilder()
                                               .addContentType(ContentType.TEXT_PLAIN)
                                               .setStatusCode(HttpStatus.NOT_FOUND)
                                               .addBody(errorObject);
            res = builder.build();
            return res;
         }
    }

}
