package com.github.darshan744.nebula.Route;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;

import com.github.darshan744.nebula.Http.Constants.ContentType;
import com.github.darshan744.nebula.Http.Constants.HttpMethod;
import com.github.darshan744.nebula.Http.Constants.HttpStatus;
import com.github.darshan744.nebula.Http.HttpRequest.Request;
import com.github.darshan744.nebula.Http.HttpRequest.Parser.HttpParser;
import com.github.darshan744.nebula.Http.HttpResponse.HttpResponseBuilder;
import com.github.darshan744.nebula.Http.HttpResponse.Response;
import com.github.darshan744.nebula.Logger.NebulaLogger;
import com.github.darshan744.nebula.Logger.NebulaLoggerFactory;
import com.github.darshan744.nebula.Middleware.Handler.MiddlewareChain;
import com.github.darshan744.nebula.Middleware.Handler.MiddlewareRegistry;
import com.github.darshan744.nebula.Route.Exception.RequestHandlerNotFoundException;
import com.github.darshan744.nebula.Route.Exception.RouteNotFoundException;

public class RequestDispatcher {
    // Router must be singleton since for the whole application
    // we need the regitered routes
    private Router router = Router.getRouter();
    private NebulaLogger logger = NebulaLoggerFactory.getLogger(RequestDispatcher.class);

    private RequestHandler getRequestHandler(HttpMethod method, String route) {
        // {"/users" , Function}
        HashMap<String, RequestHandler> routesForMethod = router.getMethodRoutes(method);
        // incase no such route is declared for execution -> throws
        // RouteNotFoundException
        if (routesForMethod == null || !routesForMethod.containsKey(route)) {
            throw new RouteNotFoundException("No Route is registered for `Route : " + route + "`");
        }
        RequestHandler requestHandler = routesForMethod.get(route);

        return requestHandler;
    }

    private void forwardRequest(Request request , HttpResponseBuilder builder) throws RequestHandlerNotFoundException {
        // functional interface for handling the incoming request
        RequestHandler requestHandler = getRequestHandler(request.getMethod(), request.getUrl());
        if (requestHandler == null) {
            throw new RequestHandlerNotFoundException(
                    "Request Handler Method not found for ROUTE : " + request.getMethod() + " Method : "
                            + request.getUrl());
        }

        requestHandler.handleRequest(request , builder);
    }

    public Response handleRequest(InputStream ioInputStream) {
        // parser for inputStream
        logger.info("Before Parser");
        HttpParser parser = new HttpParser();
        MiddlewareRegistry registry = MiddlewareRegistry.getRegistry();
        Request req = parser.parseHttpRequest(ioInputStream);
        logger.info("After Parser");
        Response res = null;
        MiddlewareChain chain = new MiddlewareChain(registry.getMiddlewares());
        HttpResponseBuilder builder = new HttpResponseBuilder();
        try {
            chain.next(req, builder, chain);
            // forwards to the resultant endpoint handler
            forwardRequest(req , builder);
            logger.info("Response");
            res = builder.build();
            return res;
        } catch (RequestHandlerNotFoundException requestHandlerNotFoundException) {
            logger.severe(requestHandlerNotFoundException.getMessage());
            
            Object errorObject = new Object() {
                public String error = requestHandlerNotFoundException.getErrorCode();
                public String httpMethod = req.getMethod().toString();
                public String route = req.getUrl();
            };
            
            builder = errorBuilder(errorObject);
            res = builder.build();
            
            return res;
        } catch (RouteNotFoundException routeNotFoundException) {
            // The Jackson serializes only the public fields
            // So we the error must be public not default
            logger.severe(routeNotFoundException.getMessage());
            Object errorObject = new Object() {
                public String error = routeNotFoundException.getErrorCode();
                public String httpMethod = req.getMethod().toString();
                public String route = req.getUrl();
                public String date = LocalDateTime.now().toString();
            };
            builder = errorBuilder(errorObject);
            res = builder.build();
            return res;
        }
    }

    private HttpResponseBuilder errorBuilder(Object errorBody) {
        return new HttpResponseBuilder()
        .addContentType(ContentType.TEXT_PLAIN)
        .setStatusCode(HttpStatus.NOT_FOUND)
        .addBody(errorBody);
    }
    

}
