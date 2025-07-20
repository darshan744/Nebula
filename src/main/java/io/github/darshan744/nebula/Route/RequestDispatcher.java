package io.github.darshan744.nebula.Route;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpRequest.Parser.HttpParser;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Logger.NebulaLogger;
import io.github.darshan744.nebula.Logger.NebulaLoggerFactory;
import io.github.darshan744.nebula.Middleware.core.MiddlewareRegistry;
import io.github.darshan744.nebula.Middleware.core.Middleware;

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

    private void setParamsToRequest(Request request, RouteDefinition def) {
        HashMap<String, String> params = RouteMatcher.extractParams(request.getUrl(), def.getPath());
        HashMap<String, String> queryParams = RouteMatcher.extractQueryParams(request.getUrl());
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
     *
     * @param ioInputStream
     * @returns the response to be sent
     */
    public Response handleRequest(InputStream ioInputStream) {
        // parser for inputStream
        HttpParser parser = new HttpParser();

        MiddlewareRegistry registry = MiddlewareRegistry.getRegistry();

        List<Middleware> middlewares = registry.getMiddlewares();
        Request req = parser.parseHttpRequest(ioInputStream);

        Response res = new Response();
        try {
            // get the matched route
            RouteDefinition matchedRoute = pathResolver(req);
            // set the params after parsing it
            setParamsToRequest(req, matchedRoute);
            // run the middlewares registered
            handleRequest(req, res, middlewares, 0, matchedRoute);
            // run the registered method for the api endpoint
            matchedRoute.call(req, res);
            // return the constructed response
            System.out.println(req.getUrl());
            return res;
        } catch (RouteNotFoundException routeNotFoundException) {
            // The Jackson serializes only the public fields
            // So we the error must be public not default
            logger.severe(routeNotFoundException.getMessage());
            Object errorObject = new Object() {
                public String error = routeNotFoundException.getErrorCode();
                public String description = routeNotFoundException.getMessage();
                public String httpMethod = req.getMethod().toString();
                public String route = req.getUrl();
                public String date = LocalDateTime.now().toString();
            };
            res = res.serverError().json(errorObject);
            return res;
        }
    }

    /**
     * @param middles           List of middleware functions to execute
     * @param currentMiddleware Tracks the current middleware to execute
     */
    public void handleRequest(Request req, Response res, List<Middleware> middles, int currentMiddleware,
            RouteDefinition route) {
        // if we get to the last middleware we execute the registered
        // request handler function
        // incase if the middleware doesn't call the next()
        // means that the request must be stopped
        if (currentMiddleware >= middles.size()) {
            route.call(req, res);
            return;
        }
        Middleware curr = middles.get(currentMiddleware);
        // using next function we are able to stop the request flow in btw
        // by not calling the next() function
        // every modfications are done to the parameter itself
        curr.handle(req, res, () -> {
            handleRequest(req, res, middles, currentMiddleware + 1, route);
        });
    }

}
