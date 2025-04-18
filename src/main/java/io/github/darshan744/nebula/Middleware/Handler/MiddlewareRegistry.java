package io.github.darshan744.nebula.Middleware.Handler;

import java.util.ArrayList;
import java.util.List;

import io.github.darshan744.nebula.Http.HttpRequest.Request;
import io.github.darshan744.nebula.Http.HttpResponse.Response;
import io.github.darshan744.nebula.Middleware.ExceptionHandler;
import io.github.darshan744.nebula.Middleware.Middleware;

public class MiddlewareRegistry {

    private List<Middleware> middlewares = new ArrayList<>();

    private static MiddlewareRegistry registry = null;
    private ExceptionHandler handler = null;
    private MiddlewareRegistry(){
        setGlobalExceptionHandler(new DefaultExceptionHandler());
    }

    public static synchronized MiddlewareRegistry getRegistry() {
        if(registry == null) {
            registry = new MiddlewareRegistry();
        }
        return registry;
    }
    /**
     * register your custom middleware
     * @param middleware functional interface for middleware
     */
    public void registerMiddleware(Middleware middleware) {middlewares.add(middleware);}
    /**
     * @return List of middlewares registered
     */
    public List<Middleware> getMiddlewares(){return middlewares;}

    public void setGlobalExceptionHandler(ExceptionHandler handler) {
        this.handler = handler;
    }
    public void handleException(Exception e , Request req , Response res){
        this.handler.handleException(e, req, res);
    }
}
