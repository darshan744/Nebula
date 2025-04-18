package io.github.darshan744.nebula.Route;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpRequest.Exceptions.HttpParserException;
import io.github.darshan744.nebula.Http.HttpRequest.Parser.HttpParser;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;
import io.github.darshan744.nebula.Middleware.Handler.MiddlewareChain;
import io.github.darshan744.nebula.Middleware.Handler.MiddlewareRegistry;
import io.github.darshan744.nebula.Route.Exception.RouteNotFoundException;

/**
 * This class handles the incoming Request 
 * It parses the stream finds the route sends it to it
 * If no route found error Response is sent
 */
public class RequestDispatcher {
    // Router must be singleton since for the whole application
    // we need the regitered routes
    private Router router = Router.getRouter();
    private NebulaLogger logger = NebulaLoggerFactory.getLogger(RequestDispatcher.class);
    private RouteMatcher matcher = new RouteMatcher();

    private void setParamsToRequest(Request request , RouteDefinition def) {
        HashMap<String,String> params = RouteMatcher.extractParams(request.getUrl(),def.getPath());
        HashMap<String,String> queryParams = RouteMatcher.extractQueryParams(request.getUrl());
        request.setParams(params);
        request.setQueryParams(queryParams);
    }

    private RouteDefinition pathResolver(Request req) throws RouteNotFoundException {
        List<RouteDefinition> routes = router.getMethodRoutes(req.getMethod());
        for (RouteDefinition route : routes) {
            boolean matched = RouteMatcher.match(req.getUrl(), route.getPath());
            if (matched) {
                return route;
            }
        }
        throw new RouteNotFoundException("No Such route is Found URL : " + req.getUrl());
    }

    /**
     * Takes the incoming stream parses it passes through middlewares passed to
     * handler
     * Gets serialized and then written with outputstream
     * @param ioInputStream the input stream of the http 
     */
    public Response handleRequest(InputStream ioInputStream) {
        // parser for inputStream
        HttpParser parser = new HttpParser();
        
        MiddlewareRegistry registry = MiddlewareRegistry.getRegistry();
        
        try {
            Request req = parser.parseHttpRequest(ioInputStream);
            
            Response res = new Response();
            
            MiddlewareChain chain = new MiddlewareChain(registry.getMiddlewares());
            try {
               RouteDefinition matchedRoute = pathResolver(req);

                chain.next(req, res, chain);

                setParamsToRequest(req, matchedRoute);
                
                matchedRoute.call(req, res);

                return res;
            } catch (RouteNotFoundException routeNotFoundException) {
                // global handler
                registry.handleException(routeNotFoundException, req, res);
                return res;
            }
        }catch(HttpParserException httpParserException) {
            return new Response().serverError().json(httpParserException);
        }
        
    }
}
