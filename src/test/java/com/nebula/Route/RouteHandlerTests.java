package com.nebula.Route;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.nebula.Http.Constants.ContentType;
import com.nebula.Http.Constants.HttpMethod;
import com.nebula.Http.Constants.HttpVersion;
import com.nebula.Http.HttpRequest.Request;
import com.nebula.Http.HttpResponse.Headers;
import com.nebula.Http.HttpResponse.HttpResponseBuilder;
import com.nebula.Http.HttpResponse.Response;

public class RouteHandlerTests {

    private static Router router = null;
    private static RouteHandlerUtils routeHandlerUtils = null;

    @BeforeAll
    public static void beforeAll() {
        router = Router.getRouter();
        routeHandlerUtils = new RouteHandlerUtils();
    }

    @Test
    public void testRouteRegister() {
        //Request Object for GET method
        Request req = routeHandlerUtils.requestGeneraterWithMethodAsGet();
        //creates a new Handler object
        RouteHandlerImpl handler = new RouteHandlerImpl();
        // register that handler
        router.registerRoute(req.getMethod(), "/users", handler);
        //Request Object for POST Method
        Request req2 = routeHandlerUtils.requestGeneraterWithMethodAsPost();
        //creates another handler for post method
        RouteHandlerImpl2 handler2 = new RouteHandlerImpl2();
        //register the new handler
        router.registerRoute(req2.getMethod(), "/users", handler2);
        //check whether the GET handlers are registered
        HashMap<String ,RequestHandler> routes = router.getMethodRoutes(HttpMethod.GET);
        //checking whether inserted
        assertNotNull(routes);
        //if present call that get the URL's handler
        RequestHandler handler3 = routes.get("/users");
        // assert handler
        assertNotNull(handler3);
        handler3.handleRequest(req);
        

        //routes for second request OBject
        HashMap<String ,RequestHandler> routes2 = router.getMethodRoutes(HttpMethod.POST);
        //asserting the routes 
        assertNotNull(routes2);
        // getting the handler
        RequestHandler handler4 = routes.get("/users");
        // checking handler found or not
        assertNotNull(handler4);
        //handle the request
        handler4.handleRequest(req2);
    }
}

class RouteHandlerUtils {

    public Request requestGeneraterWithMethodAsGet() {
        Request request = new Request();

        request.setHttpVersion(HttpVersion.V1);
        request.setMethod(HttpMethod.GET);
        request.setUrl("/users");
        request.putInHeaders(Headers.CONTENT_TYPE.getHeader(), ContentType.JSON.getContentType());
        

        return request;
    }

    public Request requestGeneraterWithMethodAsPost() {
        Request request = new Request();

        request.setHttpVersion(HttpVersion.V1);
        request.setMethod(HttpMethod.POST);
        request.setUrl("/users");
        request.putInHeaders(Headers.CONTENT_TYPE.getHeader(), ContentType.JSON.getContentType());

        return request;
    }

}


//Handler object for Request testing
class RouteHandlerImpl implements RequestHandler {

    @Override
    public Response handleRequest(Request request) {
        
        System.out.println("Route Handler called with route " + request.getUrl() + " Method : " + request.getMethod());
        HttpResponseBuilder builder = new HttpResponseBuilder();
        return builder.build();
    }
    
}

class RouteHandlerImpl2 implements RequestHandler {

    @Override
    public Response handleRequest(Request request) {
        System.out.println("Route Handler called with route " + request.getUrl() + " Method : " + request.getMethod());
        HttpResponseBuilder builder = new HttpResponseBuilder();
        return builder.build();
    }
    
}